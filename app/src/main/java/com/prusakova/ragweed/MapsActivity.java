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
import com.google.maps.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.prusakova.ragweed.activities.AboutLocationActivity;
import com.prusakova.ragweed.activities.AddPointActivity;
import com.prusakova.ragweed.activities.AddTrackerActivity;
import com.prusakova.ragweed.activities.LocationViewActivity;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.MarkerClusterItem;
import com.prusakova.ragweed.model.PolylineData;
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

public class MapsActivity extends AppCompatActivity implements
        GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback, GoogleMap.OnPolylineClickListener {


    private SupportMapFragment supportMapFragment;

    private boolean mLocationPermissionGranted;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private List<com.prusakova.ragweed.model.Location> locationList;
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
     private List<PolylineData> mPolylineData = new ArrayList<>();

    private Location mLastKnownLocation;
    private ImageView mGps;
    private Toolbar toolbar;
    private AutocompleteSupportFragment autocompleteFragment;
    private ClusterManager<MarkerClusterItem> clusterManager;
    private MarkerClusterItem  clusterItem;
    private GeoApiContext mGeoApiContext = null;
    String user = null;

    private Marker mLoc;
    int AUTOCOMPLETE_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        mGps = findViewById(R.id.ic_gps);
        if(mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key))
                    .build();
        }


        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));

          AutoComplete();
          user = SharedPref.getInstance(MapsActivity.this).LoggedInUser();

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




    public void AutoComplete(){
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG, Place.Field.NAME);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG,Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                Log.i(TAG, "Place: " + place.getName() + ", " + place.getAddress() + ", " + place.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                mMap.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(place.getLatLng(), DEFAULT_ZOOM));
                MarkerOptions options = new MarkerOptions()
                        .position(place.getLatLng())
                        .title(place.getAddress()).icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    mMap.addMarker(options);
                mMap.setOnInfoWindowClickListener(this);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getLatLng());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }




    private void init(){
        Log.d(TAG, "init: initializing");

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });



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
    mMap.setOnPolylineClickListener(this);


    }


    @Override
    public void onInfoWindowClick(final Marker marker) {
        if(marker.getTitle().equals(user)){
            marker.hideInfoWindow();
        }
        else{

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Створити маршрут до " + marker.getTitle())
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            calculateDirections(marker);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
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
                                        .title(user));
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


    private void calculateDirections(Marker marker){
        Log.d(TAG, "calculateDirections: calculating directions.");

        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                marker.getPosition().latitude,
                marker.getPosition().longitude
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(true);
        directions.origin(
                new com.google.maps.model.LatLng(
                        mLastKnownLocation.getLatitude(),
                        mLastKnownLocation.getLongitude()
                )
        );
        Log.d(TAG, "calculateDirections: destination: " + destination.toString());
        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d(TAG, "onResult: routes: " + result.routes[0].toString());
                Log.d(TAG, "onResult: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());
                addPolylinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "onFailure: " + e.getMessage() );

            }
        });
    }


    private void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: result routes: " + result.routes.length);

                if(mPolylineData.size() > 0){
                    for(PolylineData polylineData: mPolylineData){
                        polylineData.getPolyline().remove();
                    }
                    mPolylineData.clear();
                    mPolylineData = new ArrayList<>();
                }
                for(DirectionsRoute route: result.routes){
                    Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for(com.google.maps.model.LatLng latLng: decodedPath){

//                        Log.d(TAG, "run: latlng: " + latLng.toString());

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    polyline.setColor(ContextCompat.getColor(MapsActivity.this, R.color.quantum_grey));
                    polyline.setClickable(true);
                    mPolylineData.add(new PolylineData(polyline,route.legs[0]));

                }
            }
        });
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

        for(PolylineData polylineData: mPolylineData){
            if(polyline.getId().equals(polylineData.getPolyline().getId())){
                polylineData.getPolyline().setColor(ContextCompat.getColor(this,R.color.colorAccent));
                polylineData.getPolyline().setZIndex(1);
            } else{
                polylineData.getPolyline().setColor(ContextCompat.getColor(this,R.color.quantum_grey));
                polylineData.getPolyline().setZIndex(0);
            }
        }
    }
}



