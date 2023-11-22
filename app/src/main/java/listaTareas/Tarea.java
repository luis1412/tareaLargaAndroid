package listaTareas;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Tarea implements Serializable {
    String tituloTarea;
    int progreso;
    String descripcionTarea;

    public static int contadorId = 0;
    int id = 0;

    GregorianCalendar fechaCreacion;
    GregorianCalendar fechaObjetivo;
    boolean prioritaria;
    String diasRestantes;


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

    public Tarea(String tituloTarea, int progreso, boolean prioritaria, String fechaInicio, String fechaObjetivo, String descripcionTarea) {
        this.tituloTarea = tituloTarea;
        this.progreso = progreso;
        String[] a = fechaObjetivo.split("/");
        this.fechaObjetivo = new GregorianCalendar(Integer.parseInt(a[2]), Integer.parseInt(a[1]), Integer.parseInt(a[0]));
        String[] b = fechaInicio.split("/");
        this.fechaCreacion = new GregorianCalendar(Integer.parseInt(b[2]), Integer.parseInt(b[1]), Integer.parseInt(b[0]));
        this.prioritaria = prioritaria;
        this.descripcionTarea = descripcionTarea;
        this.id = ++contadorId;

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

    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    public String getfechaString(){

        int year = fechaObjetivo.get(GregorianCalendar.YEAR);
        int month = fechaObjetivo.get(GregorianCalendar.MONTH);
        int day = fechaObjetivo.get(GregorianCalendar.DAY_OF_MONTH);
        String fecha;
        fecha = day + "/" + month + "/" + year;
    return fecha;
    }

    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public GregorianCalendar getFechaObjetivo() {
        return fechaObjetivo;
    }

    public void setFechaObjetivo(GregorianCalendar fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    }

    public boolean isPrioritaria() {
        return prioritaria;
    }

    public void setPrioritaria(boolean prioritaria) {
        this.prioritaria = prioritaria;
    }

    public String getDiasRestantes() {
        GregorianCalendar diaActual = new GregorianCalendar();
        long diferenciaEnMilisegundos = diaActual.getTimeInMillis() - fechaCreacion.getTimeInMillis() ;
        long diferenciaEnDias = diferenciaEnMilisegundos / (24 * 60 * 60 * 1000);
        diasRestantes = diferenciaEnDias + "";
        return diasRestantes;
    }

}
