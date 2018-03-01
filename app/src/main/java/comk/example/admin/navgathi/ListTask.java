package comk.example.admin.navgathi;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import Adapter.ListAdapter;
import Api.ListAPI;
import Api.ListData;
import Api.Listmodel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListTask extends Fragment {
    TextView tv_t1;
    RecyclerView rv_list2;
    SharedPreferences sharedPreferences;
    Retrofit retrofit;
    HttpLoggingInterceptor interceptor;
    OkHttpClient client;
    Gson gson;
    private LinearLayoutManager mLayoutManager;
    List<ListData> listDatas;
    ProgressBar progressBar;

    public ListTask() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_task, container, false);
        //tv_t1=(TextView) view.findViewById(R.id.tab1);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        rv_list2 = (RecyclerView) view.findViewById(R.id.rv_list2);
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
        rv_list2.setLayoutManager(mLayoutManager);
        apiListMain(retrofit);

        return view;
        // Inflate the layout for this fragment
    }
    public void apiListMain(final Retrofit retrofit) {
        retrofit.create(ListAPI.class).getValues().enqueue(new Callback<Listmodel>() {
            @Override
            public void onResponse(Call<Listmodel> call, Response<Listmodel> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    listDatas = response.body().getResult();

                    if (listDatas.size() > 0) {

                        ListAdapter adapter = new ListAdapter(listDatas, getActivity());
                        rv_list2.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to getting response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Listmodel> call, Throwable t) {
            }
        });
    }

}
