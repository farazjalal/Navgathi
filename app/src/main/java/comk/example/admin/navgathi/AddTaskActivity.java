package comk.example.admin.navgathi;

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

import Api.TaskaddApi.TaskaddAPI;
import Api.TaskaddApi.TaskaddResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTaskActivity extends AppCompatActivity {

    EditText edt_category,edt_task;
    RadioGroup rg;
    Button reg;
    Retrofit retrofit;
    HttpLoggingInterceptor interceptor;
    OkHttpClient client;
    Gson gson;
    String category,task,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        edt_task = (EditText) findViewById(R.id.editText);
        edt_category = (EditText) findViewById(R.id.editText1);

        rg=(RadioGroup)findViewById(R.id.rdogrp);
        reg = (Button) findViewById(R.id.button);
        setTitle("ADD TASK");
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
                type= radioButton.getText().toString();
                task = edt_task.getText().toString();
                category = edt_category.getText().toString();


                if (validatetask(view)) {
                    if (validatecategory(view)) {

                        apiRegister(retrofit);


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
        TaskaddAPI service = retrofit.create(TaskaddAPI.class);
        Call<TaskaddResponse> call = service.get(type,task,category);
        call.enqueue(new Callback<TaskaddResponse>() {

            @Override
            public void onResponse(Call<TaskaddResponse> call, Response<TaskaddResponse> response) {
                if (response.isSuccessful()) {
                    try {


                        String from = response.body().getFrom();
                        if(from.equals("success")) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
//                            Intent in = new Intent(AddTask.this, addpject.class);
//                            startActivity(in);
                            finish();
                        }
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<TaskaddResponse> call, Throwable t) {

            }
        });
    }

    private boolean validatetask(View view) {
        if (task.equals("")) {
            Snackbar.make(view, " Enter your task", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatecategory(View view) {
        if (category.equals("")) {
            Snackbar.make(view, "Please enter category", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        } else {
            return true;
        }
    }
}
