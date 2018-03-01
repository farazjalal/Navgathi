package Api.AddProject;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by altoopa on 9/10/2016.
 */
public interface RegisterAPI {
  @GET("addproject.php")
    Call<RegisterResponse> get(
          @Query("sitelocation") String name,
          @Query("name") String location,
          @Query("type") String email,
          @Query("sitemanageremail") String type

  );
}
