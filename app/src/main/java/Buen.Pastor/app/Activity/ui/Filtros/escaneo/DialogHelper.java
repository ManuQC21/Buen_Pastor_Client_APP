package Buen.Pastor.app.Activity.ui.Filtros.escaneo;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.Equipment;

public class DialogHelper {

    private final Context context;

    public DialogHelper(Context context) {
        this.context = context;
    }

    // Método para mostrar una alerta simple
    public void showAlert(String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), null)
                .show();
    }

    // Método para mostrar un diálogo personalizado con los detalles del equipo
    public void showCustomDialog(Equipment equipo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customLayout = LayoutInflater.from(context).inflate(R.layout.custom_dialog_equipo, null);
        builder.setView(customLayout);
        builder.setPositiveButton(context.getString(R.string.ok), (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        // Configuración de los campos de texto para la información del equipo
        setTextView(customLayout, R.id.tvNombreEquipo, equipo.getEquipmentName());
        setTextView(customLayout, R.id.tvTipoEquipo, equipo.getEquipmentType());
        setTextView(customLayout, R.id.tvMarca, equipo.getBrand());
        setTextView(customLayout, R.id.tvModelo, equipo.getModel());
        setTextView(customLayout, R.id.tvSerie, equipo.getSerial());
        setTextView(customLayout, R.id.tvNumeroOrden, equipo.getOrderNumber());
        setTextView(customLayout, R.id.tvDescripcion, equipo.getDescription());
        setTextView(customLayout, R.id.tvEstado, equipo.getStatus());
        setTextView(customLayout, R.id.tvCodigoPatrimonial, equipo.getAssetCode());
        setTextView(customLayout, R.id.tvCodigoBarra, equipo.getBarcode());
        setTextView(customLayout, R.id.tvFechaCompra, equipo.getPurchaseDate() != null ? equipo.getPurchaseDate().toString() : "-");

        // Configuración de los campos de texto para la información del responsable
        setTextView(customLayout, R.id.tvNombreResponsable, equipo.getResponsible() != null ? equipo.getResponsible().getFullName() : "-");
        setTextView(customLayout, R.id.tvCargoResponsable, equipo.getResponsible() != null ? equipo.getResponsible().getPosition() : "-");

        // Configuración de los campos de texto para la información de la ubicación
        setTextView(customLayout, R.id.tvAmbiente, equipo.getLocation() != null ? equipo.getLocation().getRoom() : "-");
        setTextView(customLayout, R.id.tvUbicacionFisica, equipo.getLocation() != null ? equipo.getLocation().getPhysicalLocation() : "-");
    }

    // Método para configurar los TextViews de manera segura
    private void setTextView(View layout, int textViewId, String text) {
        TextView textView = layout.findViewById(textViewId);
        textView.setText(safeString(text));
    }

    // Método para manejar strings nulos
    private String safeString(String text) {
        return text != null ? text : "-";
    }
}
