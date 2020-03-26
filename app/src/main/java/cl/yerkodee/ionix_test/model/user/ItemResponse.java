package cl.yerkodee.ionix_test.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("detail")
    @Expose
    private DetailResponse detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailResponse getDetail() {
        return detail;
    }

    public void setDetail(DetailResponse detail) {
        this.detail = detail;
    }

}
