package com.twodwarfs.shutterstock.model;

import com.google.gson.annotations.SerializedName;
import com.twodwarfs.shutterstock.cons.Constants;
import com.twodwarfs.shutterstock.cons.Fields;

/**
 * Created by Aleksandar Balalovski on 5.11.15.
 *
 * Assets container.
 */
public class Assets extends BaseModel {

    @SerializedName(Fields.PREVIEW)
    private Asset mPreview;

    @SerializedName(Fields.SMALL_THUMB)
    private Asset mSmallThumb;

    public Asset getPreview() {
        return mPreview;
    }

    public void setPreview(Asset preview) {
        mPreview = preview;
    }

    public Asset getSmallThumb() {
        return mSmallThumb;
    }

    public void setSmallThumb(Asset smallThumb) {
        mSmallThumb = smallThumb;
    }

    @Override
    public int getType() {
        return Constants.ModelTypes.ASSETS;
    }
}
