package com.smartkitchen.kitchenmanagement.model;


import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
public class Inventory {
    
	@Id
	private String id;
	private String itemName;
	private double quantity;
	private String unit;
	private String category;
	private String addedBy;
	private Date createdAt;
	private Date updateAt;
	private Date expiryDate;
	
	public Inventory() {
		
	}
	
	public Inventory(String id, String itemName, double quantity, String unit, String category, String addedBy,
			Date createdAt, Date updateAt, Date expiryDate) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.quantity = quantity;
		this.unit = unit;
		this.category = category;
		this.addedBy = addedBy;
		this.createdAt = createdAt;
		this.updateAt = updateAt;
		this.expiryDate = expiryDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date d) {
		this.createdAt = d;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
	
}
