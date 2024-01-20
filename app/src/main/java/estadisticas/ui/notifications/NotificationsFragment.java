package estadisticas.ui.notifications;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.trasstarea.Data.AppDatabase;
import com.example.trasstarea.ListadoActivity;
import com.example.trasstarea.R;
import com.example.trasstarea.databinding.FragmentNotificationsBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import listaTareas.Tarea;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    AppDatabase appDatabase;

    Button botonBorrarTodo, botonBorrarCompletadas;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View borrarFragment = inflater.inflate(R.layout.fragment_notifications, container, false);

        botonBorrarTodo = borrarFragment.findViewById(R.id.botonBorrarTodo);
        botonBorrarCompletadas = borrarFragment.findViewById(R.id.botonBorrarCompletadas);

        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "tareasDB").allowMainThreadQueries().build();

        botonBorrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Confirmar Cambios");
                builder.setMessage("¿Estás seguro de que deseas borrar las tareas? \n Despues de borrar se volvera al menu principal ");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        appDatabase.daoTarea().borrarTodasTareas();
                        volverAlListado();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        botonBorrarCompletadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Confirmar Cambios");
                builder.setMessage("¿Estás seguro de que deseas borrar las tareas? \n Despues de borrar se volvera al menu principal");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        appDatabase.daoTarea().borrarTareasCompletadas();
                        volverAlListado();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return borrarFragment;
    }



    public void volverAlListado(){
            ListadoActivity listado = (ListadoActivity) getActivity();
            listado.recreate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}