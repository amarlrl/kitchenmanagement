package com.smartkitchen.kitchenmanagement.service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartkitchen.kitchenmanagement.model.Inventory;
import com.smartkitchen.kitchenmanagement.repository.InventoryRepository;

@Service
public class RecipeSuggestionService {

    private static final String GEMINI_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private InventoryRepository inventoryRepository;

    @Value("${gemini.api.key:${GEMINI_API_KEY:}}")
    private String geminiApiKey;

    public Map<String, Object> suggestRecipes(List<String> inventoryIds, int maxRecipes) {
        if (inventoryIds == null || inventoryIds.isEmpty()) {
            throw new RuntimeException("inventoryIds is required");
        }
        if (maxRecipes <= 0) {
            maxRecipes = 5;
        }

        List<Inventory> inventories = inventoryRepository.findAllById(inventoryIds);
        if (inventories.isEmpty()) {
            throw new RuntimeException("No inventories found for provided ids");
        }

        if (geminiApiKey == null || geminiApiKey.isBlank()) {
            throw new RuntimeException("Gemini API key not configured. Set environment variable GEMINI_API_KEY or property gemini.api.key");
        }

        //get the inventory object in string form 
        String inventoryText = inventories.stream()
                .map(this::formatInventoryLine)
                .collect(Collectors.joining("\n"));

        //generating the prompt
        String prompt = """
You are a cooking assistant. Suggest recipes the user can make using the available inventory items.

Rules:
- Use the inventory list as "available ingredients".
- Prefer recipes that maximize usage of available ingredients.
- If additional ingredients are needed, list them under missingIngredients.
- Keep steps concise and UI-friendly.
- Return ONLY valid JSON matching the provided schema.

Inventory:
""" + inventoryText;

        //Google Gemini API expects this structure
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(
                Map.of("parts", List.of(
                        Map.of("text", prompt)
                ))
        ));

        
        Map<String, Object> recipeSchema = new HashMap<>();
        recipeSchema.put("type", "object");
        recipeSchema.put("properties", Map.of(
                "recipes", Map.of(
                        "type", "array",
                        "items", Map.of(
                                "type", "object",
                                "properties", Map.of(
                                        "name", Map.of("type", "string", "description", "Recipe title"),
                                        "description", Map.of("type", "string", "description", "Short 1-2 line description"),
                                        "ingredientsUsed", Map.of("type", "array", "items", Map.of("type", "string")),
                                        "missingIngredients", Map.of("type", "array", "items", Map.of("type", "string")),
                                        "steps", Map.of("type", "array", "items", Map.of("type", "string")),
                                        "estimatedMinutes", Map.of("type", "integer", "description", "Approximate cooking time in minutes"),
                                        "difficulty", Map.of("type", "string", "description", "easy | medium | hard")
                                ),
                                "required", List.of("name", "description", "ingredientsUsed", "missingIngredients", "steps")
                        ),
                        "maxItems", maxRecipes
                )
        ));
        recipeSchema.put("required", List.of("recipes"));

        requestBody.put("generationConfig", Map.of(
                "responseMimeType", "application/json",
                "responseJsonSchema", recipeSchema
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", geminiApiKey);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(GEMINI_ENDPOINT, entity, Map.class);

        Map responseBody = responseEntity.getBody();
        if (responseBody == null) {
            throw new RuntimeException("Empty response from Gemini API");
        }

        Object text = extractCandidateText(responseBody);
        if (text == null) {
            throw new RuntimeException("Gemini response did not contain any text candidates");
        }

        String jsonText = String.valueOf(text).trim();
        try {
            return objectMapper.readValue(jsonText, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini JSON response: " + e.getMessage());
        }
    }

    private String formatInventoryLine(Inventory inv) {
        String expiry = inv.getExpiryDate() == null ? "N/A"
                : DateTimeFormatter.ISO_LOCAL_DATE.format(inv.getExpiryDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        String unit = inv.getUnit() == null ? "" : inv.getUnit();
        String category = inv.getCategory() == null ? "" : inv.getCategory();
        return "- " + nullToEmpty(inv.getItemName()) + " | qty: " + inv.getQuantity() + " " + unit + " | category: " + category + " | expiry: " + expiry;
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    private Object extractCandidateText(Map responseBody) {
        Object candidatesObj = responseBody.get("candidates");
        if (!(candidatesObj instanceof List<?> candidates) || candidates.isEmpty()) {
            return null;
        }
        Object firstCandidateObj = candidates.get(0);
        if (!(firstCandidateObj instanceof Map<?, ?> firstCandidate)) {
            return null;
        }
        Object contentObj = firstCandidate.get("content");
        if (!(contentObj instanceof Map<?, ?> content)) {
            return null;
        }
        Object partsObj = content.get("parts");
        if (!(partsObj instanceof List<?> parts) || parts.isEmpty()) {
            return null;
        }
        Object firstPartObj = parts.get(0);
        if (!(firstPartObj instanceof Map<?, ?> firstPart)) {
            return null;
        }
        return firstPart.get("text");
    }
}
