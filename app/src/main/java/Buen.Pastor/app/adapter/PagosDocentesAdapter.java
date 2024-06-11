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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.math.BigDecimal;
import java.util.List;
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.App.TeacherPaymentDTO;

public class PagosDocentesAdapter extends RecyclerView.Adapter<PagosDocentesAdapter.ViewHolder> {

    private List<TeacherPaymentDTO> pagos;

    public PagosDocentesAdapter(List<TeacherPaymentDTO> pagos) {
        this.pagos = pagos;
    }

    public void updateData(List<TeacherPaymentDTO> newPagos) {
        this.pagos = newPagos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagos_docentes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeacherPaymentDTO pago = pagos.get(position);
        holder.montoPago.setText(formatMonto(pago.getAmount(), holder.itemView.getContext()));
        holder.fechaPago.setText(pago.getPaymentDate());
        holder.estadoPago.setText(pago.getPaymentStatus());
    }

    private CharSequence formatMonto(BigDecimal monto, Context context) {
        String montoStr = "S/. " + monto.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        SpannableString spannable = new SpannableString(montoStr);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.azul_personalizado)), 0, montoStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView montoPago, fechaPago, estadoPago;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            montoPago = itemView.findViewById(R.id.monto_pago);
            fechaPago = itemView.findViewById(R.id.fecha_pago);
            estadoPago = itemView.findViewById(R.id.estado_pago);
        }
    }
}
