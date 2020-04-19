
//Pham Thi Hue
//S1839331
package com.example.trafficscotland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.view.View;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Locale;

import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener , View.OnClickListener {

    private Spinner Spinner1;
    private TextView list;
    private EditText searchText;
    private Button searchButton;
    private String result, show;
    private ListView listView, listView1;
    // Traffic Scotland URLs
    private String urlRoadWorks = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String urlPlanRoadWorks = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String urlCurrentIncidents = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String URL;
    private ObjectAdapter adapter;
    ArrayList<objectTrafficScotland> objectArray = new ArrayList<>();
    private Date startDate, endDate;
    private String startDateString, endDateString, startDateString1, endDateString1;
    private int duration;
    private TextView color;
    private String positionC;
    private Boolean isClickedDummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner1 = (Spinner) findViewById(R.id.spinner);
        Spinner1.setOnItemSelectedListener(this);
       // button = (Button) findViewById(R.id.submitButton);
       // button.setOnClickListener(this);

        color = (TextView) findViewById(R.id.duration);
        //color.setText("");
        //color.setTextColor(Color.YELLOW);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add("Select a type of road work");
        categories.add("Current Incidents");
        categories.add("Road Works");
        categories.add("Plan Road Works");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        Spinner1.setAdapter(dataAdapter);
        //list = (TextView) findViewById(R.id.list);
        listView = (ListView) findViewById(R.id.listView);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);
        searchButton.setOnClickListener(this);


    }

    public void populateListView(ArrayList<objectTrafficScotland> objectArray) {

        adapter = new ObjectAdapter(this, 0, objectArray);
        listView.setAdapter(adapter);
        if (objectArray.size()==0)
        {
            Toast.makeText(MainActivity.this, "No results are found!",  Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, "There are " + objectArray.size()+" results are found!",  Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                    Intent myintent = new Intent(view.getContext(), MapActivity.class);
                    myintent.putExtra("title", objectArray.get(position).title);
                    myintent.putExtra("description", objectArray.get(position).description);
                    //myintent.putExtra("link", objectArray.get(position).link);
                    myintent.putExtra("geopoint", objectArray.get(position).georsspoint);
                    myintent.putExtra("date", objectArray.get(position).date);

                    int duration = caculateDuration(objectArray.get(position).description);
                    myintent.putExtra("duration", (String) String.valueOf(duration));

                    startActivityForResult(myintent, position);


                }
        });

    }


    private ArrayList<objectTrafficScotland> parseData(String dataToParse) {
        try {
            objectArray = new ArrayList<objectTrafficScotland>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(dataToParse));
            int eventType = xpp.getEventType();

            int i = 0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                objectTrafficScotland obTS = new objectTrafficScotland();

                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equalsIgnoreCase("Title")) {
                        String temp = xpp.nextText();

                        obTS.setTitle(temp);

                    }
                    eventType = xpp.next();
                    if (xpp.getName().equalsIgnoreCase("Description")) {
                        String temp = xpp.nextText();
                        temp=temp.replace("<br />","\n");
                        obTS.setDescription(temp);
                    }
                    eventType = xpp.next();
                    if (xpp.getName().equalsIgnoreCase("link")) {
                        String temp = xpp.nextText() ;
                        //obTS.setLink(temp);
                    }
                    eventType = xpp.next();
                    if (xpp.getName().equalsIgnoreCase("georsspoint")) {
                        String temp = xpp.nextText();
                        obTS.setGeorsspoint(temp);
                    }
                    eventType = xpp.next();
                    if (xpp.getName().equalsIgnoreCase("pubDate")) {
                        String temp = xpp.nextText();
                        obTS.setDate(temp);
                    }
                    objectArray.add(obTS);
                }


                i++;

                // Get the next event
                eventType = xpp.next();
            } // End of while

        } catch (XmlPullParserException ae1) {
            Log.e("MyTag", "Parsing error" + ae1.toString());
        } catch (IOException ae1) {
            Log.e("MyTag", "IO error during parsing");
        }


        return objectArray;
    }
    public int caculateDuration (String des)
    {
        DateFormat formatter = new SimpleDateFormat("dd MMMMMMM yyyy", Locale.US);

        if (des.toLowerCase().contains("start date"))
        {
            startDateString = StringUtils.substringBetween(des, "Start Date:", "00:00");
            startDateString1 = StringUtils.substringBetween(startDateString, ",", "-");
            endDateString = StringUtils.substringBetween(des, "End Date:", "00:00");
            endDateString1 = StringUtils.substringBetween(endDateString, ",", "-");
        }
        else
        {
            return 0 ;
        }


        try {
            startDate = (Date)formatter.parse(startDateString1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            endDate = (Date)formatter.parse(endDateString1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calStart.setTime(startDate);
        String formatedDate1 = calStart.get(Calendar.DATE) + "/" +
                (calStart.get(Calendar.MONTH) + 1) +
                "/" +         calStart.get(Calendar.YEAR);
        calEnd.setTime(endDate);
        String formatedDate2 = calEnd.get(Calendar.DATE) + "/" +
                (calEnd.get(Calendar.MONTH) + 1) +
                "/" +         calEnd.get(Calendar.YEAR);
        System.out.println("Start Date : " + formatedDate1);
        System.out.println("End Date : " + formatedDate2);

        int difference=
                ((int)((endDate.getTime()/(24*60*60*1000))
                        -(int)(startDate.getTime()/(24*60*60*1000))));
        return difference;
    }
    public void onClick(View v) {

        if ( v== searchButton)
        {
            startProgressSearch(objectArray);
        }
    }
    private void startProgressSearch(ArrayList<objectTrafficScotland> objectArray) {

        new Thread(new SearchTask(objectArray)).start();

    }

    private class SearchTask implements Runnable {

        public SearchTask(ArrayList<objectTrafficScotland> objectArray) {
        }

        @Override
        public void run() {

            String search = searchText.getText().toString().toLowerCase();

            ArrayList<objectTrafficScotland> searchedArray = new  ArrayList<objectTrafficScotland>();

            int i = 0;
            while (objectArray.size() > i)
            {
                String title = objectArray.get(i).title;

                if(title.toLowerCase().contains(search))
                {
                    searchedArray.add(objectArray.get(i));
                }

                i++;

            }
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (searchedArray.size()==0)
                    {
                        Toast.makeText(MainActivity.this, "Not found!",  Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "There are " + searchedArray.size()+" results are found!",  Toast.LENGTH_SHORT).show();
                    }

                    populateListView(searchedArray);

                }
            });



        }
    }
    public void startProgress(String URL) {
        // Run network access on a separate thread;

        new Thread(new Task(URL)).start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

        String item = parent.getItemAtPosition(position).toString();
        if (item.equals("Select")) {
            Toast.makeText(parent.getContext(), "Please select a tye of road work", Toast.LENGTH_LONG).show();
        }

        else if (item.equals("Current Incidents")) {
            // do your stuff
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            URL = urlCurrentIncidents;
            startProgress(URL);

        } else if (item.equals("Road Works")) {
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            URL = urlRoadWorks;
            startProgress(URL);
        } else if (item.equals("Plan Road Works")) {
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            URL = urlPlanRoadWorks;
            startProgress(URL);
        }



    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class Task implements Runnable {
            private String url;

            public Task(String aurl) {
                url = aurl;
            }

        @Override
        public void run() {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                result = "";
                while ((inputLine = in.readLine()) != null) {

                    if (!inputLine.contains("<?xml") && !inputLine.contains("<rss") && !inputLine.contains("</rss>") &&
                            !inputLine.contains("<title>Traffic") && !inputLine.contains("<lastBuildDate>") && !inputLine.contains("<docs>")
                            && !inputLine.contains("<generator") && !inputLine.contains("<ttl>") && !inputLine.contains("<description>Current incidents")
                            && !inputLine.contains("<description>Roadworks currently") && !inputLine.contains("<description>Future roadworks")
                            && !inputLine.contains("<link>https://trafficscotland.org"))
                    {

                        result = result + inputLine;
                    }


                }
                result = result.replace("georss:point", "georsspoint");
                result = result.replace("<channel>", "");
                result = result.replace("</channel>", "");
                result = result.replace("<item>", "");
                result = result.replace("</item>", "");
                result = result.replace("<copyright />", "");
                result = result.replace("<managingEditor />", "");
                result = result.replace("<language />", "");
                result = result.replace("<webMaster />", "");
                result = result.replace("<rating />", "");
                result = result.replace("<author />", "");
                result = result.replace("<comments />", "");
                //result = result.replace("- 00:00", "");

                result = result.replace("00:00:00 GMT", "");
                result = result.replace("</pubDate>              <description>", "</pubDate>              <title> Unknown</title>              <description>");
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception");
            }

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    objectArray = parseData(result);
                    populateListView(objectArray);


                }
            });
        }


    }

}