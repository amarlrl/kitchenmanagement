package com.smartkitchen.kitchenmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartkitchen.kitchenmanagement.model.Inventory;
import com.smartkitchen.kitchenmanagement.requestmodel.InventoryRequest;
import com.smartkitchen.kitchenmanagement.responsemodel.DataResponse;
import com.smartkitchen.kitchenmanagement.service.InventoryService;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
    InventoryService inventoryService;
	
	@PostMapping("/create")
	public ResponseEntity<DataResponse> create(@RequestBody InventoryRequest request) {
		Inventory inv = inventoryService.createInventory(request);
		DataResponse response = new DataResponse();
		response.setData(inv);
		response.setMessage("Inventory Created Successfully");
		return ResponseEntity.ok(response);
	}
	
}
