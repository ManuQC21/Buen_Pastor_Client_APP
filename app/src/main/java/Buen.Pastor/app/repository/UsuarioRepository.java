package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.UsuarioApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Member;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {
    private static UsuarioRepository repository;
    private final UsuarioApi api;

    public UsuarioRepository() {
        this.api = ConfigApi.getUsuarioApi();
    }

    public static synchronized UsuarioRepository getInstance(){
        if (repository == null) {
            repository = new UsuarioRepository();
        }
        return repository;
    }

    public LiveData<BestGenericResponse<Member>> login(String email, String password){
        MutableLiveData<BestGenericResponse<Member>> liveData = new MutableLiveData<>();
        api.login(email, password).enqueue(new Callback<BestGenericResponse<Member>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Member>> call, Response<BestGenericResponse<Member>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>("error", response.code(), "Error en el login: " + response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Member>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>("error", 500, "Fallo de red: " + t.getMessage(), null));
            }
        });
        return liveData;
    }
}
