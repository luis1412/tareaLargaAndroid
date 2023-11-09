package com.example.trasstarea.Fragmentos;

import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.GregorianCalendar;

    public class CrearTareaViewModel extends ViewModel {

        private String titulo;
        private GregorianCalendar fechaCreacion;
        private GregorianCalendar fechaObjetivo;
        private int progreso;
        private boolean prioritaria;
        private String descripcion;

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }
}
