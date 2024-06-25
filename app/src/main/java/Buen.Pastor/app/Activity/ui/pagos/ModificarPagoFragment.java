package Buen.Pastor.app.Activity.ui.pagos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import Buen.P.App.R;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.TeacherPaymentDTO;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.entity.service.TeacherPayment;
import Buen.Pastor.app.viewModel.PagoViewModel;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.entity.service.App.TeacherDTO;

public class ModificarPagoFragment extends Fragment {

    private static final String TAG = "ModificarPagoFragment";
    private PagoViewModel pagoViewModel;
    private DocenteViewModel docenteViewModel;
    private TextInputLayout dropdownDocenteLayout, dropdownNivelEducacionLayout, dropdownEstadoPagoLayout;
    private AutoCompleteTextView dropdownDocente, dropdownNivelEducacion, dropdownEstadoPago;
    private TextInputEditText txtMontoModificar, txtFechaPagoModificar, txtReferenciaPagoModificar, txtDiasTrabajadosModificar, txtCodigoModularModificar;
    private Button btnModificarPago;
    private int pagoId;
    private TeacherPaymentDTO currentPago;
    private List<TeacherDTO> docentes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagoViewModel = new ViewModelProvider(this).get(PagoViewModel.class);
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);

        if (getArguments() != null) {
            pagoId = getArguments().getInt("pagoId", -1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_pago, container, false);
        // Funcionalidad del botón Volver Atras
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtrasModificarPago);
        btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        setupViews(view);
        setupListeners();
        loadPagoDetails();
        loadDocentes();

        return view;
    }

    private void setupViews(View view) {
        dropdownDocenteLayout = view.findViewById(R.id.dropdownDocenteLayout);
        dropdownDocente = view.findViewById(R.id.dropdownDocenteModificar);
        txtMontoModificar = view.findViewById(R.id.txtMontoModificar);
        txtFechaPagoModificar = view.findViewById(R.id.txtFechaPagoModificar);
        txtReferenciaPagoModificar = view.findViewById(R.id.txtReferenciaPagoModificar);
        txtDiasTrabajadosModificar = view.findViewById(R.id.txtDiasTrabajadosModificar);
        dropdownNivelEducacionLayout = view.findViewById(R.id.dropdownNivelEducacionLayout);
        dropdownNivelEducacion = view.findViewById(R.id.dropdownNivelEducacionModificar);
        txtCodigoModularModificar = view.findViewById(R.id.txtCodigoModularModificar);
        dropdownEstadoPagoLayout = view.findViewById(R.id.dropdownEstadoPagoLayout);
        dropdownEstadoPago = view.findViewById(R.id.dropdownEstadoPagoModificar);
        btnModificarPago = view.findViewById(R.id.btnModificarPago);

        // Agregar filtro de entrada para la referencia de pago
        txtReferenciaPagoModificar.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (!Character.isLetter(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }});

        // Agregar filtro de entrada para días trabajados
        txtDiasTrabajadosModificar.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            try {
                // Concatenar la nueva entrada con el contenido existente
                String result = dest.subSequence(0, dstart).toString() + source.toString() + dest.subSequence(dend, dest.length()).toString();
                int input = Integer.parseInt(result);
                if (input >= 1 && input <= 31) {
                    return null;
                }
            } catch (NumberFormatException nfe) {
                // No hacer nada
            }
            return "";
        }});
    }

    private void setupListeners() {
        txtFechaPagoModificar.setOnClickListener(v -> showDatePickerDialog());
        btnModificarPago.setOnClickListener(v -> {
            if (validarCampos()) {
                modificarPago();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            txtFechaPagoModificar.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void loadPagoDetails() {
        pagoViewModel.obtenerPagoPorId(pagoId).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1 && response.getBody() != null) {
                currentPago = response.getBody();
                populatePagoDetails(currentPago);
            } else {
                Toast.makeText(getContext(), "Error al cargar detalles del pago", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error al cargar detalles del pago: " + (response != null ? response.getMessage() : "Respuesta nula"));
            }
        });
    }

    private void populatePagoDetails(TeacherPaymentDTO pago) {
        txtMontoModificar.setText(String.valueOf(pago.getAmount()));
        txtFechaPagoModificar.setText(pago.getPaymentDate());
        txtReferenciaPagoModificar.setText(pago.getPaymentReference());
        txtDiasTrabajadosModificar.setText(String.valueOf(pago.getWorkDays()));
        txtCodigoModularModificar.setText(pago.getModularCode());
        dropdownNivelEducacion.setText(pago.getEducationLevel(), false);
        dropdownEstadoPago.setText(pago.getPaymentStatus(), false);
        dropdownDocente.setText(pago.getTeacherName(), false);
    }

    private void loadDocentes() {
        docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1 && response.getBody() != null) {
                docentes = response.getBody();
                ArrayAdapter<TeacherDTO> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, docentes);
                dropdownDocente.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Error al cargar la lista de docentes", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error al cargar la lista de docentes: " + (response != null ? response.getMessage() : "Respuesta nula"));
            }
        });
    }

    private void modificarPago() {
        if (currentPago == null) {
            Toast.makeText(getContext(), "No se pueden modificar los detalles del pago", Toast.LENGTH_SHORT).show();
            return;
        }

        TeacherPayment updatedPago = new TeacherPayment();
        updatedPago.setId(currentPago.getId());
        updatedPago.setAmount(new BigDecimal(txtMontoModificar.getText().toString()));
        updatedPago.setPaymentDate(txtFechaPagoModificar.getText().toString());
        updatedPago.setPaymentReference(txtReferenciaPagoModificar.getText().toString());
        updatedPago.setWorkDays(Integer.parseInt(txtDiasTrabajadosModificar.getText().toString()));
        updatedPago.setEducationLevel(dropdownNivelEducacion.getText().toString());
        updatedPago.setModularCode(txtCodigoModularModificar.getText().toString());
        updatedPago.setPaymentStatus(dropdownEstadoPago.getText().toString());

        String selectedTeacherName = dropdownDocente.getText().toString();
        TeacherDTO selectedTeacher = docentes.stream()
                .filter(teacher -> teacher.getFullName().equals(selectedTeacherName))
                .findFirst()
                .orElse(null);

        if (selectedTeacher != null) {
            updatedPago.setTeacher(new Teacher(selectedTeacher.getId()));
        } else {
            Toast.makeText(getContext(), "Debe seleccionar un docente válido", Toast.LENGTH_SHORT).show();
            return;
        }

        pagoViewModel.editarPago(updatedPago).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1) {
                Toast.makeText(getContext(), "Pago modificado correctamente", Toast.LENGTH_SHORT).show();
                getParentFragmentManager().popBackStack();
            } else {
                Toast.makeText(getContext(), "Error al modificar el pago", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error al modificar el pago: " + (response != null ? response.getMessage() : "Respuesta nula"));
            }
        });
    }

    private boolean validarCampos() {
        String referenciaPago = txtReferenciaPagoModificar.getText().toString();
        String diasTrabajados = txtDiasTrabajadosModificar.getText().toString();

        if (!referenciaPago.matches("[a-zA-Z ]+")) {
            Toast.makeText(getContext(), "La referencia de pago solo debe contener letras y espacios", Toast.LENGTH_SHORT).show();
            return false;
        }

        int dias;
        try {
            dias = Integer.parseInt(diasTrabajados);
            if (dias < 1 || dias > 31) {
                Toast.makeText(getContext(), "Los días trabajados deben estar entre 1 y 31", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Los días trabajados deben ser un número válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
