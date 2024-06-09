package Buen.Pastor.app.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import Buen.Pastor.app.entity.BestGenericResponse;
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
    public LiveData<BestGenericResponse<Equipment>> addEquipo(Equipment equipo) {
        return equipoRepository.addEquipo(equipo);
    }

    // Método para actualizar un equipo
    public LiveData<BestGenericResponse<Equipment>> updateEquipo(Equipment equipo) {
        return equipoRepository.updateEquipo(equipo);
    }

    // Método para eliminar un equipo
    public LiveData<BestGenericResponse<Void>> deleteEquipo(Integer id) {
        return equipoRepository.deleteEquipo(id);
    }

    // Método para listar todos los equipos
    public LiveData<BestGenericResponse<List<Equipment>>> listAllEquipos() {
        return equipoRepository.listAllEquipos();
    }

    // Método para obtener un equipo por su ID
    public LiveData<BestGenericResponse<Equipment>> getEquipoById(Integer id) {
        return equipoRepository.getEquipoById(id);
    }

    // Métodos para utilizar los filtros
    public LiveData<BestGenericResponse<List<Equipment>>> filtroPorNombre(String nombreEquipo) {
        return equipoRepository.filtroPorNombre(nombreEquipo);
    }

    public LiveData<BestGenericResponse<List<Equipment>>> filtroCodigoPatrimonial(String codigoPatrimonial) {
        return equipoRepository.filtroCodigoPatrimonial(codigoPatrimonial);
    }

    public LiveData<BestGenericResponse<List<Equipment>>> filtroFechaCompraBetween(String fechaInicio, String fechaFin) {
        return equipoRepository.filtroFechaCompraBetween(fechaInicio, fechaFin);
    }

    public LiveData<BestGenericResponse<Equipment>> scanAndCopyBarcodeData(MultipartBody.Part file) {
        return equipoRepository.scanAndCopyBarcodeData(file);
    }

    public LiveData<ResponseBody> downloadExcelReport() {
        return equipoRepository.downloadExcelReport();
    }
}
