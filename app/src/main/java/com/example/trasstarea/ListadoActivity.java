package com.example.trasstarea;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.trasstarea.Fragmentos.CrearTareaActivity;
import com.example.trasstarea.Fragmentos.CrearTareaViewModel;
import com.example.trasstarea.Fragmentos.EditarTarea;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import listaTareas.Tarea;

public class ListadoActivity extends AppCompatActivity {
private ArrayList<Tarea> listaTareas = new ArrayList<>();

private boolean esFavorita = false;

private ArrayList<Tarea> listaTareasPrioritarias = new ArrayList<>();
    AdaptadorTarea adaptador;


public void inicializarListaPrioritarias(){
    listaTareasPrioritarias.clear();
    for (Tarea a : listaTareas) {
        if (a.isPrioritaria()){
            listaTareasPrioritarias.add(a);
        }
    }


}
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
                    }
                    else{
                        int contador = 0;
                        int key=0;
                        Tarea tareaBorrar = (Tarea) intent.getSerializableExtra("tareaVieja");
                        for (Tarea lista: listaTareas ) {
                            if ( lista.getId() == tareaBorrar.getId()){
                                key = contador;
                          }
                            contador++;
                        }
                       listaTareas.set(key, tarea);
                    }
                    }
               adaptador.notifyDataSetChanged();
            }

        }
    };
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(contract, respuesta);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        init();
        cambiarFavorito();
        }


        public void cambiarFavorito(){
            if (!esFavorita){
                reciclerView(listaTareas);
            }
            else{
                inicializarListaPrioritarias();
                reciclerView(listaTareasPrioritarias);
            }
        }


        public void reciclerView(ArrayList<Tarea> listaTareas){
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
                    Toast.makeText(getBaseContext(), "Tarea añadida", Toast.LENGTH_LONG).show();
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
                listaTareas.remove(indiceTareaBorrar);
                adaptador.notifyDataSetChanged();
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


    public void init() {
            listaTareas.add(new Tarea("Hacer tarta", 50, true, "24/04/2023", "24/11/2023","La receta es secreta"));
            listaTareas.add(new Tarea("Hacer Tarea", 20, true , "24/04/2023", "24/11/2023", "Android es demasiado dificil"));
            listaTareas.add(new Tarea("Programar C#", 80, false, "24/04/2023", "24/11/2023", "Programar es dificl"));
            listaTareas.add(new Tarea("Ir al banco", 0, true, "24/04/2023", "24/11/2023","Me estoy quedando pobre"));
            listaTareas.add(new Tarea("LLamar abuelo" , 50, false, "24/04/2023", "24/11/2023","Otra vez q no se me olvide"));
            listaTareas.add(new Tarea("Comprar esponja", 0, true, "24/04/2023", "24/11/2023","Que los paltos no se friegan solos"));
            listaTareas.add(new Tarea("Comprar friegosuelos", 0 , false,"24/04/2023", "24/11/2023", "El suelo huele mal"));
            listaTareas.add(new Tarea("Sacar la basura", 0, false,"24/04/2023", "24/11/2023", "Lleva 1 años sin sacarse"));
            listaTareas.add(new Tarea("ReciclerView" ,  50, true,"24/04/2023", "24/11/2023", "2 semana para hacerlo"));
            listaTareas.add(new Tarea("Hacer bizcocho", 50, true, "24/04/2023", "24/11/2023","Pero sin azucar que enngorda"));
            inicializarListaPrioritarias();
        }


    }


