package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.EquipoApi;
import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.Global;
import Buen.Pastor.app.entity.service.Equipo;
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
    public LiveData<GenericResponse<Equipo>> addEquipo(Equipo equipo) {
        MutableLiveData<GenericResponse<Equipo>> liveData = new MutableLiveData<>();
        equipoApi.addEquipo(equipo).enqueue(new Callback<GenericResponse<Equipo>>() {
            @Override
            public void onResponse(Call<GenericResponse<Equipo>> call, Response<GenericResponse<Equipo>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al agregar equipo", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Equipo>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Modificar equipo
    public LiveData<GenericResponse<Equipo>> updateEquipo(Equipo equipo) {
        MutableLiveData<GenericResponse<Equipo>> liveData = new MutableLiveData<>();
        equipoApi.updateEquipo(equipo).enqueue(new Callback<GenericResponse<Equipo>>() {
            @Override
            public void onResponse(Call<GenericResponse<Equipo>> call, Response<GenericResponse<Equipo>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al modificar equipo", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Equipo>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Eliminar equipo
    public LiveData<GenericResponse<Void>> deleteEquipo(Integer id) {
        MutableLiveData<GenericResponse<Void>> liveData = new MutableLiveData<>();
        equipoApi.deleteEquipo(id).enqueue(new Callback<GenericResponse<Void>>() {
            @Override
            public void onResponse(Call<GenericResponse<Void>> call, Response<GenericResponse<Void>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al eliminar equipo", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Void>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Listar todos los equipos
    public LiveData<GenericResponse<List<Equipo>>> listAllEquipos() {
        MutableLiveData<GenericResponse<List<Equipo>>> liveData = new MutableLiveData<>();
        equipoApi.listAllEquipos().enqueue(new Callback<GenericResponse<List<Equipo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Equipo>>> call, Response<GenericResponse<List<Equipo>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al listar equipos", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Equipo>>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }
    // Obtener un equipo por su ID
    public LiveData<GenericResponse<Equipo>> getEquipoById(Integer id) {
        MutableLiveData<GenericResponse<Equipo>> liveData = new MutableLiveData<>();
        equipoApi.getEquipoById(id).enqueue(new Callback<GenericResponse<Equipo>>() {
            @Override
            public void onResponse(Call<GenericResponse<Equipo>> call, Response<GenericResponse<Equipo>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al obtener detalles del equipo", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Equipo>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para filtrar equipos por nombre
    public LiveData<GenericResponse<List<Equipo>>> filtroPorNombre(String nombreEquipo) {
        MutableLiveData<GenericResponse<List<Equipo>>> liveData = new MutableLiveData<>();
        equipoApi.filtroPorNombre(nombreEquipo).enqueue(new Callback<GenericResponse<List<Equipo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Equipo>>> call, Response<GenericResponse<List<Equipo>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al filtrar equipos por nombre", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Equipo>>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para filtrar equipos por código patrimonial
    public LiveData<GenericResponse<List<Equipo>>> filtroCodigoPatrimonial(String codigoPatrimonial) {
        MutableLiveData<GenericResponse<List<Equipo>>> liveData = new MutableLiveData<>();
        equipoApi.filtroCodigoPatrimonial(codigoPatrimonial).enqueue(new Callback<GenericResponse<List<Equipo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Equipo>>> call, Response<GenericResponse<List<Equipo>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al filtrar equipos por código patrimonial", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Equipo>>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    // Método para filtrar equipos por fecha de compra
    public LiveData<GenericResponse<List<Equipo>>> filtroFechaCompraBetween(String fechaInicio, String fechaFin) {
        MutableLiveData<GenericResponse<List<Equipo>>> liveData = new MutableLiveData<>();
        equipoApi.filtroFechaCompraBetween(fechaInicio, fechaFin).enqueue(new Callback<GenericResponse<List<Equipo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Equipo>>> call, Response<GenericResponse<List<Equipo>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al filtrar equipos por fecha de compra", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Equipo>>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<GenericResponse<Equipo>> scanAndCopyBarcodeData(MultipartBody.Part file) {
        MutableLiveData<GenericResponse<Equipo>> liveData = new MutableLiveData<>();
        equipoApi.escanearCodigoBarra(file).enqueue(new Callback<GenericResponse<Equipo>>() {
            @Override
            public void onResponse(Call<GenericResponse<Equipo>> call, Response<GenericResponse<Equipo>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Failed to scan barcode", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Equipo>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, t.getMessage(), null));
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
