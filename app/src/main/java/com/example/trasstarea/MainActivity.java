package com.example.trasstarea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Button botonEmpezar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout mainLayout = findViewById(R.id.main_layout); // Obtener el layout principal

        for (int i = 0; i < 3; i++) {
            Formas forma = new Formas(this, Formas.ShapeType.ESTRELLA);
            mainLayout.addView(forma);
        }
        for (int i = 0; i < 3; i++) {
            Formas forma = new Formas(this, Formas.ShapeType.CIRCULO);
            mainLayout.addView(forma);
        }
        for (int i = 0; i <3; i++) {
            Formas forma = new Formas(this, Formas.ShapeType.CUADRADO);
            mainLayout.addView(forma);
        }
        for (int i = 0; i < 3; i++) {
            Formas forma = new Formas(this, Formas.ShapeType.TRIANGULO);
            mainLayout.addView(forma);
        }

        botonEmpezar = findViewById(R.id.botonEmpezar);

        botonEmpezar.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iVista = new Intent(MainActivity.this, ListadoActivity.class);
                startActivity(iVista);
                finish();
            }
        });


        ImageView i = findViewById(R.id.imageView2);
        TextView eslogan = findViewById(R.id.textoEmpezar);

        Animation animation;
        animation = AnimationUtils.loadAnimation(this, R.anim.movimiento);
        Executor executor;
        executor = Executors.newFixedThreadPool(3);
        executor.execute(() -> i.post(() -> i.startAnimation(animation)));

        Animation animation2;
        animation2 = AnimationUtils.loadAnimation(this, R.anim.movimiento4);
        Executor executor2;
        executor2 = Executors.newFixedThreadPool(3);
        executor.execute(() -> eslogan.post(() -> eslogan.startAnimation(animation2)));


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