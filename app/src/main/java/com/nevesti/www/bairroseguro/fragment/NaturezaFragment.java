package com.nevesti.www.bairroseguro.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.activity.EnviaDenunciaActivity;
import com.nevesti.www.bairroseguro.adapter.NaturezasAdapter;
import com.nevesti.www.bairroseguro.adapter.RecyclerItemClickListener;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.model.NaturezaOcorrencia;

import java.util.ArrayList;
import java.util.List;

public class NaturezaFragment extends Fragment {

    private onSelecionaNatureza seleciona;

    private Query firebase;
    private ValueEventListener valueEventListenerNaturezas;

    private NaturezasAdapter adapter;
    private RecyclerView recyclerView;
    private List<NaturezaOcorrencia> listaNatureza = new ArrayList<>();
    private TextView txtnatureza;

    private ProgressDialog mProgress;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        seleciona = (NaturezaFragment.onSelecionaNatureza) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener( valueEventListenerNaturezas );
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener( valueEventListenerNaturezas );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_natureza, container, false);

        recyclerView =  view.findViewById(R.id.lv_naturezas);
        txtnatureza = view.findViewById(R.id.txt_natureza);

        mProgress = new ProgressDialog(getActivity());

        adapter = new NaturezasAdapter( listaNatureza );

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize( true );
        recyclerView.addItemDecoration( new DividerItemDecoration( getActivity(),  LinearLayout.VERTICAL));
        recyclerView.setAdapter( adapter );

        mProgress.setMessage("Carregando...");
        mProgress.show();

        // Recuperar telefones do Firebase
        firebase = ConfiguracaoFirebase.getFirebase().child("naturezas").orderByChild("descricao");

        //Listener para recuperar naturezas
        valueEventListenerNaturezas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar lista
                listaNatureza.clear();

                //Listar contatos
                for (DataSnapshot dados: dataSnapshot.getChildren() ){

                    NaturezaOcorrencia natureza = dados.getValue( NaturezaOcorrencia.class );
                    listaNatureza.add( natureza );
                }

                adapter.notifyDataSetChanged();

                mProgress.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                NaturezaOcorrencia natureza = listaNatureza.get( position );

                                Intent intent = new Intent(getActivity(), EnviaDenunciaActivity.class );
                                intent.putExtra("natureza", natureza.getDescricao() );
                                startActivity(intent);

                               /* if (seleciona != null) {
                                    NaturezaOcorrencia natureza = listaNatureza.get( position );
                                   // seleciona.onSeleciona( natureza.getDescricao() );
                                    Intent intent = new Intent(getActivity(), EnviaDenunciaActivity.class );
                                   // intent.putExtra("nome", conversa.getNome() );
                                   // intent.putExtra("email", email );
                                    startActivity(intent);
                                }*/
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )


        );

        return view;
    }

    public interface onSelecionaNatureza{
        public void onSeleciona( String natureza );
    }
}
