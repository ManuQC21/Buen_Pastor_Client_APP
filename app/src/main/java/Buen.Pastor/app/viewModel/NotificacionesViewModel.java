package Buen.Pastor.app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Notification;
import Buen.Pastor.app.repository.NotificacionesRepository;

public class NotificacionesViewModel extends AndroidViewModel {

    private NotificacionesRepository notificacionesRepository;

    public NotificacionesViewModel(@NonNull Application application) {
        super(application);
        notificacionesRepository = new NotificacionesRepository();
    }

    // Método para enviar notificación de pago
    public LiveData<BestGenericResponse<String>> enviarNotificacionPago(int paymentId) {
        return notificacionesRepository.enviarNotificacionPago(paymentId);
    }

    // Método para aceptar notificación de pago
    public LiveData<BestGenericResponse<String>> aceptarNotificacionPago(int teacherId, int paymentId) {
        return notificacionesRepository.aceptarNotificacionPago(teacherId, paymentId);
    }

    // Método para listar notificaciones de un docente específico
    public LiveData<BestGenericResponse<List<Notification>>> listarNotificacionesPorDocente(int teacherId) {
        return notificacionesRepository.listarNotificacionesPorDocente(teacherId);
    }

}
