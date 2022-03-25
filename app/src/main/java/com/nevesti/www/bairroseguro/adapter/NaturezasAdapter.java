package com.nevesti.www.bairroseguro.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.model.NaturezaOcorrencia;

import java.util.List;

// Adapter para a lista de naturezas
public class NaturezasAdapter extends RecyclerView.Adapter<NaturezasAdapter.MyViewHolder> {

    private List<NaturezaOcorrencia> naturezas;
    private NaturezaOcorrencia naturezaOcorrencia;

    public NaturezasAdapter(List<NaturezaOcorrencia> lista) {
        this.naturezas = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_naturezas, parent, false);

        return new MyViewHolder( itemLista );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        naturezaOcorrencia = naturezas.get( position );

        holder.natureza.setText( naturezaOcorrencia.getDescricao() );

    }

    @Override
    public int getItemCount() {
        return naturezas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView natureza;

        public MyViewHolder(View itemView) {
            super(itemView);

            natureza = itemView.findViewById(R.id.txt_natureza);
        }
    }

}

