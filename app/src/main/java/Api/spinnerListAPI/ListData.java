package Api.spinnerListAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class ListData {
    @SerializedName("idproject_list") @Expose private String id;
    @SerializedName("project_name") @Expose private String name;
    @SerializedName("sitelocation") @Expose private String location;
    @SerializedName("sitemanager") @Expose private String mngr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMngr() {
        return mngr;
    }

    public void setMngr(String mngr) {
        this.mngr = mngr;
    }
}
