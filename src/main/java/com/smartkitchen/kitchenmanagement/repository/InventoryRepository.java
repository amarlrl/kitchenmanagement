package com.smartkitchen.kitchenmanagement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartkitchen.kitchenmanagement.model.Inventory;


public interface InventoryRepository extends MongoRepository<Inventory,String>{

}
