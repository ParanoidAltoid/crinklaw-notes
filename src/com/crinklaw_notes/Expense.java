package com.crinklaw_notes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class Expense {

	private String date;
	private String category;
	private String description;
	private String amountSpent;
	private String currency;

	public Expense(String date, String category, String description,
			String amountSpent, String currency) {
		
		this.setDate(date);
		this.setCategory(category);
		this.setDescription(description);
		this.setAmountSpent(amountSpent);
		this.setCurrency(currency);
		
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent(String amountSpent) {
		this.amountSpent = amountSpent;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Map<String, String> toListItem() {
		Map <String, String> map = new HashMap<String, String>();
		map.put("description", this.description);
		
		return map;	
	}

}