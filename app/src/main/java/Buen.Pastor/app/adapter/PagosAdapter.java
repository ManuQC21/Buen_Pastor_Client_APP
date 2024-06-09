package Buen.Pastor.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.App.TeacherPaymentDTO;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.PagoViewHolder> {

    private List<TeacherPaymentDTO> pagos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int pagoId);
        void onViewClick(int pagoId);
        void onDeleteClick(int pagoId);
        void onNotificarClick(int pagoId);
    }

    public PagosAdapter(List<TeacherPaymentDTO> pagos, OnItemClickListener listener) {
        this.pagos = pagos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagos, parent, false);
        return new PagoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        TeacherPaymentDTO pago = pagos.get(position);
        holder.txtNombreDocente.setText(pago.getTeacherName());
        holder.txtMonto.setText(String.format("S/. %.2f", pago.getAmount()));
        holder.txtFechaPago.setText(pago.getPaymentDate());
        holder.txtEstadoPago.setText(pago.getPaymentStatus());

        if (listener != null) {
            holder.editButton.setOnClickListener(v -> listener.onEditClick(pago.getId()));
            holder.viewButton.setOnClickListener(v -> listener.onViewClick(pago.getId()));
            holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(pago.getId()));
            holder.notifButton.setOnClickListener(v -> listener.onNotificarClick(pago.getId()));
        }
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public static class PagoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreDocente, txtMonto, txtFechaPago, txtEstadoPago;
        ImageView editButton, viewButton, deleteButton, notifButton;

        PagoViewHolder(View itemView) {
            super(itemView);
            txtNombreDocente = itemView.findViewById(R.id.txtNombreDocente);
            txtMonto = itemView.findViewById(R.id.txtMonto);
            txtFechaPago = itemView.findViewById(R.id.txtFechaPago);
            txtEstadoPago = itemView.findViewById(R.id.txtEstadoPago);
            editButton = itemView.findViewById(R.id.ic_edit);
            viewButton = itemView.findViewById(R.id.ic_view);
            deleteButton = itemView.findViewById(R.id.ic_delete);
            notifButton = itemView.findViewById(R.id.ic_notif);
        }
    }

    public void updateData(List<TeacherPaymentDTO> newPagos) {
        pagos.clear();
        pagos.addAll(newPagos);
        notifyDataSetChanged();
    }
}
