package comk.example.admin.navgathi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import Adapter.ProjectAdapter;
import Api.projectApi.ProjectAPI;
import Api.projectApi.ProjectData;
import Api.projectApi.projectmodel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListProject extends Fragment {
    TextView tv_t1;
    RecyclerView rv_list1;
    SharedPreferences sharedPreferences;
    Retrofit retrofit;
    HttpLoggingInterceptor interceptor;
    OkHttpClient client;
    Gson gson;
    private LinearLayoutManager mLayoutManager;
    List<ProjectData> listData1;
    ProgressBar progressBar;

    public ListProject() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_list_project, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        rv_list1= (RecyclerView) view.findViewById(R.id.rv_list1);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

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
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_list1.setLayoutManager(mLayoutManager);
        apiListMain(retrofit);


        return view;
    }
    public void apiListMain(final Retrofit retrofit) {
        retrofit.create(ProjectAPI.class).getValues().enqueue(new Callback<projectmodel>() {
            @Override
            public void onResponse(Call<projectmodel> call, Response<projectmodel> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    listData1 = response.body().getSample();
                    if (listData1.size() > 0) {
                        ProjectAdapter adapter = new ProjectAdapter(listData1, getActivity());
                        rv_list1.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to getting response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<projectmodel> call, Throwable t) {
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return false;
    }

}
