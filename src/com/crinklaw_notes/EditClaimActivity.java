package com.crinklaw_notes;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class EditClaimActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_claim);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	public void okButtonPressed(View view){
		
		ArrayList<String> args = new ArrayList<String>();
		args.add(((TextView) (this.findViewById(R.id.startDateTextBox))).getText().toString());
		args.add(((TextView) (this.findViewById(R.id.endDateTextBox))).getText().toString());
		args.add(((TextView) (this.findViewById(R.id.descriptionTextBox))).getText().toString());		
		
		
		getIntent().putExtra("startDate", ((TextView) (this.findViewById(R.id.startDateTextBox))).getText().toString()); 
		getIntent().putExtra("endDate", ((TextView) (this.findViewById(R.id.endDateTextBox))).getText().toString()); 
		getIntent().putExtra("description", ((TextView) (this.findViewById(R.id.descriptionTextBox))).getText().toString()); 
		
		setResult(RESULT_OK, getIntent());     
		finish();
	}



}
