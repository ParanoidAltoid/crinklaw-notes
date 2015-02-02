package com.crinklaw_notes;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
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
				
		
		getIntent().putExtra("startDate", ((TextView) (this.findViewById(R.id.startDateTextBox))).getText().toString()); 
		getIntent().putExtra("endDate", ((TextView) (this.findViewById(R.id.endDateTextBox))).getText().toString()); 
		getIntent().putExtra("description", ((TextView) (this.findViewById(R.id.descriptionTextBox))).getText().toString()); 
		
		setResult(RESULT_OK, getIntent());     
		finish();
	}



}
