package com.example.trasstarea.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.trasstarea.CrearTareaViewModel;
import com.example.trasstarea.DatePicker.DatePickerFragment;
import com.example.trasstarea.R;

public class FragmentUno extends Fragment {
    private CrearTareaViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamo el layout
        View view = inflater.inflate(R.layout.activity_crear_tarea, container, false);

        // Inicializamos el ViewModel
        viewModel = ViewModelProvider.of(this).get(CrearTareaViewModel.class);

        // Inicializamos los campos del formulario
        EditText tituloEditText = view.findViewById(R.id.tareaTitulo);
        DatePickerFragment fechaCreacionDatePickerFragment = new DatePickerFragment();

        Spinner progresoSpinner = view.findViewById(R.id.progresoSpinner);
        progresoSpinner.setOnItemSelectedListener(viewModel::);
        CheckBox prioritariaCheckBox = view.findViewById(R.id.checkBox);
        prioritariaCheckBox.setOnCheckedChangeListener(viewModel::setPrioritaria);
        // Escuchamos el evento del botón "Siguiente"
        Button siguienteButton = view.findViewById(R.id.botonSiguiente);
        siguienteButton.setOnClickListener(viewModel::);

        return view;
    }

}
