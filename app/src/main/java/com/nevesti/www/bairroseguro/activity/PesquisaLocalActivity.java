package com.nevesti.www.bairroseguro.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.play.GooglePlayActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PesquisaLocalActivity extends GooglePlayActivity implements OnMapReadyCallback {
    //Define fields for Google API Client
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest request;
    private static final int PERMISSION_ID = 1;

    private Toolbar toolbar;
    private GoogleMap mMap;

    private double latitude;
    private double longitude;

    private EditText edtPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_local);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pesquisa local");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Validar permissões
        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);

        }

        edtPesquisa = findViewById(R.id.edit_pesquisa);

        edtPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Address> list = new ArrayList<Address>();
                try {
                    String endereco = edtPesquisa.getText().toString() ;
                    Geocoder gc = new Geocoder( getApplicationContext(), new Locale( "pt" , "BR" )) ;
                    list = gc.getFromLocationName(endereco , 1 ) ;

                } catch (IOException e) {
                    e.printStackTrace() ;
                }

                if (list != null && list.size() > 0){
                    Address a = list.get(0);

                    latitude = a.getLatitude();
                    longitude = a.getLongitude();

                    onMapReady( mMap );

                }

            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!GPSEnabled) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        startLocation();
    }

    @Override
    protected void onGooglePlayServicesConnected() {
        request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        request.setFastestInterval(2000);

      //  enablePermissions(PERMISSION_ID, true, android.Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    protected void onGooglePlayServicesConnectionSuspended(int errorCode) {
        Toast.makeText(this, "Suspenso: " + errorCode, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onGooglePlayServicesConnectionFailure(int errorCode) {
        Toast.makeText(this, "Falhou: " + errorCode, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        mMap = googleMap;
        LatLng localizacaoAtual = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position( localizacaoAtual ).title("SUA LOCALIZAÇÃO"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng( localizacaoAtual ));
        mMap.setMinZoomPreference(17.0f);
        mMap.setMaxZoomPreference(21.0f);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                mMap.clear();
                MarkerOptions options = new MarkerOptions();
                options.position( latLng );
                mMap.addMarker( options );
                mMap.moveCamera(CameraUpdateFactory.newLatLng( latLng ));

                retornaLocalizacao();
            }
        });

        retornaLocalizacao();

    }

    public void retornaLocalizacao(){
        try {
            List<Address> list = new ArrayList<Address>();
            Geocoder gc = new Geocoder( getApplicationContext(), new Locale( "pt" , "BR" )) ;
            list = gc.getFromLocation(latitude , longitude, 1 ) ;

            if (list != null && list.size() > 0){
                Address a = list.get(0);

                Intent i  = new Intent();
                i.putExtra("local", a.getAddressLine(0) );
                setResult(RESULT_OK, i);

            }

        } catch (IOException e) {
            e.printStackTrace() ;
        }
    }

    public void startLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            onMapReady( mMap );
                        } else {
                            Toast.makeText(getApplicationContext(), "Localização não encontrada", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onPermissionsNeeded(int requestPermissionId, List<String> permissions) {
        showPermissionDialog(0, "A permissão é realmente necessária. Deseja autorizar o acesso à localização do dispositivo?");
    }

    @Override
    protected void onPermissionsDenied(int requestPermissionId, List<String> permissions) {
        Toast.makeText(this, "Sem a permissão não é possível continuar", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPermissionsGranted(int requestPermissionId, List<String> permissions) throws SecurityException {
        startLocation();
    }
}
