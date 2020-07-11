package com.example.duncanro.widgetexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPENAME = "recipe";

    String widgetText = "I love Buggie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this,UpdateWidgetService.class);
        intent.putExtra(EXTRA_RECIPENAME, widgetText);
        startService(intent);
    }
}