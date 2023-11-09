package com.example.trasstarea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.trasstarea.Fragmentos.CrearTareaActivity;

import java.util.ArrayList;

import listaTareas.Tarea;

public class ListadoActivity extends AppCompatActivity {
private ArrayList<Tarea> listaTareas = new ArrayList<>();

private ArrayList<Tarea> listaTareasPrioritarias = new ArrayList<>();

public void inicializarListaPrioritarias(){
    for (Tarea a : listaTareas) {
        if (a.isPrioritaria()){
            listaTareasPrioritarias.add(a);
        }
    }


}
    private RecyclerView rv;
   // private Button btCerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        init();
        if (1 == 1){
            reciclerView(listaTareas);
        }
        else{
            reciclerView(listaTareasPrioritarias);
        }


        }


        public void reciclerView(ArrayList<Tarea> listaTareas){
            //Creamos el adaptador
            AdaptadorTarea adaptador = new AdaptadorTarea(this, listaTareas);
            //Vinculamos el objeto java RecyclerView con el objeto correspondiente en el layout
            rv = findViewById(R.id.rvTarea);
            //rv.setHasFixedSize(true);
            rv.setAdapter(adaptador);
            //Asignamos un LinearLayout vertical al RecyclerView de forma que los datos se vean en formato lista.
            rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rv.setRecyclerListener(new RecyclerView.RecyclerListener() {
                @Override
                public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
                    Toast.makeText(getBaseContext(), "Posicion: " + holder.getAdapterPosition(), Toast.LENGTH_LONG).show();
                }
            });
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_salir){
            Toast.makeText(this, "Hasta pronto compadre", Toast.LENGTH_SHORT).show();
            finish();
        } else if (item.getItemId() == R.id.acercade) {
            showDialog(this);
        } else if (item.getItemId() == R.id.anadirTarea) {
            Intent iVista = new Intent(ListadoActivity.this, CrearTareaActivity.class);
            startActivity(iVista);
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
            listaTareas.add(new Tarea("Hacer tarta", 50, true));
            listaTareas.add(new Tarea("Hacer Tarea", 20, true));
            listaTareas.add(new Tarea("Programar C#", 80, false));
            listaTareas.add(new Tarea("Ir al banco", 0, true));
            listaTareas.add(new Tarea("LLamar abuelo" , 50, false));
            listaTareas.add(new Tarea("Comprar esponja", 0, true));
            listaTareas.add(new Tarea("Comprar friegosuelos", 0 , false));
            listaTareas.add(new Tarea("Sacar la basura", 0, false));
            listaTareas.add(new Tarea("ReciclerView" ,  50, true));
            listaTareas.add(new Tarea("Hacer bizcocho", 50, true));
            inicializarListaPrioritarias();
        }


    }


