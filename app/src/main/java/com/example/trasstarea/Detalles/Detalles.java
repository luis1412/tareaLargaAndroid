package com.example.trasstarea.Detalles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.trasstarea.R;

import listaTareas.Tarea;

public class Detalles extends AppCompatActivity {

    TextView descripcion;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Intent iDetalles =  getIntent();
        Tarea a = (Tarea) iDetalles.getSerializableExtra("descripcion");

        descripcion = findViewById(R.id.descripcionDetalles);
        image = findViewById(R.id.imagenURL);
        descripcion.setText(a.getDescripcionTarea());


        String rutaCompleta = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + a.getRutaImagen();
        String rutaCompletaVideo = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + a.getRutaVideo();
        Bitmap bitmap = BitmapFactory.decodeFile(rutaCompleta);
        Bitmap bitmapVideo = BitmapFactory.decodeFile(rutaCompletaVideo);

        image.setImageBitmap(bitmap);



    }
}