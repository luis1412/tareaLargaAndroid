package com.example.trasstarea.Fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDos# newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDos extends Fragment {
    Button  btIr1, btGuardar, cargarImagen, cargarDocumento, cargarAudio, cargarVideo;
    EditText descripcionTarea;

    Uri imageUri2;
    int idTarea;

    public int caso = 0;
    String rutaImagen, rutaDocumento, rutaAudio, rutaVideo;

    public static final int REQUEST_CODE_SELECT_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 101;



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
                caso = 1;
                mostrarDialogoConOpciones();
            }
        });


        cargarAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caso = 3;
                mostrarDialogoConOpciones();
            }
        });

        cargarVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                caso = 4;
                mostrarDialogoConOpciones();
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

    public void mostrarDialogoConOpciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configuración del diálogo
        builder.setTitle("Selecciona una opción");

        // Opción 1
        builder.setPositiveButton("Galería", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(caso == 1) {
                    cargarFoto();
                }
                else if (caso == 4){
                    cargarVideo();
                }
                else if (caso == 3){
                    cargarAudio();
                }
            }
        });
        builder.setIcon(R.drawable.galeria);

        builder.setNegativeButton("Cámara", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             capturar();
            }
        });
        builder.setIcon(R.drawable.camara);

        builder.create().show();
    }


    public void capturar() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent aCamaraVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        Intent aGrabadora = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);


        if (caso == 1){
            lanzadorCamara.launch(i);
        }
        else if (caso == 4){
           lanzarCamaraVideo.launch(aCamaraVideo);
        }
        else if (caso == 3){
            lanzadorGrabadora.launch(aGrabadora);
        }

    }

    ActivityResultLauncher<Intent> lanzadorGrabadora = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Uri audio = o.getData() != null ? o.getData().getData() : null;
                    if (audio != null){
                        imageUri2 = audio;
                    }
                    escribirArchivos(null, 3, null);
                }

            }
    );

    ActivityResultLauncher<Intent> lanzarCamaraVideo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Uri video = o.getData() != null ? o.getData().getData() : null;
                    if (video != null) {
                        imageUri2 = video;
                    }
                    escribirArchivos(null, 4, null);
                }
            }
    );

    ActivityResultLauncher<Intent> lanzadorCamara = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                   Intent intentDevuelto = o.getData();
                   Bitmap imagenCapturada = (Bitmap) intentDevuelto.getExtras().get("data");
                   escribirArchivos(null, caso, imagenCapturada);
                    }
                }
    );


    public void cargarFoto(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        caso = 1;
        // Verificar si hay aplicaciones que puedan manejar la acción
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(i, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivityForResult(i, REQUEST_CODE_SELECT_IMAGE);
        } else {
            Toast.makeText(requireContext(), "No hay aplicaciones para manejar la acción", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarDocumento(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("application/*");
        caso = 2;

        // Verificar si hay aplicaciones que puedan manejar la acción
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(i, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivityForResult(i, REQUEST_CODE_SELECT_IMAGE);
        } else {
            Toast.makeText(requireContext(), "No hay aplicaciones para manejar la acción", Toast.LENGTH_SHORT).show();
        }

    }
    public void cargarVideo(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("video/*");
        caso = 4;

        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(i, 0);
        boolean isIntentSafe = activities.size() > 0;

        if (isIntentSafe) {
            startActivityForResult(i, REQUEST_CODE_SELECT_IMAGE);
        } else {
            Toast.makeText(requireContext(), "No hay aplicaciones para manejar la acción", Toast.LENGTH_SHORT).show();
        }

    }
    public void cargarAudio(){
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        caso = 3;
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(i, 0);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            startActivityForResult(i, REQUEST_CODE_SELECT_IMAGE);
        } else {
            Toast.makeText(requireContext(), "No hay aplicaciones para manejar la acción", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean existeSD(){
        String estado = Environment.getExternalStorageState();
        //El primer parametro devuelve true si esta disponible la tarjeta SD, y el segundo si se puede escribir en ella
        return Environment.MEDIA_MOUNTED.equals(estado) && Environment.MEDIA_MOUNTED_READ_ONLY.equals(estado);

    }

    public void escribirArchivos(Intent data, int caso, Bitmap bitmap){
        Uri imageUri = imageUri2;
        if (data != null){
           imageUri = data.getData();
        }
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
        String nombreArchivo;
        if (bitmap == null){
       nombreArchivo = tipo+  "%" + imageUri.getLastPathSegment() + "%" + dia + mes + year + formatoArchivo;
        }
        else{
            nombreArchivo = tipo+  "%" + bitmap.toString() + "%" + dia + mes + year + formatoArchivo;

        }
        //Tengo que hacer esto ya que debido a los dos puntos MediaPlayer no detecta bien la ruta del archivo
        if (nombreArchivo.contains("audio:")){
      nombreArchivo = nombreArchivo.replace("audio:", "");
        }

        File imageFile =
                new File(b
                        ? (existeSD()
                        ?
                        Environment.getExternalStorageDirectory().getAbsoluteFile()
                        : directorioImagenesAlmacenamientoInterno)
                        : directorioImagenesAlmacenamientoInterno,
                        nombreArchivo);


        if (bitmap == null){
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
        }
        else{
            try {
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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
        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                escribirArchivos(data, caso, null);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(requireContext(), "Selección de archivo cancelado", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap imagenCapturada = (Bitmap) data.getExtras().get("data");
            escribirArchivos(null, caso, imagenCapturada);
        }
    }
}