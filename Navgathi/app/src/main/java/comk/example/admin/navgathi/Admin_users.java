package comk.example.admin.navgathi;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import API.i.UserApi.UserAPI;
import API.i.UserApi.UserData;
import API.i.UserApi.Usermodel;
import Adapter.UserAdapter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Admin_users extends AppCompatActivity {
    RecyclerView rv_list;
    SharedPreferences sharedPreferences;
    Retrofit retrofit;
    HttpLoggingInterceptor interceptor;
    OkHttpClient client;
    Gson gson;
    private LinearLayoutManager mLayoutManager;
    List<UserData> listDatas;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rv_list= (RecyclerView) findViewById(R.id.rv_list);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
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
                .baseUrl("http://192.168.2.58/construction-app/dailyreport/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mLayoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(mLayoutManager);
        apiListMain(retrofit);


    }
    public void apiListMain(final Retrofit retrofit) {
        retrofit.create(UserAPI.class).getValues().enqueue(new Callback<Usermodel>() {
            @Override
            public void onResponse(Call<Usermodel> call, Response<Usermodel> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    listDatas = response.body().getSample();
                    if (listDatas.size() > 0) {
                        UserAdapter adapter = new UserAdapter(listDatas, Admin_users.this);
                        rv_list.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(Admin_users.this, "Failed to getting response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usermodel> call, Throwable t) {
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
