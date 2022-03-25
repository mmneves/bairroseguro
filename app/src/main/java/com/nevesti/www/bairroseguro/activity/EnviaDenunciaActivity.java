package com.nevesti.www.bairroseguro.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.Uri;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.helper.Preferencias;
import com.nevesti.www.bairroseguro.model.Denuncia;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EnviaDenunciaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String natureza = "Natureza";

    private DatabaseReference firebase;

    private Denuncia denuncia = new Denuncia();

    private String idUsuarioLogado;

    private EditText editMensagem;
    private EditText editLocalizacao;
    private Button btEnviar;
    private Button btAddFoto;
    private Button btAddLocal;

    private Uri filepath;

    private static final int GALERIA = 1234;
    private static final int TIRAR_FOTO = 3663;
    private static final int LOCAL_ATUAL = 8888;
    private static final int PERMISSIONS_REQUEST_CAMERA=0;
    public static final String FB_STORAGE_PATH = "image/";

    //Adicionado em 15/11/2018
    private StorageReference mStorage;
    private Preferencias preferencias;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envia_denuncia);

        toolbar = (Toolbar) findViewById(R.id.tb_conversa);

        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            natureza = extra.getString("natureza");
        }

        // Configura toolbar
        toolbar.setTitle(natureza);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        mProgress = new ProgressDialog(this);

        mStorage = FirebaseStorage.getInstance().getReference();
        // dados do usuário logado
        preferencias = new Preferencias(this);

        editLocalizacao = findViewById(R.id.edit_localizacao);
        editMensagem = (EditText) findViewById(R.id.edit_mensagem);
        btEnviar = (Button) findViewById(R.id.bt_enviar);
        btAddFoto = (Button) findViewById(R.id.btn_addfoto);
        btAddLocal = (Button) findViewById(R.id.btn_addendereco);

        // recuperar dados do usuário
        Preferencias preferencias = new Preferencias(this);
        idUsuarioLogado = preferencias.getIdentificador();

        // Enviar mensagem
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImagem();
            }
        });

        btAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionaFoto();
            }
        });

        btAddLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Pesquisar a localização
                Intent intent = new Intent(getApplicationContext(),PesquisaLocalActivity.class);
                startActivityForResult(intent, LOCAL_ATUAL);

            }
        });

    }

   /* public void selecionaLocalizacao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Configurações do Dialog
        builder.setTitle("Informar o Local");
        builder.setMessage("Você deseja informar o local:");
        builder.setCancelable(true);
        builder.setPositiveButton("PESQUISAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Pesquisar a localização
                Intent intent = new Intent(getApplicationContext(),PesquisaLocalActivity.class);
                startActivity(intent);

            }
        });

        builder.setNegativeButton("ATUAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Implementar
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }*/

    public void enviarDenuncia(){
        String textoMensagem = editMensagem.getText().toString();

        //Data e Hora atual
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
        Date dataHora = Calendar.getInstance().getTime();
        String dataMensagem = formataData.format(dataHora);
        String horaMensagem = formataHora.format(dataHora);

        if( textoMensagem.isEmpty() ){
            Toast.makeText(getApplicationContext(), "Digite uma mensagem para enviar!", Toast.LENGTH_LONG).show();
        }else{

            denuncia.setIdUsuario( idUsuarioLogado );
            denuncia.setData( dataMensagem );
            denuncia.setHora( horaMensagem );
            denuncia.setNatureza( natureza );
            denuncia.setLocalizacao( editLocalizacao.getText().toString() );
            denuncia.setMensagem( textoMensagem );

            // salvamos mensagem para o remetente
            Boolean retornoMensagemRemetente = salvarMensagem(idUsuarioLogado, denuncia);
            if (!retornoMensagemRemetente) {
                Toast.makeText(
                        getApplicationContext(),
                        "Problema ao salvar denúncia, tente novamente!",
                        Toast.LENGTH_LONG
                ).show();
            } else {

                // salvamos mensagem para o destinatario
                Toast.makeText(
                        getApplicationContext(),
                        "Denúncia enviada com sucesso!",
                        Toast.LENGTH_LONG
                ).show();

                finish();
            }
        }
    }

    private boolean salvarMensagem(String idRemetente, Denuncia denuncia){

        try {

            firebase = ConfiguracaoFirebase.getFirebase().child("complaints");

            firebase.child( idRemetente )
                    .push()
                    .setValue( denuncia );

            return true;

        }catch ( Exception e){
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TIRAR_FOTO) {

            if (resultCode == RESULT_OK) {
                if(data != null) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");

                    /*
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(photo);
                    knop.setVisibility(Button.VISIBLE);
                    */

                    // Chame este método pra obter a URI da imagem
                    filepath = getImageUri(this, photo);

                    //envia a imagem selecionada
                    //uploadImagem();

                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "A captura foi cancelada",
                            Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(this, "A câmera foi fechada",
                            Toast.LENGTH_SHORT);
                }
            }
        } else if(requestCode == GALERIA && resultCode == RESULT_OK  &&  data != null  && data.getData() != null) {

            filepath = data.getData();
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath) ;

                //envia a imagem selecionada
               // uploadImagem();

            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == LOCAL_ATUAL) {

            Bundle params = data != null ? data.getExtras() : null;

            if(params != null) {
                String localizacao = params.getString("local");

                editLocalizacao.setText( localizacao );
            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void selecionaFoto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Configurações do Dialog
        builder.setTitle("Adicionar foto");
        builder.setMessage("Selecione como deseja adicionar a foto");
        builder.setCancelable(true);
        builder.setPositiveButton("CÂMERA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Abre a camera para tirar uma foto
                chamaCamera();

            }
        });

        builder.setNegativeButton("GALERIA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Seleciona uma imagem da galeria
                ChooseImage();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private boolean checkCameraHardware(Context context){
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }

    public void chamaCamera() {

        if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera, TIRAR_FOTO);

        } else {

            ActivityCompat.requestPermissions(EnviaDenunciaActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);

        }

    }


    private void ChooseImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecione uma foto"), GALERIA);
    }

    public String getImageExt(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImagem(){

        try
        {
            if(filepath != null)
            {
                mProgress.setMessage("Enviando imagem...");
                mProgress.show();

                mStorage = mStorage.child( FB_STORAGE_PATH ).child( preferencias.getIdentificador() ).child( System.currentTimeMillis() + "." + getImageExt( filepath ));
                mStorage.putFile(filepath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                denuncia.setPath_imagem( taskSnapshot.getStorage().getRoot().toString() + taskSnapshot.getStorage().getPath() );

                                mProgress.dismiss();

                                enviarDenuncia();

                            }
                        });

            } else {
                enviarDenuncia();
            }

        }catch (Exception ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Ocorreu um erro: ");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("Ok",null);
            dlg.show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestPermissionId, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestPermissionId, permissions, grantResults);

        switch (requestPermissionId){
            case PERMISSIONS_REQUEST_CAMERA:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chamaCamera();
                }
                return;
            }
        }
    }

}
