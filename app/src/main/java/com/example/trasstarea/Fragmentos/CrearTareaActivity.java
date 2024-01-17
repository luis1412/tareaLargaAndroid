package com.example.trasstarea.Fragmentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trasstarea.ListadoActivity;
import com.example.trasstarea.R;

import listaTareas.Tarea;

public class CrearTareaActivity extends AppCompatActivity
        implements FragmentUno.ComunicacionFragmento1, FragmentDos.ComunicacionFragmento2 {

    ////////////////////////////////////////////////////////////////////////////
    // PASO 4: IMPLEMENTAR INTERFACES DE COMUNICACIÓN DEFINIDAS EN FRAGMENTOS //
    ////////////////////////////////////////////////////////////////////////////


    private FragmentUno fragmentoUno;
    private FragmentDos fragmentoDos;
    private TextView tvNombre;
    private FragmentManager fragmentManager;
    private boolean side = false;

    private CrearTareaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        viewModel = new ViewModelProvider(this).get(CrearTareaViewModel.class);

        //Creamos instancias de los dos fragmentos
        fragmentoUno = new FragmentUno();
        fragmentoDos = new FragmentDos();

        //Instanciamos el FragmentManager para cambiar de fragmento
        fragmentManager = getSupportFragmentManager();
        //Cargamos el fragmento1
        fragmentManager.beginTransaction().add(R.id.fragmentContainerView2, fragmentoUno).commit();

    }
    ////////////////////////////////////////////////////////
    // PASO 5: SOBRESCRIBIR LOS MÉTODOS DE LAS INTERFACES //
    ////////////////////////////////////////////////////////

    // MÉTODOS DE LA INTERFAZ COMUNICACIONFRAGMENTO1 //

    public boolean comprobarCambiosFragmento(){
        boolean todoCorrecto = false;
        if (!(viewModel.getTituloTarea().getValue().isEmpty() || viewModel.getProgreso().getValue().toString().isEmpty() || viewModel.getFechaInicio().getValue().isEmpty() || viewModel.getFechaFinalizacion().getValue().isEmpty())){
            todoCorrecto = true;
        }
        else{
            Toast.makeText(this, "Tienes que rellenar todos los campos del formulario para continuar", Toast.LENGTH_SHORT).show();
        }
        return todoCorrecto;
    }


    @Override
    public void onBotonSiguiente() {
        if (comprobarCambiosFragmento())
            if (!fragmentoDos.isAdded())
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, fragmentoDos).commit();
    }

    @Override
    public void onBotonIr1Clicked() {
        //Si el fragmento 1 no está cargado, se inicia una transición de fragmentos
        if (!fragmentoUno.isAdded())
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView2, fragmentoUno).commit();
    }

    @Override
    public void onBotonGuardaClicked() {
        Tarea nuevaTarea = new Tarea(viewModel.getTituloTarea().getValue(),
                viewModel.getProgreso().getValue(),
                viewModel.getDescripcionTarea().getValue(),
                viewModel.getRutaImagen().getValue(),
                viewModel.getRutaDocumento().getValue(),
                viewModel.getRutaVideo().getValue(),
                viewModel.getRutaAudio().getValue(),
                viewModel.getFechaInicio().getValue(),
                viewModel.getFechaFinalizacion().getValue(),
                viewModel.getTareaPrioritaria().getValue());
        Intent intent = new Intent(this, ListadoActivity.class);
        intent.putExtra("editable", false);
        intent.putExtra("tarea", nuevaTarea);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


}