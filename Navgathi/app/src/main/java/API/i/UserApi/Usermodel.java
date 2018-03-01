package API.i.UserApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class Usermodel {
    @SerializedName("Sample") @Expose private List<UserData> Sample  = new ArrayList<UserData>();

    public List<UserData> getSample() {
        return Sample;
    }

    public void setSample(List<UserData> sample) {
        Sample = sample;
    }
}
