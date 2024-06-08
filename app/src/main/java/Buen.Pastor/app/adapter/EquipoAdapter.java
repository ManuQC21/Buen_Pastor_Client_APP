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
import Buen.Pastor.app.entity.service.Equipment;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {

    private List<Equipment> equipos;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int equipoId);
        void onViewClick(int equipoId);
        void onDeleteClick(int equipoId);
    }

    public EquipoAdapter(List<Equipment> equipos, OnItemClickListener listener) {
        this.equipos = equipos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EquipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_equipo, parent, false);
        return new EquipoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipoViewHolder holder, int position) {
        Equipment equipo = equipos.get(position);
        holder.nombreEquipo.setText(equipo.getEquipmentName() != null ? equipo.getEquipmentName() : "-");
        holder.tipoEquipo.setText(equipo.getEquipmentType() != null ? equipo.getEquipmentType() : "-");
        holder.codigoPatrimonial.setText(equipo.getAssetCode() != null ? equipo.getAssetCode() : "-");
        holder.fechaCompra.setText(equipo.getPurchaseDate() != null ? equipo.getPurchaseDate() : "-");
        holder.estadoEquipo.setText(equipo.getStatus() != null ? equipo.getStatus() : "-");

        if (listener != null) {
            holder.editButton.setOnClickListener(v -> listener.onEditClick(equipo.getId()));
            holder.viewButton.setOnClickListener(v -> listener.onViewClick(equipo.getId()));
            holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(equipo.getId()));
        }
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreEquipo, tipoEquipo, estadoEquipo, codigoPatrimonial, fechaCompra;
        ImageView editButton, viewButton, deleteButton;

        EquipoViewHolder(View itemView) {
            super(itemView);
            nombreEquipo = itemView.findViewById(R.id.txtNombreEquipo);
            tipoEquipo = itemView.findViewById(R.id.txtTipoEquipo);
            estadoEquipo = itemView.findViewById(R.id.txtEstado);
            codigoPatrimonial = itemView.findViewById(R.id.txtCodigoPatrimonial);
            fechaCompra = itemView.findViewById(R.id.txtFechaCompra);
            editButton = itemView.findViewById(R.id.ic_edit);
            viewButton = itemView.findViewById(R.id.ic_view);
            deleteButton = itemView.findViewById(R.id.ic_delete);
        }
    }

    public void updateData(List<Equipment> newEquipos) {
        equipos.clear();
        equipos.addAll(newEquipos);
        notifyDataSetChanged();
    }
}
