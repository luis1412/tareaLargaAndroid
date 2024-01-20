package listaTareas;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
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
    void actualizarTarea(String tituloTarea, int progreso, boolean prioritaria, Date fechaInicio, Date fechaObjetivo, String descripcionTarea, int id );

    @Query("DELETE FROM tarea WHERE id = :id")
    void borrarTarea(int id);

    @Query("DELETE FROM tarea")
    void borrarTodasTareas();

    @Query("DELETE FROM tarea WHERE progreso = 100")
    void borrarTareasCompletadas();

    @Query("SELECT * FROM tarea ORDER BY tituloTarea")
    List<Tarea> obtenerTareasAlfabeticas();

    @Query("SELECT * FROM tarea ORDER BY fechaCreacion")
    List<Tarea> obtenerTareasFecha();

    @Query("SELECT * FROM tarea ORDER BY diasRestantes")
    List<Tarea> obtenerTareasDias();

    @Query("SELECT * FROM tarea ORDER BY progreso")
    List<Tarea> obtenerTareasProgreso();



}
