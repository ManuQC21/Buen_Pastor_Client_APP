package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Buen.Pastor.app.api.AdministrativoApi;
import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Administrative;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdministrativoRepository {

    private AdministrativoApi administrativoApi;

    public AdministrativoRepository() {
        administrativoApi = ConfigApi.getAdministrativoApi();
    }

    public LiveData<BestGenericResponse<List<Administrative>>> listarAdministrativos() {
        MutableLiveData<BestGenericResponse<List<Administrative>>> liveData = new MutableLiveData<>();
        administrativoApi.listarTodosLosAdministrativos().enqueue(new Callback<BestGenericResponse<List<Administrative>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<Administrative>>> call, Response<BestGenericResponse<List<Administrative>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al obtener administrativos", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<Administrative>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Administrative>> agregarAdministrativo(Administrative administrativo) {
        MutableLiveData<BestGenericResponse<Administrative>> liveData = new MutableLiveData<>();
        administrativoApi.agregarAdministrativo(administrativo).enqueue(new Callback<BestGenericResponse<Administrative>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Administrative>> call, Response<BestGenericResponse<Administrative>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al agregar administrativo", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Administrative>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Administrative>> editarAdministrativo(int id, Administrative administrativo) {
        MutableLiveData<BestGenericResponse<Administrative>> liveData = new MutableLiveData<>();
        administrativoApi.editarAdministrativo(id, administrativo).enqueue(new Callback<BestGenericResponse<Administrative>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Administrative>> call, Response<BestGenericResponse<Administrative>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al editar administrativo", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Administrative>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Void>> eliminarAdministrativo(int id) {
        MutableLiveData<BestGenericResponse<Void>> liveData = new MutableLiveData<>();
        administrativoApi.eliminarAdministrativo(id).enqueue(new Callback<BestGenericResponse<Void>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Void>> call, Response<BestGenericResponse<Void>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al eliminar administrativo", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Void>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }
}
