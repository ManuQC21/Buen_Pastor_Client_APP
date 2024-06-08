package Buen.Pastor.app.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Equipment;
import Buen.Pastor.app.repository.EquipoRepository;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;

import java.util.List;

public class EquipoViewModel extends AndroidViewModel {

    private final EquipoRepository equipoRepository;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
    }

    // Método para agregar un equipo
    public LiveData<GenericResponse<Equipment>> addEquipo(Equipment equipo) {
        return equipoRepository.addEquipo(equipo);
    }

    // Método para actualizar un equipo
    public LiveData<GenericResponse<Equipment>> updateEquipo(Equipment equipo) {
        return equipoRepository.updateEquipo(equipo);
    }

    // Método para eliminar un equipo
    public LiveData<GenericResponse<Void>> deleteEquipo(Integer id) {
        return equipoRepository.deleteEquipo(id);
    }

    // Método para listar todos los equipos
    public LiveData<GenericResponse<List<Equipment>>> listAllEquipos() {
        return equipoRepository.listAllEquipos();
    }
    // Método para obtener un equipo por su ID
    public LiveData<GenericResponse<Equipment>> getEquipoById(Integer id) {
        return equipoRepository.getEquipoById(id);
    }
    // Métodos para utilizar los filtros
    public LiveData<GenericResponse<List<Equipment>>> filtroPorNombre(String nombreEquipo) {
        return equipoRepository.filtroPorNombre(nombreEquipo);
    }

    public LiveData<GenericResponse<List<Equipment>>> filtroCodigoPatrimonial(String codigoPatrimonial) {
        return equipoRepository.filtroCodigoPatrimonial(codigoPatrimonial);
    }

    public LiveData<GenericResponse<List<Equipment>>> filtroFechaCompraBetween(String fechaInicio, String fechaFin) {
        return equipoRepository.filtroFechaCompraBetween(fechaInicio, fechaFin);
    }
    public LiveData<GenericResponse<Equipment>> scanAndCopyBarcodeData(MultipartBody.Part file) {
        return equipoRepository.scanAndCopyBarcodeData(file);
    }

    public LiveData<ResponseBody> downloadExcelReport() {
        return equipoRepository.downloadExcelReport();
    }
}
