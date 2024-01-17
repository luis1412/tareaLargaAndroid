package com.example.trasstarea;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trasstarea.Data.AppDatabase;
import com.example.trasstarea.Fragmentos.CrearTareaActivity;
import com.example.trasstarea.Fragmentos.EditarTarea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import listaTareas.Tarea;

public class ListadoActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
private List<Tarea> listaTareas = new ArrayList<>();
private List<Tarea> listaTareasPrioritarias = new ArrayList<>();

TextView txInvisible;

private boolean esFavorita = false;

    AdaptadorTarea adaptador;

    AppDatabase appDatabase;

    private RecyclerView rv;
   // private Button btCerrar;

    ActivityResultContract<Intent, ActivityResult> contract = new ActivityResultContracts.StartActivityForResult();

    ActivityResultCallback<ActivityResult> respuesta = new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK){
                Intent intent = result.getData();
                Tarea tarea = (Tarea) intent.getSerializableExtra("tarea");
                boolean editar = (boolean) intent.getSerializableExtra("editable");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    if (!editar) {
                        listaTareas.add(tarea);
                        anadirTareaBD(tarea);
                    }
                    else{
                        int contador = 0;
                        int key=0;
                        //Tarea tareaBorrar = (Tarea) intent.getSerializableExtra("tareaVieja");
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(new ActualizarTarea(tarea));
                    }
                    }
                    verificarTareaVacia();
                    adaptador.notifyDataSetChanged();
            }

        }
    };
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(contract, respuesta);

            public void verificarTareaVacia(){
                txInvisible = findViewById(R.id.textViewInvisible);
                if (listaTareas.size() == 0){
                    txInvisible.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                }
                else{
                    txInvisible.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                }


            }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
                if (key.equals("criterio")){
                    actualizarListas();
                }
                if (key.equals("ordenacion")) {
                    cambiarOrdenListas();
                }
    }

    public void cambiarOrdenListas(){
        Collections.reverse(listaTareas);
        Collections.reverse(listaTareasPrioritarias);
        reciclerView(!esFavorita ? listaTareas : listaTareasPrioritarias);
        adaptador.notifyDataSetChanged();
    }


    public void actualizarListas(){
                SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(this);
                String criterio = a.getString("criterio", "Alfabético");
                switch (criterio){
                    case "Alfabético":
                        listaTareas = appDatabase.daoTarea().obtenerTareasAlfabeticas();
                        listaTareasPrioritarias = appDatabase.daoTarea().obtenerTareasPrioritarias();
                        break;
                    case "Fecha de creación":
                        listaTareas = appDatabase.daoTarea().obtenerTareasFecha();
                        listaTareasPrioritarias = appDatabase.daoTarea().obtenerTareasPrioritarias();
                        break;
                    case "Días restantes":
                        listaTareas = appDatabase.daoTarea().obtenerTareasDias();
                        listaTareasPrioritarias = appDatabase.daoTarea().obtenerTareasPrioritarias();
                        break;
                    case "Progreso":
                        listaTareas = appDatabase.daoTarea().obtenerTareasProgreso();
                        listaTareasPrioritarias = appDatabase.daoTarea().obtenerTareasPrioritarias();
                        break;
                }
                reciclerView(!esFavorita ? listaTareas : listaTareasPrioritarias);
                adaptador.notifyDataSetChanged();
            }
            public void comprobarOrdenInicio(){
              SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(this);
              boolean ordenacion = a.getBoolean("ordenacion", true);
                if (ordenacion){
                    cambiarOrdenListas();
                }
            }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "tareasDB").allowMainThreadQueries().build();
        actualizarListas();
        cambiarFavorito();
        verificarTareaVacia();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        comprobarOrdenInicio();
        }

        public void anadirTareaBD(Tarea tarea){
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new CrearTarea(tarea));
        }

        public void cambiarFavorito(){
            if (!esFavorita){
                reciclerView(listaTareas);
            }
            else{
                reciclerView(listaTareasPrioritarias);
            }
            verificarTareaVacia();
        }


        public void reciclerView(List<Tarea> listaTareas){
            //Creamos el adaptador
            adaptador = new AdaptadorTarea(this, listaTareas);
            //Vinculamos el objeto java RecyclerView con el objeto correspondiente en el layout
            rv = findViewById(R.id.rvTarea);
            //rv.setHasFixedSize(true);
            rv.setAdapter(adaptador);
            //Asignamos un LinearLayout vertical al RecyclerView de forma que los datos se vean en formato lista.
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rv.setRecyclerListener(new RecyclerView.RecyclerListener() {
                @Override
                public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                }
            });
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }


    public boolean confirmarCambios(Tarea a){
       final boolean seBorrar = false;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Cambios");
        builder.setMessage("¿Estás seguro de que deseas realizar estos cambios?");

        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int indiceTareaBorrar =  listaTareas.indexOf(a);
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new BorrarTarea(listaTareas.get(indiceTareaBorrar)));

               // appDatabase.daoTarea().borrarTarea();
                //listaTareas.remove(indiceTareaBorrar);
                adaptador.notifyDataSetChanged();
                verificarTareaVacia();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        return seBorrar;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Tarea a = adaptador.getTarea();

        if(item.getItemId() == R.id.borrar){
            confirmarCambios(a);
            verificarTareaVacia();
            }
         else if (item.getItemId()== R.id.descripcion) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Descripcion de la Tarea: " + a.getTituloTarea())
                    .setMessage(a.getDescripcionTarea())
                    .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Aquí puedes realizar alguna acción al hacer clic en Aceptar
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (item.getItemId() == R.id.editar) {
            Intent iVista = new Intent(this, EditarTarea.class);
            iVista.putExtra("tareaEditable", a);
            launcher.launch(iVista);
        }


        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_salir){
            Toast.makeText(this, "Hasta pronto compadre", Toast.LENGTH_SHORT).show();
            finish();
        } else if (item.getItemId() == R.id.acercade) {
            showDialog(this);
        } else if (item.getItemId() == R.id.anadirTarea) {
            verificarTareaVacia();
            Intent iVista = new Intent(this, CrearTareaActivity.class);
            launcher.launch(iVista);
        } else if (item.getItemId() == R.id.filtrar) {
            if (esFavorita){
                esFavorita = false;
                cambiarFavorito();
            }
            else{
                esFavorita = true;
                cambiarFavorito();
            }
        } else if (item.getItemId() == R.id.preferencias) {
            Intent iVista = new Intent(this, Preferencias.class);
            launcher.launch(iVista);
        }

        return super.onOptionsItemSelected(item);
    }



    public static void showDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Acerca de");
        builder.setMessage(
                "Aplicación: TrassTarea\n" +
                        "Centro: IES Trassierra\n" +
                        "Autor: Luis Herrador Cruz\n" +
                        "Año: 2023"
        );
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    class BorrarTarea implements Runnable {
        private Tarea tarea;
        public BorrarTarea(Tarea tarea) {
            this.tarea = tarea;
        }
        @Override
        public void run() {
            appDatabase.daoTarea().borrarTarea(tarea.getId());
            actualizarListas();
        }
    }
    class ActualizarTarea implements Runnable {
        private Tarea tarea;
        public ActualizarTarea(Tarea tarea) {
            this.tarea = tarea;
        }
        @Override
        public void run() {
            appDatabase.daoTarea().actualizarTarea(tarea.getTituloTarea(),tarea.getProgreso(),
                    tarea.isPrioritaria(),
                    tarea.getFechaCreacion(),
                    tarea.getFechaObjetivo(),
                    tarea.getDescripcionTarea(),
                    tarea.getId());
            actualizarListas();
        }
    }
    class CrearTarea implements Runnable {
        private Tarea tarea;
        public CrearTarea(Tarea tarea) {
            this.tarea = tarea;
        }
        @Override
        public void run() {
            appDatabase.daoTarea().insertarTarea(tarea);
            actualizarListas();
        }
    }
    }


