package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.UbicacionApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Location;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbicacionRepository {

    private UbicacionApi ubicacionApi;

    public UbicacionRepository() {
        ubicacionApi = ConfigApi.getUbicacionApi();
    }

    public LiveData<BestGenericResponse<List<Location>>> listarUbicaciones() {
        MutableLiveData<BestGenericResponse<List<Location>>> liveData = new MutableLiveData<>();
        ubicacionApi.listarUbicaciones().enqueue(new Callback<BestGenericResponse<List<Location>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<Location>>> call, Response<BestGenericResponse<List<Location>>> response) {
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
            public void onFailure(Call<BestGenericResponse<List<Location>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }
}
