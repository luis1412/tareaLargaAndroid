package listaTareas;

import java.util.Date;
import java.util.GregorianCalendar;

public class Tarea {
    String tituloTarea;
    int progreso;
    GregorianCalendar fechaCreacion;
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

    public boolean isPrioritaria() {
        return prioritaria;
    }

    public void setPrioritaria(boolean prioritaria) {
        this.prioritaria = prioritaria;
    }

    public String getDiasRestantes() {
        GregorianCalendar diaActual = new GregorianCalendar();
        long diferenciaEnMilisegundos = diaActual.getTimeInMillis() - fechaCreacion.getTimeInMillis();
        long diferenciaEnDias = diferenciaEnMilisegundos / (24 * 60 * 60 * 1000);
        diasRestantes = diferenciaEnDias + "";
        return diasRestantes;
    }

}
