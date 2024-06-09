package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Location;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UbicacionApi {

    String base = "/api/ubicaciones";
    @GET(base + "/listar")
    Call<BestGenericResponse<List<Location>>> listarUbicaciones();
}
