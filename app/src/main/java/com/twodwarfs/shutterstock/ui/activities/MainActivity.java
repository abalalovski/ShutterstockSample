package com.twodwarfs.shutterstock.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.twodwarfs.shutterstock.R;

/**
 * Created by Aleksandar Balalovski
 *
 * Holder MainActivity, serves as representational container
 * for the MainFragment only. In this case.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
