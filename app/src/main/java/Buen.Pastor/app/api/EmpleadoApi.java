package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Employee;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EmpleadoApi {
    @GET("empleados/listar")
    Call<GenericResponse<List<Employee>>> listarEmpleados();
}

