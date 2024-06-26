package Buen.Pastor.app.Activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Buen.P.App.R;
import Buen.Pastor.app.Activity.ui.docente.agregardocente.AgregarDocenteFragment;
import Buen.Pastor.app.Activity.ui.docente.ListarDocentesFragment;
import Buen.Pastor.app.Activity.ui.pagos.AgregarPagoFragment;
import Buen.Pastor.app.Activity.ui.pagos.ListarPagosFragment;
import Buen.Pastor.app.viewModel.PagoViewModel;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;

public class PagosActivity extends AppCompatActivity {
    private static final int CREATE_FILE_REQUEST_CODE = 1;
    private ImageView btnLogout;
    private PagoViewModel pagoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);
        pagoViewModel = new ViewModelProvider(this).get(PagoViewModel.class);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_inicio_administrativo:
                    startActivity(new Intent(this, InicioAdministrativoActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.nav_pagos:
                    return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.nav_pagos);
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                // Mostrar BottomNavigationView cuando no hay fragmentos en el stack
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
        initViews();
    }

    private void initViews() {
        btnLogout = findViewById(R.id.btnCerrarSesion);
        btnLogout.setOnClickListener(view -> onBackPressed());
        findViewById(R.id.contenedorAgregarPagoDocente).setOnClickListener(v -> navigateToFragment(new AgregarPagoFragment()));
        findViewById(R.id.contenedorListarPagosDocentes).setOnClickListener(v -> navigateToFragment(new ListarPagosFragment()));
        findViewById(R.id.contenedorAgregarDocente).setOnClickListener(v -> navigateToFragment(new AgregarDocenteFragment()));
        findViewById(R.id.contenedorListarDocentes).setOnClickListener(v -> navigateToFragment(new ListarDocentesFragment()));
        findViewById(R.id.contenedorDescargarPagoDocente).setOnClickListener(v -> descargarReportePagos());
    }

    private void descargarReportePagos() {
        pagoViewModel.generateExcelReport().observe(this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                if (responseBody != null) {
                    createFile("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "ReportePagos.xlsx");
                } else {
                    Toast.makeText(PagosActivity.this, "Error al descargar el reporte", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createFile(String mimeType, String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.setType(mimeType);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, CREATE_FILE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                pagoViewModel.generateExcelReport().observe(this, new Observer<ResponseBody>() {
                    @Override
                    public void onChanged(ResponseBody responseBody) {
                        if (responseBody != null) {
                            saveFile(responseBody, uri);
                        }
                    }
                });
            }
        }
    }

    private void saveFile(ResponseBody body, Uri uri) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = body.byteStream();
            outputStream = getContentResolver().openOutputStream(uri);

            if (outputStream != null) {
                byte[] fileReader = new byte[4096];
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                Toast.makeText(PagosActivity.this, "Reporte guardado correctamente", Toast.LENGTH_LONG).show();

                // Abrir el archivo descargado
                openDownloadedFile(uri);
            }
        } catch (IOException e) {
            Toast.makeText(PagosActivity.this, "Error al guardar el archivo", Toast.LENGTH_SHORT).show();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void openDownloadedFile(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No hay ninguna aplicación para abrir este archivo", Toast.LENGTH_SHORT).show();
        }
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
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            // Revisar si necesitamos mostrar el BottomNavigationView
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        } else {
            // Mostrar diálogo de confirmación para salir
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
