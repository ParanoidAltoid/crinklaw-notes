package com.crinklaw_notes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class SummaryActivity extends Activity {

	private Claim claim;
	private SimpleAdapter listAdapter;
	private List<Map<String, String>> listViewData;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		
		//http://www.javacodegeeks.com/2013/06/android-listview-tutorial-and-basic-example.html
		//1 feb 2015
		claim = (Claim) getIntent().getSerializableExtra("claim");

		//set up expense list
		ListView lv = (ListView) findViewById(R.id.listView1);
		updateListViewData();

		listAdapter = new SimpleAdapter(this, listViewData, android.R.layout.simple_list_item_1, 
				Expense.getAttributes(), new int[] {android.R.id.text1});
		
		lv.setAdapter(listAdapter);
		
		Map<String, Integer> currencyAmounts = new HashMap<String, Integer>();
		
		
		for (Expense expense : claim.getExpenses()){
			if (!currencyAmounts.containsKey(expense.getCurrency())){
				currencyAmounts.put(expense.getCurrency(), Integer.parseInt(expense.getAmountSpent()));
			}
			else {
				currencyAmounts.put(expense.getCurrency(), currencyAmounts.get(expense.getCurrency()) + Integer.parseInt(expense.getAmountSpent()));
			}
		}
		
		String str = "";
		for (String key : currencyAmounts.keySet()){
			str = str + currencyAmounts.get(key) + key + ", ";
		}
		
		
		TextView amountBox = (TextView) findViewById(R.id.textView3);
		amountBox.setText(str);
	}





	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_menu, menu);
		
		return true;
	}
	




	//converts a list of claims to the format needed by listview adapter.
	public void updateListViewData() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Expense element: claim.getExpenses()){
			list.add(element.toListItem());
		}

		this.listViewData = list;
	}
	

	
}
	
