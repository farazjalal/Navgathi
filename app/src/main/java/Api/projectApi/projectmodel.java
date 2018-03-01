package Api.projectApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class projectmodel {
    @SerializedName("projectlist") @Expose private List<ProjectData> Sample  = new ArrayList<ProjectData>();

    public List<ProjectData> getSample() {
        return Sample;
    }

    public void setSample(List<ProjectData> sample) {
        Sample = sample;
    }
}
