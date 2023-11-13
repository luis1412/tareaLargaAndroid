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
    Button btCambiarNombre, btIr1;
    EditText etNombre;

    public FragmentDos() {}

    public interface ComunicacionFragmento2{
        void onBotonIr1Clicked();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflamos el layout de este fragmento
        View fragmento2 = inflater.inflate(R.layout.fragment_dos, container, false);

        //EditText donde se escribe el nombre
      //  etNombre = fragmento2.findViewById(R.id.et_nombre);
/*
        //Botón para cambiar imagen
        btCambiarNombre = fragmento2.findViewById(R.id.bt_change_nom_frag2);
        btCambiarNombre.setOnClickListener(view -> {
            comunicador2.onBotonCambiarNombreClicked(etNombre.getText().toString());
        });
*/
        //Botón para cambiar fragmento
        btIr1 = fragmento2.findViewById(R.id.Volver_fragmentoDos);
        btIr1.setOnClickListener(view -> comunicador2.onBotonIr1Clicked());

        return fragmento2;
    }
}