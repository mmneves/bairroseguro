package com.nevesti.www.bairroseguro.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.helper.Preferencias;
import com.nevesti.www.bairroseguro.model.Usuario;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FirebaseAuth usuarioFirebase;
    private TextView txtUsuario;
    private TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        txtUsuario = (TextView) headerView.findViewById(R.id.txt_usuario);
        txtEmail = (TextView) headerView.findViewById(R.id.txt_email);

        // recuperar dados do usuário
        Preferencias preferencias = new Preferencias(this);
        String identificadorUsuarioLogado = preferencias.getIdentificador() ;
        txtUsuario.setText( preferencias.getNome() );
        txtEmail.setText( preferencias.getEmail() );

        CardView denuncias = findViewById(R.id.denuncias);
        denuncias.setOnClickListener( this );

        CardView faleConosco = findViewById(R.id.faleConosco);
        faleConosco.setOnClickListener( this );

        CardView noticias = findViewById(R.id.noticias);
        noticias.setOnClickListener( this );

        CardView telefonesUteis = findViewById(R.id.telefonesUteis);
        telefonesUteis.setOnClickListener( this );

        CardView emergencia = findViewById(R.id.emergencia);
        emergencia.setOnClickListener( this );

        CardView localizacao = findViewById(R.id.localizacao);
        localizacao.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.denuncias:
                Intent intentDenuncias = new Intent(getApplicationContext(),DenunciasActivity.class);
                startActivity(intentDenuncias);
                break;
            case R.id.faleConosco:
                Intent intentFaleConosco = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intentFaleConosco);
                break;
            case R.id.noticias:
                Intent intentNoticias = new Intent(getApplicationContext(),NoticiasActivity.class);
                startActivity(intentNoticias);
                break;
            case R.id.telefonesUteis:
                Intent intentTelefones = new Intent(getApplicationContext(),TelefonesActivity.class);
                startActivity(intentTelefones);
                break;
            case R.id.emergencia:
                Intent intentEmergencia = new Intent(getApplicationContext(),EmergenciaActivity.class);
                startActivity(intentEmergencia);
                break;
            case R.id.localizacao:
                Intent intentGps = new Intent(getApplicationContext(), LocalizacaoActivity.class);
                startActivity(intentGps);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notify) {
            //

        } else if (id == R.id.nav_sethings) {
            //configurações

        } else if (id == R.id.nav_about) {
            aboutDialog();

        } else if (id == R.id.nav_privacy) {
            startActivity(new Intent(this, ActivityPrivacyPolicy.class));

        } else if (id == R.id.nav_exit) {
            exiteApp();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exiteApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja encerrar o aplicativo?");
        builder.setCancelable(true);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                usuarioFirebase.signOut();

                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
                finishAffinity();

            }
        });
        builder.setNegativeButton("Cancela", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void aboutDialog() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.custom_dialog_about, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(mView);
        alert.setCancelable(false);
        alert.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
