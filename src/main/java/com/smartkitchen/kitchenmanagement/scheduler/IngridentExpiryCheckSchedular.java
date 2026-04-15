package com.smartkitchen.kitchenmanagement.scheduler;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smartkitchen.kitchenmanagement.model.Inventory;
import com.smartkitchen.kitchenmanagement.model.User;
import com.smartkitchen.kitchenmanagement.repository.UserRepository;
import com.smartkitchen.kitchenmanagement.service.EmailService;
import com.smartkitchen.kitchenmanagement.service.InventoryService;

@Component
public class IngridentExpiryCheckSchedular {

	@Autowired
	InventoryService invservice;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	EmailService emailservice;
	
	@Scheduled(fixedRate = 120000)
	public void checkExpiry() {
		
		try {
		List<Inventory> items = invservice.getExpiryInventory();	
		if(items.size() > 0) {
			List<User> users = userRepository.findAll();
			if (!users.isEmpty()) {
				String body = buildEmailBody(items);
				for (User user : users) {
					if (user.getEmail() != null && !user.getEmail().isBlank()) {
						
						String subject = "Inventory expiry Alert!";
						emailservice.sendEmail(user.getEmail(), subject, body);
					}
				}
			}
		}
		}
		catch (Exception e) {
		throw new RuntimeException("Error occurred while checking the expiry: " + e.getMessage());
		}
		
	}

	private String buildEmailBody(List<Inventory> items) {
		StringBuilder sb = new StringBuilder();
		sb.append("The following inventory items are expiring today:\n\n");
		DateTimeFormatter df = DateTimeFormatter.ISO_LOCAL_DATE;
		for (Inventory item : items) {
			sb.append("- ");
			sb.append(item.getItemName() == null ? "" : item.getItemName());
			sb.append(" | qty: ").append(item.getQuantity()).append(" ").append(item.getUnit() == null ? "" : item.getUnit());
			sb.append(" | category: ").append(item.getCategory() == null ? "" : item.getCategory());
			if (item.getExpiryDate() != null) {
				sb.append(" | expiry: ").append(df.format(item.getExpiryDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
			}
			sb.append("\n");
		}
		sb.append("\nPlease use/remove these items before they expire.\n");
		return sb.toString();
	}
}
