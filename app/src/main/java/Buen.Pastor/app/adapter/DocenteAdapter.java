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
import Buen.Pastor.app.entity.service.App.TeacherDTO;

public class DocenteAdapter extends RecyclerView.Adapter<DocenteAdapter.DocenteViewHolder> {

    private List<TeacherDTO> docentes;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int docenteId);
        void onViewClick(int docenteId);
        void onDeleteClick(int docenteId);
    }

    public DocenteAdapter(List<TeacherDTO> docentes, OnItemClickListener listener) {
        this.docentes = docentes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DocenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_docente, parent, false);
        return new DocenteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DocenteViewHolder holder, int position) {
        TeacherDTO docente = docentes.get(position);
        holder.nombreCompleto.setText(docente.getFullName() != null ? docente.getFullName() : "-");
        holder.cargo.setText(docente.getPosition() != null ? docente.getPosition() : "-");
        holder.estado.setText(docente.isActive() ? "Activo" : "Inactivo");

        if (listener != null) {
            holder.editButton.setOnClickListener(v -> listener.onEditClick(docente.getId()));
            holder.viewButton.setOnClickListener(v -> listener.onViewClick(docente.getId()));
            holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(docente.getId()));
        }
    }

    @Override
    public int getItemCount() {
        return docentes.size();
    }

    public static class DocenteViewHolder extends RecyclerView.ViewHolder {
        TextView nombreCompleto, cargo, estado;
        ImageView editButton, viewButton, deleteButton;

        DocenteViewHolder(View itemView) {
            super(itemView);
            nombreCompleto = itemView.findViewById(R.id.txtNombreCompleto);
            cargo = itemView.findViewById(R.id.txtCargo);
            estado = itemView.findViewById(R.id.txtEstado);
            editButton = itemView.findViewById(R.id.ic_edit);
            viewButton = itemView.findViewById(R.id.ic_view);
            deleteButton = itemView.findViewById(R.id.ic_delete);
        }
    }

    public void updateData(List<TeacherDTO> newDocentes) {
        docentes.clear();
        docentes.addAll(newDocentes);
        notifyDataSetChanged();
    }
}
