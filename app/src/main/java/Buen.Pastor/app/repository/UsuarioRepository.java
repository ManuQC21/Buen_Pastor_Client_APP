package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.UsuarioApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.MemberDTO;
import Buen.Pastor.app.entity.service.Member;

import java.io.IOException;

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
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        liveData.setValue(new BestGenericResponse<>("ERROR", -1, errorMessage, null));
                    } catch (IOException e) {
                        liveData.setValue(new BestGenericResponse<>("ERROR", -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<MemberDTO>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>("ERROR", -1, "Fallo de red: " + t.getMessage(), null));
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
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        liveData.postValue(new BestGenericResponse<>("ERROR", -1, errorMessage, null));
                    } catch (IOException e) {
                        liveData.postValue(new BestGenericResponse<>("ERROR", -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<MemberDTO>> call, Throwable t) {
                liveData.postValue(new BestGenericResponse<>("ERROR", -1, "Error de red: " + t.getMessage(), null));
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
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        liveData.postValue(new BestGenericResponse<>("ERROR", -1, errorMessage, null));
                    } catch (IOException e) {
                        liveData.postValue(new BestGenericResponse<>("ERROR", -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<String>> call, Throwable t) {
                liveData.postValue(new BestGenericResponse<>("ERROR", -1, "Error de conexión: " + t.getMessage(), null));
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
                    try {
                        String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        liveData.postValue(new BestGenericResponse<>("ERROR", -1, errorMessage, null));
                    } catch (IOException e) {
                        liveData.postValue(new BestGenericResponse<>("ERROR", -1, "Error al procesar la respuesta del servidor", null));
                    }
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<MemberDTO>> call, Throwable t) {
                liveData.postValue(new BestGenericResponse<>("ERROR", -1, "Error de conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }
}
