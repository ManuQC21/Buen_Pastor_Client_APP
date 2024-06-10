package Buen.Pastor.app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.MemberDTO;
import Buen.Pastor.app.entity.service.Member;
import Buen.Pastor.app.repository.UsuarioRepository;

public class UsuarioViewModel extends AndroidViewModel {
    private final UsuarioRepository repository;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        this.repository = UsuarioRepository.getInstance();
    }

    public LiveData<BestGenericResponse<MemberDTO>> login(String email, String password) {
        return repository.login(email, password);
    }
    public LiveData<BestGenericResponse<MemberDTO>> register(Member member) {
        return repository.register(member);
    }
    public LiveData<BestGenericResponse<String>> eliminarUsuario(int id) {
        return repository.eliminarUsuario(id);
    }

    public LiveData<BestGenericResponse<MemberDTO>> obtenerUsuarioPorDocenteId(int docenteId) {
        return repository.obtenerUsuarioPorDocenteId(docenteId);
    }

}
