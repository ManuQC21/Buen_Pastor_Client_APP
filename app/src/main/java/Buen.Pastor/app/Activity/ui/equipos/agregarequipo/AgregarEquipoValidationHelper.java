package Buen.Pastor.app.Activity.ui.equipos.agregarequipo;

import android.content.Context;
import com.google.android.material.textfield.TextInputEditText;

public class AgregarEquipoValidationHelper {

    public static boolean validarEntradas(Context context, TextInputEditText txtTipoEquipo, TextInputEditText txtCodigoPatrimonial,
                                          TextInputEditText txtDescripcion, TextInputEditText txtMarca, TextInputEditText txtModelo,
                                          TextInputEditText txtNombreDeEquipo, TextInputEditText txtNumeroDeOrden, TextInputEditText txtNumeroDeSerie,
                                          TextInputEditText edtFechaCompra) {

        // Validación del tipo de equipo
        if (txtTipoEquipo.getText().toString().isEmpty()) {
            txtTipoEquipo.setError("El Tipo de Equipo es obligatorio");
            txtTipoEquipo.requestFocus();
            return false;
        }
        if (txtTipoEquipo.getText().toString().length() < 4) {
            txtTipoEquipo.setError("El Tipo de Equipo debe tener al menos 4 caracteres");
            txtTipoEquipo.requestFocus();
            return false;
        }

        // Validación del código patrimonial
        if (txtCodigoPatrimonial.getText().toString().isEmpty()) {
            txtCodigoPatrimonial.setError("El Código Patrimonial es obligatorio");
            txtCodigoPatrimonial.requestFocus();
            return false;
        }
        if (txtCodigoPatrimonial.getText().toString().length() < 6) {
            txtCodigoPatrimonial.setError("El Código Patrimonial debe tener al menos 6 caracteres");
            txtCodigoPatrimonial.requestFocus();
            return false;
        }

        // Validación de la descripción
        if (txtDescripcion.getText().toString().isEmpty()) {
            txtDescripcion.setError("La Descripción es obligatoria");
            txtDescripcion.requestFocus();
            return false;
        }
        if (txtDescripcion.getText().toString().length() < 4) {
            txtDescripcion.setError("La Descripción debe tener al menos 4 caracteres");
            txtDescripcion.requestFocus();
            return false;
        }

        // Validación de la marca
        if (txtMarca.getText().toString().isEmpty()) {
            txtMarca.setError("La Marca es obligatoria");
            txtMarca.requestFocus();
            return false;
        }
        if (txtMarca.getText().toString().length() < 2) {
            txtMarca.setError("La Marca debe tener al menos 2 caracteres");
            txtMarca.requestFocus();
            return false;
        }

        // Validación del modelo
        if (txtModelo.getText().toString().isEmpty()) {
            txtModelo.setError("El Modelo es obligatorio");
            txtModelo.requestFocus();
            return false;
        }
        if (txtModelo.getText().toString().length() < 4) {
            txtModelo.setError("El Modelo debe tener al menos 4 caracteres");
            txtModelo.requestFocus();
            return false;
        }

        // Validación del nombre del equipo
        if (txtNombreDeEquipo.getText().toString().isEmpty()) {
            txtNombreDeEquipo.setError("El Nombre de Equipo es obligatorio");
            txtNombreDeEquipo.requestFocus();
            return false;
        }
        if (txtNombreDeEquipo.getText().toString().length() < 4) {
            txtNombreDeEquipo.setError("El Nombre de Equipo debe tener al menos 4 caracteres");
            txtNombreDeEquipo.requestFocus();
            return false;
        }

        // Validación del número de orden
        if (txtNumeroDeOrden.getText().toString().isEmpty()) {
            txtNumeroDeOrden.setError("El Número de Orden es obligatorio");
            txtNumeroDeOrden.requestFocus();
            return false;
        }
        if (txtNumeroDeOrden.getText().toString().length() < 6) {
            txtNumeroDeOrden.setError("El Número de Orden debe tener al menos 6 caracteres");
            txtNumeroDeOrden.requestFocus();
            return false;
        }

        // Validación del número de serie
        if (txtNumeroDeSerie.getText().toString().isEmpty()) {
            txtNumeroDeSerie.setError("El Número de Serie es obligatorio");
            txtNumeroDeSerie.requestFocus();
            return false;
        }
        if (txtNumeroDeSerie.getText().toString().length() < 6) {
            txtNumeroDeSerie.setError("El Número de Serie debe tener al menos 6 caracteres");
            txtNumeroDeSerie.requestFocus();
            return false;
        }

        // Validación de la fecha de compra
        if (edtFechaCompra.getText().toString().isEmpty()) {
            edtFechaCompra.setError("La Fecha de Compra es obligatoria");
            edtFechaCompra.requestFocus();
            return false;
        }

        return true;
    }
}
