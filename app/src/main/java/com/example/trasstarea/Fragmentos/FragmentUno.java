package com.example.trasstarea.Fragmentos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trasstarea.DatePicker.DatePickerFragment;
import com.example.trasstarea.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FragmentUno extends Fragment implements  DatePickerDialog.OnDateSetListener{

    Button btCambiar, btIr2;
    CrearTareaViewModel viewModel;

    private EditText tituloTarea;
    private EditText fechaCreacion;
    private EditText fechaObjetivo;


    private Spinner progreso;
    private CheckBox prioritaria;

    private String fechaEscogida;


    public FragmentUno(){

    }  //Método constructor vacío

    ////////////////////////////////////////////////
    // PASO 1: CREAR UNA INTERFAZ DE COMUNICACIÓN //
    ////////////////////////////////////////////////
    public interface ComunicacionFragmento1{
        //Definimos los prototipos de los métodos que se han de implementar
        //en este caso hay dos métodos
        void onBotonIr2Clicked();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_YEAR, i2);
        fechaEscogida = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
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
        viewModel = new ViewModelProvider(requireActivity()).get(CrearTareaViewModel.class);


    }

    private void mostrarDatePickerDialog(EditText a) {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog utilizando el contexto de la actividad
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Cuando se selecciona una fecha, actualizar el texto en el EditText
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                           fechaEscogida = selectedDate;
                           a.setText(fechaEscogida);
                        }
                }, year, month, day);

        // Mostrar el DatePickerDialog
        datePickerDialog.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflamos el layout de este fragmento
        View fragmento1 = inflater.inflate(R.layout.fragment_uno, container, false);

        progreso = fragmento1.findViewById(R.id.progresoSpinner);
        fechaCreacion = fragmento1.findViewById(R.id.fechaCreacion);
        fechaObjetivo = fragmento1.findViewById(R.id.fechaObjetivo);
        tituloTarea = fragmento1.findViewById(R.id.tareaTitulo);
        prioritaria = fragmento1.findViewById(R.id.prioritariaCB);

        //Crear spinner
        List<String> listaProgreso = Arrays.asList("No iniciada", "Iniciada", "Avanzada", "Casi Finalizada", "Finalizada");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaProgreso);
        progreso.setAdapter(adapter);

        fechaCreacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatePickerDialog(fechaCreacion);
                }
        });
        fechaObjetivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDatePickerDialog(fechaObjetivo);
            }
        });


        //Botón para cambiar fragmento
        btIr2 = fragmento1.findViewById(R.id.botonSiguiente);
        btIr2.setOnClickListener(view -> {
            //TODO setear viewModel
            viewModel.setTituloTarea(tituloTarea.getText().toString());
            viewModel.setFechaInicio(fechaCreacion.getText().toString());
            viewModel.setFechaFinalizacion(fechaObjetivo.getText().toString());
            int progresoEntero = 0;
            switch(progreso.getSelectedItemPosition()){
                case 0:
                    progresoEntero = 0;
                    break;
                case 1:
                    progresoEntero = 25;
                    break;
                case 2:
                    progresoEntero = 50;
                    break;
                case 3:
                    progresoEntero = 75;
                    break;
                case 4:
                    progresoEntero = 100;
                    break;

            }
            viewModel.setProgreso(progresoEntero);
            viewModel.setPrioritaria(prioritaria.isSelected());
            /////////////////////////////////////////////////////////////////////////////
            // PASO 6B: LLAMAMOS AL MÉTODO DE LA INTERFAZ IMPLEMENTADA EN LA ACTIVIDAD //
            /////////////////////////////////////////////////////////////////////////////
            comunicador1.onBotonIr2Clicked();
        });

        return fragmento1;
    }

/*
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tituloTarea", viewModel.getNombre().getValue());
        outState.putInt("ItemId");
    //TODO terminar Girar pantalla
    }
    */



}


