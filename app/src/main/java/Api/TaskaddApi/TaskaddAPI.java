package Api.TaskaddApi;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by altoopa on 9/10/2016.
 */
public interface TaskaddAPI {
  @GET("addtasklist.php")
    Call<TaskaddResponse> get(
          @Query("type") String type,
          @Query("task") String task,
          @Query("category") String category

  );
}
