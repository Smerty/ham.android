package com.smerty.ham;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.smerty.ham.qrz.QrzDb;
import org.smerty.ham.qrz.QrzHamProfile;
import org.smerty.jham.Location;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class QRZ extends Activity {

  public static final String PREFS_NAME = "HamPrefs";

  private AsyncTask<QRZ, Integer, QrzHamProfile> updatetask;
  public ProgressDialog progressDialog;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    final Intent intent = getIntent();
    final String action = intent.getAction();

    if (Intent.ACTION_CREATE_SHORTCUT.equals(action)) {
      Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
      shortcutIntent.setClassName(this, this.getClass().getName());
      shortcutIntent.putExtra("com.smerty.ham.QRZ", "QRZ.com Callsign Lookup");

      Intent csintent = new Intent();
      csintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
      csintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "QRZ");
      Parcelable iconResource = Intent.ShortcutIconResource.fromContext(this,
          R.drawable.search_icon);
      csintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

      setResult(RESULT_OK, csintent);
      finish();
      return;
    }

    setContentView(R.layout.qrz);

    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    final String qrzUser = settings.getString("qrzUser", null);
    final String qrzPassword = settings.getString("qrzPassword", null);

    if (qrzUser == null || qrzPassword == null || qrzUser.length() == 0
        || qrzPassword.length() == 0) {
      LinearLayout searchLL = (LinearLayout) findViewById(R.id.SearchLL01);
      searchLL.setVisibility(View.INVISIBLE);
      Intent i = new Intent(this, Settings.class);
      this.startActivity(i);
      this.finish();
    }
    LinearLayout resultsLL = (LinearLayout) findViewById(R.id.ResultsLL);
    resultsLL.setVisibility(View.INVISIBLE);

    final EditText callsignSearchText = (EditText) findViewById(R.id.EditText01);

    Button searchButton = (Button) findViewById(R.id.Button01);

    searchButton.setOnClickListener(new OnClickListener() {

      public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
            Log.d("updatetask",
                "task wasn't null, status finished, calling execute");
            QRZ.this.updatetask = new GetProfileTask().execute(QRZ.this);
          }
        }

      }
    });
  }

  private class GetProfileTask extends AsyncTask<QRZ, Integer, QrzHamProfile> {

    private int linkColor = Color.rgb(Integer.parseInt("66", 16),Integer.parseInt("77", 16),Integer.parseInt("FF", 16));

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

        TableLayout profileTable = (TableLayout) findViewById(R.id.ProfileTable);
        profileTable.removeAllViewsInLayout();

        profile = qrzDb.getHamByCallsign(callsignSearchText.getText()
            .toString().replace(" ", ""));

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
      LinearLayout resultsLL = (LinearLayout) findViewById(R.id.ResultsLL);

      TableLayout profileTable = (TableLayout) findViewById(R.id.ProfileTable);
      profileTable.removeAllViewsInLayout();

      if (result == null) {
        Toast
            .makeText(
                that.getBaseContext(),
                "No results found, incorrect QRZ.com username/password set or no QRZ.com subscription",
                Toast.LENGTH_LONG).show();
        resultsLL.setVisibility(View.INVISIBLE);
        return;
      }

      TextView nameText = (TextView) findViewById(R.id.TextProfileName);

      String displayName = "";
      if (result.getFname() != null && result.getFname().length() > 0) {
        displayName = result.getFname();
        if (result.getName() != null && result.getName().length() > 0) {
          displayName += " " + result.getName();
        }
      } else {
        if (result.getName() != null && result.getName().length() > 0) {
          displayName = result.getName();
        }
      }

      if (displayName.length() > 0) {
        nameText.setText(displayName);
      } else {
        nameText.setText("no name?");
      }

      if (result.getImage() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        Spanned htmlString = Html.fromHtml("<img src='" + result.getImage()
            + "'>", new ImageGetter() {

          public Drawable getDrawable(String source) {
            // TODO Auto-generated method stub

            try {

              Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
                  source).getContent());
              Drawable d = new BitmapDrawable(bitmap);
              d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
              return d;
            } catch (MalformedURLException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }
            return null;
          }
        }, null);
        tv.setAutoLinkMask(Linkify.ALL);
        tv.setText(htmlString);
        profileTable.addView(tr);
      }

      if (result.getCall() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setText("Callsign: " + result.getCall());
        tv.setTextSize(24);
        profileTable.addView(tr);
      }

      if (result.getEmail() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setAutoLinkMask(Linkify.ALL);
        tv.setText("Email: " + result.getEmail());
        tv.setTextSize(24);
        profileTable.addView(tr);
      } else {
        // emailText.setText("Email: n/a");
      }

      if (result.getAddr1() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setText("Street: " + result.getAddr1());
        tv.setTextSize(24);
        profileTable.addView(tr);
      } else {
        // stateText.setText("State: n/a");
      }

      if (result.getAddr2() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setText("City: " + result.getAddr2());
        tv.setTextSize(24);
        profileTable.addView(tr);
      } else {
        // stateText.setText("State: n/a");
      }

      if (result.getState() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setText("State: " + result.getState());
        tv.setTextSize(24);
        profileTable.addView(tr);
      } else {
        // stateText.setText("State: n/a");
      }

      if (result.getCountry() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setText("Country: " + result.getCountry());
        tv.setTextSize(24);
        profileTable.addView(tr);
      } else {
        // countryText.setText("Country: n/a");
      }

      if (result.getGrid() != null) {
        TableRow tr = new TableRow(that);
        LinearLayout ll = new LinearLayout(that);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        TextView tv = new TextView(that);
        tv.setText("Grid: ");
        tv.setTextSize(24);
        TextView tv2 = new TextView(that);
        SpannableString gridSpan = new SpannableString(result.getGrid());
        gridSpan.setSpan(new UnderlineSpan(), 0, gridSpan.length(), 0);
        tv2.setText(gridSpan);
        tv2.setTextSize(24);
        tv2.setTextColor(linkColor);
        tv2.setOnClickListener(new OnClickListener() {

          public void onClick(View v) {
            if (v instanceof TextView) {
              TextView tv = (TextView) v;
              Intent i = new Intent(Intent.ACTION_VIEW);
              String substring = tv.getText().toString();
              Location loc = new Location(substring);
              String uriString = "geo:"
                  + ((int) (loc.getLatitude().toDegrees() * 1000) / 1000.0) + ","
                  + ((int) (loc.getLongitude().toDegrees() * 1000) / 1000.0)
                  + "?z=15";
              Log.d("QRZ", "grid click, launching intent using uri=" + uriString);
              i.setData(Uri.parse(uriString));
              that.startActivity(i);
            } else {
              Log.e("QRZ", "v not instanceof TextView");
            }
          }
        });

        ll.addView(tv);
        ll.addView(tv2);
        tr.addView(ll);
        profileTable.addView(tr);
      } else {
        // gridText.setText("Grid: n/a");
      }

      if (result.getGrid() != null) {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        android.location.Location bestLocation = locationManager
            .getLastKnownLocation(Geo.getBestProvider(locationManager));


        if (bestLocation != null) {
          Location theirLocation = new Location(result.getGrid());
          Location myLocation = new Location(bestLocation.getLatitude(),
              bestLocation.getLongitude());
          double distance = myLocation.getDistanceMi(theirLocation);
          TableRow tr = new TableRow(that);
          TextView tv = new TextView(that);
          tr.addView(tv);
          tv.setText("Distance: " + ((int) (distance * 100)) / 100.0 + " mi");
          tv.setTextSize(24);
          profileTable.addView(tr);

          int bearing = (int) myLocation.getBearing(theirLocation);
          tr = new TableRow(that);
          tv = new TextView(that);
          tr.addView(tv);
          tv.setText("Bearing: " + bearing + " deg");
          tv.setTextSize(24);
          profileTable.addView(tr);
        }


      } else {
        // gridText.setText("Grid: n/a");
      }

      if (result.getUrl() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        // Spanned htmlString = Html.fromHtml("Web: " + "<a href='" +
        // result.getUrl() + "'>" + result.getUrl() + "</a>");
        tv.setAutoLinkMask(Linkify.ALL);
        tv.setText("Web: " + result.getUrl());
        tv.setTextSize(24);
        profileTable.addView(tr);
      }

      String hamClass = result.getHamclass();
      if (hamClass != null) {

        if (hamClass.equalsIgnoreCase("e")) {
          hamClass = "Extra";
        } else if (hamClass.equalsIgnoreCase("a")) {
          hamClass = "Advanced";
        } else if (hamClass.equalsIgnoreCase("t")) {
          hamClass = "Tech";
        } else if (hamClass.equalsIgnoreCase("g")) {
          hamClass = "General";
        } else if (hamClass.equalsIgnoreCase("c")) {
          hamClass = "Club";
        }

        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setText("Class: " + hamClass);
        tv.setTextSize(24);
        profileTable.addView(tr);
      } else {
        // classText.setText("Class: n/a");
      }

      if (result.getEfdate() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setText("Effective: " + result.getEfdate());
        tv.setTextSize(24);
        profileTable.addView(tr);
      }

      if (result.getExpdate() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setText("Expires: " + result.getExpdate());
        tv.setTextSize(24);
        profileTable.addView(tr);
      } else {
        // expiresText.setText("Expires: n/a");
      }

      if (result.getU_views() != null) {
        TableRow tr = new TableRow(that);
        TextView tv = new TextView(that);
        tr.addView(tv);
        tv.setText("Views: " + result.getU_views());
        tv.setTextSize(24);
        profileTable.addView(tr);
      }

      /*
       * if (result.getBio() != null) { TableRow tr = new TableRow(that);
       * TextView tv = new TextView(that); TextView tv1 = new TextView(that);
       * tr.addView(tv1); tv1.setText("Bio: "); tr.addView(tv); Spanned
       * htmlString = Html.fromHtml(result.getBio()); tv.setText(htmlString);
       * profileTable.addView(tr); }
       */

      resultsLL.setVisibility(View.VISIBLE);

    }
  }
}
