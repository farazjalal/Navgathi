package Api.userList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface userListAPI {
    @GET("getuser.php")
    Call<userListmodel> getValues();
}

