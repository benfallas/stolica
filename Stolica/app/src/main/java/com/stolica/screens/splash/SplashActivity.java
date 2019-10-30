package com.stolica.screens.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.stolica.R;
import com.stolica.data.StoreData;
import com.stolica.screens.bloodPressure.BloodPressureActivity;

public class SplashActivity extends AppCompatActivity {

    StoreData storeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StoreData.initializeStoreData(this);
        Intent intent = new Intent(this, BloodPressureActivity.class);
        startActivity(intent);
        finish();
    }
}
