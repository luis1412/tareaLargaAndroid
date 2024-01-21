package com.example.trasstarea.Api;

import java.util.List;

import listaTareas.Tarea;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TareaApi {

    @GET("api/Tareas/{id}")
    public Call<Tarea> find(@Path("id") int id);

    @GET("api/Tareas")
    Call<List<Tarea>> obtenerListaTareas();



}
