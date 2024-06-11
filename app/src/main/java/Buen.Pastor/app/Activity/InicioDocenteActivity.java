package Buen.Pastor.app.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import Buen.P.App.R;
import Buen.Pastor.app.Activity.ui.docente.NotificacionesDocentesFragment;
import Buen.Pastor.app.Activity.ui.docente.PagosDocentesFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class InicioDocenteActivity extends AppCompatActivity {

    private ImageView btnCerrarSesion;
    private int userId;
    private int teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_docente);
        setupToolbar();

        // Obtener los IDs del Intent y mostrarlos
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);
        teacherId = intent.getIntExtra("teacherId", -1);
        showIdsInToastAndLog(userId, teacherId);

        // Configurar la barra de navegación inferior y cargar el fragmento por defecto
        setupBottomNavigationView();
        loadFragment(PagosDocentesFragment.newInstance(userId, teacherId));
    }

    private void setupToolbar() {
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmLogout();
            }
        });
    }

    private void setupBottomNavigationView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_pagos:
                    selectedFragment = PagosDocentesFragment.newInstance(userId, teacherId);
                    break;
                case R.id.navigation_notificaciones:
                    selectedFragment = NotificacionesDocentesFragment.newInstance(userId, teacherId);
                    break;
            }
            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void confirmLogout() {
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

    private void showIdsInToastAndLog(int userId, int teacherId) {
        String message = "User ID: " + userId + ", Teacher ID: " + teacherId;
        Log.d("UserDetails", message);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        confirmLogout();
    }
}
