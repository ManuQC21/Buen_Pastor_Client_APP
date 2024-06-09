package Buen.Pastor.app.Activity.ui.equipos;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import Buen.P.App.R;
import Buen.Pastor.app.adapter.EquipoAdapter;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import java.util.ArrayList;

public class ListarFragment extends Fragment implements EquipoAdapter.OnItemClickListener {

    private EquipoViewModel equipoViewModel;
    private RecyclerView recyclerViewEquipos;
    private EquipoAdapter equipoAdapter;
    private ProgressBar progressBar;

    private static final String TAG = "ListarFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar, container, false);

        recyclerViewEquipos = view.findViewById(R.id.recyclerViewEquipos);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerViewEquipos.setLayoutManager(new LinearLayoutManager(getContext()));

        equipoAdapter = new EquipoAdapter(new ArrayList<>(), this);
        recyclerViewEquipos.setAdapter(equipoAdapter);

        progressBar.setVisibility(View.VISIBLE);
        equipoViewModel.listAllEquipos().observe(getViewLifecycleOwner(), response -> {
            progressBar.setVisibility(View.GONE);
            if (response != null && response.getRpta() == 1 && response.getBody() != null) {
                equipoAdapter.updateData(response.getBody());
            } else {
                String errorMessage = response != null ? response.getMessage() : "Unknown error";
                Toast.makeText(getContext(), "Error al cargar equipos: " + errorMessage, Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error al cargar equipos: " + errorMessage);
            }
        });
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onEditClick(int equipoId) {
        Log.d(TAG, "Edit clicked for ID: " + equipoId);
        ModificarFragment modificarFragment = new ModificarFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("equipoId", equipoId);
        modificarFragment.setArguments(bundle);
        if (isAdded()) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, modificarFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onViewClick(int equipoId) {
        DetalleEquipoFragment detalleFragment = new DetalleEquipoFragment();
        Bundle args = new Bundle();
        args.putInt("equipoId", equipoId);
        detalleFragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detalleFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeleteClick(int equipoId) {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar Equipo")
                .setMessage("¿Estás seguro de eliminar este equipo?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    equipoViewModel.deleteEquipo(equipoId).observe(getViewLifecycleOwner(), response -> {
                        if (response != null && response.getRpta() == 1) {
                            Toast.makeText(getContext(), "Equipo eliminado correctamente", Toast.LENGTH_SHORT).show();
                            equipoViewModel.listAllEquipos().observe(getViewLifecycleOwner(), equipoResponse -> {
                                if (equipoResponse != null && equipoResponse.getRpta() == 1) {
                                    equipoAdapter.updateData(equipoResponse.getBody());
                                }
                            });
                        } else {
                            String errorMessage = response != null ? response.getMessage() : "Error desconocido";
                            Toast.makeText(getContext(), "Error al eliminar equipo: " + errorMessage, Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Error al eliminar equipo: " + errorMessage);
                        }
                    });
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
