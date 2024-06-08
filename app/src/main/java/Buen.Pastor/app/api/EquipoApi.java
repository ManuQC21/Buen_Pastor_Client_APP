package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.service.Equipment;
import Buen.Pastor.app.entity.GenericResponse;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface EquipoApi {
    // Ruta base para todos los endpoints de la API de Equipo
    String base = "/equipo";

    // Método para agregar un equipo
    @POST(base + "/agregar")
    Call<GenericResponse<Equipment>> addEquipo(@Body Equipment equipo);

    // Método para modificar un equipo
    @PUT(base + "/modificar")
    Call<GenericResponse<Equipment>> updateEquipo(@Body Equipment equipo);

    // Método para eliminar un equipo
    @DELETE(base + "/eliminar/{id}")
    Call<GenericResponse<Void>> deleteEquipo(@Path("id") Integer id);

    // Método para listar todos los equipos
    @GET(base + "/listar")
    Call<GenericResponse<List<Equipment>>> listAllEquipos();

    @GET(base + "/{id}")
    Call<GenericResponse<Equipment>> getEquipoById(@Path("id") Integer id);

    // Filtros
    @GET(base + "/filtro/nombre")
    Call<GenericResponse<List<Equipment>>> filtroPorNombre(@Query("nombreEquipo") String nombreEquipo);

    @GET(base + "/filtro/codigoPatrimonial")
    Call<GenericResponse<List<Equipment>>> filtroCodigoPatrimonial(@Query("codigoPatrimonial") String codigoPatrimonial);

    @GET(base + "/filtro/fechaCompra")
    Call<GenericResponse<List<Equipment>>> filtroFechaCompraBetween(
            @Query("fechaInicio") String fechaInicio,
            @Query("fechaFin") String fechaFin);


    @Multipart
    @POST(base + "/escanearCodigoBarra")
    Call<GenericResponse<Equipment>> escanearCodigoBarra(@Part MultipartBody.Part file);

    // Método para descargar un reporte de Excel de los equipos
    @GET(base + "/descargarReporte")
    Call<ResponseBody> downloadExcelReport();
}
