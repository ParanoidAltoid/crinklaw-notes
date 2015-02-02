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
	private boolean returned;
	
	public Claim(String startDate, String endDate, String description){
		this.startDate = startDate;
		this.endDate = endDate;
		this.setDescription(description);
		
		this.expenses = new ArrayList<Expense>();
		this.setSubmitted(false);
		this.setReturned(false);
		this.setApproved(false);
	}
	
	public static String[] getAttributes() {
		//return new String[] {"description", "status"};
		return new String[] {"claim"};
	}
	
	//does logic on approved and submitted and resubmitted to get status.
	public String getStatus() {
		
		if (approved){
			return "Approved";
		}

		if (returned)
			return "Returned";
		
		if (submitted)
			return "Submitted";
		
		return "In Progress";
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
		//map.put("description", this.description);
		//map.put("status", this.getStatus());
		map.put("claim", this.toString());
		
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

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean resubmitted) {
		this.returned = resubmitted;
	}

	@Override
	public String toString(){
		return this.getDescription() + "\n " + this.getStatus();
	}



}
