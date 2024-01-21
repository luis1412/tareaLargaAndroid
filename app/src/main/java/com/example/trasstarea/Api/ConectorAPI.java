package com.example.trasstarea.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import listaTareas.Tarea;

public class ConectorAPI {

    Context c;

    String url;

    public ConectorAPI(Context c) {
        this.c = c;
    }

    public void obtenerListaTareas(ApiCallBack callback) {
        SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(c);
       String bdName = a.getString("nombre", "");

        String url = bdName + "/api/Tareas";
        this.url = url;
        List<Tarea> listaTareasFinal = new ArrayList<>();

        StringRequest postR = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray;
                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Tarea tarea = new Tarea();
                        tarea.setId(jsonObject.getInt("id"));
                        tarea.setTituloTarea(jsonObject.getString("tituloTarea"));
                        tarea.setProgreso(jsonObject.getInt("progreso"));
                        tarea.setDescripcionTarea(jsonObject.getString("descripcionTarea"));
                        tarea.setPrioritaria(jsonObject.getBoolean("prioritaria"));
                        tarea.setFechaCreacion(tarea.convertirStringFecha(jsonObject.getString("fechaCreacion")));
                        tarea.setFechaObjetivo(tarea.convertirStringFecha(jsonObject.getString("fechaObjetivo")));
                        tarea.setDiasRestantes(jsonObject.getString("diasRestantes"));
                        listaTareasFinal.add(tarea);
                    }
                    callback.onApiSuccess(listaTareasFinal);

                } catch (JSONException e) {
                    callback.onApiError(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onApiError(error.getMessage());
            }
        });
        // Agrega la solicitud a la cola de solicitudes de Volley
        Volley.newRequestQueue(c).add(postR);

    }


    public void insertarObjetoAPI(Tarea tarea, ApiCallBack callback) {
        SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(c);
        String bdName = a.getString("nombre", "");
        RequestQueue colaPeticiones = Volley.newRequestQueue(c);
        String url = bdName + "/api/Tareas";

        JSONObject jsonUsuario = new JSONObject();
        try {
            jsonUsuario.put("tituloTarea", tarea.getTituloTarea());
            jsonUsuario.put("progreso", tarea.getProgreso());
            jsonUsuario.put("descripcionTarea", tarea.getDescripcionTarea());
            jsonUsuario.put("prioritaria", tarea.isPrioritaria());
            jsonUsuario.put("fechaCreacion", tarea.getfechaString(tarea.getFechaCreacion()));
            jsonUsuario.put("fechaObjetivo", tarea.getfechaString(tarea.getFechaObjetivo()));
            jsonUsuario.put("diasRestantes", tarea.getDiasRestantes());
            jsonUsuario.put("rutaAudio", tarea.getRutaAudio() != null ? tarea.getRutaAudio() : "");
            jsonUsuario.put("rutaVideo", tarea.getRutaVideo() != null ? tarea.getRutaVideo() : "");
            jsonUsuario.put("rutaImagen", tarea.getRutaImagen() != null ? tarea.getRutaImagen() : "");
            jsonUsuario.put("rutaDocumento", tarea.getRutaDocumento() != null ? tarea.getRutaDocumento() : "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, url, jsonUsuario,
                callback::onApiSuccessPOST,
                error -> {
                    callback.onApiErrorPOST(error.toString());
                });

        colaPeticiones.add(solicitud);
        colaPeticiones.start();
    }

    public List<Tarea> devolverListaTareasPrioritarias() {
        // Utiliza el método obtenerListaTareas para obtener la lista de tareas y filtra las prioritarias
        List<Tarea> lista = new ArrayList<>();
        obtenerListaTareas(new ApiCallBack() {
            @Override
            public void onApiSuccess(List<Tarea> listaTareas) {
                lista.addAll(listaTareas.stream().filter(Tarea::isPrioritaria).collect(Collectors.toList()));
            }
            @Override
            public void onApiError(String errorMessage) {
                Log.e("ConectorAPI", "Error en la API: " + errorMessage);
            }

            @Override
            public void onApiSuccessPOST(JSONObject response) {

            }

            @Override
            public void onApiErrorPOST(String errorMessage) {

            }
        });
        return lista;
    }



    public  void borrarTarea(final String itemId, final ApiCallBack callback) {
        SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(c);
        String bdName = a.getString("nombre", "");
        RequestQueue queue = Volley.newRequestQueue(c);
        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, bdName + "/" + "api/Tareas" + "/" + itemId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Llamar al callback de éxito
                        if (callback != null) {
                            callback.onDeleteSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback de error
                        if (callback != null) {
                            callback.onDeleteError(error.toString());
                        }
                    }
                });
        queue.add(deleteRequest);
        queue.start();
    }

    public  void updateTarea(final Tarea tarea, final ApiCallBack callback) {
        RequestQueue queue = Volley.newRequestQueue(c);
        SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(c);
        String bdName = a.getString("nombre", "");
        String url = bdName + "/api/Tareas/" + tarea.getId();

        try {
            JSONObject jsonTarea = new JSONObject();
            jsonTarea.put("id", tarea.getId());
            jsonTarea.put("tituloTarea", tarea.getTituloTarea());
            jsonTarea.put("progreso", tarea.getProgreso());
            jsonTarea.put("descripcionTarea", tarea.getDescripcionTarea());
            jsonTarea.put("prioritaria", tarea.isPrioritaria());
            jsonTarea.put("fechaCreacion", tarea.getfechaString(tarea.getFechaCreacion()));
            jsonTarea.put("fechaObjetivo", tarea.getfechaString(tarea.getFechaObjetivo()));
            jsonTarea.put("diasRestantes", tarea.getDiasRestantes());
            jsonTarea.put("rutaAudio", tarea.getRutaAudio() != null ? tarea.getRutaAudio() : "");
            jsonTarea.put("rutaVideo", tarea.getRutaVideo() != null ? tarea.getRutaVideo() : "");
            jsonTarea.put("rutaImagen", tarea.getRutaImagen() != null ? tarea.getRutaImagen() : "");
            jsonTarea.put("rutaDocumento", tarea.getRutaDocumento() != null ? tarea.getRutaDocumento() : "");

            JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonTarea,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Llamar al callback de éxito
                            if (callback != null) {
                                callback.onUpdateSuccess(response.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Llamar al callback de error
                            if (callback != null) {
                                callback.onUpdateError(error.toString());
                            }
                        }
                    });

            // Agregar la solicitud a la cola
            queue.add(putRequest);
            queue.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
