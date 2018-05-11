package com.example.administrator.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new robotView(this));
    }

}