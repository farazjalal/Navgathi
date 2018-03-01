package comk.example.admin.navgathi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;


import API.i.LoginApi.LoginAPI;
import API.i.LoginApi.LoginResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText edt_name, edt_pass;
    Button btn_next;
    CheckBox savepass;
    SharedPreferences loginPreferences;
     SharedPreferences.Editor loginPrefsEditor;
     Boolean saveLogin;
    String Uname, password;
    Retrofit retrofit;
    HttpLoggingInterceptor interceptor;
    OkHttpClient client;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btn_next = (Button) findViewById(R.id.btn_next);
        savepass= (CheckBox)findViewById(R.id.checkBox);



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
                .baseUrl("http://trinetz.com/trinetz/cpd/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //edt_pass.addTextChangedListener(mTextEditorWatcher);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
//                loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
//                loginPrefsEditor = loginPreferences.edit();
                Uname = edt_name.getText().toString();
                password = edt_pass.getText().toString();
//                saveLogin = loginPreferences.getBoolean("saveLogin", false);
//                if (savepass.isChecked()) {
//                    loginPrefsEditor.putBoolean("saveLogin", true);
//                    loginPrefsEditor.putString("username", Uname);
//                    loginPrefsEditor.putString("password", password);
//                    loginPrefsEditor.commit();
//                } else {
//                    loginPrefsEditor.clear();
//                    loginPrefsEditor.commit();
//                }
                if (validateName(view)) {
                    if (validatePassword(view)) {
                        apiLogin(retrofit);
                    }
                }
            }
        });
    }

    private void apiLogin(Retrofit retrofit) {

        btn_next.setVisibility(View.GONE);
        edt_name.setEnabled(false);
        edt_pass.setEnabled(false);
        Toast.makeText(getApplicationContext(), "inside api login", Toast.LENGTH_LONG).show();
        LoginAPI service = retrofit.create(LoginAPI.class);
        Call<LoginResponse> call = service.post(Uname,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        String message = response.body().getMessage();
//                        String role=response.body().getRole();
if(message.equals("login successfull")) {
    Intent i=new Intent(LoginActivity.this, AdminActivity.class);
    startActivity(i);
    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();


//                            if(role=="admin"){
//                                Intent in = new Intent(LoginActivity.this, AdminActivity.class);
//                                in.putExtra("Uname",Uname);
//                                startActivity(in);
//                                finish();
//                            }
//                            else if(role=="prodmanager"){
//                                Intent in = new Intent(LoginActivity.this, AdminActivity.class);
//                                in.putExtra("Uname",Uname);
//                                startActivity(in);
//                                finish();
//                            }
//                            else if(role=="sitemanager"){
//                                Intent in = new Intent(LoginActivity.this, AdminActivity.class);
//                                in.putExtra("Uname",Uname);
//                                startActivity(in);
//                                finish();
//                            }
//                            else if(role=="user"){
//                                Intent in = new Intent(LoginActivity.this, AdminActivity.class);
//                                in.putExtra("Uname",Uname);
//                                startActivity(in);
//                                finish();
//                            }
}else {
    Toast.makeText(getApplicationContext(), "invalid username or password", Toast.LENGTH_LONG).show();

}
                    } catch (Exception e) {

                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }

        });
    }

    private boolean validateName(View view) {
        if (Uname.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter a valid Name", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword(View view) {
        if (password.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter valid password", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }
  /*  private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()==10){
                btn_next.setBackgroundResource(R.drawable.button_style);
                btn_next.setEnabled(true);
            }else{
                btn_next.setBackgroundResource(R.drawable.button_style_gray);
                btn_next.setEnabled(false);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };*/
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
