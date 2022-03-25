package com.nevesti.www.bairroseguro.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.adapter.RecyclerItemClickListener;
import com.nevesti.www.bairroseguro.adapter.TelefonesAdapter;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.helper.Preferencias;
import com.nevesti.www.bairroseguro.model.Telefone;

import java.util.ArrayList;
import java.util.List;

public class TelefonesActivity extends AppCompatActivity {

    private Query firebase;
    private ValueEventListener valueEventListenerTelefones;

    private Toolbar toolbar;

    private TelefonesAdapter adapter;

    private RecyclerView recyclerView;

    private List<Telefone> listaTelefone = new ArrayList<>();

    private ProgressDialog mProgress;

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener( valueEventListenerTelefones );
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener( valueEventListenerTelefones );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefones);

        // Configura toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle( "Telefones Úteis" );
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        recyclerView =  findViewById(R.id.lv_telefones);

        adapter = new TelefonesAdapter( listaTelefone, this );

        mProgress = new ProgressDialog(this);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );
        recyclerView.addItemDecoration( new DividerItemDecoration( this, LinearLayout.VERTICAL));
        recyclerView.setAdapter( adapter );

        mProgress.setMessage("Carregando...");
        mProgress.show();

        // Recuperar telefones do Firebase
        firebase = ConfiguracaoFirebase.getFirebase().child("telefones").orderByChild("nome");

        //Listener para recuperar telefones
        valueEventListenerTelefones = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar lista
                listaTelefone.clear();

                //Listar contatos
                for (DataSnapshot dados: dataSnapshot.getChildren() ){

                    Telefone telefone = dados.getValue( Telefone.class );
                    listaTelefone.add( telefone );
                }

                adapter.notifyDataSetChanged();

                mProgress.dismiss();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, 10);

    }


}
