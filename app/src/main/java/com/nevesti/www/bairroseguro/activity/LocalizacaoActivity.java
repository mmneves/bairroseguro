package com.nevesti.www.bairroseguro.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.ValueEventListener;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.helper.Preferencias;
import com.nevesti.www.bairroseguro.model.Localizacao;
import com.nevesti.www.bairroseguro.play.GooglePlayActivity;

import org.w3c.dom.Comment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class LocalizacaoActivity extends GooglePlayActivity implements OnMapReadyCallback {

    private DatabaseReference firebase;
    private ChildEventListener childEventListenerLocalizacao;
    private Localizacao localizacao = new Localizacao();
    private String idUsuarioLogado;

    //Define fields for Google API Client
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest request;
    private LocationCallback locationCallback;

    private static final int PERMISSION_ID = 1;

    private PendingIntent pi;
    private Toolbar toolbar;
    private GoogleMap mMap;

    private double latitude;
    private double longitude;

    private Preferencias preferencias;

    public void Timer() {

        Timer timer = new Timer();
        Task task = new Task();

        timer.schedule(task, 30000, 30000);//1000 = 1 segundo

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_localizacao);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sua localização");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Validar permissões
        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);

        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                }
            }

            ;
        };

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!GPSEnabled) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        // dados do usuário logado
        preferencias = new Preferencias(this);
        idUsuarioLogado = preferencias.getIdentificador();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        startLocation();

        Timer();

        // Recuperar colletions do Firebase
        firebase = ConfiguracaoFirebase.getFirebase().child("rastreamento");

        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("Firebase", "onChildAdded: " + dataSnapshot.getValue() );
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("Firebase", "onChildChanged: " + dataSnapshot.getKey() );

                HashMap<String, Object> map = new HashMap<>();
                map.put("status", "read");

                for (DataSnapshot dados : dataSnapshot.getChildren()) {

                    if (dados.getValue() != null) {

                        localizacao = dados.getValue(Localizacao.class);

                        if (localizacao.getStatus().equals("wait")) {

                            Log.i("Firebase", "getStatus: " + localizacao.getStatus() );

                            String dataKey = dados.getKey();

                            //  mMap.clear();//limpa o mapa

                            Double lat = localizacao.getLatitude();
                            Double lng = localizacao.getLongitude();
                            String hora = localizacao.getHora();
                            String usuarioID = localizacao.getIdUsuario();

                            String snippet = localizacao.getIdUsuario();
                            LatLng localizacao = new LatLng(lat, lng);

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(localizacao);
                            markerOptions.title(hora);
                            markerOptions.snippet(snippet);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                            mMap.addMarker(markerOptions);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacao));

                            try {

                                //.child("rastreamento/child/child/");
                                //.child( usuarioID )
                                firebase.child( dataKey )
                                        .updateChildren( map );

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.i("Firebase", "Error: " + e.getMessage() );
                            }

                        }

                    }

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
      //  firebase.removeEventListener( childEventListenerLocalizacao );
    }

    class Task extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Esse metodo será executado a cada período
                    startLocationUpdates();
                    startLocation();
                    sendLocation();
                }
            });
        }

    }


    // Enviar localização
    public void sendLocation() {

        //Data e Hora atual
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        Date dataHora = Calendar.getInstance().getTime();
        String dataLocalizacao = formataData.format(dataHora);
        String horaLocalizacao = formataHora.format(dataHora);

        localizacao.setIdUsuario(idUsuarioLogado);
        localizacao.setData(dataLocalizacao);
        localizacao.setHora(horaLocalizacao);
        localizacao.setStatus("wait");

        // salvamos mensagem para o remetente
        Boolean retornoLocalizacaoRemetente = salvarLocalizacao(idUsuarioLogado, localizacao);
        if (!retornoLocalizacaoRemetente) {
            Toast.makeText(
                    getApplicationContext(),
                    "Problema ao enviar localização, tente novamente!",
                    Toast.LENGTH_LONG
            ).show();
        } else { Toast.makeText(getApplicationContext(), "Localização atualizada com sucesso!", Toast.LENGTH_LONG).show(); }

        // finish();

    }

    public static int hourMinutes(String fullhour)
    {
        String[] parts = fullhour.split(":");

        int hour = Integer.parseInt(parts[0]);
        int min = Integer.parseInt(parts[1]);

        return (hour * 60) + min;
    }

    private boolean salvarLocalizacao(String idRemetente, Localizacao localizacao) {
        try {

            //Data e Hora atual
            SimpleDateFormat formataData = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");//ss
            Date dataHora = Calendar.getInstance().getTime();
            String dataLocalizacao = formataData.format( dataHora );
            String horaLocalizacao = formataHora.format( dataHora );

            firebase = ConfiguracaoFirebase.getFirebase().child("rastreamento").child( String.valueOf( idRemetente ) );

            firebase.child(String.valueOf(new Date().getTime())).setValue( localizacao );

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.requestLocationUpdates(request,
                locationCallback,
                null /* Looper */);
    }

    @Override
    protected void onGooglePlayServicesConnected() {
        request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(3000);
        request.setFastestInterval(1000);

        enablePermissions(PERMISSION_ID, true, android.Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    protected void onGooglePlayServicesConnectionSuspended(int errorCode) {
        Toast.makeText(this, "Suspenso: " + errorCode, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onGooglePlayServicesConnectionFailure(int errorCode) {
        Toast.makeText(this, "Falhou: " + errorCode, Toast.LENGTH_LONG).show();
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

                            localizacao.setLatitude( latitude );
                            localizacao.setLongitude( longitude );

                            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                            try {

                                List<Address> listaEndereco = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (listaEndereco != null && listaEndereco.size() > 0) {
                                    Address endereco = listaEndereco.get(0);
                                    localizacao.setLocalizacao( endereco.getAddressLine(0) );
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Não foi possivel determinar a localização atual!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Data e Hora atual
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        Date dataHora = Calendar.getInstance().getTime();
        String horaLocal = formataHora.format( dataHora );

        mMap = googleMap;
        // mMap.clear();//limpa o mapa
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng localizacaoAtual = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position( localizacaoAtual ).title( horaLocal ).snippet( preferencias.getNome() ));

        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(15).target( localizacaoAtual ).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition( cameraPosition ));

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




