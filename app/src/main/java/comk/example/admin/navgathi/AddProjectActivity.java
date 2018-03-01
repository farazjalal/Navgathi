package comk.example.admin.navgathi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import Api.AddProject.RegisterAPI;
import Api.AddProject.RegisterResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddProjectActivity extends AppCompatActivity {
    EditText edt_name,edt_location,edt_email;
    RadioGroup rg;
    Button reg;
    Retrofit retrofit;
    HttpLoggingInterceptor interceptor;
    OkHttpClient client;
    Gson gson;
    String name,location,email,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        edt_name = (EditText) findViewById(R.id.editText4);
        edt_location = (EditText) findViewById(R.id.editText2);
        edt_email = (EditText) findViewById(R.id.editText5);
        rg=(RadioGroup)findViewById(R.id.rdogrp);
        reg = (Button) findViewById(R.id.button);
        setTitle("ADD PROJECT");
        //retrofit
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://139.162.42.216/construction-app/dailyreport/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = rg .getCheckedRadioButtonId();

                // find the radio button by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);

//               Toast.makeText(addpject.this,
//                     radioButton.getText(), Toast.LENGTH_SHORT).show();

                // closeKeyboard();
                location = edt_location.getText().toString();
                name = edt_name.getText().toString();
                type= radioButton.getText().toString();
                email = edt_email.getText().toString();

                if (validateName(view)) {
                    if (validateEmail(view)) {
                        if (validateMobile(view)) {
                            apiRegister(retrofit);
                        }

                    } }
            }
        });
    }
    private void apiRegister(Retrofit retrofit) {
//        Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();

//        reg.setVisibility(View.GONE);
//        edt_name.setEnabled(false);
//        edt_email.setEnabled(false);
//        edt_password.setEnabled(false);
        RegisterAPI service = retrofit.create(RegisterAPI.class);
        Call<RegisterResponse> call = service.get(location,name,type,email);
        call.enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    try {


                        String from = response.body().getFrom();
                        if(from.equals("success")) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(AddProjectActivity.this, AddProjectActivity.class);
                            startActivity(in);
                            finish();
                        }
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }

    private boolean validateName(View view) {
        if (name.equals("")) {
            Snackbar.make(view, " Enter your name", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateEmail(View view) {
        if (email.equals("")) {
            Snackbar.make(view, "Please enter valid email id", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else {
            return true;
        }
    }
    private boolean validateMobile(View view) {
        if (location.equals("")) {
            Snackbar.make(view, "Enter your location", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            return false;
        } else {
            return true;
        }
    }
}
