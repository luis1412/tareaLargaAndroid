package com.example.trasstarea.Fragmentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trasstarea.R;

public class CrearTareaActivity extends AppCompatActivity {

    private Fragment currentFragment;

    Button botonSiguiente = findViewById(R.id.botonSiguiente);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        currentFragment = new FragmentUno();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, currentFragment).commit();


        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFragment = new FragmentDos();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, currentFragment).addToBackStack(null).commit();
            }
        });



        CrearTareaViewModel viewModel = new CrearTareaViewModel();



    }




}