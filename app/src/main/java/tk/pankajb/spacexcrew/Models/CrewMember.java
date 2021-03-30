package tk.pankajb.spacexcrew.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class CrewMember {

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("agency")
    @Expose
    @ColumnInfo(name = "agency")
    private String agency;
    @SerializedName("image")
    @Expose
    @ColumnInfo(name = "image")
    private String image;
    @SerializedName("wikipedia")
    @Expose
    @ColumnInfo(name = "wikipedia")
    private String wikipedia;
    @SerializedName("status")
    @Expose
    @ColumnInfo(name = "status")
    private String status;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
