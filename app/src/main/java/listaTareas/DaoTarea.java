package listaTareas;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoTarea {
    @Query("SELECT * FROM tarea")
    List<Tarea> obtenerTareas();

    @Query("SELECT * FROM tarea WHERE prioritaria = true")
    List<Tarea> obtenerTareasPrioritarias();

    @Insert
    void insertarTarea(Tarea...tareas);

    @Query("UPDATE tarea SET tituloTarea = :tituloTarea, progreso =:progreso, prioritaria = :prioritaria, fechaCreacion =:fechaInicio , fechaObjetivo = :fechaObjetivo, descripcionTarea = :descripcionTarea WHERE id = :id")
    void actualizarTarea(String tituloTarea, int progreso, boolean prioritaria, String fechaInicio, String fechaObjetivo, String descripcionTarea, int id );

    @Query("DELETE FROM tarea WHERE id = :id")
    void borrarUsuario(int id);


}
