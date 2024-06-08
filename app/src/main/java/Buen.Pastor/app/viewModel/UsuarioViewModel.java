package Buen.Pastor.app.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Member;
import Buen.Pastor.app.repository.UsuarioRepository;

public class UsuarioViewModel extends AndroidViewModel {
    private final UsuarioRepository repository;
    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        this.repository = UsuarioRepository.getInstance();
    }
    public LiveData<GenericResponse<Member>> login(String correo, String clave){
        return repository.login(correo, clave);
    }
}
