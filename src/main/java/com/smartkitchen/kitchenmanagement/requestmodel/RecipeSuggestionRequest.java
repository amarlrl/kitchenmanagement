package com.smartkitchen.kitchenmanagement.requestmodel;

import java.util.List;

public class RecipeSuggestionRequest {
    private List<String> inventoryIds;
    private Integer maxRecipes;

    public RecipeSuggestionRequest() {
    }

    public RecipeSuggestionRequest(List<String> inventoryIds, Integer maxRecipes) {
        this.inventoryIds = inventoryIds;
        this.maxRecipes = maxRecipes;
    }

    public List<String> getInventoryIds() {
        return inventoryIds;
    }

    public void setInventoryIds(List<String> inventoryIds) {
        this.inventoryIds = inventoryIds;
    }

    public Integer getMaxRecipes() {
        return maxRecipes;
    }

    public void setMaxRecipes(Integer maxRecipes) {
        this.maxRecipes = maxRecipes;
    }
}
