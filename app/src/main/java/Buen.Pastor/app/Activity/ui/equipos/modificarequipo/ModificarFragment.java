package Buen.Pastor.app.Activity.ui.equipos.modificarequipo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import Buen.P.App.R;
import Buen.P.App.databinding.FragmentModificarBinding;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import Buen.Pastor.app.viewModel.UbicacionViewModel;

public class ModificarFragment extends Fragment {
    private EquipoViewModel equipoViewModel;
    private DocenteViewModel docenteViewModel;
    private UbicacionViewModel ubicacionViewModel;
    private TextInputEditText txtTipoEquipo, txtDescripcion, txtMarca, txtModelo, txtNombreDeEquipo, txtNumeroDeOrden, txtNumeroDeSerie, edtFechaCompra;
    private AutoCompleteTextView dropdownEstado, dropdownResponsable, dropdownUbicacion;
    private Button btnModificarEquipo;
    private FragmentModificarBinding binding;
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
        binding = FragmentModificarBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);
        ubicacionViewModel = new ViewModelProvider(this).get(UbicacionViewModel.class);

        setupUI(view);
        cargarDatosEquipo();
        ModificarEquipoHelper.configurarValidaciones(txtTipoEquipo, txtDescripcion, txtMarca, txtModelo, txtNombreDeEquipo, txtNumeroDeOrden, txtNumeroDeSerie);
        edtFechaCompra.setOnClickListener(v -> mostrarDatePickerDialog());
        btnModificarEquipo.setOnClickListener(v -> modificarEquipo());
        ModificarEquipoViewModelHelper.cargarResponsables(getContext(), docenteViewModel, getViewLifecycleOwner(), dropdownResponsable);
        ModificarEquipoViewModelHelper.cargarUbicaciones(getContext(), ubicacionViewModel, getViewLifecycleOwner(), dropdownUbicacion);
        ModificarEquipoHelper.cargarEstados(getContext(), dropdownEstado);
        return view;
    }

    private void setupUI(View view) {
        txtTipoEquipo = view.findViewById(R.id.txtTipoEquipoModificar);
        txtDescripcion = view.findViewById(R.id.txtDescripcionModificar);
        txtMarca = view.findViewById(R.id.txtMarcaModificar);
        txtModelo = view.findViewById(R.id.txtModeloModificar);
        txtNombreDeEquipo = view.findViewById(R.id.txtNombreDeEquipoModificar);
        txtNumeroDeOrden = view.findViewById(R.id.txtNumeroDeOrdenModificar);
        txtNumeroDeSerie = view.findViewById(R.id.txtNumeroDeSerieModificar);
        edtFechaCompra = view.findViewById(R.id.edtFechaCompraModificar);
        dropdownEstado = view.findViewById(R.id.dropdownEstadoModificar);
        dropdownResponsable = view.findViewById(R.id.dropdownResponsableModificar);
        dropdownUbicacion = view.findViewById(R.id.dropdownUbicacionModificar);
        btnModificarEquipo = view.findViewById(R.id.btnModificarEquipo);
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtrasModificar);
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

    private void cargarDatosEquipo() {
        ModificarEquipoViewModelHelper.cargarDatosEquipo(equipoId, equipoViewModel, getViewLifecycleOwner(),
                txtTipoEquipo, txtDescripcion, txtMarca, txtModelo, txtNombreDeEquipo, txtNumeroDeOrden, txtNumeroDeSerie, edtFechaCompra, dropdownEstado, dropdownResponsable, dropdownUbicacion);
    }

    private void modificarEquipo() {
        ModificarEquipoViewModelHelper.modificarEquipo(getContext(), equipoId, equipoViewModel, docenteViewModel, ubicacionViewModel,
                txtTipoEquipo, txtDescripcion, txtMarca, txtModelo, txtNombreDeEquipo, txtNumeroDeOrden, txtNumeroDeSerie, edtFechaCompra, dropdownEstado, dropdownResponsable, dropdownUbicacion);
    }
}
