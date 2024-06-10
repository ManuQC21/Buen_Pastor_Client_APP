package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.UsuarioApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.MemberDTO;
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

    public LiveData<BestGenericResponse<MemberDTO>> login(String email, String password){
        MutableLiveData<BestGenericResponse<MemberDTO>> liveData = new MutableLiveData<>();
        api.login(email, password).enqueue(new Callback<BestGenericResponse<MemberDTO>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<MemberDTO>> call, Response<BestGenericResponse<MemberDTO>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>("error", response.code(), "Error en el login: " + response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<MemberDTO>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>("error", 500, "Fallo de red: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<MemberDTO>> register(Member member) {
        MutableLiveData<BestGenericResponse<MemberDTO>> liveData = new MutableLiveData<>();
        api.register(member).enqueue(new Callback<BestGenericResponse<MemberDTO>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<MemberDTO>> call, Response<BestGenericResponse<MemberDTO>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(new BestGenericResponse<MemberDTO>("ERROR", 0, "Registro fallido", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<MemberDTO>> call, Throwable t) {
                liveData.postValue(new BestGenericResponse<MemberDTO>("ERROR", 0, "Error de red", null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<String>> eliminarUsuario(int id) {
        MutableLiveData<BestGenericResponse<String>> liveData = new MutableLiveData<>();
        api.eliminarUsuario(id).enqueue(new Callback<BestGenericResponse<String>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<String>> call, Response<BestGenericResponse<String>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(new BestGenericResponse<>("ERROR", 0, "No se pudo eliminar el usuario", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<String>> call, Throwable t) {
                liveData.postValue(new BestGenericResponse<>("ERROR", 0, "Error de conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<MemberDTO>> obtenerUsuarioPorDocenteId(int docenteId) {
        MutableLiveData<BestGenericResponse<MemberDTO>> liveData = new MutableLiveData<>();
        api.obtenerUsuarioPorDocenteId(docenteId).enqueue(new Callback<BestGenericResponse<MemberDTO>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<MemberDTO>> call, Response<BestGenericResponse<MemberDTO>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(new BestGenericResponse<>("ERROR", 0, "No se pudo obtener el usuario", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<MemberDTO>> call, Throwable t) {
                liveData.postValue(new BestGenericResponse<>("ERROR", 0, "Error de conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }
}
