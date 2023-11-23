package com.example.trasstarea;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import listaTareas.Tarea;

public class AdaptadorTarea extends RecyclerView.Adapter implements View.OnCreateContextMenuListener {
    private ArrayList<Tarea> datos;
    Context contexto;

    Tarea tarea;

    public AdaptadorTarea(Context contexto,ArrayList<Tarea> datos) {
        this.datos = datos;
        this.contexto = contexto;
    }

    public Tarea getTarea(){
        return tarea;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarealista,parent,false);
        item.setOnCreateContextMenuListener(this);
        TareaViewHolder tarea = new TareaViewHolder(item);
        return tarea;
    }





    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Asignamos el dato del array correspondiente a la posición actual al
        //objeto ViewHolder, de forma que se represente en el RecyclerView.
        ((TareaViewHolder) holder).bindTarea(datos.get(position));

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                MenuInflater inflater = new MenuInflater(view.getContext());
                inflater.inflate(R.menu.menu_contextual, contextMenu);
                tarea = datos.get(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        //Devolvemos el tamaño de array de datos de Capitales
        return datos.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    public class TareaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imagenEstrella;
        private TextView tituloTarea;
        private ProgressBar barraProgresoTarea;
        private TextView fecha;
        private TextView numeroDiasRestantes;

        //Método constructor
        public TareaViewHolder (@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imagenEstrella = itemView.findViewById(R.id.estrellaTarea);
            tituloTarea = itemView.findViewById(R.id.tituloTarea);
            barraProgresoTarea = itemView.findViewById(R.id.barraProgreso);
            fecha = itemView.findViewById(R.id.fecha);
            numeroDiasRestantes = itemView.findViewById(R.id.diasRestantes);
        }

        //Método que nos permitirá dar valores a cada campo del objeto ViewHolder y que
        //el mismo pueda ser mostrado en el RecyclerView
        public void bindTarea(Tarea c) {
            tituloTarea.setText(c.getTituloTarea());
            barraProgresoTarea.setProgress(c.getProgreso());
            fecha.setText(c.getfechaString(c.getFechaObjetivo()));
            numeroDiasRestantes.setText(c.getDiasRestantes());
            if (Integer.parseInt(c.getDiasRestantes()) < 0){
                numeroDiasRestantes.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
            }
            if (c.isPrioritaria()){
                imagenEstrella.setImageResource(R.drawable.estrellaactiva);
            }
            else{
                imagenEstrella.setImageResource(R.drawable.baseline_star_purple500_24);
            }
            if (c.getProgreso() == 100){
                Paint paint = tituloTarea.getPaint();
                paint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                paint.setAntiAlias(false);

            }

        }

        //Método para manejar el evento click en un elemento del RecyclerView

      @Override
        public void onClick(View v) {
            //Extraemos los valores de los campos de la fila en la que hemos hecho click
            String resultado = "TItulo" + (((TextView) v.findViewById(R.id.tituloTarea)).getText())
                    + "Progreso: " + ((ProgressBar) v.findViewById(R.id.barraProgreso)).getProgress() + "fecha" +
                    ((TextView) v.findViewById(R.id.fecha)).getText() + "Dias Restantes"  + ((TextView) v.findViewById(R.id.diasRestantes)).getText();
            //Mostramos la información en un diálogo.
            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
            builder.setTitle("Información");
            builder.setMessage(resultado);
            //builder.setPositiveButton("OK", new DialogInterface().OnClickListener() {
            //    public void onClick(DialogInterface dialog, int which) {
            //        dialog.cancel();
             //   }
            }//);

           // builder.show();
        }
    }



