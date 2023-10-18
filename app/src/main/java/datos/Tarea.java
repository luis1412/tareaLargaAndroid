package datos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Tarea {
    String tituloTarea;
    int progreso;
    LocalDate fechaCreacion;
    boolean prioritaria;

    long diasRestantes;


    public Tarea(String tituloTarea, int progreso , boolean prioritaria) {
        this.tituloTarea = tituloTarea;
        this.progreso = progreso;
        this.fechaCreacion = LocalDate.now();
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isPrioritaria() {
        return prioritaria;
    }

    public void setPrioritaria(boolean prioritaria) {
        this.prioritaria = prioritaria;
    }

    public int getDiasRestantes() {
       diasRestantes = ChronoUnit.DAYS.between(fechaCreacion, LocalDate.now());
        return diasRestantes;
    }

}
