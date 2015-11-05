package com.twodwarfs.shutterstock.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.twodwarfs.shutterstock.cons.Constants;
import com.twodwarfs.shutterstock.cons.Fields;

import java.util.ArrayList;

/**
 * Created by Aleksandar Balalovski on 5.11.15.
 *
 * General Response object. Holds everything returned from basic API call.
 */
public class Response extends BaseModel {

    @SerializedName(Fields.PAGE)
    private int mPage;

    @SerializedName(Fields.TOTAL_COUNT)
    private int mTotalCount;

    @SerializedName(Fields.DATA)
    private ArrayList<Image> mImages;

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    public void setTotalCount(int totalCount) {
        mTotalCount = totalCount;
    }

    public ArrayList<Image> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<Image> images) {
        mImages = images;
    }

    public static Response fromJson(String json) {
        return new Gson().fromJson(json, Response.class);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int getType() {
        return Constants.ModelTypes.RESPONSE;
    }
}
