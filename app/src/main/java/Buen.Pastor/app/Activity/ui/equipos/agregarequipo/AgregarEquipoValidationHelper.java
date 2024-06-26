package Buen.Pastor.app.Activity.ui.equipos.agregarequipo;

import android.content.Context;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

public class AgregarEquipoValidationHelper {

    public static boolean validarEntradas(Context context, TextInputEditText txtTipoEquipo, TextInputEditText txtCodigoPatrimonial,
                                          TextInputEditText txtDescripcion, TextInputEditText txtMarca, TextInputEditText txtModelo,
                                          TextInputEditText txtNombreDeEquipo, TextInputEditText txtNumeroDeOrden, TextInputEditText txtNumeroDeSerie,
                                          TextInputEditText edtFechaCompra) {

        // Validación del tipo de equipo
        if (txtTipoEquipo.getText().toString().isEmpty() || txtTipoEquipo.getText().toString().length() < 4) {
            Toast.makeText(context, "El Tipo de Equipo es obligatorio y debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación del código patrimonial
        if (txtCodigoPatrimonial.getText().toString().isEmpty()) {
            Toast.makeText(context, "El Código Patrimonial es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de la descripción
        if (txtDescripcion.getText().toString().isEmpty() || txtDescripcion.getText().toString().length() < 4) {
            Toast.makeText(context, "La Descripción es obligatoria y debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de la marca
        if (txtMarca.getText().toString().isEmpty() || txtMarca.getText().toString().length() < 4) {
            Toast.makeText(context, "La Marca es obligatoria y debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación del modelo
        if (txtModelo.getText().toString().isEmpty() || txtModelo.getText().toString().length() < 4) {
            Toast.makeText(context, "El Modelo es obligatorio y debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación del nombre del equipo
        if (txtNombreDeEquipo.getText().toString().isEmpty() || txtNombreDeEquipo.getText().toString().length() < 4) {
            Toast.makeText(context, "El Nombre de Equipo es obligatorio y debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación del número de orden
        if (txtNumeroDeOrden.getText().toString().isEmpty()) {
            Toast.makeText(context, "El Número de Orden es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación del número de serie
        if (txtNumeroDeSerie.getText().toString().isEmpty()) {
            Toast.makeText(context, "El Número de Serie es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de la fecha de compra
        if (edtFechaCompra.getText().toString().isEmpty()) {
            Toast.makeText(context, "La Fecha de Compra es obligatoria", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
