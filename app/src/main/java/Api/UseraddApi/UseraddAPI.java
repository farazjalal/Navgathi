package Api.UseraddApi;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by altoopa on 9/10/2016.
 */
public interface UseraddAPI {
  @GET("adduser.php")
    Call<UseraddResponse> get(
          @Query("type") String type,
          @Query("name") String name,
          @Query("email") String email,
          @Query("pass") String pass

  );
}
