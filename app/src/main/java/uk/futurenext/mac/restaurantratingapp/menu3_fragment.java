package uk.futurenext.mac.restaurantratingapp;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.google.android.gms.internal.zzip.runOnUiThread;

/**
 * Created by Mac on 12/04/16.
 */
public class menu3_fragment extends Fragment {
    ListGetSet listArray = new ListGetSet();
    Button searchBtn;
    String name;
    private static ArrayList<ListGetSet> initRestaurant;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu3_layout, container, false);
        searchBtn = (Button) rootView.findViewById(R.id.buttonSearch);
        /*
            Run Async On Click
         */
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskRunner().execute();
            }
        });

        return rootView;

    }

    /**
     * Retrieve
     * Provides JSON extract and RunOnUi functionality,
     * extracts all details from JSON object and stores all variables in an ArrayList
     *
     * String for this particular function wasn't formatted for UTF-8 as it does not contain any special characters
     * like spaces.
     */
    public void retrieve() {
        ArrayList<ListGetSet> restaurants = new ArrayList<ListGetSet>();



        URL higeneurl = null;
        final EditText search = (EditText) getActivity().findViewById(R.id.nameSearch);
        String name = search.getText().toString();
        String name_encode = "";
        try {
            name_encode= URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String higene = ("http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=s_name&name=" + name_encode);

        Log.d("higene url", higene);

        try {

            higeneurl = new URL(higene);
                        /*
                            For Dev purposes only
                            Check URL Formatting
                         */
            Log.i("URL", higeneurl.toString());

            HttpURLConnection tc = (HttpURLConnection) higeneurl.openConnection();
            InputStreamReader isr = new InputStreamReader(tc.getInputStream());

            final BufferedReader in = new BufferedReader(isr);
            String line;
            ArrayList<String> listItems = new ArrayList<String>();
            while ((line = in.readLine()) != null) {
                JSONArray ja = new JSONArray(line);
                for (int i = 0; i < ja.length(); i++) {


                    JSONObject jo = (JSONObject) ja.get(i);
                    String bname = (jo.getString("BusinessName"));
                    String add1 = (jo.getString("AddressLine1"));
                    String add2 = (jo.getString("AddressLine2"));
                    String add3 = (jo.getString("AddressLine3"));
                    String rating_val = (jo.getString("RatingValue"));
                    String post = (jo.getString("PostCode"));

                    ListGetSet controlRestaurant = new ListGetSet();

                    controlRestaurant.setVal(bname, add1, add2, add3, rating_val, post);
                    restaurants.add(controlRestaurant);

                    Log.d("Finished execution", "Success");

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
                    TableLayout tl = (TableLayout) getActivity().findViewById(R.id.nameResults);
                    tl.removeAllViews();



                    for(int i = 0; i < initRestaurant.size(); i++) {
                        Log.d("bname", initRestaurant.get(i).getName());
                        TextView separatorField = new TextView(getActivity().getApplicationContext());
                        TextView businessNameField = new TextView(getActivity().getApplicationContext());
                        TextView address1Field = new TextView(getActivity().getApplicationContext());
                        TextView address3Field = new TextView(getActivity().getApplicationContext());

                        separatorField.setGravity(Gravity.CENTER);
                        businessNameField.setGravity(Gravity.CENTER);
                        address1Field.setGravity(Gravity.CENTER);
                        address3Field.setGravity(Gravity.CENTER);



                        ImageView iv = new ImageView(getActivity().getApplicationContext());
                        TableRow separatorRow = new TableRow(getActivity().getApplicationContext());

                        TableRow row1 = new TableRow(getActivity().getApplicationContext());
                        TableRow row2 = new TableRow(getActivity().getApplicationContext());
                        TableRow row3 = new TableRow(getActivity().getApplicationContext());
                        TableRow row4 = new TableRow(getActivity().getApplicationContext());


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





                        businessNameField.setText(initRestaurant.get(i).getName());
                        businessNameField.setTextSize(14);
                        businessNameField.setTypeface(null, Typeface.BOLD);

                        address1Field.setText(initRestaurant.get(i).getAdd1() + ", " + initRestaurant.get(i).getAdd2());
                        address1Field.setTextSize(12);
                        address1Field.setTypeface(null, Typeface.ITALIC);

                        address3Field.setText(initRestaurant.get(i).getAdd3() + ", " + initRestaurant.get(i).getPost());
                        address3Field.setTextSize(12);

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


                         /*
                            All rows are added to the table view
                         */
                        tl.addView(separatorRow);
                        tl.addView(row1);
                        tl.addView(row2);
                        tl.addView(row3);
                        tl.addView(row4);

                    }
                }
            });
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
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }
}
