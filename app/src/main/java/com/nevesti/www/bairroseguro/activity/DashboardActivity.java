package com.nevesti.www.bairroseguro.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.model.Telefone;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth usuarioFirebase;
    private DatabaseReference firebase;
    private Telefone telefone;

    private CardView denuncias;
    private CardView faleConosco;
    private CardView noticias;
    private CardView telefonesUteis;
    private CardView emergencia;
    private CardView localizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

       /* toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Bairro Seguro - 153");
        setSupportActionBar(toolbar);*/

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_configuracoes:
                // implementar
                return true;

            case R.id.action_sair:
                exiteApp();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    */

}