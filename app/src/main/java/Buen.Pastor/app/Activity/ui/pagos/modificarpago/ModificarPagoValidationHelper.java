package Buen.Pastor.app.Activity.ui.pagos.modificarpago;

import android.content.Context;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

public class ModificarPagoValidationHelper {

    public static boolean validarEntradas(Context context, TextInputEditText txtMonto, TextInputEditText edtFechaPago,
                                          TextInputEditText txtReferenciaPago, TextInputEditText txtDiasTrabajo) {

        // Validación del monto
        if (txtMonto.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Monto es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtMonto.getText().toString().replace("S/.", "").trim().length() < 1) {
            Toast.makeText(context, "El Monto debe tener al menos 1 dígito", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de la fecha de pago
        if (edtFechaPago.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Fecha de Pago es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de la referencia de pago
        if (txtReferenciaPago.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Referencia de Pago es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (txtReferenciaPago.getText().toString().length() < 4) {
            Toast.makeText(context, "La Referencia de Pago debe tener al menos 4 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validación de los días de trabajo
        if (txtDiasTrabajo.getText().toString().isEmpty()) {
            Toast.makeText(context, "El campo Días de Trabajo es obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!txtDiasTrabajo.getText().toString().matches("([1-9]|[12]\\d|3[01])")) {
            Toast.makeText(context, "Los Días de Trabajo deben estar entre 1 y 31", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
