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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import Buen.Pastor.app.Activity.InicioActivity;
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.Empleado;
import Buen.Pastor.app.entity.service.Equipo;
import Buen.Pastor.app.entity.service.Ubicacion;
import Buen.Pastor.app.viewModel.EmpleadoViewModel;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import Buen.Pastor.app.viewModel.UbicacionViewModel;

public class ModificarFragment extends Fragment {
    private EquipoViewModel equipoViewModel;
    private EmpleadoViewModel empleadoViewModel;
    private UbicacionViewModel ubicacionViewModel;

    private TextInputEditText txtTipoEquipo, txtDescripcion, txtMarca, txtModelo, txtNombreEquipo, txtNumeroOrden, txtNumeroSerie, edtFechaCompra;
    private AutoCompleteTextView dropdownEstado, dropdownResponsable, dropdownUbicacion;
    private Button btnModificarEquipo;
    private int equipoId;

    private final Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            equipoId = getArguments().getInt("equipoId", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar, container, false);
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        empleadoViewModel = new ViewModelProvider(this).get(EmpleadoViewModel.class);
        ubicacionViewModel = new ViewModelProvider(this).get(UbicacionViewModel.class);
        cargarDatosEquipo();
        txtTipoEquipo = view.findViewById(R.id.txtTipoEquipoModificar);
        txtDescripcion = view.findViewById(R.id.txtDescripcionModificar);
        txtMarca = view.findViewById(R.id.txtMarcaModificar);
        txtModelo = view.findViewById(R.id.txtModeloModificar);
        txtNombreEquipo = view.findViewById(R.id.txtNombreDeEquipoModificar);
        txtNumeroOrden = view.findViewById(R.id.txtNumeroDeOrdenModificar);
        txtNumeroSerie = view.findViewById(R.id.txtNumeroDeSerieModificar);
        edtFechaCompra = view.findViewById(R.id.edtFechaCompraModificar);
        dropdownEstado = view.findViewById(R.id.dropdownEstadoModificar);
        dropdownResponsable = view.findViewById(R.id.dropdownResponsableModificar);
        dropdownUbicacion = view.findViewById(R.id.dropdownUbicacionModificar);
        btnModificarEquipo = view.findViewById(R.id.btnModificarEquipo);
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtrasModificar);
        btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        edtFechaCompra.setOnClickListener(v -> mostrarDatePickerDialog());
        btnModificarEquipo.setOnClickListener(v -> modificarEquipo());
        cargarEstados();
        cargarResponsables();
        cargarUbicaciones();
        return view;
    }
    private void cargarDatosEquipo() {
        if (equipoId != -1) {
            equipoViewModel.getEquipoById(equipoId).observe(getViewLifecycleOwner(), response -> {
                if (response != null && response.getRpta() == 1 && response.getBody() != null) {
                    Equipo equipo = response.getBody();
                    txtTipoEquipo.setText(equipo.getTipoEquipo());
                    txtDescripcion.setText(equipo.getDescripcion());
                    txtMarca.setText(equipo.getMarca());
                    txtModelo.setText(equipo.getModelo());
                    txtNombreEquipo.setText(equipo.getNombreEquipo());
                    txtNumeroOrden.setText(equipo.getNumeroOrden());
                    txtNumeroSerie.setText(equipo.getSerie());
                    if (equipo.getFechaCompra() != null && !equipo.getFechaCompra().isEmpty()) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                            Date fecha = sdf.parse(equipo.getFechaCompra());
                            edtFechaCompra.setText(sdf.format(fecha));
                        } catch (ParseException e) {
                            Toast.makeText(getContext(), "Error al parsear la fecha", Toast.LENGTH_SHORT).show();
                        }
                    }                    dropdownEstado.setText(equipo.getEstado(), false);
                    if (equipo.getResponsable() != null) {
                        dropdownResponsable.setText(equipo.getResponsable().getNombre(), false);
                    }
                    if (equipo.getUbicacion() != null) {
                        dropdownUbicacion.setText(equipo.getUbicacion().getAmbiente(), false);
                    }
                } else {
                    Toast.makeText(getContext(), "Error al cargar datos del equipo", Toast.LENGTH_SHORT).show();
                }
            });
        }
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

    private void modificarEquipo() {
        Equipo equipo = new Equipo();
        equipo.setId(equipoId);
        equipo.setTipoEquipo(txtTipoEquipo.getText().toString());
        equipo.setDescripcion(txtDescripcion.getText().toString());
        equipo.setEstado(dropdownEstado.getText().toString());
        equipo.setFechaCompra(edtFechaCompra.getText().toString());
        equipo.setMarca(txtMarca.getText().toString());
        equipo.setModelo(txtModelo.getText().toString());
        equipo.setNombreEquipo(txtNombreEquipo.getText().toString());
        equipo.setNumeroOrden(txtNumeroOrden.getText().toString());
        equipo.setSerie(txtNumeroSerie.getText().toString());
        String nombreResponsable = dropdownResponsable.getText().toString();
        String ambienteUbicacion = dropdownUbicacion.getText().toString();
        empleadoViewModel.listarEmpleados().observe(getViewLifecycleOwner(), empleadoResponse -> {
            if (empleadoResponse != null && empleadoResponse.getRpta() == 1) {
                for (Empleado empleado : empleadoResponse.getBody()) {
                    if (empleado.getNombre().equals(nombreResponsable)) {
                        equipo.setResponsable(empleado);
                        break;
                    }
                }
            }

            ubicacionViewModel.listarUbicaciones().observe(getViewLifecycleOwner(), ubicacionResponse -> {
                if (ubicacionResponse != null && ubicacionResponse.getRpta() == 1) {
                    for (Ubicacion ubicacion : ubicacionResponse.getBody()) {
                        if (ubicacion.getAmbiente().equals(ambienteUbicacion)) {
                            equipo.setUbicacion(ubicacion);
                            break;
                        }
                    }

                    equipoViewModel.updateEquipo(equipo).observe(getViewLifecycleOwner(), updateResponse -> {
                        if (updateResponse != null && updateResponse.getRpta() == 1) {
                            Toast.makeText(getContext(), "Equipo modificado con éxito", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                            Intent intent = new Intent(getContext(), InicioActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            getActivity().finish();

                        } else {
                            Toast.makeText(getContext(), "Error al modificar equipo: " + (updateResponse != null ? updateResponse.getMessage() : "Error desconocido"), Toast.LENGTH_LONG).show();
                        }
                    });


                }
            });
        });
    }

    private void limpiarCampos() {
        txtTipoEquipo.setText("");
        txtDescripcion.setText("");
        dropdownEstado.setText("");
        edtFechaCompra.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtNombreEquipo.setText("");
        txtNumeroOrden.setText("");
        txtNumeroSerie.setText("");
        dropdownResponsable.setText("");
        dropdownUbicacion.setText("");
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
}
