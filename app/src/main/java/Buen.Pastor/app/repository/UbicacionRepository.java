package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.UbicacionApi;
import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Ubicacion;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbicacionRepository {

    private UbicacionApi ubicacionApi;

    public UbicacionRepository() {
        ubicacionApi = ConfigApi.getUbicacionApi();
    }

    public LiveData<GenericResponse<List<Ubicacion>>> listarUbicaciones() {
        MutableLiveData<GenericResponse<List<Ubicacion>>> liveData = new MutableLiveData<>();
        ubicacionApi.listarUbicaciones().enqueue(new Callback<GenericResponse<List<Ubicacion>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Ubicacion>>> call, Response<GenericResponse<List<Ubicacion>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al obtener ubicaciones", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Ubicacion>>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }
}
