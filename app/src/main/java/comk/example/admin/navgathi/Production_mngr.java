package comk.example.admin.navgathi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Adapter.Production_tasklistAdapter;
import Api.AddProductionmgr_API.ProductionaddAPI;
import Api.AddProductionmgr_API.ProductionaddResponse;
import Api.PrjctspinnerListAPI.ListAPI;
import Api.PrjctspinnerListAPI.ListData;
import Api.PrjctspinnerListAPI.Listmodel;
import Api.taskspinnerAPI.TaskListAPI;
import Api.taskspinnerAPI.taskListData;
import Api.taskspinnerAPI.TaskListmodel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Production_mngr extends Activity implements Spinner.OnItemSelectedListener {
    List<ListData> listDatas;
    List<taskListData> listDatas1;
    List<taskListData> productiontask;
    RecyclerView rv_list2;
    Spinner spinner;
    Retrofit retrofit;
    HttpLoggingInterceptor interceptor;
    OkHttpClient client;
    Gson gson;
    EditText date,location,edt_Task;
    Button b1;
    String pjctid,taskid,date1,location1;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_mngr);
        // Spinner element

        spinner = (Spinner) findViewById(R.id.spinner);
//        spinner2 = (Spinner) findViewById(R.id.spinner2);
        rv_list2= (RecyclerView) findViewById(R.id.popuplist);
        progressBar= (ProgressBar) findViewById(R.id.popupprogressBar);
        date=(EditText) findViewById(R.id.editText6);
        edt_Task=(EditText) findViewById(R.id.editText8);
        location=(EditText) findViewById(R.id.editText7);
        b1=(Button) findViewById(R.id.button2);

        Calendar d = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        final String strTime = sdf.format(d.getTime());

        date.setText(strTime+"\n");


        // Spinner click listener
        spinner.setOnItemSelectedListener(this);


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
        apiListMain(retrofit);
        //apiListMain2(retrofit);

        edt_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout mainLayout = (LinearLayout)
                        findViewById(R.id.activity_prodmanage);
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_box, null);
                apiProductionTask(retrofit);
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                popupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);

                // dismiss the popup window when touched
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        popupWindow.dismiss();
                        return true;
                    }
                });
            }

        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date1= date.getText().toString();
                pjctid = spinner.getSelectedItem().toString();
                location1=location.getText().toString();



                            apiRegister(retrofit);

            }
        });
    }
    private void apiRegister(Retrofit retrofit) {

        ProductionaddAPI service = retrofit.create(ProductionaddAPI.class);
        Call<ProductionaddResponse> call = service.get(date1,pjctid,location1,taskid);
        call.enqueue(new Callback<ProductionaddResponse>() {

            @Override
            public void onResponse(Call<ProductionaddResponse> call, Response<ProductionaddResponse> response) {
                if (response.isSuccessful()) {
                    try {


                        String from = response.body().getFrom();
                        if(from.equals("success")) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(Production_mngr.this, AdminActivity.class);
                            startActivity(in);
                            finish();
                        }
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<ProductionaddResponse> call, Throwable t) {

            }
        });
    }
    public void apiProductionTask(final Retrofit retrofit) {
        Toast.makeText(Production_mngr.this, "enter", Toast.LENGTH_SHORT).show();

        retrofit.create(TaskListAPI.class).getValues().enqueue(new Callback<TaskListmodel>() {
            @Override
            public void onResponse(Call<TaskListmodel>call, Response<TaskListmodel> response) {
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.INVISIBLE);
                    productiontask = response.body().getResult();

                    if (productiontask.size() > 0) {
                        Production_tasklistAdapter adapter = new Production_tasklistAdapter(productiontask, Production_mngr.this);
                        rv_list2.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(Production_mngr.this, "Failed to getting response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TaskListmodel> call, Throwable t) {
            }
        });
    }


    public void apiListMain(final Retrofit retrofit) {

        retrofit.create(ListAPI.class).getValues().enqueue(new Callback<Listmodel>() {

            @Override
            public void onResponse(Call<Listmodel> call, Response<Listmodel> response) {
                if (response.isSuccessful()) {
                    listDatas = response.body().getResult();
                    if (listDatas.size() > 0) {
                        String[] s = new String[listDatas.size()];
                        for (int i = 0; i < listDatas.size(); i++) {
                            s[i] = listDatas.get(i).getId();
//                            s[i] = listDatas.get(i).getLocation();
                            ArrayAdapter dataAdapter = new ArrayAdapter(Production_mngr.this, android.R.layout.simple_spinner_item, s);
//                            dataAdapter.setDropDownViewResource(R.layout);
                            spinner.setAdapter(dataAdapter);
                        }
                } else {
                }
                    Toast.makeText(Production_mngr.this, "Failed to getting response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Listmodel> call, Throwable t) {
            }
        });
    }
    private void apiListMain2(Retrofit retrofit) {
        retrofit.create(TaskListAPI.class).getValues().enqueue(new Callback<TaskListmodel>() {

            @Override
            public void onResponse(Call<TaskListmodel> call, Response<TaskListmodel> response) {
                if (response.isSuccessful()) {
                    listDatas1 = response.body().getResult();
                    if (listDatas1.size() > 0) {
                        String[] s = new String[listDatas1.size()];
                        for (int i = 0; i < listDatas1.size(); i++) {
                            s[i] = listDatas1.get(i).getId();
                            ArrayAdapter dataAdapter = new ArrayAdapter(Production_mngr.this, android.R.layout.simple_spinner_item, s);
                           // spinner2.setAdapter(dataAdapter);
                        }
                    }
                } else {
                    Toast.makeText(Production_mngr.this, "Failed to getting response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TaskListmodel> call, Throwable t) {
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
