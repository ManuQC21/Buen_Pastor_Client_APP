package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Location;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UbicacionApi {
    @GET("ubicaciones/listar")
    Call<GenericResponse<List<Location>>> listarUbicaciones();
}
