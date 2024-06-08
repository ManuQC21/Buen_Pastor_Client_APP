package Buen.Pastor.app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Location;
import Buen.Pastor.app.repository.UbicacionRepository;

import java.util.List;

public class UbicacionViewModel extends AndroidViewModel {

    private UbicacionRepository ubicacionRepository;

    public UbicacionViewModel(@NonNull Application application) {
        super(application);
        ubicacionRepository = new UbicacionRepository();
    }

    public LiveData<GenericResponse<List<Location>>> listarUbicaciones() {
        return ubicacionRepository.listarUbicaciones();
    }
}
