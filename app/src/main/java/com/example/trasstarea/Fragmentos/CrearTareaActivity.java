package com.example.trasstarea.Fragmentos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.trasstarea.R;

public class CrearTareaActivity extends AppCompatActivity
        implements FragmentUno.ComunicacionFragmento1, FragmentDos.ComunicacionFragmento2 {
/*
    private TextView tituloTarea;
    private TextView fechaCreacion;
    private TextView fechaObjetivo;

    private Spinner progreso;
    private CheckBox prioritaria;
*/
    ////////////////////////////////////////////////////////////////////////////
    // PASO 4: IMPLEMENTAR INTERFACES DE COMUNICACIÓN DEFINIDAS EN FRAGMENTOS //
    ////////////////////////////////////////////////////////////////////////////


        private FragmentUno fragmentoUno;
        private FragmentDos fragmentoDos;
        private TextView tvNombre;
        private FragmentManager fragmentManager;
        private boolean side = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        //Creamos instancias de los dos fragmentos
        fragmentoUno = new FragmentUno();
        fragmentoDos = new FragmentDos();

        //Instanciamos el FragmentManager para cambiar de fragmento
        fragmentManager = getSupportFragmentManager();
        //Cargamos el fragmento1
        fragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragmentoUno).commit();

    }

        ////////////////////////////////////////////////////////
        // PASO 5: SOBRESCRIBIR LOS MÉTODOS DE LAS INTERFACES //
        ////////////////////////////////////////////////////////

        // MÉTODOS DE LA INTERFAZ COMUNICACIONFRAGMENTO1 //

        @Override
        public void onBotonIr2Clicked() {
        //Si el fragmento 2 no está cargado, se inicia una transición de fragmentos
        if(!fragmentoDos.isAdded())
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragmentoDos).commit();
    }

        @Override
        public void onBotonIr1Clicked() {
        //Si el fragmento 1 no está cargado, se inicia una transición de fragmentos
        if(!fragmentoUno.isAdded())
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragmentoUno).commit();
    }
}