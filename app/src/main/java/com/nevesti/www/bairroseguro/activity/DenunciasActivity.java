package com.nevesti.www.bairroseguro.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.adapter.TabDenunciaAdapter;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.fragment.NaturezaFragment;
import com.nevesti.www.bairroseguro.helper.SlidingTabLayout;

public class DenunciasActivity extends AppCompatActivity implements NaturezaFragment.onSelecionaNatureza {

    private Toolbar toolbar;

    NaturezaFragment naturezaFragment = new NaturezaFragment();

    private FirebaseAuth usuarioFirebase;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private String identificadorContato;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        toolbar.setTitle("Denúncias");
        setSupportActionBar(toolbar);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);

        //Configurar sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));

        //Configurar adapter
        TabDenunciaAdapter tabAdapter = new TabDenunciaAdapter( getSupportFragmentManager() );
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public void onSeleciona(String natureza) {

        if ( natureza != "Denúncias") {
            /*
            // Create new fragment and transaction
            DenunciaFragment denunciaFragment = new DenunciaFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.addToBackStack(null);
            transaction.replace(R.id.vp_pagina, denunciaFragment);

            Bundle bundle = new Bundle();
            bundle.putString("natureza", natureza);
            denunciaFragment.setArguments(bundle);

            // Commit the transaction
            transaction.commit();
            */

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
