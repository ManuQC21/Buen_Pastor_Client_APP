package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.PagosApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.Global;
import Buen.Pastor.app.entity.service.App.TeacherPaymentDTO;
import Buen.Pastor.app.entity.service.TeacherPayment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
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
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        liveData.setValue(new BestGenericResponse<>(null, -1, errorMessage, null));
                    } catch (IOException e) {
                        liveData.setValue(new BestGenericResponse<>(null, -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<TeacherPayment>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
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
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        liveData.setValue(new BestGenericResponse<>(null, -1, errorMessage, null));
                    } catch (IOException e) {
                        liveData.setValue(new BestGenericResponse<>(null, -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<TeacherPayment>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
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
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        liveData.setValue(new BestGenericResponse<>(null, -1, errorMessage, null));
                    } catch (IOException e) {
                        liveData.setValue(new BestGenericResponse<>(null, -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Void>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para listar todos los pagos
    public LiveData<BestGenericResponse<List<TeacherPaymentDTO>>> listarTodosLosPagos() {
        MutableLiveData<BestGenericResponse<List<TeacherPaymentDTO>>> liveData = new MutableLiveData<>();
        pagosApi.listarTodosLosPagos().enqueue(new Callback<BestGenericResponse<List<TeacherPaymentDTO>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<TeacherPaymentDTO>>> call, Response<BestGenericResponse<List<TeacherPaymentDTO>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        liveData.setValue(new BestGenericResponse<>(null, -1, errorMessage, null));
                    } catch (IOException e) {
                        liveData.setValue(new BestGenericResponse<>(null, -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<TeacherPaymentDTO>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
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
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        liveData.setValue(new BestGenericResponse<>(null, -1, errorMessage, null));
                    } catch (IOException e) {
                        liveData.setValue(new BestGenericResponse<>(null, -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<TeacherPaymentDTO>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<TeacherPaymentDTO>> obtenerPagoPorId(int id) {
        final MutableLiveData<BestGenericResponse<TeacherPaymentDTO>> data = new MutableLiveData<>();
        pagosApi.obtenerPagoPorId(id).enqueue(new Callback<BestGenericResponse<TeacherPaymentDTO>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<TeacherPaymentDTO>> call, Response<BestGenericResponse<TeacherPaymentDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        data.setValue(new BestGenericResponse<>(null, -1, errorMessage, null));
                    } catch (IOException e) {
                        data.setValue(new BestGenericResponse<>(null, -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<TeacherPaymentDTO>> call, Throwable t) {
                data.setValue(new BestGenericResponse<>(null, -1, "Error al obtener el pago: " + t.getMessage(), null));
            }
        });
        return data;
    }
}
