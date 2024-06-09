package Buen.Pastor.app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Administrative;
import Buen.Pastor.app.repository.AdministrativoRepository;

import java.util.List;

public class AdministrativoViewModel extends AndroidViewModel {

    private AdministrativoRepository administrativoRepository;

    public AdministrativoViewModel(@NonNull Application application) {
        super(application);
        administrativoRepository = new AdministrativoRepository();
    }

    // Método para listar todos los administrativos
    public LiveData<BestGenericResponse<List<Administrative>>> listarAdministrativos() {
        return administrativoRepository.listarAdministrativos();
    }

    // Método para agregar un administrativo
    public LiveData<BestGenericResponse<Administrative>> agregarAdministrativo(Administrative administrativo) {
        return administrativoRepository.agregarAdministrativo(administrativo);
    }

    // Método para editar un administrativo
    public LiveData<BestGenericResponse<Administrative>> editarAdministrativo(int id, Administrative administrativo) {
        return administrativoRepository.editarAdministrativo(id, administrativo);
    }

    // Método para eliminar un administrativo
    public LiveData<BestGenericResponse<Void>> eliminarAdministrativo(int id) {
        return administrativoRepository.eliminarAdministrativo(id);
    }
}
