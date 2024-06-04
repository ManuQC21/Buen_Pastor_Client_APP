package Buen.Pastor.app.Activity.ui.equipos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import Buen.Pastor.app.Activity.InicioActivity;
import Buen.P.App.R;
import Buen.P.App.databinding.FragmentAgregarBinding;
import Buen.Pastor.app.entity.service.Empleado;
import Buen.Pastor.app.entity.service.Equipo;
import Buen.Pastor.app.entity.service.Ubicacion;
import Buen.Pastor.app.viewModel.EmpleadoViewModel;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import Buen.Pastor.app.viewModel.UbicacionViewModel;

public class AgregarFragment extends Fragment {
    private EquipoViewModel equipoViewModel;
    private TextInputEditText txtTipoEquipo, txtCodigoPatrimonial, txtDescripcion, txtMarca, txtModelo, txtNombreDeEquipo, txtNumeroDeOrden, txtNumeroDeSerie, edtFechaCompra;
    private AutoCompleteTextView dropdownEstado, dropdownResponsable, dropdownUbicacion;
    private Button btnAgregarEquipo;
    private FragmentAgregarBinding binding;
    private EmpleadoViewModel empleadoViewModel;
    private UbicacionViewModel ubicacionViewModel;
    private final Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        empleadoViewModel = new ViewModelProvider(this).get(EmpleadoViewModel.class);
        ubicacionViewModel = new ViewModelProvider(this).get(UbicacionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAgregarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        txtTipoEquipo = view.findViewById(R.id.txtTipoEquipo);
        txtCodigoPatrimonial = view.findViewById(R.id.txtCodigoPatrimonial);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtMarca = view.findViewById(R.id.txtMarca);
        txtModelo = view.findViewById(R.id.txtModelo);
        txtNombreDeEquipo = view.findViewById(R.id.txtNombreDeEquipo);
        txtNumeroDeOrden = view.findViewById(R.id.txtNumeroDeOrden);
        txtNumeroDeSerie = view.findViewById(R.id.txtNumeroDeSerie);
        edtFechaCompra = view.findViewById(R.id.edtFechaCompra);
        dropdownEstado = view.findViewById(R.id.dropdownEstado);
        dropdownResponsable = view.findViewById(R.id.dropdownResponsable);
        dropdownUbicacion = view.findViewById(R.id.dropdownUbicacion);
        btnAgregarEquipo = view.findViewById(R.id.btnAgregarEquipo);
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        edtFechaCompra.setOnClickListener(v -> mostrarDatePickerDialog());
        btnAgregarEquipo.setOnClickListener(v -> agregarEquipo());
        cargarResponsables();
        cargarUbicaciones();
        cargarEstados();

        return view;
    }

    private void mostrarDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            edtFechaCompra.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void agregarEquipo() {
        Equipo equipo = new Equipo();
        equipo.setTipoEquipo(txtTipoEquipo.getText().toString());
        equipo.setCodigoPatrimonial(txtCodigoPatrimonial.getText().toString());
        equipo.setDescripcion(txtDescripcion.getText().toString());
        equipo.setEstado(dropdownEstado.getText().toString());
        equipo.setFechaCompra(edtFechaCompra.getText().toString());
        equipo.setMarca(txtMarca.getText().toString());
        equipo.setModelo(txtModelo.getText().toString());
        equipo.setNombreEquipo(txtNombreDeEquipo.getText().toString());
        equipo.setNumeroOrden(txtNumeroDeOrden.getText().toString());
        equipo.setSerie(txtNumeroDeSerie.getText().toString());
        String nombreResponsable = dropdownResponsable.getText().toString();
        String ambienteUbicacion = dropdownUbicacion.getText().toString();

            empleadoViewModel.listarEmpleados().observe(getViewLifecycleOwner(), response -> {
            if (response.getRpta() == 1) {
                for (Empleado empleado : response.getBody()) {
                    if (empleado.getNombre().equals(nombreResponsable)) {
                        equipo.setResponsable(empleado);
                        break;
                    }
                }
            }

            ubicacionViewModel.listarUbicaciones().observe(getViewLifecycleOwner(), ubicacionResponse -> {
                if (ubicacionResponse.getRpta() == 1) {
                    for (Ubicacion ubicacion : ubicacionResponse.getBody()) {
                        if (ubicacion.getAmbiente().equals(ambienteUbicacion)) {
                            equipo.setUbicacion(ubicacion);
                            break;
                        }
                    }
                }

                equipoViewModel.addEquipo(equipo).observe(getViewLifecycleOwner(), equipoResponse -> {
                    if (equipoResponse.getRpta() == 1) {
                        Toast.makeText(getContext(), "Equipo agregado con éxito", Toast.LENGTH_SHORT).show();
                        limpiarCampos();
                        Intent intent = new Intent(getContext(), InicioActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Error al agregar equipo", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });
    }

    private void limpiarCampos() {
        txtTipoEquipo.setText("");
        txtCodigoPatrimonial.setText("");
        txtDescripcion.setText("");
        dropdownEstado.setText("");
        edtFechaCompra.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtNombreDeEquipo.setText("");
        txtNumeroDeOrden.setText("");
        txtNumeroDeSerie.setText("");
        dropdownResponsable.setText("");
        dropdownUbicacion.setText("");
    }

    private void cargarResponsables() {
        empleadoViewModel.listarEmpleados().observe(getViewLifecycleOwner(), response -> {
            if (response.getRpta() == 1) {
                List<String> nombres = new ArrayList<>();
                for (Empleado empleado : response.getBody()) {
                    nombres.add(empleado.getNombre());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nombres);
                dropdownResponsable.setAdapter(adapter);
            }
        });
    }

    private void cargarUbicaciones() {
        ubicacionViewModel.listarUbicaciones().observe(getViewLifecycleOwner(), response -> {
            if (response.getRpta() == 1) {
                List<String> ubicaciones = new ArrayList<>();
                for (Ubicacion ubicacion : response.getBody()) {
                    ubicaciones.add(ubicacion.getAmbiente());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ubicaciones);
                dropdownUbicacion.setAdapter(adapter);
            }
        });
    }

    private void cargarEstados() {
        List<String> estados = new ArrayList<>();
        estados.add("Mal estado");
        estados.add("Estable");
        estados.add("Buen estado");
        estados.add("Requiere mantenimiento");
        estados.add("En reparación");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, estados);
        dropdownEstado.setAdapter(adapter);
    }
}
