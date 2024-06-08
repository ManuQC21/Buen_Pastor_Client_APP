package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.UsuarioApi;
import Buen.Pastor.app.entity.GenericResponse;
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

    public LiveData<GenericResponse<Member>> login(String correo, String clave){
        MutableLiveData<GenericResponse<Member>> liveData = new MutableLiveData<>();
        api.login(correo, clave).enqueue(new Callback<GenericResponse<Member>>() {
            @Override
            public void onResponse(Call<GenericResponse<Member>> call, Response<GenericResponse<Member>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>("error", response.code(), "Error en el login: " + response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Member>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>("error", 500, "Fallo de red: " + t.getMessage(), null));
            }
        });
        return liveData;
    }
}
