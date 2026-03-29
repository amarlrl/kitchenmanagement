package com.smartkitchen.kitchenmanagement.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.smartkitchen.kitchenmanagement.model.Inventory;
import com.smartkitchen.kitchenmanagement.repository.InventoryRepository;
import com.smartkitchen.kitchenmanagement.requestmodel.InventoryRequest;

@Service
public class InventoryService {
	
	@Autowired
	InventoryRepository repo;

    public Inventory createInventory(InventoryRequest request) {
        try {
            Inventory in = new Inventory();
            
            in.setItemName(request.getItemName());
            in.setCategory(request.getCategory());
            in.setExpiryDate(request.getExpiryDate());
            in.setAddedBy(request.getAddedBy());
            in.setQuantity(request.getQuantity());
            in.setUnit(request.getUnit());
            in.setCreatedAt(new Date());

            return repo.save(in);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while creating inventory: " + e.getMessage());
        }
    }
}
