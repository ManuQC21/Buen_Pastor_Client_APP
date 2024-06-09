package Buen.Pastor.app.api;

import java.util.List;

import Buen.Pastor.app.entity.service.App.TeacherPaymentDTO;
import Buen.Pastor.app.entity.service.TeacherPayment;
import Buen.Pastor.app.entity.BestGenericResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface PagosApi {
    // Ruta base para todos los endpoints de la API de Pagos
    String base = "/api/payment";

    // Método para agregar un pago
    @POST(base + "/agregar")
    Call<BestGenericResponse<TeacherPayment>> agregarPago(@Body TeacherPayment pago);

    // Método para modificar un pago
    @PUT(base + "/editar")
    Call<BestGenericResponse<TeacherPayment>> editarPago(@Body TeacherPayment pago);

    // Método para eliminar un pago
    @DELETE(base + "/eliminar/{id}")
    Call<BestGenericResponse<Void>> eliminarPago(@Path("id") int id);

    // Método para listar todos los pagos
    @GET(base + "/listar")
    Call<BestGenericResponse<List<TeacherPaymentDTO>>> listarTodosLosPagos();

    // Método para generar un baucher en PDF de un pago específico
    @GET(base + "/baucher/{paymentId}")
    Call<ResponseBody> generarBaucherPdf(@Path("paymentId") int paymentId);

    // Método para descargar un reporte de todos los pagos en formato Excel
    @GET(base + "/reporte")
    Call<ResponseBody> generateExcelReport();

    // Método para listar todos los pagos de un docente específico
    @GET(base + "/pagosDocente/{teacherId}")
    Call<BestGenericResponse<List<TeacherPaymentDTO>>> listarPagosPorDocenteId(@Path("teacherId") int teacherId);

    @GET(base + "/detalle/{id}")
    Call<BestGenericResponse<TeacherPaymentDTO>> obtenerPagoPorId(@Path("id") int id);

}
