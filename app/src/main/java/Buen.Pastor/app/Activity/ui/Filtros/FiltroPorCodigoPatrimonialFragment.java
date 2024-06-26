package Buen.Pastor.app.Activity.ui.Filtros;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

import Buen.Pastor.app.Activity.ui.equipos.DetalleEquipoFragment;
import Buen.P.App.R;
import Buen.Pastor.app.Activity.ui.equipos.modificarequipo.ModificarFragment;
import Buen.Pastor.app.adapter.EquipoAdapter;
import Buen.Pastor.app.entity.service.Equipment;
import Buen.Pastor.app.viewModel.EquipoViewModel;

public class FiltroPorCodigoPatrimonialFragment extends Fragment {

    private EquipoViewModel equipoViewModel;
    private RecyclerView recyclerView;
    private EquipoAdapter equipoAdapter;
    private TextInputEditText edtBuscarCodigoPatrimonial;
    private Button btnBuscarCodigo;
    private TextView txtNoResultsCodigo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtro_por_codigo_patrimonial, container, false);
        edtBuscarCodigoPatrimonial = view.findViewById(R.id.edtBuscarCodigoPatrimonial);
        btnBuscarCodigo = view.findViewById(R.id.btnBuscarCodigo);
        txtNoResultsCodigo = view.findViewById(R.id.txtNoResultsCodigo);
        recyclerView = view.findViewById(R.id.recyclerViewFiltroCodigoPatrimonial);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        equipoAdapter = new EquipoAdapter(new ArrayList<>(), new EquipoAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int equipoId) {
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
                                    Toast.makeText(getContext(), "Error al eliminar equipo: " + (response != null ? response.getMessage() : "Error desconocido"), Toast.LENGTH_LONG).show();
                                }
                            });
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        recyclerView.setAdapter(equipoAdapter);
        setupListeners();
        return view;
    }

    private void setupListeners() {
        // Filtro para permitir solo números en el campo de texto
        edtBuscarCodigoPatrimonial.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (!Character.isDigit(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }});

        btnBuscarCodigo.setOnClickListener(v -> filterEquipments(edtBuscarCodigoPatrimonial.getText().toString()));
    }

    private void filterEquipments(String query) {
        equipoViewModel.filtroCodigoPatrimonial(query).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getBody() != null) {
                List<Equipment> filteredList = response.getBody();
                if (filteredList.isEmpty()) {
                    txtNoResultsCodigo.setVisibility(View.VISIBLE);
                } else {
                    txtNoResultsCodigo.setVisibility(View.GONE);
                }
                equipoAdapter.updateData(filteredList);
            } else {
                txtNoResultsCodigo.setVisibility(View.VISIBLE);
                equipoAdapter.updateData(new ArrayList<>());
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);

        // Cargar todos los equipos al iniciar el Fragmento
        equipoViewModel.listAllEquipos().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getBody() != null) {
                equipoAdapter.updateData(response.getBody());
            }
        });
    }
}
