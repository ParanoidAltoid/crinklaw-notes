package com.crinklaw_notes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class MainActivity extends Activity {

	private static final String CLAIMS_FILENAME = "claims.sav";
	private static final int REQUEST_CODE_CREATE_CLAIM = 0;
	private static final int REQUEST_CODE_EDIT_CLAIM = 1;
	private static final int REQUEST_CODE_VIEW_EXPENSES = 2;
	private List<Claim> claims;
	private SimpleAdapter listAdapter;
	private List<Map<String, String>> listViewData;
	private int selectedClaimIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//http://www.javacodegeeks.com/2013/06/android-listview-tutorial-and-basic-example.html
		//1 feb 2015
		

		ListView lv = (ListView) findViewById(R.id.claims);

		loadClaims();
		
		listViewData = createList(claims);
		
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
	       
	       selectedClaimIndex = aInfo.position;
	       //HashMap map =  (HashMap) listAdapter.getItem(aInfo.position);
	       
	       //menu.setHeaderTitle("Options for " + map.get("planet"));
	       menu.add(1, 1, 1, "View Expenses");
	       menu.add(1, 2, 2, "Email Claim");
	       menu.add(1, 3, 3, "Edit Claim");
	       menu.add(1, 4, 4, "Delete");
	   }
	 
	 //Toast.makeText(this, "Item id ["+itemId+"]", Toast.LENGTH_SHORT).show();	 
	 @Override
	 public boolean onContextItemSelected(MenuItem item) {
	     int itemId = item.getItemId();
	     switch (itemId){
	     case 1://view expenses
	    	 Intent i = new Intent(this, ViewExpensesActivity.class);
	    	 i.putExtra("claim", claims.get(selectedClaimIndex));
	    	 startActivityForResult(i, REQUEST_CODE_VIEW_EXPENSES);
	    	 break;
	     case 2://email claim
	    	 break;
	     case 3://edit claim
	    	 if (!claims.get(selectedClaimIndex).isSubmitted()){
	    		 Intent i2= new Intent(this, CreateClaimActivity.class);
	    		 startActivityForResult(i2, REQUEST_CODE_EDIT_CLAIM);
	    	 }
	    	 else {
	    		 Toast.makeText(this, "Claim is submited, cannot edit.", Toast.LENGTH_SHORT).show();	 
	    	 }
	    	 
	    	 break;
	     case 4://delete
	    	 claims.remove(selectedClaimIndex);
	    	 listViewData.remove(selectedClaimIndex);
	    	 saveClaims();

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
	        case R.id.createClaim:
	        	Intent i = new Intent(this, CreateClaimActivity.class);
	        	startActivityForResult(i, REQUEST_CODE_CREATE_CLAIM);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	//http://www.android-ios-tutorials.com/android/switch-between-activities-in-android/
	//feb 1 2015
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_CREATE_CLAIM:
			if(resultCode == RESULT_OK) {

	        	Claim claim = new Claim(data.getStringExtra("startDate"),
	        			data.getStringExtra("endDate"),
	        			data.getStringExtra("description"));
	        	claims.add(claim);
	        	
	        	listViewData.add(claim.toListItem());
	        	
	        	saveClaims();
	        	listAdapter.notifyDataSetChanged();
			}
        	break;
		case REQUEST_CODE_EDIT_CLAIM:
			if(resultCode == RESULT_OK) {

				if (!data.getStringExtra("startDate").contentEquals(""))
					claims.get(selectedClaimIndex).setStartDate(data.getStringExtra("startDate"));
				
				if (!data.getStringExtra("endDate").contentEquals(""))
					claims.get(selectedClaimIndex).setEndDate(data.getStringExtra("endDate"));
				
				if (!data.getStringExtra("description").contentEquals(""))
					claims.get(selectedClaimIndex).setDescription(data.getStringExtra("description"));

	        	saveClaims();
	        	
	        	//listViewData = createList(claims);
	        	listViewData.set(selectedClaimIndex, claims.get(selectedClaimIndex).toListItem());
	        	listAdapter.notifyDataSetChanged();
	        }
	        break;
		case REQUEST_CODE_VIEW_EXPENSES:
			if(resultCode == RESULT_OK) {

				claims.set(selectedClaimIndex, (Claim) data.getSerializableExtra("claim"));

	        	saveClaims();
	        	
	        	//listViewData = createList(claims);
	        	listViewData.set(selectedClaimIndex, claims.get(selectedClaimIndex).toListItem());
	        	listAdapter.notifyDataSetChanged();
	        }
	        break;
	    }
	}

	//converts a list of claims to the format needed by listview adapter.
	public static List<Map<String, String>> createList(List<Claim> elements) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Claim element: elements){
			list.add(element.toListItem());
		}

		return list;
	}
	
	private void loadClaims() {
		claims = new ArrayList<Claim>();
		try {
			FileInputStream fis = openFileInput(CLAIMS_FILENAME);
		
			Gson gson = new Gson();
			
			//From joshua2ua in lab 3:
			Type dataType = new TypeToken<List<Claim>>() {}.getType();
			InputStreamReader isr = new InputStreamReader(fis);
			claims = gson.fromJson(isr, dataType);
			fis.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (claims == null)
			claims = new ArrayList<Claim>();
		

	}
	

	
	private void saveClaims() {
		try {
						
			FileOutputStream fos = openFileOutput(CLAIMS_FILENAME, 0);
			Gson gson = new Gson();
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(claims, osw);
			osw.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
