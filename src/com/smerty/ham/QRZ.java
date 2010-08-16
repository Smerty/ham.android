package com.smerty.ham;

import org.smerty.ham.qrz.QrzDb;
import org.smerty.ham.qrz.QrzHamProfile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QRZ extends Activity {
	
	public static final String PREFS_NAME = "HamPrefs";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.qrz);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final String qrzUser = settings.getString("qrzUser", null);
		final String qrzPassword = settings.getString("qrzPassword", null);
		
		if (qrzUser == null || qrzPassword == null || qrzUser.length() == 0 || qrzPassword.length() == 0) {
			LinearLayout searchLL = (LinearLayout) findViewById(R.id.SearchLL01);
			searchLL.setVisibility(View.INVISIBLE);
			Intent i = new Intent(this, Settings.class);
			this.startActivity(i);
			this.finish();
		}
		LinearLayout resultsLL = (LinearLayout) findViewById(R.id.ResultsLL01);
		resultsLL.setVisibility(View.INVISIBLE);
		
		final EditText callsignSearchText = (EditText) findViewById(R.id.EditText01);
		
		Button searchButton = (Button) findViewById(R.id.Button01);
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(callsignSearchText.getWindowToken(), 0);
				
				if (callsignSearchText.getText().toString().length() == 0) {
					return;
				}
				
				TextView nameText = (TextView) findViewById(R.id.TextProfileName);
				TextView emailText = (TextView) findViewById(R.id.TextProfileEmail);
				TextView classText = (TextView) findViewById(R.id.TextProfileClass);
				TextView expiresText = (TextView) findViewById(R.id.TextProfileExpDate);
				
				
				
				TextView callText = (TextView) findViewById(R.id.TextView01);
				TextView stateText = (TextView) findViewById(R.id.TextView02);
				TextView countryText = (TextView) findViewById(R.id.TextView03);
				TextView gridText = (TextView) findViewById(R.id.TextView04);
				
				try {
					QrzDb qrzDb = new QrzDb(qrzUser, qrzPassword);
					
					QrzHamProfile profile = qrzDb.getHamByCallsign(callsignSearchText.getText().toString());
					
					nameText.setText(profile.getFname() + " " + profile.getName());
					callText.setText("Callsign: " + profile.getCall());
					stateText.setText("State: " + profile.getState());
					emailText.setText("Email: " + profile.getEmail());
					classText.setText("Class: " + profile.getHamclass());
					expiresText.setText("Expires: " + profile.getExpdate());
					countryText.setText("Country: " + profile.getCountry());
					gridText.setText("Grid: " + profile.getGrid());
					
					LinearLayout resultsLL = (LinearLayout) findViewById(R.id.ResultsLL01);
					resultsLL.setVisibility(View.VISIBLE);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
}
