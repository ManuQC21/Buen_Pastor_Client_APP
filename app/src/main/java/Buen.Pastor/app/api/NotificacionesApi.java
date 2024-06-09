package Buen.Pastor.app.api;

import Buen.Pastor.app.entity.BestGenericResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface NotificacionesApi {


    String base = "/api/notification";
    // Enviar notificación de pago a un docente específico
    @POST(base + "/enviar/{paymentId}")
    Call<BestGenericResponse<String>> enviarNotificacionPago(@Path("paymentId") int paymentId);

    // Aceptar notificación de pago por parte del docente
    @POST(base + "/aceptar/{teacherId}/{paymentId}")
    Call<BestGenericResponse<String>> aceptarNotificacionPago(@Path("teacherId") int teacherId, @Path("paymentId") int paymentId);
}
