package com.nevesti.www.bairroseguro.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.model.Telefone;

import java.util.List;

// Adapter para a lista de telefones
public class TelefonesAdapter extends RecyclerView.Adapter<TelefonesAdapter.MyViewHolder> {

    private Context context;
    private List<Telefone> listaTelefone;
    private Telefone telefone;

    public TelefonesAdapter(List<Telefone> lista, Context context) {
        this.listaTelefone = lista;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_telefones, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        telefone = listaTelefone.get(position);

        holder.txtnome.setText(telefone.getNome());
        holder.txttelefone.setText(telefone.getTelefone());
        holder.txtendereco.setText(telefone.getEndereco());

    }

    @Override
    public int getItemCount() {
        return listaTelefone.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtnome;
        TextView txttelefone;
        TextView txtendereco;
        ImageView imgPhone;
        ImageView imgMaps;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtnome = itemView.findViewById(R.id.txt_nome);
            txttelefone = itemView.findViewById(R.id.txt_telefone);
            txtendereco = itemView.findViewById(R.id.txt_endereco);
            imgPhone = itemView.findViewById(R.id.image_phone);
            imgMaps = itemView.findViewById(R.id.image_maps);

            imgPhone.setOnClickListener(this);
            imgMaps.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            telefone = listaTelefone.get(getAdapterPosition());

            if (v.getId() == imgPhone.getId()) {
                Uri uri = Uri.parse("tel:" + telefone.getTelefone());

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.CALL_PHONE}, 10);
                    if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE))
                    {
                        Intent intent = new Intent(Intent.ACTION_CALL, uri);
                        context.startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    context.startActivity(intent);
                }

            } else if (v.getId()==imgMaps.getId()) {
               // Toast.makeText(context, "Maps: " + telefone.getNome(), Toast.LENGTH_SHORT).show();
                showRoute(telefone.getEndereco());
            }
        }
    }

    /*
     * Método listener de toque (clique) no botão "VISUALIZAR ROTA",
     * responsável por invocar o Google Maps para apresentar ao
     * usuário a rota que ele terá de percorrer até o salão de
     * beleza, isso partindo do ponto atual dele. Como o salão de
     * beleza é ficticio, está sendo utilizada uma estética presente
     * em Morada de Larajeiras, Serra, ES.
     * */
    public void showRoute(String endereco){

        String address = ( endereco );
        String location = Uri.encode( address );
        String navigation = "google.navigation:q="+location;

        Uri navigationUri = Uri.parse( navigation );

        Intent intent = new Intent( Intent.ACTION_VIEW, navigationUri );

        intent.setPackage( "com.google.android.apps.maps" );
        context.startActivity( intent );

        /*
         * Caso o aplicativo do Google Maps não esteja presente no
         * aparelho (algo difícil de acontecer), partimos para a
         * apresentação de rota pelo Google Maps Web, via navegador
         * mobile.
         * */

      /*  if( intent.resolveActivity( packageManager == null )){

            String dirAction = "dir_action=navigate";
            String destination = "destination=$beautySalon";
            navigation = "https://www.google.com/maps/dir/?api=1&$dirAction&$destination";

            navigationUri = Uri.parse( navigation );
            intent = new Intent( Intent.ACTION_VIEW, navigationUri );
        }

        if( intent.resolveActivity( packageManager ) != null ){

            context.startActivity( intent );
        }
        else{
            /*
             * Se nem Google Maps e nem navegador mobile estiverem
             * presentes no aparelho, informe ao usuário para
             * instalar ao menos um dos dois.
             * */
           /* Toast.makeText(context,"É necessário instalar o Google Maps",Toast.LENGTH_LONG).show();
        }*/
    }

}
