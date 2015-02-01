package com.crinklaw_notes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private static final String CLAIMS_FILENAME = "claims.sav";
	private List<Claim> claims;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//http://www.javacodegeeks.com/2013/06/android-listview-tutorial-and-basic-example.html
		//1 feb 2015
		

		ListView lv = (ListView) findViewById(R.id.claims);

		loadClaims();
		
		SimpleAdapter simpleAdpt = new SimpleAdapter(this, createList(claims), android.R.layout.simple_list_item_1, 
				Claim.getAttributes(), new int[] {android.R.id.text1});
		
		lv.setAdapter(simpleAdpt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);		
		
		return true;
	}

	//converts a list to the format needed by listview adapter.
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
	
	
	private void saveClaims(String text, Date date) {
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
