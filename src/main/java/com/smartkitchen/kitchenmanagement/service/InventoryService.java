package com.smartkitchen.kitchenmanagement.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Inventory updateInventory(String id, InventoryRequest request) {
        try {
            Optional<Inventory> inventoryOpt = repo.findById(id);
            if (inventoryOpt.isEmpty()) {
                throw new RuntimeException("Inventory not found with id: " + id);
            }

            Inventory inventory = inventoryOpt.get();
            inventory.setItemName(request.getItemName());
            inventory.setCategory(request.getCategory());
            inventory.setExpiryDate(request.getExpiryDate());
            inventory.setAddedBy(request.getAddedBy());
            inventory.setQuantity(request.getQuantity());
            inventory.setUnit(request.getUnit());
            inventory.setUpdateAt(new Date());

            return repo.save(inventory);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while updating inventory: " + e.getMessage());
        }
    }
    
    public Inventory getInventory(String id) {
    	
    	try {
    		Optional<Inventory> inventoryOpt = repo.findById(id);
            if (inventoryOpt.isEmpty()) {
                throw new RuntimeException("Inventory not found with id: " + id);
            }
            return inventoryOpt.get();	
    	}
    	catch (Exception e) {
    		throw new RuntimeException("Error occurred while getting inventory: " + e.getMessage());
    	}
    }

    public Inventory deleteInventory(String id) {
        try {
            Optional<Inventory> inventoryOpt = repo.findById(id);
            if (inventoryOpt.isEmpty()) {
                throw new RuntimeException("Inventory not found with id: " + id);
            }

            Inventory inventory = inventoryOpt.get();
            repo.delete(inventory);
            return inventory;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting inventory: " + e.getMessage());
        }
    }

    public List<Inventory> getAllInventories() {
        try {
            return repo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while getting inventories: " + e.getMessage());
        }
    }
}
