package Buen.Pastor.app.Activity.ui.Filtros.escaneo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

public class PermissionHandler {

    private final Context context;
    private final CameraHandler cameraHandler;
    private final LifecycleOwner lifecycleOwner;

    public PermissionHandler(Context context, CameraHandler cameraHandler, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.cameraHandler = cameraHandler;
        this.lifecycleOwner = lifecycleOwner;
    }

    public boolean allPermissionsGranted() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void handlePermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == 101 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cameraHandler.startCamera(lifecycleOwner);
        } else {
            Toast.makeText(context, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
        }
    }
}
