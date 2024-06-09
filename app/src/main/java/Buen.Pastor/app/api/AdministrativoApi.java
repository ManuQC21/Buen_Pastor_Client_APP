package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.service.Administrative;
import Buen.Pastor.app.entity.BestGenericResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface AdministrativoApi {

    String base = "/api/administrativo";

    @POST(base + "/agregar")
    Call<BestGenericResponse<Administrative>> agregarAdministrativo(@Body Administrative administrativo);

    // Editar un administrativo existente
    @PUT(base + "/editar/{id}")
    Call<BestGenericResponse<Administrative>> editarAdministrativo(@Path("id") int id, @Body Administrative administrativo);

    // Eliminar un administrativo
    @DELETE(base + "/eliminar/{id}")
    Call<BestGenericResponse<Void>> eliminarAdministrativo(@Path("id") int id);

    // Listar todos los administrativos
    @GET(base + "/listar")
    Call<BestGenericResponse<List<Administrative>>> listarTodosLosAdministrativos();
}
