package com.example.trasstarea.Fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trasstarea.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentUno extends Fragment {

    Button btCambiar, btIr2;

    public FragmentUno(){

    }  //Método constructor vacío

    ////////////////////////////////////////////////
    // PASO 1: CREAR UNA INTERFAZ DE COMUNICACIÓN //
    ////////////////////////////////////////////////
    public interface ComunicacionFragmento1{
        //Definimos los prototipos de los métodos que se han de implementar
        //en este caso hay dos métodos
      //  void onBotonCambiarImg1Clicked();
        void onBotonIr2Clicked();
    }

    /////////////////////////////////////////////////////////
    // PASO 2: DEFINIR OBJETO CON INTERFAZ DE COMUNICACIÓN //
    /////////////////////////////////////////////////////////
    private ComunicacionFragmento1 comunicador1;

    //////////////////////////////////////////////////////////////////////
    // PASO 3: ASIGNAR ACTIVIDAD AL OBJETO CON INTERFAZ DE COMUNICACIÓN //
    //////////////////////////////////////////////////////////////////////
    @Override
    public void onAttach(@NonNull Context context) {
        //Sobrescribimos para esto el método onAttach() donde recibimos el contexto (=Actividad)
        super.onAttach(context);
        if (context instanceof ComunicacionFragmento1) {  //Si la Actividad implementa la interfaz de comunicación
            comunicador1 = (ComunicacionFragmento1) context; //la Actividad se convierte en comunicador
        } else {
            throw new ClassCastException(context + " debe implementar interfaz de comunicación con el 1º fragmento");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflamos el layout de este fragmento
        View fragmento1 = inflater.inflate(R.layout.fragment_uno, container, false);

        //Botón para cambiar fragmento
        btIr2 = fragmento1.findViewById(R.id.botonSiguiente);
        btIr2.setOnClickListener(view -> {
            /////////////////////////////////////////////////////////////////////////////
            // PASO 6B: LLAMAMOS AL MÉTODO DE LA INTERFAZ IMPLEMENTADA EN LA ACTIVIDAD //
            /////////////////////////////////////////////////////////////////////////////
            comunicador1.onBotonIr2Clicked();
        });

        return fragmento1;
    }
}