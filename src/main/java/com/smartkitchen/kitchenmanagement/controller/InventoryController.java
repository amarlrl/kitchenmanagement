package com.smartkitchen.kitchenmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		response.setStatus(true);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<DataResponse> update(@PathVariable String id, @RequestBody InventoryRequest request) {
		Inventory inv = inventoryService.updateInventory(id, request);
		DataResponse response = new DataResponse();
		response.setData(inv);
		response.setMessage("Inventory Updated Successfully");
		response.setStatus(true);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<DataResponse> get(@PathVariable String id){
		Inventory inv = inventoryService.getInventory(id);
		DataResponse res = new DataResponse();
		res.setData(inv);
		res.setStatus(true);
		res.setMessage("Inventory has been fetched successfully");
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/getall")
	public ResponseEntity<DataResponse> getAll(){
		DataResponse res = new DataResponse();
		res.setData(inventoryService.getAllInventories());
		res.setStatus(true);
		res.setMessage("Inventories have been fetched successfully");
		return ResponseEntity.ok(res);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<DataResponse> delete(@PathVariable String id) {
		Inventory inv = inventoryService.deleteInventory(id);
		DataResponse response = new DataResponse();
		response.setData(inv);
		response.setMessage("Inventory Deleted Successfully");
		response.setStatus(true);
		return ResponseEntity.ok(response);
	}
	
}
