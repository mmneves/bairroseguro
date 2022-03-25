package com.nevesti.www.bairroseguro.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.adapter.MensagemAdapter;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.helper.Base64Custom;
import com.nevesti.www.bairroseguro.helper.Preferencias;
import com.nevesti.www.bairroseguro.model.Conversa;
import com.nevesti.www.bairroseguro.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference firebase;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;
    private ValueEventListener valueEventListenerMensagem;

    // dados do destinatário
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    // dados do rementente
    private String idUsuarioRemetente;
    private String nomeUsuarioRemetente;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar) findViewById(R.id.tb_conversa);
        editMensagem = (EditText) findViewById(R.id.edit_mensagem);
        btMensagem = (ImageButton) findViewById(R.id.bt_enviar);
        listView = (ListView) findViewById(R.id.lv_conversas);

        mProgress = new ProgressDialog( this );

        // dados do usuário logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente = preferencias.getIdentificador();
        nomeUsuarioRemetente = preferencias.getNome();

        Bundle extra = getIntent().getExtras();

        if( extra != null ){
            nomeUsuarioDestinatario = extra.getString("nome");
            String emailDestinatario = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64( emailDestinatario );
        }

        // Configura toolbar
        toolbar.setTitle( nomeUsuarioDestinatario );
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        // Monta listview e adapter
        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        listView.setAdapter( adapter );

        mProgress.setMessage("Carregando...");
        mProgress.show();

        // Recuperar mensagens do Firebase
        firebase = ConfiguracaoFirebase.getFirebase()
                    .child("messages")
                    .child( idUsuarioRemetente )
                    .child( idUsuarioDestinatario );

        // Cria listener para mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Limpar mensagens
                mensagens.clear();

                // Recupera mensagens
                for ( DataSnapshot dados: dataSnapshot.getChildren() ){
                    Mensagem mensagem = dados.getValue( Mensagem.class );
                    mensagens.add( mensagem );
                }

                adapter.notifyDataSetChanged();

                mProgress.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        firebase.addValueEventListener( valueEventListenerMensagem );


        // Enviar mensagem
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoMensagem = editMensagem.getText().toString();

                //Data e Hora atual
                SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
                Date dataHora       = Calendar.getInstance().getTime();
                String dataMensagem = formataData.format(dataHora);
                String horaMensagem = formataHora.format(dataHora);

                if( textoMensagem.isEmpty() ){
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem para enviar!", Toast.LENGTH_LONG).show();
                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setFrom( idUsuarioRemetente );
                    mensagem.setData( dataMensagem );
                    mensagem.setHora( horaMensagem );
                    mensagem.setType( "text" );
                    mensagem.setStatus( "wait" );
                    mensagem.setContent( textoMensagem );

                    // salvamos mensagem para o remetente
                    Boolean retornoMensagemRemetente = salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem );
                    if( !retornoMensagemRemetente ){
                        Toast.makeText(
                                ConversaActivity.this,
                                "Problema ao salvar mensagem, tente novamente!",
                                Toast.LENGTH_LONG
                        ).show();
                    }else {

                        // salvamos mensagem para o destinatario
                        Boolean retornoMensagemDestinatario = salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem );
                        if( !retornoMensagemDestinatario ){
                            Toast.makeText(
                                    ConversaActivity.this,
                                    "Problema ao enviar mensagem para o destinatário, tente novamente!",
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                    }

                    // salvamos Conversa para o remetente
                    Conversa conversa = new Conversa();
                    conversa.setIdUsuario( idUsuarioDestinatario );
                    conversa.setData( dataMensagem );
                    conversa.setHora( horaMensagem );
                    conversa.setName( nomeUsuarioDestinatario );
                    conversa.setContent( textoMensagem );
                    conversa.setStatus("new");

                    Boolean retornoConversaRemetente = salvarConversa(idUsuarioRemetente, idUsuarioDestinatario, conversa);
                    if( !retornoConversaRemetente ){
                        Toast.makeText(
                                ConversaActivity.this,
                                "Problema ao salvar conversa, tente novamente!",
                                Toast.LENGTH_LONG
                        ).show();
                    }else {
                        // salvamos Conversa para o Destinatario

                        conversa = new Conversa();
                        conversa.setIdUsuario( idUsuarioRemetente );
                        conversa.setData( dataMensagem );
                        conversa.setHora( horaMensagem );
                        conversa.setName( nomeUsuarioRemetente );
                        conversa.setContent(textoMensagem);
                        conversa.setStatus("new");

                        Boolean retornoConversaDestinatario = salvarConversa(idUsuarioDestinatario, idUsuarioRemetente, conversa );
                        if( !retornoConversaDestinatario ){
                            Toast.makeText(
                                    ConversaActivity.this,
                                    "Problema ao salvar conversa para o destinatário, tente novamente!",
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                    }

                    editMensagem.setText("");
                }

            }
        });



    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem){
        try {

            firebase = ConfiguracaoFirebase.getFirebase().child("messages");

            firebase.child( idRemetente )
                    .child( idDestinatario )
                    .push()
                    .setValue( mensagem );

            return true;

        }catch ( Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean salvarConversa(String idRemetente, String idDestinatario, Conversa conversa){
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("chats");//Contacts
            firebase.child( idRemetente )
                    .child( idDestinatario )
                    .setValue( conversa );

            return true;

        }catch ( Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerMensagem);
    }
}
