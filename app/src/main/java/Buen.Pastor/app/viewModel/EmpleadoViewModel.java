package Buen.Pastor.app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Empleado;
import Buen.Pastor.app.repository.EmpleadoRepository;

import java.util.List;

public class EmpleadoViewModel extends AndroidViewModel {

    private EmpleadoRepository empleadoRepository;

    public EmpleadoViewModel(@NonNull Application application) {
        super(application);
        empleadoRepository = new EmpleadoRepository();
    }

    public LiveData<GenericResponse<List<Empleado>>> listarEmpleados() {
        return empleadoRepository.listarEmpleados();
    }
}
