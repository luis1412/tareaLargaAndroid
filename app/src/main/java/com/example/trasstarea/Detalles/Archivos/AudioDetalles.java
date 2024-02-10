package com.example.trasstarea.Detalles.Archivos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.trasstarea.R;

import java.io.File;

public class AudioDetalles extends AppCompatActivity {


    Button play;
    MediaPlayer mp;
    TextView tv;

    SeekBar pb;

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
        pb = findViewById(R.id.seekBar);



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

        pb.setMax(mp.getDuration());

// Actualizar la barra de progreso mientras la canción se reproduce
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        int currentPosition = mp.getCurrentPosition();
                        pb.setProgress(currentPosition);
                        Thread.sleep(1000); // Actualizar la barra de progreso cada segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        pb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }
}