package Api.userList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class userListData {
    @SerializedName("iduser_list") @Expose private String iduser_list;
    @SerializedName("type") @Expose private String type;
    @SerializedName("name") @Expose private String name;
    @SerializedName("email") @Expose private String email;
    @SerializedName("pass") @Expose private String pass;

    public String getIduser_list() {
        return iduser_list;
    }

    public void setIduser_list(String iduser_list) {
        this.iduser_list = iduser_list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
