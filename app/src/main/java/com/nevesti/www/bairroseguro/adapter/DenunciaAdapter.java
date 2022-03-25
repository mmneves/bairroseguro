package com.nevesti.www.bairroseguro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.model.Denuncia;

import java.util.ArrayList;

public class DenunciaAdapter extends ArrayAdapter<Denuncia> {

    private ArrayList<Denuncia> denuncias;
    private Context context;

    public DenunciaAdapter(Context c, ArrayList<Denuncia> objects) {
        super(c, 0, objects);
        this.denuncias = objects;
        this.context = c;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        // Verifica se a lista está vazia
        if( denuncias != null ){

            // inicializar objeto para montagem da view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            // Monta view a partir do xml
            view = inflater.inflate(R.layout.lista_denuncia, parent, false);

            // recupera elemento para exibição
            TextView natureza = (TextView) view.findViewById(R.id.txt_natureza);
            TextView data = (TextView) view.findViewById(R.id.txt_data);
            TextView mensagem = (TextView) view.findViewById(R.id.txt_mensagem);
            TextView hora = (TextView) view.findViewById(R.id.txt_hora);

            Denuncia denuncia = denuncias.get( position );
            natureza.setText( denuncia.getNatureza());
            data.setText( denuncia.getData() );
            mensagem.setText( denuncia.getMensagem() );
            hora.setText( denuncia.getHora() );

        }

        return view;

    }
}
