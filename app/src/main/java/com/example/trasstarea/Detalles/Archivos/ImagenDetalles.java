package com.example.trasstarea.Detalles.Archivos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.trasstarea.R;

public class ImagenDetalles extends AppCompatActivity {

   ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_detalles);
        Intent iDetalles =  getIntent();
        String ruta = (String) iDetalles.getStringExtra("imagen");
        iv = findViewById(R.id.imagenDetalles1);
        String rutaCompleta = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + ruta;
        Bitmap bitmap = BitmapFactory.decodeFile(rutaCompleta);
        iv.setImageBitmap(bitmap);

    }
}