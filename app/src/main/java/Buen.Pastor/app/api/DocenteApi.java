package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.service.App.TeacherDTO;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.entity.BestGenericResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface DocenteApi {

    String base = "/api/docente";

    @POST(base + "/agregar")
    Call<BestGenericResponse<Teacher>> agregarDocente(@Body Teacher docente);

    // Método para editar un docente existente
    @PUT( base + "editar/{id}")
    Call<BestGenericResponse<Teacher>> editarDocente(@Path("id") int id, @Body Teacher docente);

    // Método para eliminar un docente
    @DELETE( base + "/eliminar/{id}")
    Call<BestGenericResponse<Void>> eliminarDocente(@Path("id") int id);

    // Método para listar todos los docentes
    @GET(base + "/listar")
    Call<BestGenericResponse<List<TeacherDTO>>> listarTodosLosDocentes();

    // Método para obtener los detalles de un docente específico por ID
    @GET(base + "/detalles/{id}")
    Call<BestGenericResponse<Teacher>> obtenerDocentePorId(@Path("id") int id);
}
