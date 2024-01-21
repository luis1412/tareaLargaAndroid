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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trasstarea.Api.TareaApi;
import com.example.trasstarea.Data.AppDatabase;
import com.example.trasstarea.Detalles.Detalles;
import com.example.trasstarea.Fragmentos.CrearTareaActivity;
import com.example.trasstarea.Fragmentos.EditarTarea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import estadisticas.Estadisticas;
import listaTareas.Tarea;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListadoActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
private List<Tarea> listaTareas = new ArrayList<>();
private List<Tarea> listaTareasPrioritarias = new ArrayList<>();


boolean bdExterna;
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

            /*
    @Override
    protected void onResume() {
        actualizarListas();
        verificarTareaVacia();
        super.onResume();
    }
    */

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

/*
    public void actualizarListaAPI() {



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://160a-95-16-240-244.ngrok-free.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TareaApi tareaApi = retrofit.create(TareaApi.class);
            Call<List<Tarea>> call = tareaApi.obtenerListaTareas();
            call.enqueue(new Callback<List<Tarea>>() {
                @Override
                public void onResponse(Call<List<Tarea>> call, Response<List<Tarea>> response) {
                    try {
                        if (response.isSuccessful()){
                            List<Tarea> listaTareas2 = response.body();
                            listaTareas = listaTareas2;
                            listaTareasPrioritarias = listaTareas2;
                        }
                    } catch (Exception ex){
                        Toast.makeText(getApplicationContext(), "No se ha podido conectar con la API", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<List<Tarea>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "No se ha podido conectar con la API ERROR", Toast.LENGTH_SHORT).show();
                }
            });
    }
*/




    public void asignarListaTareasPrioritarias(){
        List<Tarea> lista = listaTareas;
        listaTareasPrioritarias = lista.stream().filter(Tarea::isPrioritaria).collect(Collectors.toList());
    }


    public void leerAPI(){
        String url = "https://160a-95-16-240-244.ngrok-free.app/api/Tareas";

        StringRequest postR = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray = null;
                Tarea tarea = new Tarea();
                try {
                    jsonArray = new JSONArray(response);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = null;
                    try {
                    jsonObject = jsonArray.getJSONObject(i);
                    tarea.setId(jsonObject.getInt("id"));
                    tarea.setTituloTarea(jsonObject.getString("tituloTarea"));
                    tarea.setProgreso(jsonObject.getInt("progreso"));
                    tarea.setDescripcionTarea(jsonObject.getString("descripcionTarea"));
                    tarea.setPrioritaria(jsonObject.getBoolean("prioritaria"));
                    tarea.setFechaCreacion(tarea.convertirStringFecha(jsonObject.getString("fechaCreacion")));
                    tarea.setFechaObjetivo(tarea.convertirStringFecha(jsonObject.getString("fechaObjetivo")));
                    tarea.setDiasRestantes(jsonObject.getString("diasRestantes"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    // Agregar la tarea a la lista
                    listaTareas.add(tarea);
                }

                asignarListaTareasPrioritarias();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postR);

    }

    @SuppressLint("NotifyDataSetChanged")
    public void actualizarListas(){

                SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(this);
                String criterio = a.getString("criterio", "Alfabético");
               bdExterna = a.getBoolean("bd", false);
                switch (criterio){
                    case "Alfabético":
                        if (bdExterna){
                            //actualizarListaAPI();
                            leerAPI();
                        }
                        else{
                        listaTareas = appDatabase.daoTarea().obtenerTareasAlfabeticas();
                        listaTareasPrioritarias = appDatabase.daoTarea().obtenerTareasPrioritarias();
                        }
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
        limpiezaArchivos();
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


        public void limpiezaArchivos(){
            SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(this);

            String numeroDias2 = a.getString("limpieza", "0");
            boolean sdPreference = a.getBoolean("tarjeta", false);

            if (numeroDias2 != null){

            int numeroDias = Integer.parseInt(numeroDias2);

            Date fecha = new Date();
            Calendar rightNow = Calendar.getInstance();
            String dia = rightNow.get(Calendar.DAY_OF_MONTH) + "";
            String mes = fecha.getMonth() + "";
            String year = fecha.getYear() + "";



            if (numeroDias != 0){
                // La funcion de que borre la SD no he conseguido hacerla porque no se pone la ruta que es no se porque
                // Mientras que en el fragmento 2 si coge la ruta bien
              // File ficheroInterno = sdPreference ? Environment.getExternalStorageDirectory() : getExternalFilesDir(Environment.DIRECTORY_PICTURES);
               File ficheroInterno = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File[] archivos = ficheroInterno.listFiles();
                if (archivos != null) {
                    for (File archivo : archivos) {
                      String nombreArchivo = archivo.getName();
                       Date fechaArchivo = stringAFecha(nombreArchivo);
                       long dias = calcularDiferenciaFechas(fecha, fechaArchivo);
                        if (dias >= numeroDias) {
                            // Borrar el archivo
                            if (archivo.delete()) {
                                System.out.println("Archivo eliminado: " + archivo.getAbsolutePath());
                            } else {
                                System.err.println("No se pudo eliminar el archivo: " + archivo.getAbsolutePath());
                            }
                        }
                    }
                }
            }
            }
        }
        public long calcularDiferenciaFechas(Date fechaObjetivo, Date fechaACctual){
            long diferenciaMillis = fechaObjetivo.getTime() - fechaACctual.getTime();
            diferenciaMillis +=  2592000000L;
            long diferenciaDias = TimeUnit.MILLISECONDS.toDays(diferenciaMillis);

            return diferenciaDias;
        }


        public Date stringAFecha(String fecha){
            String finalString = fecha.substring(fecha.length() - 10, fecha.length() - 4);
            String[] a = finalString.split("");
            String dia = a[0] + a[1];
            String mes = a[2] + a[3];
            String year =  a[4] + a[5];
          int diaI = Integer.parseInt(dia);
          int mesI = Integer.parseInt(mes);
          int yearI = Integer.parseInt(year) + 100;
          Date fechaFinal = new Date(yearI,mesI,diaI);
          return fechaFinal;
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
            Intent iVista = new Intent(this, Detalles.class);
           iVista.putExtra("descripcion", a);
           launcher.launch(iVista);
             /*
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
            */

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
        else if (item.getItemId() == R.id.estadisticas){
            Intent iVista = new Intent(this, Estadisticas.class);
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
            if (bdExterna){
                insertarObjetoAPI(tarea);
            }
            else {
                appDatabase.daoTarea().insertarTarea(tarea);
            }
            actualizarListas();
        }
    }


    public void insertarObjetoAPI(Tarea tarea){
        RequestQueue colaPeticiones = Volley.newRequestQueue(this);
        String url = "https://160a-95-16-240-244.ngrok-free.app/api/Tareas";

        JSONObject jsonUsuario = new JSONObject();
        try {
            jsonUsuario.put("tituloTarea", tarea.getTituloTarea());
            jsonUsuario.put("progreso", tarea.getProgreso());
           jsonUsuario.put("descripcionTarea", tarea.getDescripcionTarea());
            jsonUsuario.put("prioritaria", tarea.isPrioritaria());
            jsonUsuario.put("fechaCreacion", tarea.getfechaString(tarea.getFechaCreacion()));
            jsonUsuario.put("fechaObjetivo",tarea.getfechaString(tarea.getFechaObjetivo()));
            jsonUsuario.put("diasRestantes", tarea.getDiasRestantes());
            jsonUsuario.put("rutaAudio", tarea.getRutaAudio() != null ? tarea.getRutaAudio() : " ");
            jsonUsuario.put("rutaVideo", tarea.getRutaVideo() != null ? tarea.getRutaVideo() : " ");
            jsonUsuario.put("rutaImagen", tarea.getRutaImagen() != null ? tarea.getRutaImagen() : " ");
            jsonUsuario.put("rutaDocumento", tarea.getRutaDocumento() != null ? tarea.getRutaDocumento() : " ");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, url, jsonUsuario,
                response -> {
                    Log.d("Volley", "Respuesta exitosa: " + response.toString());
                },
                error -> {
                    Log.e("Volley", "Error en la solicitud: " + error.toString());
                });

        colaPeticiones.add(solicitud);
        colaPeticiones.start();
    }


    @Override
    public Resources.Theme getTheme() {
        SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(this);
        boolean b = a.getBoolean("tema", false);
        if (b){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        return super.getTheme();
    }

    }


