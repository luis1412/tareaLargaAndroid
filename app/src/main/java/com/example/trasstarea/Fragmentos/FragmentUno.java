package com.example.trasstarea.Fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.trasstarea.R;

public class FragmentUno extends Fragment {

    public interface ComunicacionFragmento1{
    void onBotonSiguiente();
    void onBotonCancelarClicked();

    }
    private ComunicacionFragmento1 comunicador1;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ComunicacionFragmento1){
            comunicador1 = (ComunicacionFragmento1) context;
        }
        else {
            throw new ClassCastException(context + " debe implementar interfaz de comunicación con el 1 Fragmento");
        }
    }

    private Spinner spinner;
    private Button siguienteButton;


    public FragmentUno() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_uno, container, false);
    return view;
    }
}