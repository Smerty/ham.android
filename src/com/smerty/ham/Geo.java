/**
 *
 */
package com.smerty.ham;

import java.util.Date;

import org.smerty.android.util.IntentUtils;
import org.smerty.jham.Location;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * @author paul
 *
 */
public class Geo extends Activity implements LocationListener {

  private LocationManager locationManager;
  private android.location.Location bestLocation;
  private boolean locating = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    ScrollView sv = new ScrollView(this);

    TableLayout table = new TableLayout(this);

    // table.setStretchAllColumns(true);
    table.setShrinkAllColumns(true);

    if (locationManager == null) {
      locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    if (!isProviderAvailable(locationManager)) {
      if (IntentUtils.isIntentAvailable(this, android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)) {
        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        // code will continue, even if settings screen is brought up.
      }
    }

    if (bestLocation == null) {

      bestLocation = locationManager.getLastKnownLocation(getBestProviderWithGPSFallback(locationManager));
    }

    if (bestLocation != null) {
      Location myLocation = new Location(bestLocation.getLatitude(),
          bestLocation.getLongitude());
      table.addView(rowHelper("Grid: " + myLocation.toMaidenhead(), 48));

      table.addView(rowHelper("Latitude: "
          + ((bestLocation.getLatitude() * 100000000) / 100000000.0),24));
      table.addView(rowHelper("Longitude: "
          + ((bestLocation.getLongitude() * 100000000) / 100000000.0),24));



      table.addView(rowHelper(""
          + new Date(bestLocation.getTime()).toString(), 18));

      /*
      table.addView(rowHelper("Provider: " + bestLocation.getProvider()));

      table.addView(rowHelper("Age: "
          + ((System.currentTimeMillis() - bestLocation.getTime()) / 1000.0)
          + " sec"));
      table.addView(rowHelper("Accuracy: " + bestLocation.getAccuracy()));


      table.addView(rowHelper("Speed: " + bestLocation.getSpeed()));
      table.addView(rowHelper("Alt: " + bestLocation.getAltitude()));
      table.addView(rowHelper("Bearing" + bestLocation.getBearing()));
      */

    } else {
      table.addView(rowHelper("Position Not Known."));
    }

    sv.addView(table);

    setContentView(sv);

    // start up the gps
    startLocating();
  }

  private TableRow rowHelper(String textStr) {
    return rowHelper(textStr, 28);
  }

  private TableRow rowHelper(String textStr, int textSize) {
    TableRow row = new TableRow(this);

    TextView text = new TextView(this);
    text.setText(textStr);
    text.setTextSize(textSize);

    row.setPadding(5, 5, 5, 5);
    row.setBackgroundColor(Color.argb(200, 51, 51, 51));

    row.addView(text);
    return row;
  }

  public void onLocationChanged(android.location.Location location) {
    bestLocation = location;
    this.onResume();
  }

  @Override
  protected void onPause() {
    stopLocating();
    super.onPause();
  }

  private void stopLocating() {
    locationManager.removeUpdates(this);
    locating = false;
  }

  public void onProviderDisabled(String provider) {
    Log.d("Geo", "provider: " + provider + " disabled");
  }

  public void onProviderEnabled(String provider) {
    Log.d("Geo", "provider: " + provider + " enabled");
  }

  private String getStatusDescription(int status) {
    switch (status) {
    case LocationProvider.AVAILABLE:
      return "AVAILABLE";
    case LocationProvider.OUT_OF_SERVICE:
      return "OUT_OF_SERVICE";
    case LocationProvider.TEMPORARILY_UNAVAILABLE:
      return "TEMPORARILY_UNAVAILABLE";
    }
    return null;
  }

  public void onStatusChanged(String provider, int status, Bundle extras) {
    // Log.d("Geo", "status changed, provider=" + provider + " status=" +
    // getStatusDescription(status) + " extras=" + extras.toString());
  }

  private void startLocating() {
    if (locating) {
      return;
    }

    locationManager.requestLocationUpdates(
        getBestProviderWithGPSFallback(locationManager), 2000, 0, this);
  }

  public static boolean isProviderAvailable(LocationManager locationManager) {
    String bestProvider = getBestProviderOrNull(locationManager);
    // Probably would be nice to migrate to return StringUtils.isBlank(bestProvider)
    if (bestProvider != null) {
      return true;
    } else {
      return false;
    }
  }

  public static String getBestProviderWithGPSFallback(LocationManager locationManager) {
    String bestProvider = getBestProviderOrNull(locationManager);
    if (bestProvider != null) {
      return bestProvider;
    }
    else {
      return LocationManager.GPS_PROVIDER;
    }
  }

  public static String getBestProviderOrNull(LocationManager locationManager) {
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    String bestProvider = locationManager.getBestProvider(criteria, true);
    return bestProvider;
  }
}
