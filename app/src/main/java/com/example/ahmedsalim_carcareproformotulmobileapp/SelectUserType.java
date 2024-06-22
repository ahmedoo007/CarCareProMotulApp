package com.example.ahmedsalim_carcareproformotulmobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician.AutoHome;
import com.example.ahmedsalim_carcareproformotulmobileapp.AutomotiveTechnician.AutoLogin;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ClientHome;
import com.example.ahmedsalim_carcareproformotulmobileapp.Client.ClientLogin;

public class SelectUserType extends AppCompatActivity {
    Button client, auto;
    public static final String SHARED_PREFS= "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);

        client= (Button) findViewById(R.id.client);
        auto= (Button) findViewById(R.id.auto);

        checkBox();

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(SelectUserType.this, ClientLogin.class));
                    finish();
            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectUserType.this, AutoLogin.class));
                finish();
            }
        });

    }

    private void checkBox() {
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check= sharedPreferences.getString("name", "");

        if (check.equals("client")){
                startActivity(new Intent(SelectUserType.this, ClientHome.class));
                finish();
        }

        if (check.equals("auto")){
            startActivity(new Intent(SelectUserType.this, AutoHome.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}