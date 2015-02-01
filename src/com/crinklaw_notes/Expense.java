package com.crinklaw_notes;

import java.util.Date;



public class Expense {

	private Date date;
	private ExpenseCategory category;
	private String description;
	private double amountSpent;
	private String currency;

	public Expense(Date date, ExpenseCategory category, String description,
			double amountSpent, String currency) {
		
		this.setDate(date);
		this.setCategory(category);
		this.setDescription(description);
		this.setAmountSpent(amountSpent);
		this.setCurrency(currency);
		
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ExpenseCategory getCategory() {
		return category;
	}

	public void setCategory(ExpenseCategory category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent(double amountSpent) {
		this.amountSpent = amountSpent;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}