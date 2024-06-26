package Buen.Pastor.app.Activity.ui.equipos.agregarequipo;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class AgregarEquipoHelper {

    public static TextWatcher crearSoloLetrasWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String resultado = s.toString().replaceAll("[^a-zA-Z ]", "").replaceAll("\\s+", " ");
                if (!s.toString().equals(resultado)) {
                    s.replace(0, s.length(), resultado);
                }
            }
        };
    }

    public static TextWatcher crearSoloNumerosWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String resultado = s.toString().replaceAll("[^0-9]", "");
                if (!s.toString().equals(resultado)) {
                    s.replace(0, s.length(), resultado);
                }
            }
        };
    }

    public static void cargarEstados(Context context, AutoCompleteTextView dropdownEstado) {
        List<String> estados = new ArrayList<>();
        estados.add("Mal estado");
        estados.add("Estable");
        estados.add("Buen estado");
        estados.add("Requiere mantenimiento");
        estados.add("En reparación");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, estados);
        dropdownEstado.setAdapter(adapter);
    }
}
