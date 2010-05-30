package com.smerty.ham;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class QRZ extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView text = new TextView(this);
		text.setText("QRZ Callsign Search Stub!");

		setContentView(text);
	}
}
