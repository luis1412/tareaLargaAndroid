package com.example.trasstarea.Fragmentos;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.GregorianCalendar;

    public class CrearTareaViewModel extends ViewModel {

    private final MutableLiveData<String> nombre = new MutableLiveData<>();

public void setNombre(String nomb)
{
    this.nombre.setValue(nomb);
}

public MutableLiveData<String> getNombre(){
    return nombre;
}


}
