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
import Buen.Pastor.app.Activity.InicioAdministrativoActivity;
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.App.TeacherDTO;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.entity.service.Location;
import Buen.Pastor.app.entity.service.Equipment;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import Buen.Pastor.app.viewModel.UbicacionViewModel;

public class ModificarFragment extends Fragment {
    private EquipoViewModel equipoViewModel;
    private DocenteViewModel docenteViewModel;
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
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);
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
                    Equipment equipo = response.getBody();
                    txtTipoEquipo.setText(equipo.getEquipmentType());
                    txtDescripcion.setText(equipo.getDescription());
                    txtMarca.setText(equipo.getBrand());
                    txtModelo.setText(equipo.getModel());
                    txtNombreEquipo.setText(equipo.getEquipmentName());
                    txtNumeroOrden.setText(equipo.getOrderNumber());
                    txtNumeroSerie.setText(equipo.getSerial());
                    if (equipo.getPurchaseDate() != null && !equipo.getPurchaseDate().isEmpty()) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                            Date fecha = sdf.parse(equipo.getPurchaseDate());
                            edtFechaCompra.setText(sdf.format(fecha));
                        } catch (ParseException e) {
                            Toast.makeText(getContext(), "Error al parsear la fecha", Toast.LENGTH_SHORT).show();
                        }
                    }                    dropdownEstado.setText(equipo.getStatus(), false);
                    if (equipo.getResponsible() != null) {
                        dropdownResponsable.setText(equipo.getResponsible().getFullName(), false);
                    }
                    if (equipo.getLocation() != null) {
                        dropdownUbicacion.setText(equipo.getLocation().getRoom(), false);
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            edtFechaCompra.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void modificarEquipo() {
        Equipment equipo = new Equipment();
        equipo.setId(equipoId);
        equipo.setEquipmentType(txtTipoEquipo.getText().toString());
        equipo.setDescription(txtDescripcion.getText().toString());
        equipo.setStatus(dropdownEstado.getText().toString());
        equipo.setPurchaseDate(edtFechaCompra.getText().toString());
        equipo.setBrand(txtMarca.getText().toString());
        equipo.setModel(txtModelo.getText().toString());
        equipo.setEquipmentName(txtNombreEquipo.getText().toString());
        equipo.setOrderNumber(txtNumeroOrden.getText().toString());
        equipo.setSerial(txtNumeroSerie.getText().toString());
        String nombreResponsable = dropdownResponsable.getText().toString();
        String ambienteUbicacion = dropdownUbicacion.getText().toString();
            docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), empleadoResponse -> {
                if (empleadoResponse != null && empleadoResponse.getRpta() == 1) {
                    for (TeacherDTO teacher : empleadoResponse.getBody()) {
                        if (teacher.getFullName().equals(nombreResponsable)) {
                            equipo.setResponsible(new Teacher(teacher.getId()));  // Asume que tienes un constructor en Teacher que acepta solo id
                            break;
                        }
                    }
                }

            ubicacionViewModel.listarUbicaciones().observe(getViewLifecycleOwner(), ubicacionResponse -> {
                if (ubicacionResponse != null && ubicacionResponse.getRpta() == 1) {
                    for (Location ubicacion : ubicacionResponse.getBody()) {
                        if (ubicacion.getRoom().equals(ambienteUbicacion)) {
                            equipo.setLocation(ubicacion);
                            break;
                        }
                    }

                    equipoViewModel.updateEquipo(equipo).observe(getViewLifecycleOwner(), updateResponse -> {
                        if (updateResponse != null && updateResponse.getRpta() == 1) {
                            Toast.makeText(getContext(), "Equipo modificado con éxito", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                            Intent intent = new Intent(getContext(), InicioAdministrativoActivity.class);
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
        docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), response -> {
            if (response.getRpta() == 1) {
                List<String> nombres = new ArrayList<>();
                for (TeacherDTO teacher : response.getBody()) {
                    nombres.add(teacher.getFullName());
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
                for (Location ubicacion : response.getBody()) {
                    ubicaciones.add(ubicacion.getRoom());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ubicaciones);
                dropdownUbicacion.setAdapter(adapter);
            }
        });
    }
}
