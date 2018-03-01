package Api.PrjctspinnerListAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class Listmodel {
    @SerializedName("projectlist") @Expose private List<ListData> result = new ArrayList<ListData>();

    public List<ListData> getResult() {
        return result;
    }

    public void setResult(List<ListData> result) {
        this.result = result;
    }
}
