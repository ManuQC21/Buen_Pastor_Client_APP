package Buen.Pastor.app.Activity.ui.pagos.agregarpago;

import android.content.Context;
import com.google.android.material.textfield.TextInputEditText;

public class AgregarPagoValidationHelper {

    public static boolean validarEntradas(Context context, TextInputEditText txtMonto, TextInputEditText edtFechaPago,
                                          TextInputEditText txtReferenciaPago, TextInputEditText txtDiasTrabajo) {

        // Validación del monto
        if (txtMonto.getText().toString().isEmpty()) {
            txtMonto.setError("El campo Monto es obligatorio");
            txtMonto.requestFocus();
            return false;
        }
        if (txtMonto.getText().toString().replace("S/.", "").trim().length() < 1) {
            txtMonto.setError("El Monto debe tener al menos 1 dígito");
            txtMonto.requestFocus();
            return false;
        }

        // Validación de la fecha de pago
        if (edtFechaPago.getText().toString().isEmpty()) {
            edtFechaPago.setError("El campo Fecha de Pago es obligatorio");
            edtFechaPago.requestFocus();
            return false;
        }

        // Validación de la referencia de pago
        if (txtReferenciaPago.getText().toString().isEmpty()) {
            txtReferenciaPago.setError("El campo Referencia de Pago es obligatorio");
            txtReferenciaPago.requestFocus();
            return false;
        }
        if (txtReferenciaPago.getText().toString().length() < 4) {
            txtReferenciaPago.setError("La Referencia de Pago debe tener al menos 4 caracteres");
            txtReferenciaPago.requestFocus();
            return false;
        }

        // Validación de los días de trabajo
        if (txtDiasTrabajo.getText().toString().isEmpty()) {
            txtDiasTrabajo.setError("El campo Días de Trabajo es obligatorio");
            txtDiasTrabajo.requestFocus();
            return false;
        }
        if (!txtDiasTrabajo.getText().toString().matches("([1-9]|[12]\\d|3[01])")) {
            txtDiasTrabajo.setError("Los Días de Trabajo deben estar entre 1 y 31");
            txtDiasTrabajo.requestFocus();
            return false;
        }

        return true;
    }
}
