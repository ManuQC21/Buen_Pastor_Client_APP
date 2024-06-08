package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.UbicacionApi;
import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Location;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbicacionRepository {

    private UbicacionApi ubicacionApi;

    public UbicacionRepository() {
        ubicacionApi = ConfigApi.getUbicacionApi();
    }

    public LiveData<GenericResponse<List<Location>>> listarUbicaciones() {
        MutableLiveData<GenericResponse<List<Location>>> liveData = new MutableLiveData<>();
        ubicacionApi.listarUbicaciones().enqueue(new Callback<GenericResponse<List<Location>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Location>>> call, Response<GenericResponse<List<Location>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al obtener ubicaciones", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Location>>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }
}
