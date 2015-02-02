package com.crinklaw_notes;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class CreateExpenseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_expense);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	public void okButtonPressed(View view){
		
		getIntent().putExtra("date", ((TextView) (this.findViewById(R.id.dateTextBox))).getText().toString()); 
		getIntent().putExtra("category", ((TextView) (this.findViewById(R.id.categoryTextBox))).getText().toString()); 
		getIntent().putExtra("description", ((TextView) (this.findViewById(R.id.expenseDescriptionTextBox))).getText().toString()); 
		getIntent().putExtra("amountSpent", ((TextView) (this.findViewById(R.id.amountSpentTextBox))).getText().toString()); 
		getIntent().putExtra("currency", ((TextView) (this.findViewById(R.id.currencyTextBox))).getText().toString()); 
		
		
		setResult(RESULT_OK, getIntent());     
		finish();
	}



}
