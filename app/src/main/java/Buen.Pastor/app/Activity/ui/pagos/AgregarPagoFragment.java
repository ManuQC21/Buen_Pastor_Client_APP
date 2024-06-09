package Buen.Pastor.app.Activity.ui.pagos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.entity.service.TeacherPayment;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.viewModel.PagoViewModel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import Buen.Pastor.app.entity.service.App.TeacherDTO;

public class AgregarPagoFragment extends Fragment {

    private static final String TAG = "AgregarPagoFragment";

    private DocenteViewModel docenteViewModel;
    private PagoViewModel pagoViewModel;
    private AutoCompleteTextView dropdownDocente;
    private AutoCompleteTextView dropdownNivelEducacion;
    private TextInputEditText txtMonto, edtFechaPago, txtReferenciaPago, txtDiasTrabajo, txtCodigoModular;
    private final Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_pago, container, false);

        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);
        pagoViewModel = new ViewModelProvider(this).get(PagoViewModel.class);
        dropdownDocente = view.findViewById(R.id.dropdownDocente);
        dropdownNivelEducacion = view.findViewById(R.id.dropdownNivelEducacion);
        txtMonto = view.findViewById(R.id.txtMonto);
        edtFechaPago = view.findViewById(R.id.edtFechaPago);
        txtReferenciaPago = view.findViewById(R.id.txtReferenciaPago);
        txtDiasTrabajo = view.findViewById(R.id.txtDiasTrabajo);
        Button btnAgregarPago = view.findViewById(R.id.btnAgregarPago);

        cargarNombresDocentes();
        cargarNivelesEducacion();

        // Funcionalidad del botón Volver Atras
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        edtFechaPago.setOnClickListener(v -> mostrarDatePickerDialog());

        btnAgregarPago.setOnClickListener(v -> agregarPago());

        // Agregar TextWatcher al campo de texto de monto para mostrar el prefijo "S/."
        txtMonto.addTextChangedListener(new TextWatcher() {
            private boolean isEditing = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEditing) return;

                isEditing = true;
                String originalString = s.toString();

                if (!originalString.startsWith("S/.")) {
                    txtMonto.setText("S/." + originalString);
                    txtMonto.setSelection(txtMonto.getText().length());
                }

                isEditing = false;
            }
        });

        return view;
    }

    private void cargarNombresDocentes() {
        docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1) {
                List<String> nombresDocentes = new ArrayList<>();
                for (TeacherDTO teacher : response.getBody()) {
                    nombresDocentes.add(teacher.getFullName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nombresDocentes);
                dropdownDocente.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Error al cargar los nombres de los docentes", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error al cargar los nombres de los docentes: " + (response != null ? response.getMessage() : "Respuesta nula"));
            }
        });
    }

    private void cargarNivelesEducacion() {
        List<String> nivelesEducacion = new ArrayList<>();
        nivelesEducacion.add("Licenciatura");
        nivelesEducacion.add("Maestría");
        nivelesEducacion.add("Doctorado");
        nivelesEducacion.add("Postdoctorado");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nivelesEducacion);
        dropdownNivelEducacion.setAdapter(adapter);
    }

    private void mostrarDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            edtFechaPago.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void agregarPago() {
        TeacherPayment pago = new TeacherPayment();
        pago.setAmount(BigDecimal.valueOf(Double.parseDouble(txtMonto.getText().toString().replace("S/.", "").trim())));
        pago.setPaymentDate(edtFechaPago.getText().toString());
        pago.setPaymentReference(txtReferenciaPago.getText().toString());
        pago.setWorkDays(Integer.parseInt(txtDiasTrabajo.getText().toString()));
        pago.setEducationLevel(dropdownNivelEducacion.getText().toString());

        // Obtener el ID del docente seleccionado
        String nombreDocente = dropdownDocente.getText().toString();
        docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1) {
                for (TeacherDTO teacher : response.getBody()) {
                    if (teacher.getFullName().equals(nombreDocente)) {
                        pago.setTeacher(new Teacher(teacher.getId()));
                        break;
                    }
                }

                pagoViewModel.agregarPago(pago).observe(getViewLifecycleOwner(), agregarResponse -> {
                    if (agregarResponse != null && agregarResponse.getRpta() == 1) {
                        Toast.makeText(getContext(), "Pago agregado con éxito", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Pago agregado con éxito");
                        getParentFragmentManager().popBackStack(); // Cerrar la ventana después de agregar el pago con éxito
                    } else {
                        Toast.makeText(getContext(), "Error al agregar pago: " + (agregarResponse != null ? agregarResponse.getMessage() : "Error desconocido"), Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Error al agregar pago: " + (agregarResponse != null ? agregarResponse.getMessage() : "Error desconocido"));
                    }
                });
            } else {
                Toast.makeText(getContext(), "Error al obtener los datos del docente", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error al obtener los datos del docente: " + (response != null ? response.getMessage() : "Respuesta nula"));
            }
        });
    }
}
