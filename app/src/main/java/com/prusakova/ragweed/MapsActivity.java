package com.prusakova.ragweed;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.prusakova.ragweed.activities.AboutLocationActivity;
import com.prusakova.ragweed.api.Api;
import com.prusakova.ragweed.api.ApiClient;
import com.prusakova.ragweed.api.SharedPref;
import com.prusakova.ragweed.model.MarkerClusterItem;
import com.prusakova.ragweed.model.PolylineData;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnPolylineClickListener {


    SupportMapFragment supportMapFragment;
    Api apiInterface;
    private Toolbar toolbar;
    private boolean mLocationPermissionGranted;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private List<com.prusakova.ragweed.model.Location> locationList;
    private FusedLocationProviderClient mFusedLocationProviderClient;
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
    private AutocompleteSupportFragment autocompleteFragment;
    private ClusterManager<MarkerClusterItem> clusterManager;
    private GeoApiContext mGeoApiContext = null;
    String user = null;
    Marker selectedMarker = null;
    private ArrayList<Marker> mTripMarker = new ArrayList<>();
    Marker marker = null;
    private Marker mLoc;
    String userPhoto;
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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));


          user = SharedPref.getInstance(MapsActivity.this).LoggedInUser();
          userPhoto = SharedPref.getInstance(MapsActivity.this).LoggedInUserPhoto();

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
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                AutoComplete();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void AutoComplete(){
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG, Place.Field.NAME);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);


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
                        .title(place.getAddress()).icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                if(marker != null){
                    marker.remove();
                    marker = mMap.addMarker(options);
                    resetMarker();
                    selectedMarker = marker;
                    calculateDirections(marker);
                    mMap.setOnPolylineClickListener(this);
                } else {
                    marker = mMap.addMarker(options);
                    resetMarker();
                    selectedMarker = marker;
                    calculateDirections(marker);

                    mMap.setOnPolylineClickListener(this);
                }
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
        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();
        init();




    }




    public  Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        Picasso.with(this).load(userPhoto).into(markerImage);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
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

                Log.i("Location:", listLan.get(i).toString());
                MarkerClusterItem infoWindowItem = new MarkerClusterItem(lat, lng,name,snippet,id, desc,date,img);
                clusterManager.addItem(infoWindowItem);

            }
        }

    }


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

                            if (mLastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                mLoc = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()))
                                        .icon(BitmapDescriptorFactory.fromBitmap(
                                                createCustomMarker(MapsActivity.this,R.drawable.picture_brain,user)))
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
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


    private void removeMarker(){
        for(Marker marker : mTripMarker){
            marker.remove();
        }
    }

    private void resetMarker(){
        if(selectedMarker != null){
            selectedMarker.setVisible(true);
            selectedMarker = null;
            removeMarker();
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
                double duration = 99999999;
                for(DirectionsRoute route: result.routes){
                    Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    for(com.google.maps.model.LatLng latLng: decodedPath){

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    polyline.setColor(ContextCompat.getColor(MapsActivity.this, R.color.quantum_grey));
                    polyline.setClickable(true);
                    mPolylineData.add(new PolylineData(polyline,route.legs[0]));


                    double tempduratio = route.legs[0].duration.inSeconds;
                    if(tempduratio < duration){
                        duration = tempduratio;
                        onPolylineClick(polyline);
                        zoomRoute(polyline.getPoints());
                    }
                    selectedMarker.setVisible(false);

                }
            }
        });
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

        int index = 0;
        for(PolylineData polylineData: mPolylineData){
            index++;
            if(polyline.getId().equals(polylineData.getPolyline().getId())){
                polylineData.getPolyline().setColor(ContextCompat.getColor(this,R.color.colorAccent));
                polylineData.getPolyline().setZIndex(1);

                LatLng endLocation = new LatLng(
                        polylineData.getLeg().endLocation.lat,
                        polylineData.getLeg().endLocation.lng
                );
                Marker marker = mMap.addMarker(new MarkerOptions()
                .position(endLocation)
                .title("Маршрут № "+ index)
                .snippet("Тривалість: " + polylineData.getLeg().duration).icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))));
                marker.showInfoWindow();
                mTripMarker.add(marker);
            } else{
                polylineData.getPolyline().setColor(ContextCompat.getColor(this,R.color.quantum_grey));
                polylineData.getPolyline().setZIndex(0);
            }
        }
    }


    public void zoomRoute(List<LatLng> latLngList){
        if (mMap == null || latLngList == null || latLngList.isEmpty())  return;

        LatLngBounds.Builder boudsBuilder = new LatLngBounds.Builder();
        for(LatLng latLngPoint : latLngList){
            boudsBuilder.include(latLngPoint);

            int routePaddin = 120;
            LatLngBounds latLngBounds = boudsBuilder.build();
            mMap.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(latLngBounds,routePaddin),
                    600,
                    null
            );

        }
    }



}



