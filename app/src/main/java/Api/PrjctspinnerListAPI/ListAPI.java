package Api.PrjctspinnerListAPI;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface ListAPI {
    @GET("getproject.php")
    Call<Listmodel> getValues();
}

