package cl.yerkodee.ionix_test.model.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "detail_table")
public class DetailResponse {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "det_id")
    private int id;

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "det_email")
    private String email;

    @SerializedName("phone_number")
    @Expose
    @ColumnInfo(name = "det_phone_number")
    private String phoneNumber;

    public DetailResponse(int id, String email, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
