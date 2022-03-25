package com.nevesti.www.bairroseguro.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nevesti.www.bairroseguro.Config;
import com.nevesti.www.bairroseguro.R;
import com.nevesti.www.bairroseguro.model.Value;
import com.nevesti.www.bairroseguro.rests.ApiInterface;
import com.nevesti.www.bairroseguro.util.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityReplyComment extends AppCompatActivity {

    String user_id, user_name;
    Long nid;
    View view;
    EditText edt_user_id, edt_nid, edt_comment_message;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);
        view = findViewById(android.R.id.content);

        if (Config.ENABLE_RTL_MODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_send_comment));
        }

        user_id = getIntent().getStringExtra("user_id");
        user_name = getIntent().getStringExtra("user_name");
        nid = getIntent().getLongExtra("nid", 0);

        edt_user_id = findViewById(R.id.edt_user_id);
        edt_nid = findViewById(R.id.edt_nid);
        edt_comment_message = findViewById(R.id.edt_comment_message);

        edt_user_id.setText(user_id);
        //edt_nid.setText(nid);
        edt_comment_message.setText("@" + user_name + " ");
        edt_comment_message.setSelection(edt_comment_message.getText().length());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.send:
                if (edt_comment_message.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please write your message first...", Toast.LENGTH_SHORT).show();
                } else if (edt_comment_message.getText().toString().length() <= 6) {
                    Toast.makeText(getApplicationContext(), "your message at least 6 character...", Toast.LENGTH_SHORT).show();
                } else {
                    dialogSendComment();
                }
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void dialogSendComment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityReplyComment.this);
        builder.setMessage(getString(R.string.confirm_send_comment));
        builder.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                sendComment();
            }
        });

        builder.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void sendComment() {

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage(getResources().getString(R.string.sending_comment));
        progress.show();

        //String nid = edt_nid.getText().toString();
        String user_id = edt_user_id.getText().toString();
        String content = edt_comment_message.getText().toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date_time = simpleDateFormat.format(new Date());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.ADMIN_PANEL_URL + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<Value> call = apiInterface.sendComment(nid, user_id, content, date_time);

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                final String value = response.body().getValue();
                final String message = response.body().getMessage();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        if (value.equals("1")) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, Constant.DELAY_REFRESH);

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
