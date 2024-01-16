package com.example.trasstarea.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trasstarea.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDos# newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDos extends Fragment {
    Button  btIr1, btGuardar, cargarImagen;
    EditText descripcionTarea;

    String rutaImagen, rutaDocumento, rutaAudio, rutaVideo;

    public static final int REQUEST_CODE_SELECT_IMAGE = 1;


    private CrearTareaViewModel viewModel;

    public FragmentDos() {}

    public interface ComunicacionFragmento2{
        void onBotonIr1Clicked();
        void onBotonGuardaClicked();
    }
    private ComunicacionFragmento2 comunicador2;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ComunicacionFragmento2) {
            comunicador2 = (ComunicacionFragmento2) context;
        } else {
            throw new ClassCastException(context + " debe implementar interfaz de comunicación con el 2º fragmento");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CrearTareaViewModel.class);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflamos el layout de este fragmento
        View fragmento2 = inflater.inflate(R.layout.fragment_dos, container, false);
        //Botón para cambiar fragmento

        btIr1 = fragmento2.findViewById(R.id.boton_Volver);
        btIr1.setOnClickListener(view -> comunicador2.onBotonIr1Clicked());
        descripcionTarea = fragmento2.findViewById(R.id.descripcionTarea);
        cargarImagen = fragmento2.findViewById(R.id.botonImagen);


        if (viewModel.getDescripcionTarea() != null){
            descripcionTarea.setText(viewModel.getDescripcionTarea().getValue());
        }


        btGuardar = fragmento2.findViewById(R.id.boton_Guardar);
        btGuardar.setOnClickListener(view -> {
            viewModel.setDescripcionTarea(descripcionTarea.getText().toString());
            viewModel.setRutaAudio(rutaAudio);
            viewModel.setRutaVideo(rutaVideo);
            viewModel.setRutaDocumento(rutaDocumento);
            viewModel.setRutaImagen(rutaImagen);
            comunicador2.onBotonGuardaClicked();
        });

        cargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFoto();
            }
        });

        if (viewModel.getRutaImagen() != null){
            cargarImagen.setText(viewModel.getRutaImagen().getValue());
            rutaImagen = viewModel.getRutaImagen().getValue();
        }
        return fragmento2;
    }

    public void cargarFoto(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Uri imageUri = data.getData();

        // Guarda la imagen en el almacenamiento interno del dispositivo
        File imageFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageUri.getLastPathSegment());
        try {
            InputStream inputStream =  getContext().getContentResolver().openInputStream(imageUri);
            OutputStream outputStream = new FileOutputStream(imageFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
    } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cargarImagen.setText(imageFile.getAbsolutePath());
        rutaImagen = imageFile.getAbsolutePath();

    }
}