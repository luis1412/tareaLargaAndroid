package com.example.trasstarea.Fragmentos;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.GregorianCalendar;

import listaTareas.Tarea;

public class CrearTareaViewModel extends ViewModel {


    private final MutableLiveData<Integer> idTarea = new MutableLiveData<>();
    private final MutableLiveData<String> tituloTarea = new MutableLiveData<>();
    private final MutableLiveData<Boolean> tareaPrioritaria = new MutableLiveData<>();
    private final MutableLiveData<String> descripcionTarea = new MutableLiveData<>();
    private final MutableLiveData<String> fechaInicio = new MutableLiveData<>();
    private final MutableLiveData<String> fechaFinalizacion = new MutableLiveData<>();
    private final MutableLiveData<Integer> progreso = new MutableLiveData<>();
    private final MutableLiveData<Tarea> tareaEditable = new MutableLiveData<>();

    private final MutableLiveData<String> rutaImagen = new MutableLiveData<>();
    private final MutableLiveData<String> rutaAudio = new MutableLiveData<>();
    private final MutableLiveData<String> rutaDocumento = new MutableLiveData<>();
    private final MutableLiveData<String> rutaVideo = new MutableLiveData<>();




  public void setRutaVideo(String rutaVideo) {this.rutaVideo.setValue(rutaVideo);}
  public void setRutaAudio(String rutaAudio) {this.rutaAudio.setValue(rutaAudio);}
  public void setRutaDocumento(String rutaDocumento) {this.rutaDocumento.setValue(rutaDocumento);}
  public void setRutaImagen(String rutaImagen) {this.rutaImagen.setValue(rutaImagen);}

    public void setIdTarea(Integer idTarea) {this.idTarea.setValue(idTarea);}
public void setTituloTarea(String tituloTarea)
{
    this.tituloTarea.setValue(tituloTarea);
}
public void setPrioritaria(Boolean prioritaria)
{
    this.tareaPrioritaria.setValue(prioritaria);
}
public void setDescripcionTarea(String descripcionTarea) {this.descripcionTarea.setValue(descripcionTarea);}
public void setFechaInicio(String fechaInicio)
{this.fechaInicio.setValue(fechaInicio);}

    public void setTareaEditable(Tarea tareaEditable)
{this.tareaEditable.setValue(tareaEditable);}


public void setFechaFinalizacion(String fechaFinalizacion)
{
    this.fechaFinalizacion.setValue(fechaFinalizacion);
}
        public void setProgreso(Integer progreso)
{
    this.progreso.setValue(progreso);
}

        public MutableLiveData<Integer> getIdTarea() {return idTarea; }
        public MutableLiveData<String> getTituloTarea() {
            return tituloTarea;
        }
        public MutableLiveData<Tarea> getTareaEditable() {
            return tareaEditable;
        }
        public MutableLiveData<String> getDescripcionTarea() {return descripcionTarea;}
        public MutableLiveData<Boolean> getTareaPrioritaria() {return tareaPrioritaria;}
        public MutableLiveData<String> getFechaInicio() {return fechaInicio;}
        public MutableLiveData<String> getFechaFinalizacion() {return fechaFinalizacion;}
        public MutableLiveData<String> getRutaImagen() {return rutaImagen;}
        public MutableLiveData<String> getRutaAudio() {return rutaAudio;}
        public MutableLiveData<String> getRutaDocumento() {return rutaDocumento;}
        public MutableLiveData<String> getRutaVideo() {return rutaVideo;}
        public MutableLiveData<Integer> getProgreso() {
                return progreso;
        }


}
