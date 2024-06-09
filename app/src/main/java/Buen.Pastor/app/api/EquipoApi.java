package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.service.Equipment;
import Buen.Pastor.app.entity.BestGenericResponse;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface EquipoApi {
    // Ruta base para todos los endpoints de la API de Equipo
    String base = "/api/equipo";

    // Método para agregar un equipo
    @POST(base + "/agregar")
    Call<BestGenericResponse<Equipment>> addEquipo(@Body Equipment equipo);

    // Método para modificar un equipo
    @PUT(base + "/modificar")
    Call<BestGenericResponse<Equipment>> updateEquipo(@Body Equipment equipo);

    // Método para eliminar un equipo
    @DELETE(base + "/eliminar/{id}")
    Call<BestGenericResponse<Void>> deleteEquipo(@Path("id") Integer id);

    // Método para listar todos los equipos
    @GET(base + "/listar")
    Call<BestGenericResponse<List<Equipment>>> listAllEquipos();

    @GET(base + "/{id}")
    Call<BestGenericResponse<Equipment>> getEquipoById(@Path("id") Integer id);

    // Filtros
    @GET(base + "/filtro/nombre")
    Call<BestGenericResponse<List<Equipment>>> filtroPorNombre(@Query("nombreEquipo") String nombreEquipo);

    @GET(base + "/filtro/codigoPatrimonial")
    Call<BestGenericResponse<List<Equipment>>> filtroCodigoPatrimonial(@Query("codigoPatrimonial") String codigoPatrimonial);

    @GET(base + "/filtro/fechaCompra")
    Call<BestGenericResponse<List<Equipment>>> filtroFechaCompraBetween(
            @Query("fechaInicio") String fechaInicio,
            @Query("fechaFin") String fechaFin);


    @Multipart
    @POST(base + "/escanearCodigoBarra")
    Call<BestGenericResponse<Equipment>> escanearCodigoBarra(@Part MultipartBody.Part file);

    // Método para descargar un reporte de Excel de los equipos
    @GET(base + "/descargarReporte")
    Call<ResponseBody> downloadExcelReport();

    @GET(base + "/generarCodigoBarra/{codigoPatrimonial}")
    Call<ResponseBody> generarCodigoBarra(@Path("codigoPatrimonial") String codigoPatrimonial);

}
