package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.NotificacionesApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionesRepository {

    private NotificacionesApi notificacionesApi;

    public NotificacionesRepository() {
        notificacionesApi = ConfigApi.getNotificacionesApi();
    }

    // Método para enviar notificación de pago a un docente específico
    public LiveData<BestGenericResponse<String>> enviarNotificacionPago(int paymentId) {
        MutableLiveData<BestGenericResponse<String>> liveData = new MutableLiveData<>();
        notificacionesApi.enviarNotificacionPago(paymentId).enqueue(new Callback<BestGenericResponse<String>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<String>> call, Response<BestGenericResponse<String>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al enviar notificación de pago", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<String>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para aceptar notificación de pago por parte del docente
    public LiveData<BestGenericResponse<String>> aceptarNotificacionPago(int teacherId, int paymentId) {
        MutableLiveData<BestGenericResponse<String>> liveData = new MutableLiveData<>();
        notificacionesApi.aceptarNotificacionPago(teacherId, paymentId).enqueue(new Callback<BestGenericResponse<String>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<String>> call, Response<BestGenericResponse<String>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al aceptar notificación de pago", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<String>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }
}
