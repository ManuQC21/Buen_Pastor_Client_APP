package Buen.Pastor.app.Activity.ui.docente.agregardocente;

import android.content.Context;
import android.util.Patterns;
import com.google.android.material.textfield.TextInputEditText;

public class AgregarDocenteValidationHelper {

    public static boolean validarEntradas(Context context, TextInputEditText txtNombreCompleto, TextInputEditText txtPosicion,
                                          TextInputEditText txtDNI, TextInputEditText txtEmail, TextInputEditText txtTelefono,
                                          TextInputEditText txtDireccion, TextInputEditText txtFechaContratacion, TextInputEditText txtContrasena) {

        // Validación del nombre completo
        if (txtNombreCompleto.getText().toString().isEmpty()) {
            txtNombreCompleto.setError("El campo Nombre Completo es obligatorio");
            txtNombreCompleto.requestFocus();
            return false;
        }
        if (txtNombreCompleto.getText().toString().length() < 8) {
            txtNombreCompleto.setError("El Nombre Completo debe tener al menos 8 caracteres");
            txtNombreCompleto.requestFocus();
            return false;
        }

        // Validación de la posición
        if (txtPosicion.getText().toString().isEmpty()) {
            txtPosicion.setError("El campo Puesto es obligatorio");
            txtPosicion.requestFocus();
            return false;
        }
        if (txtPosicion.getText().toString().length() < 4) {
            txtPosicion.setError("El Cargo debe tener al menos 4 caracteres");
            txtPosicion.requestFocus();
            return false;
        }

        // Validación del DNI
        if (txtDNI.getText().toString().isEmpty()) {
            txtDNI.setError("El campo DNI es obligatorio");
            txtDNI.requestFocus();
            return false;
        }
        if (!txtDNI.getText().toString().matches("\\d{8}")) {
            txtDNI.setError("El DNI debe tener 8 dígitos");
            txtDNI.requestFocus();
            return false;
        }

        // Validación del correo electrónico
        if (txtEmail.getText().toString().isEmpty()) {
            txtEmail.setError("El campo Email es obligatorio");
            txtEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
            txtEmail.setError("Formato de correo inválido");
            txtEmail.requestFocus();
            return false;
        }

        // Validación del teléfono
        if (txtTelefono.getText().toString().isEmpty()) {
            txtTelefono.setError("El campo Teléfono es obligatorio");
            txtTelefono.requestFocus();
            return false;
        }
        if (!txtTelefono.getText().toString().matches("9\\d{8}")) {
            txtTelefono.setError("El Teléfono debe comenzar con 9 y tener 9 dígitos");
            txtTelefono.requestFocus();
            return false;
        }

        // Validación de la dirección
        if (txtDireccion.getText().toString().isEmpty()) {
            txtDireccion.setError("El campo Dirección es obligatorio");
            txtDireccion.requestFocus();
            return false;
        }
        if (txtDireccion.getText().toString().length() < 4) {
            txtDireccion.setError("La Dirección debe tener al menos 4 caracteres");
            txtDireccion.requestFocus();
            return false;
        }

        // Validación de la fecha de contratación
        if (txtFechaContratacion.getText().toString().isEmpty()) {
            txtFechaContratacion.setError("El campo Fecha de Contratación es obligatorio");
            txtFechaContratacion.requestFocus();
            return false;
        }

        // Validación de la contraseña
        if (txtContrasena.getText().toString().isEmpty()) {
            txtContrasena.setError("El campo Contraseña es obligatorio");
            txtContrasena.requestFocus();
            return false;
        }
        if (txtContrasena.getText().toString().length() < 8) {
            txtContrasena.setError("La Contraseña debe tener al menos 8 caracteres");
            txtContrasena.requestFocus();
            return false;
        }

        return true;
    }
}
