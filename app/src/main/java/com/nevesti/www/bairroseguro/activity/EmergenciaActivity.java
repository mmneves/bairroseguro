package com.nevesti.www.bairroseguro.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.helper.Preferencias;
import com.nevesti.www.bairroseguro.model.Emergencia;
import com.nevesti.www.bairroseguro.play.GooglePlayActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class EmergenciaActivity extends GooglePlayActivity {

    private Toolbar toolbar;

    private DatabaseReference firebase;
    private StorageReference  mStorage;
    private ProgressDialog    mProgress;
    private Emergencia        emergencia = new Emergencia();

    private String idUsuarioLogado;

    private Preferencias preferencias;

    private LocationRequest request;
    private static final int PERMISSION_LOCAL  = 10;
    private static final int PERMISSION_RECORD = 20;
    private static final int PERMISSION_MEMORIA = 30;

    private Chronometer timer;
    private Button mRecordBtn;
    private Button btnPlay;
    private TextView mRecordLabel;
    private TextView txtLocalizacao;

    private MediaRecorder mRecorder;
    private MediaPlayer mediaPlayer;
    private Boolean recorderStart = false;

    private String mFileName = null;
    private String fileNameUpload = null;
    private String data;

    private static final String LOG_TAG = "Record_log";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);

        // Configura toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle( "Emergência" );
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Verificar permissões
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCAL);
        } else { startLocation(); }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!GPSEnabled) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        mStorage = FirebaseStorage.getInstance().getReference();

        // dados do usuário logado
        Preferencias preferencias = new Preferencias(this);
        idUsuarioLogado = preferencias.getIdentificador();

        timer = (Chronometer) findViewById(R.id.cronometer);
        mRecordLabel = (TextView) findViewById(R.id.txt_recordLabel);
        mRecordBtn = (Button) findViewById(R.id.btn_record);

        txtLocalizacao = findViewById(R.id.txt_localizacao);

        mProgress = new ProgressDialog(this);

        //Data e Hora atual
        SimpleDateFormat formataData = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HHmm");
        Date hora = Calendar.getInstance().getTime();
        data = formataData.format(hora);
        String horaAtual = formataHora.format(hora);

        fileNameUpload = data + "_" + horaAtual + "_audio.3gp";

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/" + fileNameUpload;

        mRecordBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN ){
                     startRecording();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    stopRecording();
                }
                return false;
            }
        });

    }

    // Enviar emeregencia
    public void enviarEmergencia() {

        //Data e Hora atual
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
        Date dataHora = Calendar.getInstance().getTime();
        String dataMensagem = formataData.format(dataHora);
        String horaMensagem = formataHora.format(dataHora);

        emergencia.setIdUsuario( idUsuarioLogado );
        emergencia.setData( dataMensagem );
        emergencia.setHora( horaMensagem );
        emergencia.setStatus("wait");

        // salvamos mensagem para o remetente
        Boolean retornoEmergenciaRemetente = salvarEmergencia(idUsuarioLogado, emergencia );
        if( !retornoEmergenciaRemetente ){
            Toast.makeText(
                    getApplicationContext(),
                    "Problema ao enviar emergência, tente novamente!",
                    Toast.LENGTH_LONG
            ).show();
        }

       // finish();

    }

    private boolean salvarEmergencia(String idRemetente, Emergencia emergencia){
        try {

            firebase = ConfiguracaoFirebase.getFirebase().child("emergencies");

            firebase.push()
                    .setValue( emergencia );

            return true;

        }catch ( Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onGooglePlayServicesConnected() {
        request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        request.setFastestInterval(2000);

        //Verificar permissões
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enablePermissions(PERMISSION_LOCAL, true, android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

    }

    @Override
    protected void onGooglePlayServicesConnectionSuspended(int errorCode) {
        Toast.makeText(this, "Suspenso: " + errorCode, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onGooglePlayServicesConnectionFailure(int errorCode) {
        Toast.makeText(this, "Falhou: " + errorCode, Toast.LENGTH_LONG).show();
    }

    private void startRecording() {

        if (ActivityCompat.checkSelfPermission(EmergenciaActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(EmergenciaActivity.this , new String[] {Manifest.permission.RECORD_AUDIO}, PERMISSION_RECORD);

        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_MEMORIA);

        } else {

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }

            mRecorder.start();
            recorderStart = true;
            timer.start(); // start a chronometer
            mRecordLabel.setText("Gravando áudio...");
        }

    }

    private void stopRecording() {

        if (recorderStart) {
            mRecorder.stop();
            timer.stop();

            mRecorder.release();
            mRecorder = null;

            uploadAudio();

            recorderStart = false;

            mRecordLabel.setText("Gravação finalizada!");
        }
    }

    private void uploadAudio(){

        mProgress.setMessage("Enviando áudio...");
        mProgress.show();

        final StorageReference filepath = mStorage.child("audio").child( idUsuarioLogado ).child(data).child(fileNameUpload);

        Uri uri = Uri.fromFile(new File(mFileName));

        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        mProgress.dismiss();

                       // String uriPath = taskSnapshot.getStorage().getRoot().toString() + taskSnapshot.getStorage().getPath();

                        emergencia.setPath_audio( uri.toString() );

                        enviarEmergencia();

                        mRecordLabel.setText("Emergência enviada!");

                    }
                });

            }
        });

    }

    private void downloadAudio( String uriDownload ){

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // Primeiro vamos intanciar o FirebaseStorage para que possamos receber os links dos arquivos
        final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    /* Agora podemos pegar a referência do nosso arquivo de áudio
       podem ser múltiplos arquivos, para isso, consulte a documentação do firebase
       O caminho do seu arquivo de áudio estará disponível no console */

        StorageReference storageReference = firebaseStorage.getReferenceFromUrl( uriDownload );
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                final String audioUrl = uri.toString();

                // enviar como parâmetro para o método sendUrlToMediaPlayer()
                sendUrlToMediaPlayer(audioUrl);
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Erro ao reproduzir: ", e.getMessage());
            }

        });

    }

    void sendUrlToMediaPlayer(String url) {
        try {
            // enviar a StreamUrl para o player
            mediaPlayer.setDataSource(url);

            // esperar que ele fique pronto e após ficar pronto tocar o áudio
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    mRecordLabel.setText("Reproduzindo Áudio...");
                    mediaPlayer.start();

                }
            });

            mediaPlayer.prepareAsync();
        } catch (IOException err) {
            Log.e("Audio Error", err.toString());
        }
    }

    public void startLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {

                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                                try {

                                    List<Address> listaEndereco = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    if (listaEndereco != null && listaEndereco.size() > 0) {
                                        Address endereco = listaEndereco.get(0);
                                        txtLocalizacao.setText( endereco.getAddressLine(0) );
                                        emergencia.setLocalizacao( endereco.getAddressLine(0) );
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                txtLocalizacao.setText("Localização não encontrada");
                            }

                        }
                    });
        }
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

    @Override
    public void onRequestPermissionsResult(int requestPermissionId, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestPermissionId, permissions, grantResults);

        switch (requestPermissionId){
            case PERMISSION_LOCAL:{
                if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Com permissão de LOCAL!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case PERMISSION_MEMORIA:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Com permissão de MEMÓRIA!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case PERMISSION_RECORD:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Com permissão de MICROFONE!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
