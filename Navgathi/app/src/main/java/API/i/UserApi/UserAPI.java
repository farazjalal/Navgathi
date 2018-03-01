package API.i.UserApi;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface UserAPI {
    @GET("getuser.php")
    Call<Usermodel> getValues();
}

