package com.example.trasstarea.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDos# newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDos extends Fragment {
    Button  btIr1, btGuardar, cargarImagen, cargarDocumento, cargarAudio, cargarVideo;
    EditText descripcionTarea;

    int idTarea;

    public int caso = 0;
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
        cargarDocumento = fragmento2.findViewById(R.id.botonDocumento);
        cargarAudio = fragmento2.findViewById(R.id.botonAudio);
        cargarVideo = fragmento2.findViewById(R.id.botonVideo);

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


        cargarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarAudio();
            }
        });

        cargarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarVideo();
            }
        });

        cargarDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDocumento();
            }
        });


        if (viewModel.getRutaImagen() != null){
            String rutaImagen = viewModel.getRutaImagen().getValue();
            cargarImagen.setText(rutaImagen);
            rutaImagen = viewModel.getRutaImagen().getValue();
        }
        if (viewModel.getRutaAudio() != null){
            String rutaAudio = viewModel.getRutaAudio().getValue();
            cargarAudio.setText(rutaAudio);
            rutaAudio = viewModel.getRutaAudio().getValue();
        }
        if (viewModel.getRutaVideo() != null){
            String rutaVideo = viewModel.getRutaVideo().getValue();
            cargarVideo.setText(rutaVideo);
            rutaVideo = viewModel.getRutaVideo().getValue();
        }
        if (viewModel.getRutaDocumento() != null){
            String rutaDocumento = viewModel.getRutaDocumento().getValue();
            cargarDocumento.setText(rutaDocumento);
            rutaDocumento = viewModel.getRutaDocumento().getValue();
        }


        return fragmento2;
    }

    public void cargarFoto(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        caso = 1;
        startActivityForResult(i,REQUEST_CODE_SELECT_IMAGE);
    }

    public void cargarDocumento(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("application/*");
        caso = 2;
        startActivityForResult(i,REQUEST_CODE_SELECT_IMAGE);

    }
    public void cargarVideo(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("video/*");
        caso = 4;
        startActivityForResult(i,REQUEST_CODE_SELECT_IMAGE);

    }
    public void cargarAudio(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        caso = 3;
        startActivityForResult(i,REQUEST_CODE_SELECT_IMAGE);

    }


    public boolean existeSD(){
        String estado = Environment.getExternalStorageState();
        //El primer parametro devuelve true si esta disponible la tarjeta SD, y el segundo si se puede escribir en ella
        return Environment.MEDIA_MOUNTED.equals(estado) && Environment.MEDIA_MOUNTED_READ_ONLY.equals(estado);

    }

    public void escribirArchivos(Intent data, int caso){
        Uri imageUri = data.getData();
        SharedPreferences a = PreferenceManager.getDefaultSharedPreferences(getContext());
        File directorioImagenesAlmacenamientoInterno = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        boolean b = a.getBoolean("tarjeta", false);
        Date fecha = new Date();
        Calendar rightNow = Calendar.getInstance();
        String dia = rightNow.get(Calendar.DAY_OF_MONTH) + "";
        String mes = fecha.getMonth() + "";
        String year = fecha.getYear() + "";

        String tipo ="";
        String formatoArchivo = "";
        if (caso == 1){tipo = "IMG"; formatoArchivo = ".png";}
        else if (caso == 2){tipo = "DOC"; formatoArchivo = ".pdf";}
        else if (caso == 3){tipo = "AUD"; formatoArchivo = ".mp3";}
        else if (caso == 4){tipo = "VID"; formatoArchivo = ".mp4";}

        //FORMATO DE LOS ARCHIVOS PARA QUE A LA HORA DE BORRARLOS DEPENDIENDO DE LOS DIAS QUE SE HAYA PUESTO EN LA PREFERENCIA
        //DEL SIMBOLO % HACIA DELANTE INDICA A LA FECHA DESDE OTRO LADO CONTROLAREMOS LA FECHA Y BORRAREMOS EN CASO DE QUE
        //HAYAN PASADO LOS DIAS DE LA PREFERENCIA

        String nombreArchivo = tipo+  "%" + imageUri.getLastPathSegment() + "%" + dia + mes + year + formatoArchivo;



        File imageFile =
                new File(b
                        ? (existeSD()
                        ?
                        Environment.getExternalStorageDirectory().getAbsoluteFile()
                        : directorioImagenesAlmacenamientoInterno)
                        : directorioImagenesAlmacenamientoInterno,
                        nombreArchivo);
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
        switch (caso){
            case 1:
                rutaImagen = imageFile.getName();
                cargarImagen.setText(rutaImagen);
                break;
            case 2:
                rutaDocumento = imageFile.getName();
                cargarDocumento.setText(rutaDocumento);
                break;
            case 3:
                rutaAudio = imageFile.getName();
                cargarAudio.setText(rutaAudio);
                break;
            case 4:
                rutaVideo = imageFile.getName();
                cargarVideo.setText(rutaVideo);
                break;
        }




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        escribirArchivos(data, caso);
    }
}