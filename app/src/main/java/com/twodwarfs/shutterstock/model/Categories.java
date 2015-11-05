package com.twodwarfs.shutterstock.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.twodwarfs.shutterstock.cons.Constants;
import com.twodwarfs.shutterstock.cons.Fields;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandar Balalovski on 5.11.15.
 *
 * Categories container.
 */
public class Categories extends BaseModel {

    @SerializedName(Fields.DATA)
    private List<Category> mCategories = new ArrayList<>();

    public List<Category> getCategories() {
        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    public static Categories fromJson(String json) {
        return new Gson().fromJson(json, Categories.class);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int getType() {
        return Constants.ModelTypes.CATEGORIES;
    }
}
