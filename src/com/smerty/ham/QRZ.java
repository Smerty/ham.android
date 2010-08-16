package com.smerty.ham;

import org.smerty.ham.qrz.QrzDb;
import org.smerty.ham.qrz.QrzHamProfile;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QRZ extends Activity {
	
	public static final String PREFS_NAME = "HamPrefs";
	
	private AsyncTask<QRZ, Integer, QrzHamProfile> updatetask;
	public ProgressDialog progressDialog;
	
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
				
				if (QRZ.this.updatetask == null) {
					Log.d("startDownloading", "task was null, calling execute");
					QRZ.this.updatetask = new GetProfileTask().execute(QRZ.this);
				} else {
					Status s = QRZ.this.updatetask.getStatus();
					if (s == Status.FINISHED) {
						Log
								.d("updatetask",
										"task wasn't null, status finished, calling execute");
						QRZ.this.updatetask = new GetProfileTask().execute(QRZ.this);
					}
				}
				
				
				
			}
		});
	}
	
	private class GetProfileTask extends AsyncTask<QRZ, Integer, QrzHamProfile> {

		QRZ that;

		protected QrzHamProfile doInBackground(QRZ... thats) {

			if (that == null) {
				this.that = thats[0];
			}

			QrzHamProfile profile = null;
			
			publishProgress(0);
			
			
			
			try {
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				final String qrzUser = settings.getString("qrzUser", null);
				final String qrzPassword = settings.getString("qrzPassword", null);
				
				QrzDb qrzDb = new QrzDb(qrzUser, qrzPassword);
				
				final EditText callsignSearchText = (EditText) findViewById(R.id.EditText01);
				
				profile = qrzDb.getHamByCallsign(callsignSearchText.getText().toString());
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}


			publishProgress(100);

			return profile;
		}

		protected void onProgressUpdate(Integer... progress) {
			Log.d("onProgressUpdate", progress[0].toString());
			if (progress[0] == 0) {
				that.progressDialog = ProgressDialog.show(that, "Ham",
						"Querying QRZ.com API", true, false);
			}
			if (progress[0] == 100) {
				that.progressDialog.dismiss();
			}

		}

		protected void onPostExecute(QrzHamProfile result) {
			//Log.d("onPostExecute", that.getApplicationInfo().packageName);
			//that.allTogether();
			
			if (result == null) {
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
			
			nameText.setText(result.getFname() + " " + result.getName());
			callText.setText("Callsign: " + result.getCall());
			stateText.setText("State: " + result.getState());
			emailText.setText("Email: " + result.getEmail());
			classText.setText("Class: " + result.getHamclass());
			expiresText.setText("Expires: " + result.getExpdate());
			countryText.setText("Country: " + result.getCountry());
			gridText.setText("Grid: " + result.getGrid());
			
			LinearLayout resultsLL = (LinearLayout) findViewById(R.id.ResultsLL01);
			resultsLL.setVisibility(View.VISIBLE);
			
		}
	}
}
