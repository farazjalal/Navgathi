package Api.taskspinnerAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class TaskListmodel {
    @SerializedName("tasklist") @Expose private List<taskListData> result = new ArrayList<taskListData>();

    public List<taskListData> getResult() {
        return result;
    }

    public void setResult(List<taskListData> result) {
        this.result = result;
    }
}
