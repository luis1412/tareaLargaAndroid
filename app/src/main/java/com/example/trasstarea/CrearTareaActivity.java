package com.example.trasstarea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CrearTareaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        CrearTareaViewModel viewModel = new CrearTareaViewModel();


    }
}