package Api.userList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class userListmodel {
    @SerializedName("userlist") @Expose private List<userListData> userList = new ArrayList<userListData>();

    public List<userListData> getUserList() {
        return userList;
    }

    public void setUserList(List<userListData> userList) {
        this.userList = userList;
    }
}
