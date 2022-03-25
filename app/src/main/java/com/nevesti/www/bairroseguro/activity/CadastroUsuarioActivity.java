package com.nevesti.www.bairroseguro.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.config.ConfiguracaoFirebase;
import com.nevesti.www.bairroseguro.helper.Base64Custom;
import com.nevesti.www.bairroseguro.helper.Preferencias;
import com.nevesti.www.bairroseguro.model.Usuario;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import com.nevesti.www.bairroseguro.util.NumberUtils;


public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText endereco;
    private EditText telefone;
    private EditText cpf;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = (EditText) findViewById(R.id.edit_cadastro_nome);

        endereco = (EditText) findViewById(R.id.edit_cadastro_endereco);
        telefone = (EditText) findViewById(R.id.edit_cadastro_telefone);
        cpf = (EditText) findViewById(R.id.edit_cadastro_cpf);

        email = (EditText) findViewById(R.id.edit_cadastro_email);
        senha = (EditText) findViewById(R.id.edit_cadastro_senha);
        botaoCadastrar = (Button) findViewById(R.id.bt_cadastrar);

        /*Definir mascaras*/
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);
        telefone.addTextChangedListener( maskTelefone );

        SimpleMaskFormatter simpleMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskCpf = new MaskTextWatcher(cpf, simpleMaskCpf);
        cpf.addTextChangedListener( maskCpf );

        cpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (!(NumberUtils.isCPF(NumberUtils.removeCaracteresEspeciais(cpf.getText().toString())))){
                        Toast.makeText(CadastroUsuarioActivity.this, "CPF inválido!", Toast.LENGTH_LONG ).show();
                        cpf.setText( "" );
                    }
                }
            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nome.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo NOME está vazio!", Toast.LENGTH_SHORT).show();
                    nome.requestFocus();
                    return;
                } else if (endereco.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo ENDEREÇO está vazio!", Toast.LENGTH_SHORT).show();
                    endereco.requestFocus();
                    return;
                } else if (telefone.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo TELEFONE está vazio!", Toast.LENGTH_SHORT).show();
                    telefone.requestFocus();
                    return;
                } else if (cpf.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo CPF está vazio!", Toast.LENGTH_SHORT).show();
                    cpf.requestFocus();
                    return;
                } else if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo E-MAIL está vazio!", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    return;
                } else if (senha.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo SENHA está vazio!", Toast.LENGTH_SHORT).show();
                    senha.requestFocus();
                    return;
                }

                usuario = new Usuario();
                usuario.setName( nome.getText().toString() );
                usuario.setEndereco( endereco.getText().toString() );
                usuario.setTelefone( telefone.getText().toString() );
                usuario.setCpf( cpf.getText().toString() );
                usuario.setEmail( email.getText().toString() );
                usuario.setSenha( senha.getText().toString() );
                usuario.setStatus( "novo" );
                cadastrarUsuario();

            }
        });

    }

    private void cadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful() ){

                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_LONG ).show();

                    String identificadorUsuario = Base64Custom.codificarBase64( usuario.getEmail() );
                    usuario.setId( identificadorUsuario );
                    usuario.salvar();

                    Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
                    preferencias.salvarDados( identificadorUsuario, usuario.getName(), usuario.getEmail() );

                    abrirLoginUsuario();

                }else{

                    String erro = "";
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Escolha uma senha que contenha, letras e números.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email indicado não é válido.";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Já existe uma conta com esse e-mail.";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuarioActivity.this, "Erro ao cadastrar usuário: " + erro, Toast.LENGTH_LONG ).show();
                }

            }
        });

    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
