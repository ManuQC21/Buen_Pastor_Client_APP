package Buen.Pastor.app.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.Notification;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.ViewHolder> {

    private List<Notification> notificaciones;
    private final OnNotificationCheckListener listener;

    public interface OnNotificationCheckListener {
        void onCheck(Notification notification, int paymentId);
    }

    public NotificacionesAdapter(List<Notification> notificaciones, OnNotificationCheckListener listener) {
        this.notificaciones = notificaciones;
        this.listener = listener;
    }

    public void updateData(List<Notification> newNotificaciones) {
        this.notificaciones = newNotificaciones;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificaciones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notificaciones.get(position);
        CharSequence formattedMessage = formatMessage(notification.getMessage(), holder.itemView.getContext());
        holder.mensajeNotificacion.setText(formattedMessage);
        holder.tituloNotificacion.setText("Notificación");

        holder.iconoCheck.setOnClickListener(v -> {
            if (notification.getTeacher() != null && !notification.getTeacher().getTeacherPayments().isEmpty()) {
                int paymentId = notification.getTeacher().getTeacherPayments().get(0).getId(); // Asumiendo que siempre hay al menos un pago.
                listener.onCheck(notification, paymentId);
            }
            notificaciones.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, notificaciones.size());
        });
    }


    private CharSequence formatMessage(String message, Context context) {
        try {
            // Ejemplo de mensaje: "Estimado/a Gimenita,Tiene un Nuevo Pago Programado2000.00Para la Fecha 2023-05-15."
            String[] parts = message.split(",Tiene un Nuevo Pago Programado");
            String nombre = parts[0].replace("Estimado/a ", "").trim();
            String[] pagoParts = parts[1].split("Para la Fecha");
            String monto = pagoParts[0].trim();
            String fecha = pagoParts[1].replace(".", "").trim();

            // Cambiar el formato de la fecha de "2023-05-15" a "15/05/2023"
            String[] fechaParts = fecha.split("-");
            String fechaFormateada = fechaParts[2] + "/" + fechaParts[1] + "/" + fechaParts[0];

            // Crear un Spannable para formatear el texto
            String fullText = "Estimado/a " + nombre + "\nTiene un nuevo pago programado\nEl monto total es de S/. " + monto + "\nLa fecha de pago es " + fechaFormateada;
            SpannableString spannable = new SpannableString(fullText);

            // Aplicar color y estilo
            int montoStart = fullText.indexOf("S/. ");
            int montoEnd = montoStart + ("S/. " + monto).length(); // Asegurarse de que todo el monto esté incluido
            int fechaStart = fullText.indexOf(fechaFormateada);

            spannable.setSpan(new StyleSpan(Typeface.BOLD), 0, 11 + nombre.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Bold para "Estimado/a Gimenita"
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.azul_personalizado)), montoStart, montoEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Azul para el monto
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.rojo_brillante)), fechaStart, fechaStart + fechaFormateada.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Rojo para la fecha

            return spannable;
        } catch (Exception e) {
            e.printStackTrace();
            return message; // Return original message if parsing fails
        }
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tituloNotificacion, mensajeNotificacion;
        ImageView iconoCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloNotificacion = itemView.findViewById(R.id.titulo_notificacion);
            mensajeNotificacion = itemView.findViewById(R.id.message);
            iconoCheck = itemView.findViewById(R.id.ic_check);
        }
    }
}
