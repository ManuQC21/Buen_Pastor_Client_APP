package Buen.Pastor.app.Activity.ui.docente.modificardocente;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import Buen.Pastor.app.Activity.InicioAdministrativoActivity;
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.App.TeacherDTO;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.utils.DatePickerHelper;
import Buen.Pastor.app.viewModel.DocenteViewModel;

public class ModificarDocenteFragment extends Fragment {
    private static final String TAG = "ModificarDocenteFragment";

    private DocenteViewModel docenteViewModel;

    private TextInputEditText txtNombreCompleto, txtPosicion, txtDNI, txtEmail, txtTelefono, txtDireccion, txtFechaContratacion;
    private MaterialCheckBox checkboxActivo;
    private Button btnModificarDocente;
    private int docenteId;

    private final Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            docenteId = getArguments().getInt("docenteId", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modificar_docente, container, false);
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);

        txtNombreCompleto = view.findViewById(R.id.txtNombreCompletoModificar);
        txtPosicion = view.findViewById(R.id.txtPosicionModificar);
        txtDNI = view.findViewById(R.id.txtDNIModificar);
        txtEmail = view.findViewById(R.id.txtEmailModificar);
        txtTelefono = view.findViewById(R.id.txtTelefonoModificar);
        txtDireccion = view.findViewById(R.id.txtDireccionModificar);
        txtFechaContratacion = view.findViewById(R.id.txtFechaContratacionModificar);
        checkboxActivo = view.findViewById(R.id.checkboxActivoModificar);
        btnModificarDocente = view.findViewById(R.id.btnModificarDocente);

        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtrasModificarDocente);
        btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        txtFechaContratacion.setOnClickListener(v -> DatePickerHelper.mostrarDatePickerDialog(getContext(), txtFechaContratacion, calendar));
        btnModificarDocente.setOnClickListener(v -> modificarDocente());

        setupInputFilters();
        cargarDatosDocente();

        return view;
    }

    private void setupInputFilters() {
        InputFilter letraEspacioFilter = new InputFilter() {
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

        txtNombreCompleto.setFilters(new InputFilter[]{letraEspacioFilter, new InputFilter.LengthFilter(50)});
        txtPosicion.setFilters(new InputFilter[]{letraEspacioFilter, new InputFilter.LengthFilter(50)});
        txtDNI.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        txtTelefono.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(9),
                (source, start, end, dest, dstart, dend) -> {
                    if (dstart == 0 && source.length() > 0 && source.charAt(0) != '9') {
                        return "";
                    }
                    for (int i = start; i < end; i++) {
                        if (!Character.isDigit(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
                }
        });
        txtDireccion.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                                return "";
                            }
                        }
                        return null;
                    }
                }, new InputFilter.LengthFilter(100)
        });
    }


    private void cargarDatosDocente() {
        if (docenteId != -1) {
            docenteViewModel.obtenerDocentePorId(docenteId).observe(getViewLifecycleOwner(), response -> {
                if (response != null && response.getRpta() == 1 && response.getBody() != null) {
                    TeacherDTO docente = response.getBody();
                    txtNombreCompleto.setText(docente.getFullName());
                    txtPosicion.setText(docente.getPosition());
                    txtDNI.setText(docente.getDni());
                    txtEmail.setText(docente.getEmail());
                    txtTelefono.setText(docente.getPhone());
                    txtDireccion.setText(docente.getAddress());
                    if (docente.getHiringDate() != null && !docente.getHiringDate().isEmpty()) {
                        try {
                            SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                            Date fecha = sdfInput.parse(docente.getHiringDate());
                            txtFechaContratacion.setText(sdfOutput.format(fecha));
                        } catch (ParseException e) {
                            Log.e(TAG, "Error al parsear la fecha: " + e.getMessage());
                            Toast.makeText(getContext(), "Error al parsear la fecha", Toast.LENGTH_SHORT).show();
                        }
                    }
                    checkboxActivo.setChecked(docente.isActive());
                } else {
                    Log.e(TAG, "Error al cargar datos del docente: " + (response != null ? response.getMessage() : "Respuesta nula"));
                    Toast.makeText(getContext(), "Error al cargar datos del docente", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void modificarDocente() {
        String nombreCompleto = txtNombreCompleto.getText().toString();
        String posicion = txtPosicion.getText().toString();
        String dni = txtDNI.getText().toString();
        String email = txtEmail.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String direccion = txtDireccion.getText().toString();
        String fechaContratacion = txtFechaContratacion.getText().toString();

        if (!ModificarDocenteValidationHelper.validarEntradas(getContext(), txtNombreCompleto, txtPosicion, txtDNI, txtEmail, txtTelefono, txtDireccion, txtFechaContratacion)) {
            return;
        }

        Teacher docente = new Teacher();
        docente.setId(docenteId);
        docente.setFullName(nombreCompleto);
        docente.setPosition(posicion);
        docente.setDni(dni);
        docente.setEmail(email);
        docente.setPhone(telefono);
        docente.setAddress(direccion);
        docente.setActive(checkboxActivo.isChecked());
        docente.setHiringDate(fechaContratacion);

        docenteViewModel.editarDocente(docenteId, docente).observe(getViewLifecycleOwner(), updateResponse -> {
            if (updateResponse != null && updateResponse.getRpta() == 1) {
                Log.i(TAG, "Docente modificado con éxito");
                Toast.makeText(getContext(), "Docente modificado con éxito", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                Intent intent = new Intent(getContext(), InicioAdministrativoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            } else {
                Log.e(TAG, "Error al modificar docente: " + (updateResponse != null ? updateResponse.getMessage() : "Error desconocido"));
                Toast.makeText(getContext(), "Error al modificar docente", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiarCampos() {
        txtNombreCompleto.setText("");
        txtPosicion.setText("");
        txtDNI.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtFechaContratacion.setText("");
        checkboxActivo.setChecked(false);
    }
}
