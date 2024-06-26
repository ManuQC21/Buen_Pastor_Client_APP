package Buen.Pastor.app.Activity.ui.docente.agregardocente;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

public class AgregarDocenteValidationHelper {

    public static boolean validarEntradas(Context context, TextInputEditText txtNombreCompleto, TextInputEditText txtPosicion,
                                          TextInputEditText txtDNI, TextInputEditText txtEmail, TextInputEditText txtTelefono,
                                          TextInputEditText txtDireccion, TextInputEditText txtFechaContratacion, TextInputEditText txtContrasena) {

        // Validación del nombre completo
        if (txtNombreCompleto.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Nombre Completo es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtNombreCompleto.getText().toString().length() < 4) {
            Toast.makeText(context, "El Nombre Completo debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de la posición
        if (txtPosicion.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Puesto es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtPosicion.getText().toString().length() < 4) {
            Toast.makeText(context, "La Posición debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación del DNI
        if (txtDNI.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo DNI es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!txtDNI.getText().toString().matches("\\d{8}")) {
            Toast.makeText(context, "El DNI debe tener 8 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación del correo electrónico
        if (txtEmail.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Email es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
            Toast.makeText(context, "Formato de correo inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación del teléfono
        if (txtTelefono.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Teléfono es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!txtTelefono.getText().toString().matches("9\\d{8}")) {
            Toast.makeText(context, "El Teléfono debe comenzar con 9 y tener 9 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de la dirección
        if (txtDireccion.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Dirección es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtDireccion.getText().toString().length() < 4) {
            Toast.makeText(context, "La Dirección debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de la fecha de contratación
        if (txtFechaContratacion.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Fecha de Contratación es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de la contraseña
        if (txtContrasena.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Contraseña es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtContrasena.getText().toString().length() < 6) {
            Toast.makeText(context, "La Contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
