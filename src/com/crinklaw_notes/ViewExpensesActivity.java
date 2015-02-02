package com.crinklaw_notes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
import android.widget.Toast;

public class ViewExpensesActivity extends Activity {

	private static final int REQUEST_CODE_CREATE_EXPENSE = 0;
	private static final int REQUEST_CODE_EDIT_EXPENSE = 1;
	private Claim claim;
	private SimpleAdapter listAdapter;
	private List<Map<String, String>> listViewData;
	private int selectedExpenseIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_expenses);
		
		//http://www.javacodegeeks.com/2013/06/android-listview-tutorial-and-basic-example.html
		//1 feb 2015
		

		ListView lv = (ListView) findViewById(R.id.expenses);

		updateListViewData();
		
		//claims.add(new Claim(new Date("2014-08-08"), new Date(), "test"));
		
		listAdapter = new SimpleAdapter(this, listViewData, android.R.layout.simple_list_item_1, 
				Claim.getAttributes(), new int[] {android.R.id.text1});
		
		lv.setAdapter(listAdapter);
		
		// React to user clicks on item
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
				
			}
			
			
		});
		
		registerForContextMenu(lv);

		
	}

	 @Override
	   public void onCreateContextMenu(ContextMenu menu, View v,
	           ContextMenuInfo menuInfo) {
	       super.onCreateContextMenu(menu, v, menuInfo);
	       AdapterContextMenuInfo aInfo = (AdapterContextMenuInfo) menuInfo;
	       
	       selectedExpenseIndex = aInfo.position;
	       //HashMap map =  (HashMap) listAdapter.getItem(aInfo.position);
	       
	       //menu.setHeaderTitle("Options for " + map.get("planet"));
	       menu.add(1, 1, 1, "View Expenses");
	       menu.add(1, 2, 2, "Email Claim");
	       menu.add(1, 3, 3, "Edit Claim");
	       menu.add(1, 4, 4, "Delete");
	   }
	 
	 @Override
	 public boolean onContextItemSelected(MenuItem item) {
	     int itemId = item.getItemId();
	     switch (itemId){
	     case 1://edit claim
	    	 Intent i2= new Intent(this, CreateClaimActivity.class);
	    	 startActivityForResult(i2, REQUEST_CODE_EDIT_EXPENSE);
	    	 break;
	     case 2://delete
	    	 claim.getExpenses().remove(selectedExpenseIndex);
	    	 listViewData.remove(selectedExpenseIndex);

	    	 listAdapter.notifyDataSetChanged();
	    	 break;
	     }
	     return true;
	 }


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	

	//http://developer.android.com/guide/topics/ui/menus.html
	//feb 1 2015
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.createExpense:
	        	Intent i = new Intent(this, CreateClaimActivity.class);
	        	startActivityForResult(i, REQUEST_CODE_CREATE_EXPENSE);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	//http://www.android-ios-tutorials.com/android/switch-between-activities-in-android/
	//feb 1 2015
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_CREATE_EXPENSE:
			if(resultCode == RESULT_OK) {

	        	Expense expense = new Expense(data.getStringExtra("date"),
	        			data.getStringExtra("category"),
	        			data.getStringExtra("description"),
	        			data.getStringExtra("amountSpent"),
	        			data.getStringExtra("currency"));
	        	
	        	this.claim.getExpenses().add(expense);
	        	
	        	listViewData.add(expense.toListItem());

	        	listAdapter.notifyDataSetChanged();
			}
        	break;
		case REQUEST_CODE_EDIT_EXPENSE:
			if(resultCode == RESULT_OK) {

				if (!data.getStringExtra("date").contentEquals(""))
					claim.getExpenses().get(selectedExpenseIndex).setDate(data.getStringExtra("date"));
				
				if (!data.getStringExtra("category").contentEquals(""))
					claim.getExpenses().get(selectedExpenseIndex).setCategory(data.getStringExtra("category"));
				
				if (!data.getStringExtra("description").contentEquals(""))
					claim.getExpenses().get(selectedExpenseIndex).setDescription(data.getStringExtra("description"));
				
				if (!data.getStringExtra("amountSpent").contentEquals(""))
					claim.getExpenses().get(selectedExpenseIndex).setAmountSpent(data.getStringExtra("amountSpent"));
				
				if (!data.getStringExtra("currency").contentEquals(""))
					claim.getExpenses().get(selectedExpenseIndex).setCurrency(data.getStringExtra("currency"));
				
				

	        	//listViewData = createList(claims);
	        	listViewData.set(selectedExpenseIndex, claim.getExpenses().get(selectedExpenseIndex).toListItem());
	        	listAdapter.notifyDataSetChanged();
	        }
	        break;
	    }
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
	
