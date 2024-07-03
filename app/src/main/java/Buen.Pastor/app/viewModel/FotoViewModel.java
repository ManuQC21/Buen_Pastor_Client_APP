package Buen.Pastor.app.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Foto;
import Buen.Pastor.app.repository.FotoRepository;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class FotoViewModel extends AndroidViewModel {
    private final FotoRepository repository;

    public FotoViewModel(@NonNull Application application) {
        super(application);
        repository = new FotoRepository();
    }

    public LiveData<BestGenericResponse<Iterable<Foto>>> listAllFotos() {
        return repository.listAllFotos();
    }

    public LiveData<BestGenericResponse<Foto>> findFotoById(Long id) {
        return repository.findFotoById(id);
    }

    public LiveData<BestGenericResponse<Foto>> saveFoto(MultipartBody.Part file, RequestBody nombre) {
        return repository.saveFoto(file, nombre);
    }

    public LiveData<BestGenericResponse<Foto>> updateFoto(Long id, MultipartBody.Part file, RequestBody nombre) {
        return repository.updateFoto(id, file, nombre);
    }

    public LiveData<BestGenericResponse<Void>> deleteFoto(Long id) {
        return repository.deleteFoto(id);
    }

    public LiveData<ResponseBody> downloadFotoByFileName(String fileName) {
        return repository.downloadFotoByFileName(fileName);
    }

    public LiveData<ResponseBody> downloadFotoById(Long id) {
        return repository.downloadFotoById(id);
    }
}
