package Buen.Pastor.app.Activity.ui.Filtros.escaneo;

import android.Manifest;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.io.File;
import Buen.P.App.databinding.FragmentEscanearCodigoBarrasBinding;
import Buen.Pastor.app.entity.Global;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EscanearCodigoBarrasFragment extends Fragment {

    private FragmentEscanearCodigoBarrasBinding binding;
    private EquipoViewModel equipoViewModel;
    private CameraHandler cameraHandler;
    private PermissionHandler permissionHandler;
    private ImageProcessor imageProcessor;
    private DialogHelper dialogHelper;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEscanearCodigoBarrasBinding.inflate(inflater, container, false);
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        cameraHandler = new CameraHandler(getContext(), binding);
        permissionHandler = new PermissionHandler(getContext(), cameraHandler, this);
        imageProcessor = new ImageProcessor(getContext());
        dialogHelper = new DialogHelper(getContext());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Analizando...");
        progressDialog.setCancelable(false);

        binding.btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        if (permissionHandler.allPermissionsGranted()) {
            cameraHandler.startCamera(this);
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 101);
        }

        binding.btnAnalyze.setOnClickListener(v -> {
            progressDialog.show();
            takePhoto();
        });
        return binding.getRoot();
    }

    private void takePhoto() {
        File photoFile = new File(requireContext().getExternalFilesDir(null), "scan_" + System.currentTimeMillis() + ".png");
        cameraHandler.takePhoto(photoFile, new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Uri savedUri = Uri.fromFile(photoFile);
                if (cameraHandler.isVerticalOrientation()) {
                    imageProcessor.rotateImage(photoFile, -90);
                }
                sendImageToServer(savedUri, photoFile);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Error taking photo: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                if (photoFile.exists()) {
                    photoFile.delete();
                }
            }
        });
    }

    private void sendImageToServer(Uri fileUri, File photoFile) {
        RequestBody requestFile = RequestBody.create(MediaType.get("image/png"), photoFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", photoFile.getName(), requestFile);

        equipoViewModel.scanAndCopyBarcodeData(body).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            // Eliminar el archivo después de enviarlo al servidor, sin importar el resultado
            if (photoFile.exists()) {
                photoFile.delete();
            }

            if (response.getRpta() == Global.RPTA_OK) {
                dialogHelper.showCustomDialog(response.getBody());
            } else {
                dialogHelper.showAlert("Error", response.getMessage());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHandler.handlePermissionsResult(requestCode, grantResults);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
