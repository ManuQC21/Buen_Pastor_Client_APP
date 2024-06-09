package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.DocenteApi;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.TeacherDTO;
import Buen.Pastor.app.entity.service.Teacher;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocenteRepository {

    private DocenteApi docenteApi;

    public DocenteRepository() {
        docenteApi = ConfigApi.getDocenteApi();
    }

    public LiveData<BestGenericResponse<List<TeacherDTO>>> listarDocentes() {
        MutableLiveData<BestGenericResponse<List<TeacherDTO>>> liveData = new MutableLiveData<>();
        docenteApi.listarTodosLosDocentes().enqueue(new Callback<BestGenericResponse<List<TeacherDTO>>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<List<TeacherDTO>>> call, Response<BestGenericResponse<List<TeacherDTO>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al obtener docentes", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<List<TeacherDTO>>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }
    public LiveData<BestGenericResponse<Teacher>> agregarDocente(Teacher docente) {
        MutableLiveData<BestGenericResponse<Teacher>> liveData = new MutableLiveData<>();
        docenteApi.agregarDocente(docente).enqueue(new Callback<BestGenericResponse<Teacher>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Teacher>> call, Response<BestGenericResponse<Teacher>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al agregar docente", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Teacher>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Teacher>> editarDocente(int id, Teacher docente) {
        MutableLiveData<BestGenericResponse<Teacher>> liveData = new MutableLiveData<>();
        docenteApi.editarDocente(id, docente).enqueue(new Callback<BestGenericResponse<Teacher>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Teacher>> call, Response<BestGenericResponse<Teacher>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al editar docente", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Teacher>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<Void>> eliminarDocente(int id) {
        MutableLiveData<BestGenericResponse<Void>> liveData = new MutableLiveData<>();
        docenteApi.eliminarDocente(id).enqueue(new Callback<BestGenericResponse<Void>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<Void>> call, Response<BestGenericResponse<Void>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al eliminar docente", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<Void>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }

    public LiveData<BestGenericResponse<TeacherDTO>> obtenerDocentePorId(int id) {
        MutableLiveData<BestGenericResponse<TeacherDTO>> liveData = new MutableLiveData<>();
        docenteApi.obtenerDocentePorId(id).enqueue(new Callback<BestGenericResponse<TeacherDTO>>() {
            @Override
            public void onResponse(Call<BestGenericResponse<TeacherDTO>> call, Response<BestGenericResponse<TeacherDTO>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new BestGenericResponse<>(null, -1, "Error al obtener detalles del docente", null));
                }
            }

            @Override
            public void onFailure(Call<BestGenericResponse<TeacherDTO>> call, Throwable t) {
                liveData.setValue(new BestGenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }
}
