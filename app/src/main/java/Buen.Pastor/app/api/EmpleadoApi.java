package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Empleado;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EmpleadoApi {
    @GET("empleados/listar")
    Call<GenericResponse<List<Empleado>>> listarEmpleados();
}

