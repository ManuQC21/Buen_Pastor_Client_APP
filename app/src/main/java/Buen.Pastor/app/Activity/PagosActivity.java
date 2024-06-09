package Buen.Pastor.app.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import Buen.P.App.R;
import Buen.Pastor.app.Activity.ui.docente.AgregarDocenteFragment;
import Buen.Pastor.app.Activity.ui.docente.ListarDocentesFragment;
import Buen.Pastor.app.Activity.ui.pagos.AgregarPagoFragment;
import Buen.Pastor.app.Activity.ui.pagos.ListarPagosFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class PagosActivity extends AppCompatActivity {
    private ImageView btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);

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
