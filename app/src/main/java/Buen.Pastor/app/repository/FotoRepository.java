package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;

import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.FotoApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Foto;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FotoRepository {

    private final FotoApi fotoApi;

    public FotoRepository() {
        fotoApi = ConfigApi.getFotoApi();
    }

    public LiveData<BestGenericResponse<Iterable<Foto>>> listAllFotos() {
        MutableLiveData<BestGenericResponse<Iterable<Foto>>> liveData = new MutableLiveData<>();
        fotoApi.listAllFotos().enqueue(new Callback<BestGenericResponse<Iterable<Foto>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Iterable<Foto>>> call, Response<BestGenericResponse<Iterable<Foto>>> response) {
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
            public void onFailure(Call<BestGenericResponse<Iterable<Foto>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Foto>> findFotoById(Long id) {
        MutableLiveData<BestGenericResponse<Foto>> liveData = new MutableLiveData<>();
        fotoApi.findFotoById(id).enqueue(new Callback<BestGenericResponse<Foto>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Foto>> call, Response<BestGenericResponse<Foto>> response) {
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
            public void onFailure(Call<BestGenericResponse<Foto>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Foto>> saveFoto(MultipartBody.Part file, RequestBody nombre) {
        MutableLiveData<BestGenericResponse<Foto>> liveData = new MutableLiveData<>();
        fotoApi.saveFoto(file, nombre).enqueue(new Callback<BestGenericResponse<Foto>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Foto>> call, Response<BestGenericResponse<Foto>> response) {
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
            public void onFailure(Call<BestGenericResponse<Foto>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Foto>> updateFoto(Long id, MultipartBody.Part file, RequestBody nombre) {
        MutableLiveData<BestGenericResponse<Foto>> liveData = new MutableLiveData<>();
        fotoApi.updateFoto(id, file, nombre).enqueue(new Callback<BestGenericResponse<Foto>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Foto>> call, Response<BestGenericResponse<Foto>> response) {
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
            public void onFailure(Call<BestGenericResponse<Foto>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Void>> deleteFoto(Long id) {
        MutableLiveData<BestGenericResponse<Void>> liveData = new MutableLiveData<>();
        fotoApi.deleteFoto(id).enqueue(new Callback<BestGenericResponse<Void>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Void>> call, Response<BestGenericResponse<Void>> response) {
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
            public void onFailure(Call<BestGenericResponse<Void>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, "Fallo en la conexión: " + t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<ResponseBody> downloadFotoByFileName(String fileName) {
        MutableLiveData<ResponseBody> liveData = new MutableLiveData<>();
        fotoApi.downloadFotoByFileName(fileName).enqueue(new Callback<ResponseBody>() {
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
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public LiveData<ResponseBody> downloadFotoById(Long id) {
        MutableLiveData<ResponseBody> liveData = new MutableLiveData<>();
        fotoApi.downloadFotoById(id).enqueue(new Callback<ResponseBody>() {
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
                liveData.setValue(null);
            }
        });
        return liveData;
    }
}
