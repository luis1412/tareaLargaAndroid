package com.example.trasstarea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button botonEmpezar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonEmpezar = findViewById(R.id.botonEmpezar);

        botonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iVista = new Intent(MainActivity.this, ListadoActivity.class);
                startActivity(iVista);
                finish();
            }
        });
    }

    @Override
    public Resources.Theme getTheme() {
        SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(this);
        boolean b = a.getBoolean("tema", false);
        if (b){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        return super.getTheme();
    }
}