package Buen.Pastor.app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.TeacherDTO;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.repository.DocenteRepository;

import java.util.List;

public class DocenteViewModel extends AndroidViewModel {

    private DocenteRepository docenteRepository;

    public DocenteViewModel(@NonNull Application application) {
        super(application);
        docenteRepository = new DocenteRepository();
    }

    public LiveData<BestGenericResponse<List<TeacherDTO>>> listarDocentes() {
        return docenteRepository.listarDocentes();
    }

    // Método para agregar un docente
    public LiveData<BestGenericResponse<Teacher>> agregarDocente(Teacher docente) {
        return docenteRepository.agregarDocente(docente);
    }

    // Método para editar un docente
    public LiveData<BestGenericResponse<Teacher>> editarDocente(int id, Teacher docente) {
        return docenteRepository.editarDocente(id, docente);
    }

    // Método para eliminar un docente
    public LiveData<BestGenericResponse<Void>> eliminarDocente(int id) {
        return docenteRepository.eliminarDocente(id);
    }

    // Método para obtener los detalles de un docente específico por ID
    public LiveData<BestGenericResponse<TeacherDTO>> obtenerDocentePorId(int id) {
        return docenteRepository.obtenerDocentePorId(id);
    }
}
