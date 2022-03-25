package com.nevesti.www.bairroseguro.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.adapter.DenunciaAdapter;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.helper.Preferencias;
import com.nevesti.www.bairroseguro.model.Denuncia;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MinhasDenunciasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Denuncia> denuncias;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerDenuncias;

    private NaturezaFragment.onSelecionaNatureza seleciona;

    public MinhasDenunciasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener( valueEventListenerDenuncias );
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener( valueEventListenerDenuncias );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Instânciar objetos
        denuncias = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //Monta listview e adapter
        listView = (ListView) view.findViewById(R.id.lv_contatos);
        /*adapter = new ArrayAdapter(
                getActivity(),
                R.layout.lista_contato,
                contatos
        );*/
        adapter = new DenunciaAdapter(getActivity(), denuncias );
        listView.setAdapter( adapter );

        //Recuperar denuncias do firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuarioLogado = preferencias.getIdentificador();

        firebase = ConfiguracaoFirebase.getFirebase()
                .child("complaints")
                .child( identificadorUsuarioLogado );

        //Listener para recuperar denuncias
        valueEventListenerDenuncias = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar lista
                denuncias.clear();

                //Listar denuncias
                for (DataSnapshot dados: dataSnapshot.getChildren() ){

                    Denuncia denuncia = dados.getValue( Denuncia.class );
                    denuncias.add( denuncia );


                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        seleciona = (NaturezaFragment.onSelecionaNatureza) context;
    }

}
