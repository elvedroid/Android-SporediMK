package com.example.elvedin.sporedimk.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.elvedin.sporedimk.AppSingleton;
import com.example.elvedin.sporedimk.model.Category;
import com.example.elvedin.sporedimk.ui.activity.BaseActivity;
import com.example.elvedin.sporedimk.ui.activity.MainActivity;
import com.example.elvedin.sporedimk.ui.manager.AppHolder;
import com.example.elvedin.sporedimk.ui.manager.network_manager.Client;
import com.example.elvedin.sporedimk.ui.manager.network_manager.ClientInterface;
import com.example.elvedin.sporedimk.utils.Constants;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elvedin on 9/23/17.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        AppSingleton.getInstance().setClientInterface(Client.getClient(Constants.BASE_URL, false).create(ClientInterface.class));
        getCategories();
    }

    private void getCategories() {
        Call<List<Category>> call = AppHolder.getInstance().getClientInterface().getLeafCategories("en");
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AppSingleton.getInstance().setCategories(response.body());
                }
                startMainActivity();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
