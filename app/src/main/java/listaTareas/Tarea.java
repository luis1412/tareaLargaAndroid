package listaTareas;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

@Entity
public class Tarea implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;
    @NonNull
    String tituloTarea;
    @NonNull
    int progreso;
    String descripcionTarea;

    String rutaImagen;
    String rutaDocumento;
    String rutaVideo;
    String rutaAudio;
    @NonNull
    Date fechaCreacion;
    @NonNull
    Date fechaObjetivo;
    boolean prioritaria;
    String diasRestantes;


    public Tarea(@NonNull String tituloTarea, int progreso, String descripcionTarea, String rutaImagen, String rutaDocumento, String rutaVideo, String rutaAudio, String fechaCreacion, String fechaObjetivo, boolean prioritaria) {
        this.tituloTarea = tituloTarea;
        this.progreso = progreso;
        this.descripcionTarea = descripcionTarea;
        this.rutaImagen = rutaImagen;
        this.rutaDocumento = rutaDocumento;
        this.rutaVideo = rutaVideo;
        this.rutaAudio = rutaAudio;
        this.fechaObjetivo = convertirStringFecha(fechaObjetivo);
        this.fechaCreacion = convertirStringFecha(fechaCreacion);
        this.prioritaria = prioritaria;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getRutaDocumento() {
        return rutaDocumento;
    }

    public void setRutaDocumento(String rutaDocumento) {
        this.rutaDocumento = rutaDocumento;
    }

    public String getRutaVideo() {
        return rutaVideo;
    }

    public void setRutaVideo(String rutaVideo) {
        this.rutaVideo = rutaVideo;
    }

    public String getRutaAudio() {
        return rutaAudio;
    }

    public void setRutaAudio(String rutaAudio) {
        this.rutaAudio = rutaAudio;
    }

    public Tarea (){};

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDiasRestantes(String diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public String getGetFechaString() {
        return getFechaString;
    }

    public void setGetFechaString(String getFechaString) {
        this.getFechaString = getFechaString;
    }

    String getFechaString;


    public Date convertirStringFecha(String fechaString){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = new Date();
        try {
            fecha = formato.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fecha;
    }


    public Tarea(String tituloTarea, int progreso, boolean prioritaria, String fechaInicio, String fechaObjetivo, String descripcionTarea) {
        this.tituloTarea = tituloTarea;
        this.progreso = progreso;
        this.fechaObjetivo = convertirStringFecha(fechaObjetivo);
        this.fechaCreacion = convertirStringFecha(fechaInicio);
        this.prioritaria = prioritaria;
        this.descripcionTarea = descripcionTarea;
    }

    public String getTituloTarea() {
        return tituloTarea;
    }

    public void setTituloTarea(String tituloTarea) {
        this.tituloTarea = tituloTarea;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public String getfechaString(Date fecha){

        // Crear un formato de fecha
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        // Convertir la fecha a una cadena de texto
        String fechaString = formato.format(fecha);

    return fechaString;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaObjetivo() {
        return fechaObjetivo;
    }

    public void setFechaObjetivo(Date fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    }

    public boolean isPrioritaria() {
        return prioritaria;
    }

    public void setPrioritaria(boolean prioritaria) {
        this.prioritaria = prioritaria;
    }

    public String getDiasRestantes() {
        Date fechaACctual = new Date();
        long diferenciaMillis = fechaObjetivo.getTime() - fechaACctual.getTime() ;
        long diferenciaDias = TimeUnit.MILLISECONDS.toDays(diferenciaMillis);

        return diferenciaDias + "";
    }

}
