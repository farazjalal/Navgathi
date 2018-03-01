package API.i.LoginApi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 1/9/2018.
 */

public interface LoginAPI {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> post(
            @Field("username") String username,
            @Field("password") String password
    );
}
