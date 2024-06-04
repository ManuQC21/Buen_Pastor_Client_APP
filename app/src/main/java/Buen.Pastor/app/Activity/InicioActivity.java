package Buen.Pastor.app.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Buen.P.App.BuildConfig;
import Buen.P.App.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import Buen.Pastor.app.Activity.ui.Filtros.EscanearCodigoBarrasFragment;
import Buen.Pastor.app.Activity.ui.Filtros.FiltroPorCodigoPatrimonialFragment;
import Buen.Pastor.app.Activity.ui.Filtros.FiltroPorFechasFragment;
import Buen.Pastor.app.Activity.ui.Filtros.FiltroPorNombreFragment;
import Buen.Pastor.app.entity.service.Usuario;
import Buen.Pastor.app.Activity.ui.equipos.*;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import okhttp3.ResponseBody;

public class InicioActivity extends AppCompatActivity {
    private Usuario usuario;
    private ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        initUsuario();
        initViews();
    }

    private void initUsuario() {
        String usuarioJson = getIntent().getStringExtra("UsuarioJson");
        if (usuarioJson == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            usuarioJson = preferences.getString("UsuarioJson", "");
        }

        if (!usuarioJson.isEmpty()) {
            usuario = new Gson().fromJson(usuarioJson, new TypeToken<Usuario>() {}.getType());
        }
    }

    private void initViews() {
        btnLogout = findViewById(R.id.btnCerrarSesion);
        btnLogout.setOnClickListener(view -> onBackPressed());
        findViewById(R.id.contenedorAgregarEquipo).setOnClickListener(v -> navigateToFragment(new AgregarFragment()));
        findViewById(R.id.contenedorListarEquipos).setOnClickListener(v -> navigateToFragment(new ListarFragment()));
        findViewById(R.id.contenedorListarNombreEquipo).setOnClickListener(v -> navigateToFragment(new FiltroPorNombreFragment()));
        findViewById(R.id.contenedorListarCodigoPatrimonial).setOnClickListener(v -> navigateToFragment(new FiltroPorCodigoPatrimonialFragment()));
        findViewById(R.id.contenedorListarPorFechas).setOnClickListener(v -> navigateToFragment(new FiltroPorFechasFragment()));
        findViewById(R.id.contenedorReporteEquipos).setOnClickListener(v -> showDownloadConfirmationDialog());
        findViewById(R.id.contenedorEscanearCodigoBarras).setOnClickListener(v -> navigateToFragment(new EscanearCodigoBarrasFragment()));
    }
    private void showDownloadConfirmationDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Desea descargar el Reporte General de Equipos")
                .setConfirmText("Sí")
                .setCancelText("No")
                .showCancelButton(true)
                .setCancelClickListener(SweetAlertDialog::dismissWithAnimation)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    downloadExcelReport();
                }).show();
    }
    private void downloadExcelReport() {
        EquipoViewModel equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        equipoViewModel.downloadExcelReport().observe(this, responseBody -> {
            if (responseBody != null) {
                saveExcelFile(responseBody);
            } else {
                Toast.makeText(this, "Error descargando el archivo", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveExcelFile(ResponseBody body) {
        try {
            // Usar el directorio de descargas público
            File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(downloadsFolder, "Reporte_De_Equipos.xlsx");

            InputStream inputStream = body.byteStream();
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            Toast.makeText(this, "Reporte descargado: " + file.toString(), Toast.LENGTH_LONG).show();
            openDownloadedFile(file);
        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar el archivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void openDownloadedFile(File file) {
        Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Open with"));
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment); // Cambio aquí
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¿Desea salir de la aplicación?")
                .setConfirmText("Sí, salir")
                .setCancelText("No, cancelar")
                .showCancelButton(true)
                .setCancelClickListener(SweetAlertDialog::dismissWithAnimation)
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    logout();
                }).show();
    }

    private void logout() {
        clearUsuarioPreferences();
        showToastLogout();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void clearUsuarioPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
    }

    private void showToastLogout() {
        View layout = LayoutInflater.from(this).inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
        TextView text = layout.findViewById(R.id.txtMensajeToast1);
        text.setText("Has cerrado sesión correctamente");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
