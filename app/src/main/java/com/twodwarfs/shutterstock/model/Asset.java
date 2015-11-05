package com.twodwarfs.shutterstock.model;

import com.google.gson.annotations.SerializedName;
import com.twodwarfs.shutterstock.cons.Constants;
import com.twodwarfs.shutterstock.cons.Fields;

/**
 * Created by Aleksandar Balalovski on 5.11.15.
 *
 * Asset object representation.
 */
public class Asset extends BaseModel {

    @SerializedName(Fields.WIDTH)
    private int mWidth;

    @SerializedName(Fields.HEIGHT)
    private int mHeight;

    @SerializedName(Fields.URL)
    private String mUrl;

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public int getType() {
        return Constants.ModelTypes.ASSET;
    }
}
