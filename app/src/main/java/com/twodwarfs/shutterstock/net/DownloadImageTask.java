package com.twodwarfs.shutterstock.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.twodwarfs.shutterstock.utils.Logger;
import com.twodwarfs.shutterstock.utils.Utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Aleksandar Balalovski on 5.11.15.
 * <p/>
 * AsyncTask for downloading images. Generally I'd go with using Glide,
 * but the task requirement was to use basic SDK.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView mHelperImageView;
    private Context mContext;

    public DownloadImageTask(Context context, ImageView bmImage) {
        mContext = context;
        mHelperImageView = bmImage;

    }

    protected Bitmap doInBackground(String... params) {

        if (!Utils.hasActiveNetworkConnection(mContext)) {
            Utils.doToast(mContext, "No Network connectivity.");
            return null;
        }

        String imageUrl = params[0];
        Bitmap bitmap = null;

        try {
            InputStream in = new java.net.URL(imageUrl).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Logger.doLogException(e);
        }

        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            mHelperImageView.setImageBitmap(result);
        }
    }
}