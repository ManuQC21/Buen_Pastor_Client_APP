package Buen.Pastor.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import Buen.P.App.R;

public class PagosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_inicio_administrativo:
                    // Cambia a InicioAdministrativoActivity si no estamos ya ahí
                    startActivity(new Intent(this, InicioAdministrativoActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.nav_pagos:
                    // No hacer nada si ya estamos en PagosActivity
                    return true;
            }
            return false;
        });

        // Asegura que el ítem correcto está marcado
        bottomNavigationView.setSelectedItemId(R.id.nav_pagos);
    }
}
