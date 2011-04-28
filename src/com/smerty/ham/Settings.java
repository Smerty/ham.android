package com.smerty.ham;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends Activity {

  public static final String PREFS_NAME = "HamPrefs";

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.settings);

    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    String qrzUser = settings.getString("qrzUser", null);
    String qrzPassword = settings.getString("qrzPassword", null);

    EditText userET = (EditText) findViewById(R.id.EditText01);
    EditText passwordET = (EditText) findViewById(R.id.EditText02);

    if (qrzUser != null) {
      userET.setText(qrzUser);
    }
    if (qrzPassword != null) {
      passwordET.setText(qrzPassword);
    }

    Button saveButton = (Button) findViewById(R.id.Button01);
    Button cancelButton = (Button) findViewById(R.id.Button04);
    Button deleteButton = (Button) findViewById(R.id.Button03);

    saveButton.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        EditText userET = (EditText) findViewById(R.id.EditText01);
        EditText passwordET = (EditText) findViewById(R.id.EditText02);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("qrzUser", userET.getText().toString());
        editor.putString("qrzPassword", passwordET.getText().toString());
        editor.commit();
        Settings.this.finish();
      }
    });

    cancelButton.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        Settings.this.finish();
      }
    });

    deleteButton.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("qrzUser", null);
        editor.putString("qrzPassword", null);
        editor.commit();
        Settings.this.finish();
      }
    });
  }
}
