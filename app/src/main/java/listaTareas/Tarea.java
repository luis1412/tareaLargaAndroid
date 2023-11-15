package listaTareas;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

public class Tarea implements Serializable {
    String tituloTarea;
    int progreso;
    GregorianCalendar fechaCreacion;
    GregorianCalendar fechaObjetivo;
    boolean prioritaria;
    String diasRestantes;

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
    public Tarea(String tituloTarea, int progreso , boolean prioritaria) {
        this.tituloTarea = tituloTarea;
        this.progreso = progreso;
        this.fechaCreacion = new GregorianCalendar();
        this.prioritaria = prioritaria;
        this.fechaObjetivo = new GregorianCalendar(2023, 12, 23);

    }

    public Tarea(String tituloTarea, int progreso, boolean prioritaria, String fechaInicio, String fechaObjetivo){
        this.tituloTarea = tituloTarea;
        this.progreso = progreso;
        String[] a = fechaInicio.split("/");
        this.fechaCreacion = new GregorianCalendar(Integer.parseInt(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[2]));
        a = fechaObjetivo.split("/");
        this.fechaObjetivo = new GregorianCalendar(Integer.parseInt(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[2]));
        this.prioritaria = prioritaria;

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

        int year = fechaCreacion.get(GregorianCalendar.YEAR);
        int month = fechaCreacion.get(GregorianCalendar.MONTH) + 1;
        int day = fechaCreacion.get(GregorianCalendar.DAY_OF_MONTH);
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
        long diferenciaEnMilisegundos = fechaObjetivo.getTimeInMillis() - fechaCreacion.getTimeInMillis();
        long diferenciaEnDias = diferenciaEnMilisegundos / (24 * 60 * 60 * 1000);
        diasRestantes = diferenciaEnDias + "";
        return diasRestantes;
    }

}
