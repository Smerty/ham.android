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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class PSKReporter extends Activity {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    TextView text = new TextView(this);
    text.setText("PSKReport!");

    ScrollView sv = new ScrollView(this);

    LinearLayout ll = new LinearLayout(this);

    ll.addView(text);

    this.getSolar(ll);

    sv.addView(ll);

    setContentView(sv);
  }

  public class PSKReport {
    public String rxCallsign, rxCountry, rxGrid, txCallsign, txCountry, txGrid,
        Timestamp, Datestr, Frequency, Band, Mode;

    public PSKReport() {
      // do nothing
    }
  }

  public void getSolar(LinearLayout retval) {

    // LinearLayout retval = new LinearLayout(this);

    TextView text1 = new TextView(this);
    text1.setText("PSKReport1!");
    retval.addView(text1);

    // SolarData retval = new SolarData();
    // Log.v("test", "Moo");
    try {

      HttpParams params = new BasicHttpParams();
      HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(params, "UTF-8");
      HttpProtocolParams.setUseExpectContinue(params, true);
      HttpProtocolParams.setHttpElementCharset(params, "UTF-8");
      HttpProtocolParams.setUserAgent(params, "ham 1.1 for android");

      DefaultHttpClient client = new DefaultHttpClient(params);

      InputStream data = null;

      try {
        HttpGet method = new HttpGet(
            "http://www.pskreporter.info/query?noactive=1&rptlimit=300&flowStartSeconds=-86400&rronly=1&receiverCallsign=KI6KIK");
        HttpResponse res = client.execute(method);
        data = res.getEntity().getContent();
      } catch (IOException e) {
        e.printStackTrace();
        Toast.makeText(getBaseContext(), "Network Failure...",
            Toast.LENGTH_SHORT).show();
        return;
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
        return;
        // finish();
      } catch (SAXException e) {
        // TODO Auto-generated catch block
        Toast.makeText(getBaseContext(), "SAXException", Toast.LENGTH_SHORT)
            .show();
        e.printStackTrace();
        return;
        // finish();
      } catch (ParserConfigurationException e) {
        // TODO Auto-generated catch block
        Toast.makeText(getBaseContext(), "ParserConfigurationException",
            Toast.LENGTH_SHORT).show();
        e.printStackTrace();
        return;
      }

      TextView text2 = new TextView(this);
      text2.setText("PSKReport2!");
      retval.addView(text2);

      doc.getDocumentElement().normalize();

      // Toast.makeText(getBaseContext(),"Woot",
      // Toast.LENGTH_SHORT).show();

      NodeList itemNodes = null;

      // NodeList itemNodes =
      // doc.getElementsByTagName("receptionReport").item(0).getChildNodes();
      // NodeList itemNodes = doc.getElementsByTagName("receptionReport");
      // Toast.makeText(getBaseContext(),itemNodes.getLength(),
      // Toast.LENGTH_SHORT).show();

      if (itemNodes == null || itemNodes.getLength() == 0) {
        return;
      } else {
        Log.v("moot", "not empty");
      }

    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }
}
