package com.crinklaw_notes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Claim implements Serializable{
	
	/**
	 * Stores the data for a claim.
	 */
	private static final long serialVersionUID = 1L;
	private List<Expense> expenses;
	private String startDate;
	private String endDate;
	private String description;
	private boolean submitted;
	private boolean approved;
	private boolean resubmitted;
	
	public Claim(String startDate, String endDate, String description){
		this.startDate = startDate;
		this.endDate = endDate;
		this.setDescription(description);
		
		this.expenses = new ArrayList<Expense>();
		this.setSubmitted(false);
	}
	
	public static String[] getAttributes() {
		return new String[] {"description"};
	}


	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	

	public Map<String, String> toListItem() {
		Map <String, String> map = new HashMap<String, String>();
		map.put("description", this.description);
		
		return map;	
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isResubmitted() {
		return resubmitted;
	}

	public void setResubmitted(boolean resubmitted) {
		this.resubmitted = resubmitted;
	}

	




}
