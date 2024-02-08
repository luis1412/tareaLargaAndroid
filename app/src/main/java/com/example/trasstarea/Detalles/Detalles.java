package com.example.trasstarea.Detalles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trasstarea.R;

import listaTareas.Tarea;

public class Detalles extends AppCompatActivity {

    TextView descripcion;
    VideoView videoView;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Intent iDetalles =  getIntent();
        Tarea a = (Tarea) iDetalles.getSerializableExtra("descripcion");

      videoView = findViewById(R.id.videoDetalle);
        descripcion = findViewById(R.id.descripcionDetalles);
        image = findViewById(R.id.imagenURL);
        descripcion.setText(a.getDescripcionTarea());


        String rutaCompleta = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + a.getRutaImagen();

        Bitmap bitmap = BitmapFactory.decodeFile(rutaCompleta);
        image.setImageBitmap(bitmap);
if (a.getRutaVideo() != null){
        String rutaCompletaVideo = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)+ "/" + a.getRutaVideo();
        Uri uri = Uri.parse(rutaCompletaVideo);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
         mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        // Iniciar la reproducción del video
        videoView.start();
}
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }
}