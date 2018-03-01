package Api.projectApi;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface ProjectAPI {
    @GET("getproject.php")
    Call<projectmodel> getValues();
}

