package API.i.projectApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class ProjectData {
    @SerializedName("idproject_list") @Expose private String id;
    @SerializedName("project_name") @Expose private String title;
    @SerializedName("sitelocation") @Expose private String site;
    @SerializedName("sitemanager") @Expose private String mgr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMgr() {
        return mgr;
    }

    public void setMgr(String mgr) {
        this.mgr = mgr;
    }
}
