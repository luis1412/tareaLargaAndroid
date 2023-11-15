package com.example.trasstarea.Fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.trasstarea.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDos# newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDos extends Fragment {
    Button  btIr1, btGuardar;
    EditText descripcionTarea;
    private CrearTareaViewModel viewModel;

    public FragmentDos() {}

    public interface ComunicacionFragmento2{
        void onBotonIr1Clicked();
        void onBotonGuardaClicked();
    }
    private ComunicacionFragmento2 comunicador2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ComunicacionFragmento2) {
            comunicador2 = (ComunicacionFragmento2) context;
        } else {
            throw new ClassCastException(context + " debe implementar interfaz de comunicación con el 2º fragmento");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CrearTareaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflamos el layout de este fragmento
        View fragmento2 = inflater.inflate(R.layout.fragment_dos, container, false);
        //Botón para cambiar fragmento
        btIr1 = fragmento2.findViewById(R.id.boton_Volver);
        btIr1.setOnClickListener(view -> comunicador2.onBotonIr1Clicked());
        descripcionTarea = fragmento2.findViewById(R.id.descripcionTarea);

        btGuardar = fragmento2.findViewById(R.id.boton_Guardar);
        btGuardar.setOnClickListener(view -> {
            viewModel.setDescripcionTarea(descripcionTarea.getText().toString());
        comunicador2.onBotonGuardaClicked();
        });

        return fragmento2;
    }
}