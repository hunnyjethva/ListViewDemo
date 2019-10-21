package demoapp.com.demoapp1;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class InfoModel {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("imageHref")
    public String imageHref;

    /*public InfoModel(String title, String description,String imageHref) {
        this.title = title;
        this.description = description;
        this.imageHref = imageHref;
    }*/

    public InfoModel(){

    }


}
