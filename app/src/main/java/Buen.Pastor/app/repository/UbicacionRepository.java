package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.UbicacionApi;
import Buen.Pastor.app.entity.BestGenericResponse;
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

    public LiveData<BestGenericResponse<List<Location>>> listarUbicaciones() {
        MutableLiveData<BestGenericResponse<List<Location>>> liveData = new MutableLiveData<>();
        ubicacionApi.listarUbicaciones().enqueue(new Callback<BestGenericResponse<List<Location>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<Location>>> call, Response<BestGenericResponse<List<Location>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al obtener ubicaciones", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<Location>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }
}
