package estadisticas.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.trasstarea.Data.AppDatabase;
import com.example.trasstarea.Fragmentos.CrearTareaViewModel;
import com.example.trasstarea.R;
import com.example.trasstarea.databinding.FragmentHomeBinding;

import java.util.List;

import listaTareas.Tarea;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    AppDatabase appDatabase;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View home = inflater.inflate(R.layout.fragment_home, container, false);

        appDatabase = Room.databaseBuilder(requireContext(), AppDatabase.class, "tareasDB").allowMainThreadQueries().build();

        //TODO RECOGER LA LISTA DEL ACTIVITY NO DE LA BD
        List<Tarea> tareas = appDatabase.daoTarea().obtenerTareas();
        List<Tarea> tareasP = appDatabase.daoTarea().obtenerTareasPrioritarias();


        TextView numeroTareas = home.findViewById(R.id.totalTareas);
        TextView numeroTareasP =  home.findViewById(R.id.totalTareasP);
        TextView progresoMedia =  home.findViewById(R.id.mediaProgreso);

        double mediaEdades = tareas.stream()
                .mapToDouble(Tarea::getProgreso)
                .average()
                .orElse(0);

        numeroTareasP.setText(tareasP.size() + "");
        numeroTareas.setText(tareas.size() + "");
         progresoMedia.setText(mediaEdades + "");


        return home;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}