package org.emobile.myitmarket.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import org.emobile.myitmarket.base.BaseActivity;
import org.emobile.myitmarket.base.main.MainActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
