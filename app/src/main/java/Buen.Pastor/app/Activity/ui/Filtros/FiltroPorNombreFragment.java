package Buen.Pastor.app.Activity.ui.Filtros;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;
import Buen.Pastor.app.Activity.ui.equipos.DetalleEquipoFragment;
import Buen.Pastor.app.Activity.ui.equipos.modificarequipo.ModificarFragment;
import Buen.P.App.R;
import Buen.Pastor.app.adapter.EquipoAdapter;
import Buen.Pastor.app.entity.service.Equipment;
import Buen.Pastor.app.viewModel.EquipoViewModel;

public class FiltroPorNombreFragment extends Fragment {

    private EquipoViewModel equipoViewModel;
    private RecyclerView recyclerView;
    private EquipoAdapter equipoAdapter;
    private TextInputEditText edtBuscarNombre;
    private Button btnBuscar;
    private TextView txtNoResults;
    private List<Equipment> allEquipments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtro_por_nombre, container, false);
        edtBuscarNombre = view.findViewById(R.id.edtBuscarNombre);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        txtNoResults = view.findViewById(R.id.txtNoResults);
        recyclerView = view.findViewById(R.id.recyclerViewFiltroNombre);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        equipoAdapter = new EquipoAdapter(new ArrayList<>(), new EquipoAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int equipoId) {
                Log.d("ListarFragment", "Edit clicked for ID: " + equipoId);
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
                                            allEquipments = equipoResponse.getBody();
                                            equipoAdapter.updateData(allEquipments);
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
        setupInputFilters();
        return view;
    }

    private void setupListeners() {
        edtBuscarNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se necesita implementar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No se necesita implementar
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    // Si el campo de búsqueda está vacío, listar todos los equipos
                    equipoViewModel.listAllEquipos().observe(getViewLifecycleOwner(), response -> {
                        if (response != null && response.getBody() != null) {
                            allEquipments = response.getBody();
                            equipoAdapter.updateData(allEquipments);
                            txtNoResults.setVisibility(allEquipments.isEmpty() ? View.VISIBLE : View.GONE);
                        } else {
                            txtNoResults.setVisibility(View.VISIBLE);
                            equipoAdapter.updateData(new ArrayList<>());
                        }
                    });
                }
            }
        });

        btnBuscar.setOnClickListener(v -> filterEquipments(edtBuscarNombre.getText().toString().trim()));
    }

    private void filterEquipments(String query) {
        equipoViewModel.filtroPorNombre(query).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getBody() != null) {
                List<Equipment> filteredList = response.getBody();
                if (filteredList.isEmpty()) {
                    txtNoResults.setVisibility(View.VISIBLE);
                } else {
                    txtNoResults.setVisibility(View.GONE);
                }
                equipoAdapter.updateData(filteredList);
            } else {
                txtNoResults.setVisibility(View.VISIBLE);
                equipoAdapter.updateData(new ArrayList<>());
            }
        });
    }

    private void setupInputFilters() {
        InputFilter lettersOnlyFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        edtBuscarNombre.setFilters(new InputFilter[]{lettersOnlyFilter});
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);

        // Cargar todos los equipos al iniciar el Fragmento
        equipoViewModel.listAllEquipos().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getBody() != null) {
                allEquipments = response.getBody();
                equipoAdapter.updateData(allEquipments);
            }
        });
    }
}
