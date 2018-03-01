package Api.taskspinnerAPI;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by altoopa on 9/26/2016.
 */
public interface ListAPI1 {
    @GET("gettasklist.php")
    Call<Listmodel1> getValues();
}

