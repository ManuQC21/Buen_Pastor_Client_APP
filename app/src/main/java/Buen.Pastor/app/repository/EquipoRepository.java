package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.EquipoApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.Global;
import Buen.Pastor.app.entity.service.Equipment;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipoRepository {

    private final EquipoApi equipoApi;

    public EquipoRepository() {
        equipoApi = ConfigApi.getEquipoApi();
    }

    // Agregar equipo
    public LiveData<BestGenericResponse<Equipment>> addEquipo(Equipment equipo) {
        MutableLiveData<BestGenericResponse<Equipment>> liveData = new MutableLiveData<>();
        equipoApi.addEquipo(equipo).enqueue(new Callback<BestGenericResponse<Equipment>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Equipment>> call, Response<BestGenericResponse<Equipment>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al agregar equipo", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Equipment>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Modificar equipo
    public LiveData<BestGenericResponse<Equipment>> updateEquipo(Equipment equipo) {
        MutableLiveData<BestGenericResponse<Equipment>> liveData = new MutableLiveData<>();
        equipoApi.updateEquipo(equipo).enqueue(new Callback<BestGenericResponse<Equipment>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Equipment>> call, Response<BestGenericResponse<Equipment>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al modificar equipo", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Equipment>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Eliminar equipo
    public LiveData<BestGenericResponse<Void>> deleteEquipo(Integer id) {
        MutableLiveData<BestGenericResponse<Void>> liveData = new MutableLiveData<>();
        equipoApi.deleteEquipo(id).enqueue(new Callback<BestGenericResponse<Void>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Void>> call, Response<BestGenericResponse<Void>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al eliminar equipo", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Void>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Listar todos los equipos
    public LiveData<BestGenericResponse<List<Equipment>>> listAllEquipos() {
        MutableLiveData<BestGenericResponse<List<Equipment>>> liveData = new MutableLiveData<>();
        equipoApi.listAllEquipos().enqueue(new Callback<BestGenericResponse<List<Equipment>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<Equipment>>> call, Response<BestGenericResponse<List<Equipment>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al listar equipos", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<Equipment>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }
    // Obtener un equipo por su ID
    public LiveData<BestGenericResponse<Equipment>> getEquipoById(Integer id) {
        MutableLiveData<BestGenericResponse<Equipment>> liveData = new MutableLiveData<>();
        equipoApi.getEquipoById(id).enqueue(new Callback<BestGenericResponse<Equipment>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Equipment>> call, Response<BestGenericResponse<Equipment>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al obtener detalles del equipo", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Equipment>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para filtrar equipos por nombre
    public LiveData<BestGenericResponse<List<Equipment>>> filtroPorNombre(String nombreEquipo) {
        MutableLiveData<BestGenericResponse<List<Equipment>>> liveData = new MutableLiveData<>();
        equipoApi.filtroPorNombre(nombreEquipo).enqueue(new Callback<BestGenericResponse<List<Equipment>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<Equipment>>> call, Response<BestGenericResponse<List<Equipment>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al filtrar equipos por nombre", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<Equipment>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para filtrar equipos por código patrimonial
    public LiveData<BestGenericResponse<List<Equipment>>> filtroCodigoPatrimonial(String codigoPatrimonial) {
        MutableLiveData<BestGenericResponse<List<Equipment>>> liveData = new MutableLiveData<>();
        equipoApi.filtroCodigoPatrimonial(codigoPatrimonial).enqueue(new Callback<BestGenericResponse<List<Equipment>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<Equipment>>> call, Response<BestGenericResponse<List<Equipment>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al filtrar equipos por código patrimonial", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<Equipment>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para filtrar equipos por fecha de compra
    public LiveData<BestGenericResponse<List<Equipment>>> filtroFechaCompraBetween(String fechaInicio, String fechaFin) {
        MutableLiveData<BestGenericResponse<List<Equipment>>> liveData = new MutableLiveData<>();
        equipoApi.filtroFechaCompraBetween(fechaInicio, fechaFin).enqueue(new Callback<BestGenericResponse<List<Equipment>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<Equipment>>> call, Response<BestGenericResponse<List<Equipment>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al filtrar equipos por fecha de compra", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<Equipment>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Equipment>> scanAndCopyBarcodeData(MultipartBody.Part file) {
        MutableLiveData<BestGenericResponse<Equipment>> liveData = new MutableLiveData<>();
        equipoApi.escanearCodigoBarra(file).enqueue(new Callback<BestGenericResponse<Equipment>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Equipment>> call, Response<BestGenericResponse<Equipment>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Failed to scan barcode", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Equipment>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, t.getMessage(), null));
            }
        });
        return liveData;
    }


    public LiveData<ResponseBody> downloadExcelReport() {
        MutableLiveData<ResponseBody> liveData = new MutableLiveData<>();
        equipoApi.downloadExcelReport().enqueue(new Callback<ResponseBody>() {
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
                // Puedes manejar errores de red aquí
                liveData.setValue(null);
            }
        });
        return liveData;
    }


}
