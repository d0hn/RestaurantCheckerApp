package maps.layout;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import uk.futurenext.mac.restaurantratingapp.ListGetSet;
import uk.futurenext.mac.restaurantratingapp.R;
import uk.futurenext.mac.restaurantratingapp.menu1_fragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ListGetSet listArray = new ListGetSet();
    private static ArrayList<ListGetSet> initRestaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override

    public void onMapReady(GoogleMap googleMap) {
        /*
            Get restaurant details from menu1_fragment
            Receives the array list

         */
        initRestaurant = menu1_fragment.getRestaurantDetails();

        if(initRestaurant != null) {
            /*
                Initialize Google Maps
             */
            mMap = googleMap;
            /*
                Get icon, default is just a menu_send
             */
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_send);

            /*
                Custom icons that I have created for map pins
                Each pin corresponds to the restaurant rating, however I noticed sometimes they switch their ratings e.g. sometimes when exempt pin is clicked it
                displays 5 or vice versa.
             */
            for(int i = 0; i < initRestaurant.size(); i++) {
                if(initRestaurant.get(i).getRating_val().equals("5"))
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.five_pin);
                else if(initRestaurant.get(i).getRating_val().equals("4"))
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.four_pin);
                else if (initRestaurant.get(i).getRating_val().equals("3"))
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.three_pin);
                else if (initRestaurant.get(i).getRating_val().equals("2"))
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.two_pin);
                else if (initRestaurant.get(i).getRating_val().equals("1"))
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.one_pin);
                else if (initRestaurant.get(i).getRating_val().equals("0"))
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.zero_pin);
                else if (initRestaurant.get(i).getRating_val().equals("-1"))
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.expin);
                else {

                }
                /*
                    Get latitude of restaurant and combine them together at newpos LatLng variable.
                 */
                double Latitude = Double.parseDouble(initRestaurant.get(i).getLatitude());
                double Longitude = Double.parseDouble(initRestaurant.get(i).getLongitude());


                /*
                    Set all markers
                    Title is the name of the restaurant
                    Snippet contais address and postcode
                 */
                LatLng newpos = new LatLng(Latitude, Longitude);
                Marker newmarker = mMap.addMarker(new MarkerOptions()
                                .position(newpos)
                                .title(initRestaurant.get(i).getName())
                                .snippet(initRestaurant.get(i).getAdd1() + " " + initRestaurant.get(i).getAdd2() + " " + initRestaurant.get(i).getAdd3()
                                + " " + initRestaurant.get(i).getPost())
                                .icon(icon)
                );

            }
            /*
                Get position of the first restaurant and center the map that way. Set zoom to 13.

             */
            double frstLat = Double.parseDouble(initRestaurant.get(0).getLatitude());
            double frstLon = Double.parseDouble(initRestaurant.get(0).getLongitude());
            LatLng campos = new LatLng (frstLat, frstLon);

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campos, 13));


        }
    }

}
