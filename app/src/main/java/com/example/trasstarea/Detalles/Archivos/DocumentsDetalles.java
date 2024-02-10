package com.example.trasstarea.Detalles.Archivos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trasstarea.R;
import com.pdfview.PDFView;

import java.io.File;

public class DocumentsDetalles extends AppCompatActivity {

    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_detalles);
        Intent iDetalles =  getIntent();
        String ruta = (String) iDetalles.getStringExtra("documento");
        pdfView = findViewById(R.id.pdfView);
        File file = new File(ruta);
        pdfView.fromFile(file);
        pdfView.isZoomEnabled();
        pdfView.show();
    }
}