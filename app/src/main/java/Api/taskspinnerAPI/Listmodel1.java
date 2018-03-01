package Api.taskspinnerAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class Listmodel1 {
    @SerializedName("tasklist") @Expose private List<ListData1> result = new ArrayList<ListData1>();

    public List<ListData1> getResult() {
        return result;
    }

    public void setResult(List<ListData1> result) {
        this.result = result;
    }
}
