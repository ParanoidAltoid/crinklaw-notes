package com.crinklaw_notes;

import java.util.ArrayList;
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
import android.widget.Toast;


public class ViewExpensesActivity extends Activity {
	/*
	 * Displays expenses of a claim and allows actions pertaining to
	 * expenses to be performed.
	 */

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
		claim = (Claim) getIntent().getSerializableExtra("claim");

		ListView lv = (ListView) findViewById(R.id.expenses);

		updateListViewData();
		
		//claims.add(new Claim(new Date("2014-08-08"), new Date(), "test"));
		
		listAdapter = new SimpleAdapter(this, listViewData, android.R.layout.simple_list_item_1, 
				Expense.getAttributes(), new int[] {android.R.id.text1});
		
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
	       
	       menu.add(1, 1, 1, "Edit Expense");
	       menu.add(1, 2, 2, "Delete");
	   }
	 
	 @Override
	 public boolean onContextItemSelected(MenuItem item) {
	     int itemId = item.getItemId();
	     switch (itemId){
	     case 1://edit expense
	    	 if (!claim.isSubmitted()){
	    		 Intent i2= new Intent(this, EditExpenseActivity.class);
	    		 startActivityForResult(i2, REQUEST_CODE_EDIT_EXPENSE);
	    	 }
	    	 else {
	    		 Toast.makeText(this, "Claim is submited, cannot edit.", Toast.LENGTH_SHORT).show();	 
	    	 }
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
		getMenuInflater().inflate(R.menu.claim_menu, menu);
		
		return true;
	}
	

	//http://developer.android.com/guide/topics/ui/menus.html
	//feb 1 2015
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.createExpense:
	        	if (!claim.isSubmitted()){
	        		Intent i = new Intent(this, CreateExpenseActivity.class);
	        		startActivityForResult(i, REQUEST_CODE_CREATE_EXPENSE);;
	        	}
	        	else {
	        		Toast.makeText(this, "Claim is submited, cannot edit.", Toast.LENGTH_SHORT).show();	 
	        	}
	        	
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
	
	
	public void okButtonPressed(View view){
		
		getIntent().putExtra("claim", claim);
		
		setResult(RESULT_OK, getIntent());     
		finish();
	}
	
	public void submitButtonPressed(View view){
		
		if (claim.isSubmitted()){
			claim.setReturned(true);
			claim.setSubmitted(false);
		}
		else {
			claim.setSubmitted(true);
		}
	}
	
	public void approvedButtonPressed(View view){
		
		claim.setSubmitted(true);
		claim.setApproved(true);
	}

	
}
	
