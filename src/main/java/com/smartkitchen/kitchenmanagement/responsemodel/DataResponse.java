package com.smartkitchen.kitchenmanagement.responsemodel;

import com.smartkitchen.kitchenmanagement.model.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponse {
    private String message;
    private boolean status;
    private Inventory data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Inventory getData() {
		return data;
	}
	public void setData(Inventory data) {
		this.data = data;
	}
    
    
}
