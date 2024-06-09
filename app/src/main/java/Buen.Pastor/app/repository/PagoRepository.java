package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.PagosApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.TeacherPaymentDTO;
import Buen.Pastor.app.entity.service.TeacherPayment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class PagoRepository {

    private PagosApi pagosApi;

    public PagoRepository() {
        pagosApi = ConfigApi.getPagosApi();
    }

    // Método para agregar un pago
    public LiveData<BestGenericResponse<TeacherPayment>> agregarPago(TeacherPayment pago) {
        MutableLiveData<BestGenericResponse<TeacherPayment>> liveData = new MutableLiveData<>();
        pagosApi.agregarPago(pago).enqueue(new Callback<BestGenericResponse<TeacherPayment>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<TeacherPayment>> call, Response<BestGenericResponse<TeacherPayment>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al agregar el pago", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<TeacherPayment>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para modificar un pago
    public LiveData<BestGenericResponse<TeacherPayment>> editarPago(TeacherPayment pago) {
        MutableLiveData<BestGenericResponse<TeacherPayment>> liveData = new MutableLiveData<>();
        pagosApi.editarPago(pago).enqueue(new Callback<BestGenericResponse<TeacherPayment>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<TeacherPayment>> call, Response<BestGenericResponse<TeacherPayment>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al editar el pago", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<TeacherPayment>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para eliminar un pago
    public LiveData<BestGenericResponse<Void>> eliminarPago(int id) {
        MutableLiveData<BestGenericResponse<Void>> liveData = new MutableLiveData<>();
        pagosApi.eliminarPago(id).enqueue(new Callback<BestGenericResponse<Void>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Void>> call, Response<BestGenericResponse<Void>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al eliminar el pago", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Void>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para listar todos los pagos
    public LiveData<BestGenericResponse<List<TeacherPayment>>> listarTodosLosPagos() {
        MutableLiveData<BestGenericResponse<List<TeacherPayment>>> liveData = new MutableLiveData<>();
        pagosApi.listarTodosLosPagos().enqueue(new Callback<BestGenericResponse<List<TeacherPayment>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<TeacherPayment>>> call, Response<BestGenericResponse<List<TeacherPayment>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al listar los pagos", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<TeacherPayment>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para generar un baucher en PDF de un pago específico
    public LiveData<ResponseBody> generarBaucherPdf(int paymentId) {
        MutableLiveData<ResponseBody> liveData = new MutableLiveData<>();
        pagosApi.generarBaucherPdf(paymentId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    // Método para descargar un reporte de todos los pagos en formato Excel
    public LiveData<ResponseBody> generateExcelReport() {
        MutableLiveData<ResponseBody> liveData = new MutableLiveData<>();
        pagosApi.generateExcelReport().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }
    // Método para obtener la lista de pagos de un docente específico

    public LiveData<BestGenericResponse<List<TeacherPaymentDTO>>> listarPagosPorDocenteId(int teacherId) {
        MutableLiveData<BestGenericResponse<List<TeacherPaymentDTO>>> liveData = new MutableLiveData<>();
        pagosApi.listarPagosPorDocenteId(teacherId).enqueue(new Callback<BestGenericResponse<List<TeacherPaymentDTO>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<TeacherPaymentDTO>>> call, Response<BestGenericResponse<List<TeacherPaymentDTO>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al obtener pagos del docente", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<TeacherPaymentDTO>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

}
