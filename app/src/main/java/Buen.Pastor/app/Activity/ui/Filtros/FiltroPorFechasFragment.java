package Buen.Pastor.app.Activity.ui.Filtros;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Calendar;
import Buen.Pastor.app.Activity.ui.equipos.DetalleEquipoFragment;
import Buen.Pastor.app.Activity.ui.equipos.ModificarFragment;
import Buen.P.App.R;
import Buen.Pastor.app.adapter.EquipoAdapter;
import Buen.Pastor.app.viewModel.EquipoViewModel;

public class FiltroPorFechasFragment extends Fragment {

    private EquipoViewModel equipoViewModel;
    private RecyclerView recyclerView;
    private EquipoAdapter equipoAdapter;
    private TextInputEditText edtFechaInicio, edtFechaFin;
    private Button btnFiltrar;
    private DatePickerDialog datePickerDialogInicio, datePickerDialogFin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filtro_por_fechas, container, false);
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        setupUI(view);
        setupDatePickerDialogs();
        loadInitialData();
        return view;
    }

    private void setupUI(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewFiltroFechas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        edtFechaInicio = view.findViewById(R.id.edtFechaInicio);
        edtFechaFin = view.findViewById(R.id.edtFechaFin);
        btnFiltrar = view.findViewById(R.id.btnFiltrarFechas);

        btnFiltrar.setOnClickListener(v -> applyDateFilter());

        edtFechaInicio.setOnClickListener(v -> datePickerDialogInicio.show());
        edtFechaFin.setOnClickListener(v -> datePickerDialogFin.show());

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
                                            equipoAdapter.updateData(equipoResponse.getBody());
                                        }
                                    });
                                } else {
                                    Toast.makeText(getContext(), "Error al eliminar equipo: " + (response != null ? response.getMessage() : "Error desconocido"), Toast.LENGTH_LONG).show();
                                }
                            });
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        recyclerView.setAdapter(equipoAdapter);
    }

    private void setupDatePickerDialogs() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialogInicio = new DatePickerDialog(getContext(), this::setStartDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialogFin = new DatePickerDialog(getContext(), this::setEndDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setStartDate(DatePicker view, int year, int month, int dayOfMonth) {
        edtFechaInicio.setText(String.format("%02d-%02d-%d", dayOfMonth, month + 1, year));
    }

    private void setEndDate(DatePicker view, int year, int month, int dayOfMonth) {
        edtFechaFin.setText(String.format("%02d-%02d-%d", dayOfMonth, month + 1, year));
    }

    private void loadInitialData() {
        equipoViewModel.listAllEquipos().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1) {
                equipoAdapter.updateData(response.getBody());
            } else {
                Toast.makeText(getContext(), "Error loading initial data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyDateFilter() {
        String startDate = edtFechaInicio.getText().toString().trim();
        String endDate = edtFechaFin.getText().toString().trim();
        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            equipoViewModel.filtroFechaCompraBetween(startDate, endDate).observe(getViewLifecycleOwner(), response -> {
                if (response != null && response.getRpta() == 1) {
                    equipoAdapter.updateData(response.getBody());
                } else {
                    Toast.makeText(getContext(), "No data found for selected dates", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Please select both start and end dates", Toast.LENGTH_SHORT).show();
        }
    }
}
