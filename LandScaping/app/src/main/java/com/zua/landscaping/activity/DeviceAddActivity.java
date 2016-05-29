package com.zua.landscaping.activity;

import android.app.Activity;
import android.os.Bundle;

import com.zua.landscaping.R;
import com.zua.landscaping.app.App;

/**
 * Created by roy on 16/5/6.
 */
public class DeviceAddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_device_add);
        App.getInstance().addActivity(this);
    }
}
