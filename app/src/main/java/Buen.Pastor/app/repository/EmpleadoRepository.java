package Buen.Pastor.app.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import Buen.Pastor.app.api.ConfigApi;
import Buen.Pastor.app.api.EmpleadoApi;
import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Employee;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpleadoRepository {

    private EmpleadoApi empleadoApi;

    public EmpleadoRepository() {
        empleadoApi = ConfigApi.getEmpleadoApi();
    }

    public LiveData<GenericResponse<List<Employee>>> listarEmpleados() {
        MutableLiveData<GenericResponse<List<Employee>>> liveData = new MutableLiveData<>();
        empleadoApi.listarEmpleados().enqueue(new Callback<GenericResponse<List<Employee>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Employee>>> call, Response<GenericResponse<List<Employee>>> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    liveData.setValue(new GenericResponse<>(null, -1, "Error al obtener empleados", null));
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Employee>>> call, Throwable t) {
                liveData.setValue(new GenericResponse<>(null, -1, t.getMessage(), null));
            }
        });
        return liveData;
    }
}
