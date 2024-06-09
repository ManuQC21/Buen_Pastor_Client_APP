package Buen.Pastor.app.api;

import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Notification;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface NotificacionesApi {

    String base = "/api/notification";

    // Enviar notificación de pago a un docente específico
    @POST(base + "/enviar/{paymentId}")
    Call<BestGenericResponse<String>> enviarNotificacionPago(@Path("paymentId") int paymentId);

    // Aceptar notificación de pago por parte del docente
    @POST(base + "/aceptar/{teacherId}/{paymentId}")
    Call<BestGenericResponse<String>> aceptarNotificacionPago(@Path("teacherId") int teacherId, @Path("paymentId") int paymentId);

    // Listar notificaciones de un docente específico
    @GET(base + "/listar/{teacherId}")
    Call<BestGenericResponse<List<Notification>>> listarNotificacionesPorDocente(@Path("teacherId") int teacherId);
}
