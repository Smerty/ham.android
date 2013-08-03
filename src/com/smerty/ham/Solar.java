package com.smerty.ham;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.smerty.ham.solar.Calculatedconditions;
import org.smerty.ham.solar.Phenomenon;
import org.smerty.ham.solar.Solardata;

public class Solar extends Activity {

  ScrollView sv;
  TableLayout table;

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
      shortcutIntent.putExtra("com.smerty.ham.Solar",
          "Solar Conditions For Hams");

      Intent csintent = new Intent();
      csintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
      csintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Solar");
      Parcelable iconResource = Intent.ShortcutIconResource.fromContext(this,
          R.drawable.solar_icon);
      csintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

      setResult(RESULT_OK, csintent);
      finish();
      return;
    }

    sv = new ScrollView(this);

    table = new TableLayout(this);

    // table.setStretchAllColumns(true);
    table.setShrinkAllColumns(true);

    sv.addView(table);


      String agent = "ham ";

      try {
          ComponentName compName = new ComponentName(this, Solar.class);
          PackageInfo pkgInfo = this.getPackageManager().getPackageInfo(
                  compName.getPackageName(), 0);
          agent += "(v" + pkgInfo.versionName + "-" + pkgInfo.versionCode + ") ";
      } catch (android.content.pm.PackageManager.NameNotFoundException e) {
          agent += "(version unknown) ";
      }

      agent += "for android";

      Ion.with(this.getApplicationContext(), "http://api.smerty.org/ham/solardata.php?format=json&agent=" + agent)
              .progressDialog(this.progressDialog)
              .as(new TypeToken<Solardata>() {
              })
              .setCallback(new FutureCallback<Solardata>() {
                  @Override
                  public void onCompleted(Exception e, Solardata result) {
                      if (e == null) {
                          allTogether(result);
                      } else {
                          e.printStackTrace();
                          Toast.makeText(getBaseContext(), "Network Failure...", Toast.LENGTH_SHORT).show();
                      }
                  }
              });

    setContentView(sv);
  }

  public void allTogether(Solardata conds) {

    TableRow row = new TableRow(this);
    TextView text = new TextView(this);
    // text.setText("Solar Terrestrial Data");
    text.setText(R.string.stdstr);
    text.setTextSize(24);
    // text.setTextSize(12.0);
    row.setPadding(3, 3, 3, 3);
    row.setBackgroundColor(Color.argb(200, 51, 51, 51));
    row.addView(text);
    table.addView(row);

    if (conds != null) {

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("Solar Flux: \t" + conds.getSolarflux());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("A-Index: \t\t" + conds.getAindex());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("K-Index: \t\t" + conds.getKindex() + " / "
          + conds.getKindexnt());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("X-Ray: \t\t\t" + conds.getXray());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("Sun Spots: \t" + conds.getSunspots());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("Proton Flux: \t" + conds.getProtonflux());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("Electron Flux: \t" + conds.getElectronflux());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("Aurora: \t" + conds.getAurora() + " / n="
          + conds.getNormalization());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      // next section

      row = new TableRow(this);
      text = new TextView(this);
      text.setText(R.string.cscstr);
      text.setTextSize(24);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      LinearLayout llayout = null;

      row = new TableRow(this);

      llayout = new LinearLayout(this);

      text = new TextView(this);
      text.setText("Bands");
      text.setTextSize(18);
      text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
      // text.setBackgroundColor(Color.argb(200, 51, 0, 0));

      llayout.addView(text);

      text = new TextView(this);
      text.setText("\t\t\t" + "Day");
      text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
      // text.setTextAppearance(context, resid)
      // text.setGravity(1);
      text.setTextSize(18);
      // text.setTextColor(ConditionColor(conds.band4day));
      // text.setBackgroundColor(Color.argb(200, 0, 51, 0));
      llayout.addView(text);

      text = new TextView(this);
      text.setText("\t\t" + "Night");
      text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
      text.setTextSize(18);
      // text.setGravity(1);
      // text.setTextColor(ConditionColor(conds.band4night));
      // text.setBackgroundColor(Color.argb(200, 0, 9, 51));
      llayout.addView(text);

      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(llayout);
      table.addView(row);

      row = new TableRow(this);

      for (Calculatedconditions calccond : conds.getCalculatedconditions()) {
        row = new TableRow(this);
        llayout = new LinearLayout(this);

        text = new TextView(this);
        text.setText(calccond.getBand() + ":");
        text.setTextSize(18);
        // text.setBackgroundColor(Color.argb(200, 51, 0, 0));

        llayout.addView(text);

        text = new TextView(this);
        text.setText("\t\t" + calccond.getDay());
        // text.setGravity(1);
        text.setTextSize(18);
        text.setTextColor(conditionColor(calccond.getDay()));
        // text.setBackgroundColor(Color.argb(200, 0, 51, 0));
        llayout.addView(text);

        text = new TextView(this);
        text.setText("\t\t" + calccond.getNight());
        text.setTextSize(18);
        // text.setGravity(1);
        text.setTextColor(conditionColor(calccond.getNight()));
        // text.setBackgroundColor(Color.argb(200, 0, 9, 51));
        llayout.addView(text);

        row.setPadding(3, 3, 3, 3);
        row.setBackgroundColor(Color.argb(200, 51, 51, 51));
        row.addView(llayout);
        table.addView(row);
      }

      // next section

      row = new TableRow(this);
      text = new TextView(this);
      text.setText(R.string.vhfcondstr);
      text.setTextSize(24);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      if (conds.getCalculatedvhfconditions() != null) {
        for (Phenomenon pheno : conds.getCalculatedvhfconditions()
            .getPhenomenon()) {
          row = new TableRow(this);
          text = new TextView(this);
          if (pheno.getName().equals("vhf-aurora")) {
            text.setText("Aurora: \t" + pheno.getValue());
          } else if (pheno.getName().equals("E-Skip")) {
            if (pheno.getLocation().equals("north_america")) {
              text.setText("2m Es N. America: \t" + pheno.getValue());
            } else if (pheno.getLocation().equals("europe")) {
              text.setText("2m Es Europe: \t" + pheno.getValue());
            } else if (pheno.getLocation().equals("europe_6m")) {
              text.setText("6m Es Europe: \t" + pheno.getValue());
            } else if (pheno.getLocation().equals("europe_4m")) {
              text.setText("4m Es Europe: \t" + pheno.getValue());
            }
          }
          text.setTextSize(18);
          row.setPadding(3, 3, 3, 3);
          row.setBackgroundColor(Color.argb(200, 51, 51, 51));
          row.addView(text);
          table.addView(row);
        }
      } else {
        Log.w("allTogether", "calculatedvhfconditions was null");
      }

      row = new TableRow(this);
      text = new TextView(this);
      text.setText(R.string.otherstr);
      text.setTextSize(24);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("Geomag Field: \t" + conds.getGeomagfield());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("Signal Noise: \t" + conds.getSignalnoise());
      text.setTextSize(18);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      table.addView(row);

      row = new TableRow(this);
      row.setPadding(3, 1, 3, 1);
      row.setBackgroundColor(Color.argb(200, 80, 80, 80));
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText("Updated: " + conds.getUpdated());
      text.setTextSize(14);
      text.setGravity(1);
      row.setPadding(3, 3, 3, 3);
      // row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);

      row = new TableRow(this);
      text = new TextView(this);
      text.setText(R.string.solarsource);
      text.setTextSize(14);
      text.setGravity(1);
      row.setPadding(3, 3, 3, 3);
      // row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);
    } else {
      row = new TableRow(this);
      text = new TextView(this);
      text.setText("Data not available...");
      // text.setText(R.string.solarsource);
      text.setTextSize(14);
      text.setGravity(1);
      row.setPadding(3, 3, 3, 3);
      row.setBackgroundColor(Color.argb(200, 51, 51, 51));
      row.addView(text);
      table.addView(row);
    }

  }

  public int conditionColor(String conditionStr) {

    if (conditionStr.equals("Poor")) {
      return Color.argb(200, 255, 0, 0);
    }
    if (conditionStr.equals("Fair")) {
      return Color.argb(200, 255, 255, 0);
    }
    if (conditionStr.equals("Good")) {
      return Color.argb(200, 0, 255, 0);
    }

    return Color.argb(200, 51, 51, 51);
  }

}
