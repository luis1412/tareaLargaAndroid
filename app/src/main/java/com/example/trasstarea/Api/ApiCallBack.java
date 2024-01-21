package com.example.trasstarea.Api;

import org.json.JSONObject;

import java.util.List;

import listaTareas.Tarea;

public interface ApiCallBack {

      default   void onApiSuccess(List<Tarea> listaTareas){};
      default   void onApiError(String errorMessage){};

      default   void onApiSuccessPOST(JSONObject response){};
       default void onApiErrorPOST(String errorMessage){};

       default void onDeleteSuccess(String response){};
       default void onDeleteError(String response){};

       default void onUpdateSuccess(String response){};
       default void onUpdateError(String error){};


}
