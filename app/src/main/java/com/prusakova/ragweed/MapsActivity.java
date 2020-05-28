package com.prusakova.ragweed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.clustering.ClusterManager;
import com.prusakova.ragweed.activities.AboutLocationActivity;
import com.prusakova.ragweed.activities.AddPointActivity;
import com.prusakova.ragweed.activities.AddTrackerActivity;
import com.prusakova.ragweed.activities.LocationViewActivity;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.MarkerClusterItem;
import com.prusakova.ragweed.model.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements  OnMapReadyCallback {


    private SupportMapFragment supportMapFragment;

    private boolean mLocationPermissionGranted;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private List<com.prusakova.ragweed.model.Location> locationList;
    LocationAdapter.RecyclerViewClickListener listener;
    private LocationAdapter locationAdapter;
    private Api apiInterface;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
     List<Double> Lat = new ArrayList<Double>();
     List<Double> Lng = new ArrayList<Double>();
     List<Integer> LocId = new ArrayList<>();
     List<String> LocAddress = new ArrayList<>();
     List<String> LocPoint = new ArrayList<>();
     List<String> LocImage = new ArrayList<>();
     List<String> LocDescription = new ArrayList<>();
     List<String> LocDate = new ArrayList<>();

    private Location mLastKnownLocation;
    private ImageView mGps;
    private Toolbar toolbar;
    private AutocompleteSupportFragment autocompleteFragment;
    private ClusterManager<MarkerClusterItem> clusterManager;
    private MarkerClusterItem  clusterItem;


    private Marker mLoc;
    int AUTOCOMPLETE_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        mGps = findViewById(R.id.ic_gps);



//        autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//        Places.initialize(getApplicationContext(), getString(R.string.google_api_key));

//          AutoComplete();

    }

    public void fetchLocation(){

        apiInterface = ApiClient.getClient().create(Api.class);

        Call<List<com.prusakova.ragweed.model.Location>> call = apiInterface.getLocation();
        call.enqueue(new Callback<List<com.prusakova.ragweed.model.Location>>() {
            @Override
            public void onResponse(Call<List<com.prusakova.ragweed.model.Location>> call, Response<List<com.prusakova.ragweed.model.Location>> response) {
                locationList = response.body();
                for(int i = 0; i < locationList.size();i++){
                    String l = locationList.get(i).getLoc_lat_lng().substring(10,locationList.get(i).getLoc_lat_lng().length()-1);
                    String[] latlong = l.split(",");

                    double lat = Double.parseDouble(latlong[0]);
                    double lng = Double.parseDouble(latlong[1]);

                    Lat.add(lat);
                    Lng.add(lng);
                    String names = locationList.get(i).getLoc_name();
                    String[] name = names.split(",");
                    LocAddress.add(name[0]);
                    LocPoint.add(locationList.get(i).getLoc_point());
                    LocImage.add(locationList.get(i).getLoc_photo());
                    LocId.add(locationList.get(i).getLocation_id());
                    LocDate.add(locationList.get(i).getLoc_date());
                    LocDescription.add(locationList.get(i).getLoc_description());

                }
                setUpClusterer(Lat,Lng,LocAddress,LocPoint,LocId,LocDescription,LocDate,LocImage);


            }

            @Override
            public void onFailure(Call<List<com.prusakova.ragweed.model.Location>> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }




//    public void AutoComplete(){
//        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG, Place.Field.NAME);
//        Intent intent = new Autocomplete.IntentBuilder(
//                AutocompleteActivityMode.OVERLAY, fields)
//                .build(this);
//        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
//
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.NAME));
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(@NonNull Place place) {
//
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getAddress() + ", " + place.getLatLng());
//            }
//
//            @Override
//            public void onError(@NonNull Status status) {
//                Log.i(TAG, "An error occurred: " + status);
//            }
//        });
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
//        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                Place place = Autocomplete.getPlaceFromIntent(data);
////                mMap.moveCamera(CameraUpdateFactory
////                        .newLatLngZoom(place.getLatLng(), DEFAULT_ZOOM));
////                MarkerOptions options = new MarkerOptions()
////                        .position(place.getLatLng())
////                        .title(place.getName()+ " " + place.getAddress());
////                mMap.addMarker(options);
//                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
//            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//                Status status = Autocomplete.getStatusFromIntent(data);
//                Log.i(TAG, status.getStatusMessage());
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//        }
//    }




    private void init(){
        Log.d(TAG, "init: initializing");

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });


        hideSoftKeyboard();

    }



    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        fetchLocation();

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        init();



    }

    private void setUpClusterer(List<Double> listOne, List<Double> listTwo,List<String> info, List<String> snip,
                                 List<Integer> id, List<String> locDescription, List<String> date, List<String> locImage) {

        clusterManager = new ClusterManager<MarkerClusterItem>(this, mMap);

        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        final MarkerClusterRenderer renderer = new MarkerClusterRenderer(this, mMap, clusterManager);
        clusterManager.setRenderer(renderer);
        clusterManager.getMarkerCollection()
                .setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(this)));
        mMap.setInfoWindowAdapter(clusterManager.getMarkerManager());
        addItems(listOne,listTwo,info,snip,id,locDescription, date, locImage);
        clusterManager.setOnClusterItemInfoWindowClickListener(
                new ClusterManager.OnClusterItemInfoWindowClickListener<MarkerClusterItem>() {
                    @Override public void onClusterItemInfoWindowClick(MarkerClusterItem stringClusterItem) {
                        String title = stringClusterItem.getTitle();
                        String point = stringClusterItem.getSnippet();
                        int id = stringClusterItem.getmId();
                        String desc = stringClusterItem.getmDescription();
                        String date = stringClusterItem.getmDate();
                        String image = stringClusterItem.getmPhoto();
                        Intent i = new Intent(MapsActivity.this, AboutLocationActivity.class);
                        i.putExtra("loc_name", title);
                        i.putExtra("loc_point", point);
                        i.putExtra("location_id",id);
                        i.putExtra("loc_description", desc);
                        i.putExtra("loc_date", date);
                        i.putExtra("loc_photo",image);
                        startActivity(i);
                    }
                });

        mMap.setOnInfoWindowClickListener(clusterManager);



    }

    private void addItems(List<Double> listLan, List<Double> listLng, List<String> info, List<String> listSnip,
                           List<Integer> locId, List<String> locDescription, List<String> locDate, List<String> locImage) {
        if (!listLan.isEmpty() && !listLng.isEmpty()) {
            double lat = listLan.get(0);
            double lng = listLng.get(0);
            String name = info.get(0);
            String snippet = listSnip.get(0);
            String img = locImage.get(0);
            int id = locId.get(0);
            String desc = locDescription.get(0);
            String date = locDate.get(0);
            for (int i = 0; i < listLan.size(); i++) {
                lat = listLan.get(i);
                lng = listLng.get(i);
                name = info.get(i);
                snippet = listSnip.get(i);
                img = locImage.get(i);
                id = locId.get(i);
                desc = locDescription.get(i);
                date = locDate.get(i);
//                Picasso.with(this).load(img).into(image);

                Log.i("Location:", listLan.get(i).toString());
                MarkerClusterItem infoWindowItem = new MarkerClusterItem(lat, lng,name,snippet,id, desc,date,img);
                clusterManager.addItem(infoWindowItem);

            }
        }

    }




    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {

        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            String userPhoto = SharedPref.getInstance(MapsActivity.this).LoggedInUserPhoto();


                            if (mLastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                mLoc = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()))
                                        .title(SharedPref.getInstance(MapsActivity.this).LoggedInUser()));
                                mLoc.setTag(0);

                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }

    }



    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }




}



