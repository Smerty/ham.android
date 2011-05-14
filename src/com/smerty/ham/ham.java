package com.smerty.ham;

import org.smerty.jham.Location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ham extends Activity {

  public static final String PREFS_NAME = "HamPrefs";

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ScrollView sv = new ScrollView(this);

    TableLayout table = new TableLayout(this);

    // table.setStretchAllColumns(true);
    table.setShrinkAllColumns(true);

    table.addView(this.getTableRow("Solar Conditions",
        "current solar conditions", Solar.class, R.drawable.solar_icon, null,
        this));
    table.addView(this.getTableRow("Callsign Lookup",
        "current solar conditions", QRZ.class, R.drawable.search_icon, null,
        this));

    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE);

    android.location.Location bestLocation = locationManager
        .getLastKnownLocation(locationManager.getBestProvider(criteria, true));

    if (bestLocation != null) {
      Location myLocation = new Location(bestLocation.getLatitude(),
          bestLocation.getLongitude());

      table.addView(this.getTableRow("My Grid: " + myLocation.toMaidenhead(),
          "geo tools", Geo.class, R.drawable.radar_icon, null, this));
    }
    // table.addView(this.getTableRow("PSKreporter", "current solar conditions",
    // PSKReporter.class, R.drawable.radar_icon, null, this));
    table.addView(this.getTableRow("Settings", "ham settings", Settings.class,
        R.drawable.gear_icon, null, this));
    table.addView(this.getTableRow("Email Developer",
        "current solar conditions", null, R.drawable.mail_icon,
        "mailto:ham.android@smerty.org?subject=ham for android", this));
    // table.addView(this.getTableRow("Credits", "current solar conditions",
    // null, R.drawable.gear_icon, "http://smerty.org", this));
    table.addView(this.getTableRow("Exit", "current solar conditions", null,
        R.drawable.house_icon, null, this));

    sv.addView(table);

    setContentView(sv);
  }

  private TableRow getTableRow(final String rowTitle,
      final String rowDescription, final Class<?> activity, int iconId,
      final String url, final Activity that) {
    TableRow row = new TableRow(that);

    LinearLayout ll = new LinearLayout(that);
    ll.setGravity(Gravity.CENTER_VERTICAL);
    ll.setOrientation(LinearLayout.HORIZONTAL);

    ImageView icon = new ImageView(that);
    icon.setImageResource(iconId);

    ll.addView(icon);

    TextView text = new TextView(this);
    text.setText(" " + rowTitle);
    text.setTextSize(28);

    row.setPadding(5, 5, 5, 5);
    row.setBackgroundColor(Color.argb(200, 51, 51, 51));

    ll.addView(text);

    row.addView(ll);

    row.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {
        // TODO Auto-generated method stub
        if (activity == null && url == null) {
          that.finish();
          return;
        } else if (url != null) {
          Intent i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse(url));
          that.startActivity(i);
        } else {
          Intent i = new Intent(that, activity);
          that.startActivity(i);
        }
      }
    }

    );

    return row;
  }

  public static final int MENU_ABOUT = 10;
  public static final int MENU_QUIT = 11;
  public static final int MENU_REFRESH = 12;
  public static final int MENU_PSKREPORTER = 13;
  public static final int MENU_SOLAR = 14;
  public static final int MENU_QRZ = 15;

  /* Creates the menu items */
  public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, MENU_ABOUT, 0, "About");
    // menu.add(0, MENU_REFRESH, 0, "Refresh");
    // menu.add(0, MENU_QRZ, 0, "QRZ");
    // menu.add(0, MENU_SOLAR, 0, "Solar");
    // menu.add(0, MENU_PSKREPORTER, 0, "PSKReporter");
    menu.add(0, MENU_QUIT, 0, "Quit");
    return true;
  }

  /* Handles item selections */
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case MENU_ABOUT:
      Toast.makeText(getBaseContext(),
          "Developed by Smerty Software (Paul K6SH)", Toast.LENGTH_LONG).show();
      return true;
    case MENU_REFRESH:
      this.onCreate(null);
      return true;
    case MENU_PSKREPORTER:
      Intent pskreporterIntent = new Intent(ham.this, PSKReporter.class);
      this.startActivity(pskreporterIntent);
      return true;
    case MENU_SOLAR:
      Intent solarIntent = new Intent(ham.this, Solar.class);
      this.startActivity(solarIntent);
      return true;
    case MENU_QRZ:
      Intent qrzIntent = new Intent(ham.this, QRZ.class);
      this.startActivity(qrzIntent);
      return true;
    case MENU_QUIT:
      finish();
      return true;
    }
    return false;
  }

}