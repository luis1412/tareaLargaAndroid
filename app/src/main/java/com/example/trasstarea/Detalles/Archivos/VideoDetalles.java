package com.example.trasstarea.Detalles.Archivos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.trasstarea.R;

public class VideoDetalles extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detalles);
        Intent iDetalles =  getIntent();
        String ruta = (String) iDetalles.getStringExtra("video");

        videoView = findViewById(R.id.videoViewDetalles);

            Uri uri = Uri.parse(ruta);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.start();


    }
}