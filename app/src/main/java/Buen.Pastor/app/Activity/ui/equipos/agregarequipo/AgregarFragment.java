package Buen.Pastor.app.Activity.ui.equipos.agregarequipo;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;
import Buen.P.App.R;
import Buen.P.App.databinding.FragmentAgregarBinding;
import Buen.Pastor.app.entity.service.Equipment;
import Buen.Pastor.app.utils.DatePickerHelper;
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
        edtFechaCompra.setOnClickListener(v -> DatePickerHelper.mostrarDatePickerDialog(getContext(), edtFechaCompra, calendar));
        btnAgregarEquipo.setOnClickListener(v -> agregarEquipo());
        AgregarEquipoViewModelHelper.cargarResponsables(getContext(), docenteViewModel, getViewLifecycleOwner(), dropdownResponsable);
        AgregarEquipoViewModelHelper.cargarUbicaciones(getContext(), ubicacionViewModel, getViewLifecycleOwner(), dropdownUbicacion);
        AgregarEquipoHelper.cargarEstados(getContext(), dropdownEstado);
        configurarValidaciones();
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

    private void agregarEquipo() {
        if (!AgregarEquipoValidationHelper.validarEntradas(getContext(), txtTipoEquipo, txtCodigoPatrimonial, txtDescripcion, txtMarca, txtModelo, txtNombreDeEquipo, txtNumeroDeOrden, txtNumeroDeSerie, edtFechaCompra)) {
            return; // Detiene la ejecución si alguna validación falla
        }

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

        AgregarEquipoViewModelHelper.establecerResponsableYUbicacion(getContext(), equipo, docenteViewModel, ubicacionViewModel, nombreResponsable, ambienteUbicacion, getViewLifecycleOwner(), equipoViewModel);
    }

    private void configurarValidaciones() {
        TextWatcher soloLetrasWatcher = AgregarEquipoHelper.crearSoloLetrasWatcher();
        TextWatcher soloNumerosWatcher = AgregarEquipoHelper.crearSoloNumerosWatcher();

        txtTipoEquipo.addTextChangedListener(soloLetrasWatcher);
        txtMarca.addTextChangedListener(soloLetrasWatcher);
        txtModelo.addTextChangedListener(soloLetrasWatcher);
        txtNombreDeEquipo.addTextChangedListener(soloLetrasWatcher);

        txtCodigoPatrimonial.addTextChangedListener(soloNumerosWatcher);
        txtNumeroDeOrden.addTextChangedListener(soloNumerosWatcher);
        txtNumeroDeSerie.addTextChangedListener(soloNumerosWatcher);
    }
}
