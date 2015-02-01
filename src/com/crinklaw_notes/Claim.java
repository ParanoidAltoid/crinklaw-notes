package com.crinklaw_notes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Claim{
	
	private List<Expense> expenses;
	private Date startDate;
	private Date endDate;	
	
	public Claim(){
		
	}
	
	public static String[] getAttributes() {
		return new String[] {"startDate"};
	}


	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	

	public Map<String, String> toListItem() {
		Map <String, String> map = new HashMap<String, String>();
		map.put("startDate", this.startDate.toString());
		
		return map;	
	}
	




}
