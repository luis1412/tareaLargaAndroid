package com.example.trasstarea.Detalles.Archivos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trasstarea.R;

import java.io.File;

public class AudioDetalles extends AppCompatActivity {


    Button play;
    MediaPlayer mp;
    TextView tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_detalles);
        Intent iDetalles =  getIntent();
        String ruta = (String) iDetalles.getStringExtra("audio");
        String nombreArchivo = (String) iDetalles.getStringExtra("nombreCancion");

        Uri a = Uri.parse(ruta);

        tv = findViewById(R.id.nombreRecurso);
        tv.setText(nombreArchivo);


        play = findViewById(R.id.playButton);
        mp = MediaPlayer.create(this, a);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying()){
                    mp.pause();
                    play.setBackgroundResource(R.drawable.play);

                }
                else{
                    mp.start();
                    play.setBackgroundResource(R.drawable.pause);
                }
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }
}