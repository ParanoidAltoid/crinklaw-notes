package com.crinklaw_notes;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

public class Claim implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Expense> expenses;
	private String startDate;
	private String endDate;
	private String description;	
	
	public Claim(String startDate, String endDate, String description){
		this.startDate = startDate;
		this.endDate = endDate;
		this.setDescription(description);
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

	




}
