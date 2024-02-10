package com.example.trasstarea.Detalles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trasstarea.Detalles.Archivos.AudioDetalles;
import com.example.trasstarea.Detalles.Archivos.DocumentsDetalles;
import com.example.trasstarea.Detalles.Archivos.ImagenDetalles;
import com.example.trasstarea.Detalles.Archivos.VideoDetalles;
import com.example.trasstarea.R;

import listaTareas.Tarea;

public class Detalles extends AppCompatActivity {

    TextView descripcion;
    Button botonI, botonV, botonA, botonD;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Intent iDetalles =  getIntent();
        Tarea a = (Tarea) iDetalles.getSerializableExtra("descripcion");
        descripcion = findViewById(R.id.descripcionDetalles);

        descripcion.setText(a.getDescripcionTarea());

        botonA = findViewById(R.id.botonAudioDetalles);
        botonD = findViewById(R.id.botonDocumentoDetalles);
        botonI = findViewById(R.id.botonImagenDetalles);
        botonV = findViewById(R.id.botonVideoDetalles);

        if (a.getDescripcionTarea().isEmpty()){
            descripcion.setText("Sin descripción");
            descripcion.setTextColor(R.color.grey);

        }

        if (a.getRutaVideo() == null){
            botonV.setEnabled(false);
            botonV.setText("Video no añadido");
        }
        if (a.getRutaImagen() == null){
            botonI.setEnabled(false);
            botonI.setText("Imagen no añadida");
        }
        if (a.getRutaDocumento() == null){
            botonD.setEnabled(false);
            botonD.setText("Vacio");
        }
        if (a.getRutaAudio() == null){
            botonA.setEnabled(false);
            botonA.setText("Audio no añadido");
        }

        botonI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detalles.this, ImagenDetalles.class);
                String tuStringExtra = a.getRutaImagen();
                intent.putExtra("imagen", tuStringExtra);
                startActivity(intent);
            }
        });

        botonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detalles.this, DocumentsDetalles.class);
                String tuStringExtra =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + a.getRutaDocumento();
                intent.putExtra("documento", tuStringExtra);
                startActivity(intent);
            }
        });


        botonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detalles.this, AudioDetalles.class);
                String tuStringExtra =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + a.getRutaAudio();
                intent.putExtra("audio", tuStringExtra);
                intent.putExtra("nombreCancion", a.getRutaAudio());
                startActivity(intent);
            }
        });

        botonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detalles.this, VideoDetalles.class);
                String tuStringExtra =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + a.getRutaVideo();
                intent.putExtra("video", tuStringExtra);
                startActivity(intent);
            }
        });

    }


}