package cl.yerkodee.ionix_test.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateRutResponse {

    @SerializedName("responseCode")
    @Expose
    private int responseCode;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("result")
    @Expose
    private ResultResponse result;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResultResponse getResult() {
        return result;
    }

    public void setResult(ResultResponse result) {
        this.result = result;
    }

}
