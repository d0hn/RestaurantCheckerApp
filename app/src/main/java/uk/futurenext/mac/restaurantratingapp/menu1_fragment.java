package uk.futurenext.mac.restaurantratingapp;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.*;
import android.content.pm.LabeledIntent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.content.pm.PackageManager;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.google.android.gms.internal.zzip.runOnUiThread;

/**
 * Created by Mac on 12/04/16.
 */
public class menu1_fragment extends Fragment{
    /*
        Initialize all variables / obj / views
     */
    public static Object obj = new Object();
    private static ArrayList<ListGetSet> initRestaurant;
    TableLayout tl;

    double lat = 0.0;
    double lon = 0.0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu1_layout, container, false);

        final ListGetSet listget = new ListGetSet();


        Context context = getActivity().getApplicationContext();
        LocationManager locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        tl = (TableLayout) rootView.findViewById(R.id.gpsResults);

        PackageManager pm = context.getPackageManager();
        int checkPerm = pm.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, context.getPackageName());

        if(checkPerm == PackageManager.PERMISSION_GRANTED) {


            locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {

                @Override

                public void onLocationChanged(Location location) {
                    Log.i("DEV", "onLocationChanged");

                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    /*
                        Execute the AsyncTask
                     */
                    new AsyncTaskRunner().execute();
                    }


                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) { Log.i("DEV", "onStatusChanged"); }

                @Override
                public void onProviderEnabled(String provider) { Log.i("DEV", "onProviderEnabled"); }

                @Override
                public void onProviderDisabled(String provider) { Log.i("DEV", "onProviderDisabled"); }

            });

        }
        else {
            // Permission denied :(
        }


        return rootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Retrieve
     * Provides JSON extract and RunOnUi functionality,
     * extracts all details from JSON object and stores all variables in an ArrayList
     *
     * String for this particular function wasn't formatted for UTF-8 as it does not contain any special characters
     * like spaces.
     *
     * Double variable is formatted to include only 2 decimal places.
     *
     *
     */
    public void retrieve() {

        /*

         */
        ArrayList<ListGetSet> restaurants = new ArrayList<ListGetSet>();

        URL higeneurl = null;

        String higene = ("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=s_loc&lat=" + lat + "&long=" + lon);

        try {


            higeneurl = new URL(higene);

            Log.i("URL", higeneurl.toString());

            HttpURLConnection tc = (HttpURLConnection) higeneurl.openConnection();
            InputStreamReader isr = new InputStreamReader(tc.getInputStream());

            final BufferedReader in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                JSONArray ja = new JSONArray(line);
                for (int i = 0; i < ja.length(); i++) {


                    JSONObject jo = (JSONObject) ja.get(i);
                    String name = (jo.getString("BusinessName"));
                    String add1 = (jo.getString("AddressLine1"));
                    String add2 = (jo.getString("AddressLine2"));
                    String add3 = (jo.getString("AddressLine3"));
                    String rating_val = (jo.getString("RatingValue"));
                    String post = (jo.getString("PostCode"));
                    String lon = (jo.getString("Longitude"));
                    String lat = (jo.getString("Latitude"));
                    String distance = (jo.getString("DistanceKM"));


                    Double distance_dbl = Double.parseDouble(distance);

                    DecimalFormat df = new DecimalFormat("0.00");

                    String finalDis = df.format(distance_dbl);

                    ListGetSet controlRestaurant = new ListGetSet();

                    controlRestaurant.setGpsVal(name, add1, add2, add3, rating_val, post, lat, lon, finalDis);

                    restaurants.add(controlRestaurant);

                    Log.d("Status", "Success");

                }


            }
            initRestaurant = restaurants;
            /**
             * runOnUiThread allows function to modify thread that's responsible for handling UI requests.
             *
             * If table is null and it has child elements then table erases all views. This prevents the results from stacking up on each other
             * resulting in huge lists.
             *
             * All TextViews are initialised. Gravity on all TextView fields is set to Center for more aesthetic design.
             *
             * TableRows are initialised.
             *
             *
             */
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(tl != null)
                        tl.removeAllViews();

                    for(int i = 0; i < initRestaurant.size(); i++) {

                        TextView separatorField = new TextView(getActivity().getApplicationContext());
                        TextView businessNameField = new TextView(getActivity().getApplicationContext());
                        TextView address1Field = new TextView(getActivity().getApplicationContext());
                        TextView address3Field = new TextView(getActivity().getApplicationContext());
                        TextView dashField = new TextView(getActivity().getApplicationContext());
                        TextView distanceField = new TextView(getActivity().getApplicationContext());

                        separatorField.setGravity(Gravity.CENTER);
                        businessNameField.setGravity(Gravity.CENTER);
                        address1Field.setGravity(Gravity.CENTER);
                        address3Field.setGravity(Gravity.CENTER);
                        dashField.setGravity(Gravity.CENTER);
                        distanceField.setGravity(Gravity.CENTER);

                        ImageView iv = new ImageView(getActivity().getApplicationContext());
                        TableRow separatorRow = new TableRow(getActivity().getApplicationContext());


                        TableRow row1 = new TableRow(getActivity().getApplicationContext());
                        TableRow row2 = new TableRow(getActivity().getApplicationContext());
                        TableRow row3 = new TableRow(getActivity().getApplicationContext());
                        TableRow row4 = new TableRow(getActivity().getApplicationContext());
                        TableRow row5 = new TableRow(getActivity().getApplicationContext());

                        separatorField.setText(" \n \n");

                        /*
                            If statement checks the ratings of a given restaurant and given the rating it chooses which image to display
                            for higene ratings. 0 and -1 use the same image as 0 rating was not provided with png sprites.

                         */
                        if(initRestaurant.get(i).getRating_val().equalsIgnoreCase("-1")){
                            iv.setImageResource(R.drawable.fhrs_exempt_en_gb);
                        }else if(initRestaurant.get(i).getRating_val().equalsIgnoreCase("0")){
                            iv.setImageResource(R.drawable.fhrs_exempt_en_gb);
                        }else if(initRestaurant.get(i).getRating_val().equalsIgnoreCase("1")){
                            iv.setImageResource(R.drawable.fhrs_1_en_gb);
                        }else if(initRestaurant.get(i).getRating_val().equalsIgnoreCase("2")){
                            iv.setImageResource(R.drawable.fhrs_2_en_gb);
                        }else if(initRestaurant.get(i).getRating_val().equalsIgnoreCase("3")){
                            iv.setImageResource(R.drawable.fhrs_3_en_gb);
                        }else if(initRestaurant.get(i).getRating_val().equalsIgnoreCase("4")){
                            iv.setImageResource(R.drawable.fhrs_4_en_gb);
                        }else if(initRestaurant.get(i).getRating_val().equalsIgnoreCase("5")){
                            iv.setImageResource(R.drawable.fhrs_5_en_gb);
                        }else{

                        }


                        /*
                            Here we set all Layout params for particular fields, e.g. text size and additional fields like KM or spaces.
                         */


                        businessNameField.setText(initRestaurant.get(i).getName());
                        businessNameField.setTextSize(14);
                        businessNameField.setTypeface(null, Typeface.BOLD);

                        address1Field.setText(initRestaurant.get(i).getAdd1() + ", " + initRestaurant.get(i).getAdd2());
                        address1Field.setTextSize(12);
                        address1Field.setTypeface(null, Typeface.ITALIC);

                        address3Field.setText(initRestaurant.get(i).getAdd3() + ", " + initRestaurant.get(i).getPost());
                        address3Field.setTextSize(12);
                        distanceField.setText(initRestaurant.get(i).getDistance() + " KM");

                        /*
                            Set all layout params for rows
                            FILL_PARENT deprecated.
                         */
                        row1.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.FILL_PARENT, 1.0f));
                        row2.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.FILL_PARENT, 1.0f));
                        row3.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.FILL_PARENT, 1.0f));

                        /*
                            All fields are added to the row view

                         */
                        separatorRow.addView(separatorField);

                        row1.addView(businessNameField);
                        row2.addView(address1Field);
                        row3.addView(address3Field);
                        row4.addView(iv);
                        row5.addView(distanceField);

                        /*
                            All rows are added to the table view
                         */
                        tl.addView(separatorRow);
                        tl.addView(row1);
                        tl.addView(row2);
                        tl.addView(row3);
                        tl.addView(row5);
                        tl.addView(row4);


                    }
                }
            });
            /*
                Catch all exceptions and display their stackTrace if something goes wrong.
             */
        } catch (SecurityException se) {
            Log.d("Error", "SE exc");
            se.printStackTrace();
        } catch (IOException io) {
            Log.d("Error", "IO exc");
            io.printStackTrace();
        } catch (JSONException je) {
            Log.d("Error", "JE exc");
            je.printStackTrace();
        }
    }
    /*
        Used for map display.
     */
    public static ArrayList<ListGetSet> getRestaurantDetails(){
        return initRestaurant;
    }

    /*
        AsyncTaskRunner is responsible for threading and working in the background.
        Retrieve function is the only one that works in the background.
     */
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            retrieve();
            return null;
        }
        @Override
        protected void onPostExecute(String result) {}

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(String... text) {}
    }
}
