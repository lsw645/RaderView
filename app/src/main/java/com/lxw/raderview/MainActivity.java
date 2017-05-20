package com.lxw.raderview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private RadarView mRadarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRadarView = (RadarView) findViewById(R.id.radar_view);
    }

    public void onStart(View view) {
        mRadarView.start();
    }

    public void onStop(View view) {
        mRadarView.stop();
    }
}
