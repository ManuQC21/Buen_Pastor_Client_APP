package Buen.Pastor.app.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import Buen.P.App.R;
import Buen.Pastor.app.Activity.ui.equipos.agregarequipo.AgregarFragment;
import Buen.Pastor.app.entity.service.Member;
import cn.pedant.SweetAlert.SweetAlertDialog;
import Buen.Pastor.app.Activity.ui.Filtros.EscanearCodigoBarrasFragment;
import Buen.Pastor.app.Activity.ui.Filtros.FiltroPorCodigoPatrimonialFragment;
import Buen.Pastor.app.Activity.ui.Filtros.FiltroPorFechasFragment;
import Buen.Pastor.app.Activity.ui.Filtros.FiltroPorNombreFragment;
import Buen.Pastor.app.Activity.ui.equipos.*;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import okhttp3.ResponseBody;

public class InicioAdministrativoActivity extends AppCompatActivity {
    private Member usuario;
    private ImageView btnLogout;
    private ActivityResultLauncher<Intent> createDocumentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_administrativo);
        initUsuario();
        initViews();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_inicio_administrativo:
                    // No es necesario reiniciar la misma actividad.
                    return true;
                case R.id.nav_pagos:
                    // Iniciar la actividad de pagos y finalizar la actual para evitar acumulación en el stack.
                    Intent intent = new Intent(this, PagosActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
            }
            return false;
        });


        bottomNavigationView.setSelectedItemId(R.id.nav_inicio_administrativo);
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                // Mostrar BottomNavigationView cuando no hay fragmentos en el stack
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        // Initialize the ActivityResultLauncher here
        createDocumentLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                downloadExcelReport(uri);
            }
        });
    }

    private void initUsuario() {
        String usuarioJson = getIntent().getStringExtra("UsuarioJson");
        if (usuarioJson == null) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            usuarioJson = preferences.getString("UsuarioJson", "");
        }

        if (!usuarioJson.isEmpty()) {
            usuario = new Gson().fromJson(usuarioJson, new TypeToken<Member>() {}.getType());
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
                    chooseSaveLocation();
                }).show();
    }

    private void chooseSaveLocation() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.putExtra(Intent.EXTRA_TITLE, "Reporte_De_Equipos.xlsx");
        createDocumentLauncher.launch(intent);
    }

    private void downloadExcelReport(Uri uri) {
        EquipoViewModel equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        equipoViewModel.downloadExcelReport().observe(this, responseBody -> {
            if (responseBody != null) {
                saveExcelFile(responseBody, uri);
            } else {
                Toast.makeText(this, "Error descargando el archivo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveExcelFile(ResponseBody body, Uri uri) {
        try (ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uri, "w");
             FileOutputStream fileOutputStream = new FileOutputStream(pfd.getFileDescriptor());
             InputStream inputStream = body.byteStream()) {

            byte[] buffer = new byte[4096];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
            }

            Toast.makeText(this, "Reporte descargado: " + uri.getPath(), Toast.LENGTH_LONG).show();
            openDownloadedFile(uri);

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar el archivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void openDownloadedFile(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Open with"));
    }

    private void navigateToFragment(Fragment fragment) {
        // Ocultar BottomNavigationView cuando se navega a un fragmento
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.GONE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
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
