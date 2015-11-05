package com.twodwarfs.shutterstock.net;

import android.os.AsyncTask;
import android.util.Base64;

import com.twodwarfs.shutterstock.utils.Logger;
import com.twodwarfs.shutterstock.model.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Aleksandar Balalovski on 5.11.15.
 *
 * Api Requests AsyncTask. I'd generally go with using Retrofit for this purpose,
 * much much faster and easier. Sticking to the task requierments.
 */
public class ApiRequestTask extends AsyncTask<Void, Void, String> {

    public interface OnResultListener {
        void onResult(String json);
    }

    private final String mQuery;

    private OnResultListener mListener;

    /** Shutterstock API auth details **/
    private static final String CLIENT_ID = "9847e6f8cab8e2a1ebb3";
    private static final String CLIENT_SECRET = "c054069df1279844e2c8605db0865f3f3a676e26";
    private static final String BASE_URL = "https://api.shutterstock.com/v2/images/";

    public ApiRequestTask(String query, OnResultListener listener) {
        mListener = listener;
        mQuery = query;
    }

    @Override
    protected String doInBackground(Void... params) {
        return basicAuthRequest();
    }

    @Override
    protected void onPostExecute(String success) {
        super.onPostExecute(success);

        Response r = Response.fromJson(success);
        if (r != null) {
            mListener.onResult(success);
        }
    }

    private String basicAuthRequest() {
        try {
            HttpURLConnection c = (HttpURLConnection) new URL(BASE_URL + mQuery).openConnection();
            c.setUseCaches(false);
            c.setRequestProperty("Authorization", buildBasicAuthorizationString(CLIENT_ID, CLIENT_SECRET));
            c.setRequestProperty("Accept", "*/*");
            c.setRequestProperty("User-Agent", "curl/7.44.0");
            c.connect();

            InputStream content = c.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = in.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();

        } catch (IOException e) {
            Logger.doLogException(e);
        }

        return null;
    }

    private String buildBasicAuthorizationString(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + new String(Base64.encode(credentials.getBytes(), Base64.NO_WRAP));
    }

}
