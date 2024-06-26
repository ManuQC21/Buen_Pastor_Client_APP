package Buen.Pastor.app.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickerHelper {

    public static void mostrarDatePickerDialog(Context context, TextView edtFechaCompra, Calendar calendar) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            edtFechaCompra.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // Establecer los límites de fecha desde el inicio de 2024 hasta la fecha actual del dispositivo
        Calendar minDate = Calendar.getInstance();
        minDate.set(2024, Calendar.JANUARY, 1);

        Calendar maxDate = Calendar.getInstance(); // Fecha actual

        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        datePickerDialog.show();
    }
}
