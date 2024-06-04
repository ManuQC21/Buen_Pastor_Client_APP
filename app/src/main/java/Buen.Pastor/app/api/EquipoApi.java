package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.service.Equipo;
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
    Call<GenericResponse<Equipo>> addEquipo(@Body Equipo equipo);

    // Método para modificar un equipo
    @PUT(base + "/modificar")
    Call<GenericResponse<Equipo>> updateEquipo(@Body Equipo equipo);

    // Método para eliminar un equipo
    @DELETE(base + "/eliminar/{id}")
    Call<GenericResponse<Void>> deleteEquipo(@Path("id") Integer id);

    // Método para listar todos los equipos
    @GET(base + "/listar")
    Call<GenericResponse<List<Equipo>>> listAllEquipos();

    @GET(base + "/{id}")
    Call<GenericResponse<Equipo>> getEquipoById(@Path("id") Integer id);

    // Filtros
    @GET(base + "/filtro/nombre")
    Call<GenericResponse<List<Equipo>>> filtroPorNombre(@Query("nombreEquipo") String nombreEquipo);

    @GET(base + "/filtro/codigoPatrimonial")
    Call<GenericResponse<List<Equipo>>> filtroCodigoPatrimonial(@Query("codigoPatrimonial") String codigoPatrimonial);

    @GET(base + "/filtro/fechaCompra")
    Call<GenericResponse<List<Equipo>>> filtroFechaCompraBetween(
            @Query("fechaInicio") String fechaInicio,
            @Query("fechaFin") String fechaFin);


    @Multipart
    @POST(base + "/escanearCodigoBarra")
    Call<GenericResponse<Equipo>> escanearCodigoBarra(@Part MultipartBody.Part file);

    // Método para descargar un reporte de Excel de los equipos
    @GET(base + "/descargarReporte")
    Call<ResponseBody> downloadExcelReport();
}
