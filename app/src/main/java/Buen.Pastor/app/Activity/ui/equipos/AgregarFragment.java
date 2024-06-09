package Buen.Pastor.app.Activity.ui.equipos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import Buen.Pastor.app.Activity.InicioAdministrativoActivity;
import Buen.P.App.R;
import Buen.P.App.databinding.FragmentAgregarBinding;
import Buen.Pastor.app.entity.service.App.TeacherDTO;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.entity.service.Location;
import Buen.Pastor.app.entity.service.Equipment;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import Buen.Pastor.app.viewModel.UbicacionViewModel;

public class AgregarFragment extends Fragment {
    private EquipoViewModel equipoViewModel;
    private TextInputEditText txtTipoEquipo, txtCodigoPatrimonial, txtDescripcion, txtMarca, txtModelo, txtNombreDeEquipo, txtNumeroDeOrden, txtNumeroDeSerie, edtFechaCompra;
    private AutoCompleteTextView dropdownEstado, dropdownResponsable, dropdownUbicacion;
    private Button btnAgregarEquipo;
    private FragmentAgregarBinding binding;
    private DocenteViewModel docenteViewModel;
    private UbicacionViewModel ubicacionViewModel;
    private final Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);
        ubicacionViewModel = new ViewModelProvider(this).get(UbicacionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAgregarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);

        setupUI(view);
        edtFechaCompra.setOnClickListener(v -> mostrarDatePickerDialog());
        btnAgregarEquipo.setOnClickListener(v -> agregarEquipo());
        cargarResponsables();
        cargarUbicaciones();
        cargarEstados();

        return view;
    }

    private void setupUI(View view) {
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

    private void agregarEquipo() {
        Equipment equipo = new Equipment();
        equipo.setEquipmentType(txtTipoEquipo.getText().toString());
        equipo.setAssetCode(txtCodigoPatrimonial.getText().toString());
        equipo.setDescription(txtDescripcion.getText().toString());
        equipo.setStatus(dropdownEstado.getText().toString());
        equipo.setPurchaseDate(edtFechaCompra.getText().toString());
        equipo.setBrand(txtMarca.getText().toString());
        equipo.setModel(txtModelo.getText().toString());
        equipo.setEquipmentName(txtNombreDeEquipo.getText().toString());
        equipo.setOrderNumber(txtNumeroDeOrden.getText().toString());
        equipo.setSerial(txtNumeroDeSerie.getText().toString());
        String nombreResponsable = dropdownResponsable.getText().toString();
        String ambienteUbicacion = dropdownUbicacion.getText().toString();

        docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), response -> {
            if (response.getRpta() == 1 && response.getBody() != null) {
                for (TeacherDTO teacher : response.getBody()) {
                    if (teacher.getFullName().equals(nombreResponsable)) {
                        equipo.setResponsible(new Teacher(teacher.getId()));  // Make sure Teacher has a constructor accepting id
                        break;
                    }
                }
                ubicacionViewModel.listarUbicaciones().observe(getViewLifecycleOwner(), ubicacionResponse -> {
                    if (ubicacionResponse.getRpta() == 1) {
                        for (Location ubicacion : ubicacionResponse.getBody()) {
                            if (ubicacion.getRoom().equals(ambienteUbicacion)) {
                                equipo.setLocation(ubicacion);
                                break;
                            }
                        }
                        equipoViewModel.addEquipo(equipo).observe(getViewLifecycleOwner(), equipoResponse -> {
                            if (equipoResponse.getRpta() == 1) {
                                Toast.makeText(getContext(), "Equipo agregado con éxito", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                                Intent intent = new Intent(getContext(), InicioAdministrativoActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Error al agregar equipo: " + equipoResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            } else {
                Log.e("AgregarFragment", "Error al cargar docentes: " + response.getMessage());
                Toast.makeText(getContext(), "Error al cargar docentes: " + response.getMessage(), Toast.LENGTH_LONG).show();
            }
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
        docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), response -> {
            if (response.getRpta() == 1) {
                List<String> nombres = new ArrayList<>();
                for (TeacherDTO teacher : response.getBody()) {
                    nombres.add(teacher.getFullName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nombres);
                dropdownResponsable.setAdapter(adapter);
            } else {
                Log.e("AgregarFragment", "Error al cargar responsables: " + response.getMessage());
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
