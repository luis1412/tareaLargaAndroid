package com.example.trasstarea.Api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

import listaTareas.Tarea;

public class ConectorAPI {
   List<Tarea> listaTareas;


    public List<Tarea> listaDeTareas (){
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

                    listaTareas.add(tarea);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.getMessage());
            }
        });



        return listaTareas;
    }



    public List<Tarea> devolverListaTareasPrioritarias(){
        List<Tarea> lista = listaDeTareas();
        return lista.stream().filter(Tarea::isPrioritaria).collect(Collectors.toList());
    }


}
