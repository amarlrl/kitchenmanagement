package com.smartkitchen.kitchenmanagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartkitchen.kitchenmanagement.requestmodel.RecipeSuggestionRequest;
import com.smartkitchen.kitchenmanagement.responsemodel.DataResponse;
import com.smartkitchen.kitchenmanagement.service.RecipeSuggestionService;

@RestController
@RequestMapping("/recipes")
public class RecipeSuggestionController {

    @Autowired
    private RecipeSuggestionService recipeSuggestionService;

    @PostMapping("/suggest")
    public ResponseEntity<DataResponse> suggest(@RequestBody RecipeSuggestionRequest request) {
        int maxRecipes = request.getMaxRecipes() == null ? 5 : request.getMaxRecipes();
        Map<String, Object> suggestions = recipeSuggestionService.suggestRecipes(request.getInventoryIds(), maxRecipes);

        DataResponse res = new DataResponse();
        res.setData(suggestions);
        res.setStatus(true);
        res.setMessage("Recipe suggestions generated successfully");
        return ResponseEntity.ok(res);
    }
}
