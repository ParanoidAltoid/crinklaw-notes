package com.crinklaw_notes;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EmailActivity extends Activity {
	private Claim claim;


	/* 
	 * Proivdes a form for claim creation.
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
		
		
		//http://www.javacodegeeks.com/2013/06/android-listview-tutorial-and-basic-example.html
		//1 feb 2015
		claim = (Claim) getIntent().getSerializableExtra("claim");
	}

	
	public void okButtonPressed(View view){
		
		String emailAddress = ((TextView) (this.findViewById(R.id.editText1))).getText().toString();
		
		
		String str = "";		
		
		Map<String, Integer> currencyAmounts = new HashMap<String, Integer>();
		
		for (Expense expense : claim.getExpenses()){
			
			str = str + expense.getDescription() + ": " + expense.getAmountSpent() + 
					expense.getCurrency() + " on " + expense.getDate() + "\n";
			
			if (!currencyAmounts.containsKey(expense.getCurrency())){
				currencyAmounts.put(expense.getCurrency(), Integer.parseInt(expense.getAmountSpent()));
			}
			else {
				currencyAmounts.put(expense.getCurrency(), currencyAmounts.get(expense.getCurrency()) + Integer.parseInt(expense.getAmountSpent()));
			}
		}
		
		str = str + "\n";
		for (String key : currencyAmounts.keySet()){
			str = str + currencyAmounts.get(key) + key + ", ";
		}

		///http://examples.javacodegeeks.com/android/core/email/android-sending-email-example/
		//feb 3 2015
		Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
		// prompts email clients only
		email.setType("message/rfc822");
		
		email.putExtra(Intent.EXTRA_EMAIL, new String[] {emailAddress});
		email.putExtra(Intent.EXTRA_SUBJECT, "Claim email from crinklaw");
		email.putExtra(Intent.EXTRA_TEXT, str);
		
		try {
			// the user can choose the email client
			startActivity(Intent.createChooser(email, "Choose an email client from..."));
			
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(this, "No email client installed.",
					Toast.LENGTH_LONG).show();
		}
		
		
		
		finish();
	}



}

