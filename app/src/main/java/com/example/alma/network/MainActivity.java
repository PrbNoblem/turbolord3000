package com.example.alma.network;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    public static final String EXTRA_MESSAGE = "com.example.alma.network.EXTRA_MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}