package Api.AddProductionmgr_API;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by altoopa on 9/10/2016.
 */
public interface ProductionaddAPI {
  @GET("adddailytask.php")
    Call<ProductionaddResponse> get(
          @Query("date") String date,
          @Query("idproject") String idproject,
          @Query("sitelocation") String sitelocation,
          @Query("tasks") String tasks

  );
}
