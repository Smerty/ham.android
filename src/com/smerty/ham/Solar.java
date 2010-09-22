package com.smerty.ham;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Solar extends Activity {
	
	ScrollView sv;
	TableLayout table;
	
	SolarData conds;
	
	private AsyncTask<Solar, Integer, Integer> updatetask;
	public ProgressDialog progressDialog;
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		sv = new ScrollView(this);

		table = new TableLayout(this);

		// table.setStretchAllColumns(true);
		table.setShrinkAllColumns(true);

		
		sv.addView(table);
		
		
		
		if (this.updatetask == null) {
			Log.d("startDownloading", "task was null, calling execute");
			this.updatetask = new UpdateFeedTask().execute(this);
		} else {
			Status s = this.updatetask.getStatus();
			if (s == Status.FINISHED) {
				Log
						.d("updatetask",
								"task wasn't null, status finished, calling execute");
				this.updatetask = new UpdateFeedTask().execute(this);
			}
		}
		
		// sv.setLayoutParams(new ViewGroup.LayoutParams(
		// ViewGroup.LayoutParams.FILL_PARENT,
		// ViewGroup.LayoutParams.FILL_PARENT
		// ));
		// new TableLayout.LayoutParams( LayoutParams.FILL_PARENT,
		// LayoutParams.WRAP_CONTENT))
		// sv.setBackgroundColor(Color.argb(200, 51, 51, 51));

		setContentView(sv);
	}
	
	private class UpdateFeedTask extends AsyncTask<Solar, Integer, Integer> {

		Solar that;

		protected Integer doInBackground(Solar... thats) {

			if (that == null) {
				this.that = thats[0];
			}

			publishProgress(0);
			
			try {
				that.conds = that.getSolar();
			} catch (Exception e) {
				that.conds = null;
			}
			


			publishProgress(100);

			return 0;
		}

		protected void onProgressUpdate(Integer... progress) {
			Log.d("onProgressUpdate", progress[0].toString());
			if (progress[0] == 0) {
				that.progressDialog = ProgressDialog.show(that, "Ham",
						"Downloading Solar XML Feed", true, false);
			}
			if (progress[0] == 100) {
				that.progressDialog.dismiss();
			}

		}

		protected void onPostExecute(Integer result) {
			//Log.d("onPostExecute", that.getApplicationInfo().packageName);
			that.allTogether();
			
		}
	}
	
	public void allTogether() {
		

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
			text.setText("Solar Flux: \t" + conds.solarflux);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("A-Index: \t\t" + conds.aindex);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("K-Index: \t\t" + conds.kindex + " / "
					+ conds.kindexnt);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("X-Ray: \t\t\t" + conds.xray);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("Sun Spots: \t" + conds.sunspots);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("Proton Flux: \t" + conds.protonflux);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("Electron Flux: \t" + conds.electronflux);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("Aurora: \t" + conds.aurora + " / n="
					+ conds.normalization);
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

			/*
			 * row = new TableRow(this); text = new TextView(this);
			 * text.setText("Bands             Day          Night");
			 * text.setTextSize(18); row.setPadding(3, 3, 3, 3);
			 * row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			 * row.addView(text); table.addView(row);
			 * 
			 * row = new TableRow(this); text = new TextView(this);
			 * text.setText("80m-40m:       " + conds.band1day + "       \t" +
			 * conds.band1night); text.setTextSize(18); row.setPadding(3, 3, 3,
			 * 3); row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			 * row.addView(text); table.addView(row);
			 * 
			 * row = new TableRow(this); text = new TextView(this);
			 * text.setText("30m-20m:       " + conds.band2day + "       \t" +
			 * conds.band2night); text.setTextSize(18); row.setPadding(3, 3, 3,
			 * 3); row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			 * row.addView(text); table.addView(row);
			 * 
			 * row = new TableRow(this); text = new TextView(this);
			 * text.setText("17m-15m:       " + conds.band3day + "       \t" +
			 * conds.band3night); text.setTextSize(18); row.setPadding(3, 3, 3,
			 * 3); row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			 * row.addView(text); table.addView(row);
			 */
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

			llayout = new LinearLayout(this);

			text = new TextView(this);
			text.setText("80m-40m:");
			text.setTextSize(18);
			// text.setBackgroundColor(Color.argb(200, 51, 0, 0));

			llayout.addView(text);

			text = new TextView(this);
			text.setText("\t\t" + conds.band1day);
			// text.setGravity(1);
			text.setTextSize(18);
			text.setTextColor(ConditionColor(conds.band1day));
			// text.setBackgroundColor(Color.argb(200, 0, 51, 0));
			llayout.addView(text);

			text = new TextView(this);
			text.setText("\t\t" + conds.band1night);
			text.setTextSize(18);
			// text.setGravity(1);
			text.setTextColor(ConditionColor(conds.band1night));
			// text.setBackgroundColor(Color.argb(200, 0, 9, 51));
			llayout.addView(text);

			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(llayout);
			table.addView(row);

			row = new TableRow(this);

			llayout = new LinearLayout(this);

			text = new TextView(this);
			text.setText("30m-20m:");
			text.setTextSize(18);
			// text.setBackgroundColor(Color.argb(200, 51, 0, 0));

			llayout.addView(text);

			text = new TextView(this);
			text.setText("\t\t" + conds.band2day);
			// text.setGravity(1);
			text.setTextSize(18);
			text.setTextColor(ConditionColor(conds.band2day));
			// text.setBackgroundColor(Color.argb(200, 0, 51, 0));
			llayout.addView(text);

			text = new TextView(this);
			text.setText("\t\t" + conds.band2night);
			text.setTextSize(18);
			// text.setGravity(1);
			text.setTextColor(ConditionColor(conds.band2night));
			// text.setBackgroundColor(Color.argb(200, 0, 9, 51));
			llayout.addView(text);

			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(llayout);
			table.addView(row);

			row = new TableRow(this);

			llayout = new LinearLayout(this);

			text = new TextView(this);
			text.setText("17m-15m:");
			text.setTextSize(18);
			// text.setBackgroundColor(Color.argb(200, 51, 0, 0));

			llayout.addView(text);

			text = new TextView(this);
			text.setText("\t\t" + conds.band3day);
			// text.setGravity(1);
			text.setTextSize(18);
			text.setTextColor(ConditionColor(conds.band3day));
			// text.setBackgroundColor(Color.argb(200, 0, 51, 0));
			llayout.addView(text);

			text = new TextView(this);
			text.setText("\t\t" + conds.band3night);
			text.setTextSize(18);
			// text.setGravity(1);
			text.setTextColor(ConditionColor(conds.band3night));
			// text.setBackgroundColor(Color.argb(200, 0, 9, 51));
			llayout.addView(text);

			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(llayout);
			table.addView(row);

			row = new TableRow(this);

			llayout = new LinearLayout(this);

			text = new TextView(this);
			text.setText("12m-10m:");
			text.setTextSize(18);
			// text.setBackgroundColor(Color.argb(200, 51, 0, 0));

			llayout.addView(text);

			text = new TextView(this);
			text.setText("\t\t" + conds.band4day);
			// text.setGravity(1);
			text.setTextSize(18);
			text.setTextColor(ConditionColor(conds.band4day));
			// text.setBackgroundColor(Color.argb(200, 0, 51, 0));
			llayout.addView(text);

			text = new TextView(this);
			text.setText("\t\t" + conds.band4night);
			text.setTextSize(18);
			// text.setGravity(1);
			text.setTextColor(ConditionColor(conds.band4night));
			// text.setBackgroundColor(Color.argb(200, 0, 9, 51));
			llayout.addView(text);

			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(llayout);
			table.addView(row);

			// next section

			row = new TableRow(this);
			text = new TextView(this);
			text.setText(R.string.vhfcondstr);
			text.setTextSize(24);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("Aurora: \t" + conds.vhfaurora);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("6m Es Europe: \t" + conds.eskip6meurope);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);
			
			row = new TableRow(this);
			text = new TextView(this);
			text.setText("4m Es Europe: \t" + conds.eskip4meurope);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);
			
			row = new TableRow(this);
			text = new TextView(this);
			text.setText("2m Es Europe: \t" + conds.eskipeurope);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("2m Es N. America: \t" + conds.eskipnorthamerica);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

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
			text.setText("Geomag Field: \t" + conds.geomagfield);
			text.setTextSize(18);
			row.setPadding(3, 3, 3, 3);
			row.setBackgroundColor(Color.argb(200, 51, 51, 51));
			row.addView(text);
			table.addView(row);

			row = new TableRow(this);
			text = new TextView(this);
			text.setText("Signal Noise: \t" + conds.signalnoise);
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
			text.setText("Updated: " + conds.updated);
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

	public int ConditionColor(String conditionStr) {

		if (conditionStr.equals("Poor")) {
			// if(conditionStr == "Poor") {
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

	public class SolarData {
		public String source, updated, solarflux, aindex, kindex, kindexnt,
				xray, sunspots, protonflux, electronflux, aurora,
				normalization, band1day, band1night, band2day, band2night,
				band3day, band3night, band4day, band4night, vhfaurora,
				eskipeurope, eskipnorthamerica, eskip6meurope, eskip4meurope, geomagfield, signalnoise;

		public SolarData() {
			// do nothing
		}
	}

	public SolarData getSolar() {

		SolarData retval = new SolarData();
		// Log.v("test", "Moo");
		try {

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, "UTF-8");
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setHttpElementCharset(params, "UTF-8");

			String agent = "ham ";

			try {
				ComponentName compName = new ComponentName(this, Solar.class);
				PackageInfo pkgInfo = this.getPackageManager().getPackageInfo(compName.getPackageName(), 0);
				agent += "(v" + pkgInfo.versionName + "-" +  pkgInfo.versionCode + ") ";
			} catch (android.content.pm.PackageManager.NameNotFoundException e) {
				agent += "(version unknown) ";
			}
			
			agent += "for android";

			HttpProtocolParams.setUserAgent(params, agent);

			DefaultHttpClient client = new DefaultHttpClient(params);

			InputStream data = null;

			try {
				HttpGet method = new HttpGet(
						"http://iphone.smerty.com/ham/solar_xml.php");
				// HttpGet method = new HttpGet("http://www.yahoo.com");
				HttpResponse res = client.execute(method);
				data = res.getEntity().getContent();
			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(getBaseContext(), "Network Failure...",
						Toast.LENGTH_SHORT).show();
				return null;
			}

			Document doc = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;

			try {
				db = dbf.newDocumentBuilder();
				doc = db.parse(data);
				// finish();
			} catch (SAXParseException e) {
				e.printStackTrace();
				Toast.makeText(getBaseContext(), "SAXParseException, bad XML?",
						Toast.LENGTH_SHORT).show();
				return null;
				// finish();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				Toast.makeText(getBaseContext(), "SAXException",
						Toast.LENGTH_SHORT).show();
				e.printStackTrace();
				return null;
				// finish();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				Toast.makeText(getBaseContext(),
						"ParserConfigurationException", Toast.LENGTH_SHORT)
						.show();
				e.printStackTrace();
				return null;
			}

			doc.getDocumentElement().normalize();

			retval.source = doc.getElementsByTagName("source").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.updated = doc.getElementsByTagName("updated").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.solarflux = doc.getElementsByTagName("solarflux").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.aindex = doc.getElementsByTagName("aindex").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.kindex = doc.getElementsByTagName("kindex").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.kindexnt = doc.getElementsByTagName("kindexnt").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.xray = doc.getElementsByTagName("xray").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.sunspots = doc.getElementsByTagName("sunspots").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.protonflux = doc.getElementsByTagName("protonflux").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.electronflux = doc.getElementsByTagName("electronflux")
					.item(0).getChildNodes().item(0).getNodeValue();
			retval.aurora = doc.getElementsByTagName("aurora").item(0)
					.getChildNodes().item(0).getNodeValue();
			retval.normalization = doc.getElementsByTagName("normalization")
					.item(0).getChildNodes().item(0).getNodeValue();

			retval.geomagfield = doc.getElementsByTagName("geomagfield")
					.item(0).getChildNodes().item(0).getNodeValue();
			retval.signalnoise = doc.getElementsByTagName("signalnoise")
					.item(0).getChildNodes().item(0).getNodeValue();

			// Toast.makeText(getBaseContext(), boo, Toast.LENGTH_LONG).show();

			NodeList itemNodes = doc.getElementsByTagName(
					"calculatedconditions").item(0).getChildNodes();

			for (int i = 0; i < itemNodes.getLength(); i++) {
				Node itemNode = itemNodes.item(i);
				if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
					// ---convert the Node into an Element---
					Element itemElement = (Element) itemNode;

					// ---get all the <title> element under the <item>
					// element---
					NodeList titleNodes = (itemElement)
							.getElementsByTagName("band");

					// ---convert a Node into an Element---
					Element titleElement = (Element) titleNodes.item(0);

					// ---get all the child nodes under the <title> element---
					NodeList textNodes = ((Node) titleElement).getChildNodes();

					// ---retrieve the text of the <title> element---

					String daynight = (String) titleElement
							.getAttribute("time");
					String bandname = (String) titleElement
							.getAttribute("name");

					if (daynight.equals("day")) {
						// Toast.makeText(getBaseContext(),"Moo?",
						// Toast.LENGTH_SHORT).show();
						if (bandname.equals("80m-40m")) {
							retval.band1day = ""
									+ (textNodes.item(0)).getNodeValue();
						} else if (bandname.equals("30m-20m")) {
							retval.band2day = (textNodes.item(0))
									.getNodeValue();
						} else if (bandname.equals("17m-15m")) {
							retval.band3day = (textNodes.item(0))
									.getNodeValue();
						} else if (bandname.equals("12m-10m")) {
							retval.band4day = (textNodes.item(0))
									.getNodeValue();
						}
					} else if (daynight.equals("night")) {
						if (bandname.equals("80m-40m")) {
							retval.band1night = (textNodes.item(0))
									.getNodeValue();
						} else if (bandname.equals("30m-20m")) {
							retval.band2night = (textNodes.item(0))
									.getNodeValue();
						} else if (bandname.equals("17m-15m")) {
							retval.band3night = (textNodes.item(0))
									.getNodeValue();
						} else if (bandname.equals("12m-10m")) {
							retval.band4night = (textNodes.item(0))
									.getNodeValue();
						}
					} else {
						Toast.makeText(getBaseContext(), daynight,
								Toast.LENGTH_SHORT).show();
					}
				}
			}

			NodeList itemNodesTwo = doc.getElementsByTagName(
					"calculatedvhfconditions").item(0).getChildNodes();

			for (int iTwo = 0; iTwo < itemNodesTwo.getLength(); iTwo++) {
				Node itemNodeTwo = itemNodesTwo.item(iTwo);
				if (itemNodeTwo.getNodeType() == Node.ELEMENT_NODE) {
					// ---convert the Node into an Element---
					Element itemElement = (Element) itemNodeTwo;

					// ---get all the <title> element under the <item>
					// element---
					NodeList titleNodes = (itemElement)
							.getElementsByTagName("phenomenon");

					// ---convert a Node into an Element---
					Element titleElement = (Element) titleNodes.item(0);

					// ---get all the child nodes under the <title> element---
					NodeList textNodes = ((Node) titleElement).getChildNodes();

					// ---retrieve the text of the <title> element---

					String name = (String) titleElement.getAttribute("name");
					String location = (String) titleElement
							.getAttribute("location");

					if (name.equals("vhf-aurora")) {
						// Toast.makeText(getBaseContext(),"Moo?",
						// Toast.LENGTH_SHORT).show();
						retval.vhfaurora = (textNodes.item(0)).getNodeValue();
					} else if (name.equals("E-Skip")) {
						if (location.equals("europe")) {
							retval.eskipeurope = (textNodes.item(0))
									.getNodeValue();
						} else if (location.equals("north_america")) {
							retval.eskipnorthamerica = (textNodes.item(0))
									.getNodeValue();
						} else if (location.equals("europe_6m")) {
							retval.eskip6meurope = (textNodes.item(0))
							.getNodeValue();
						} else if (location.equals("europe_4m")) {
							retval.eskip4meurope = (textNodes.item(0))
							.getNodeValue();
						} 
						
					}
				}

			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return retval;
	}
}
