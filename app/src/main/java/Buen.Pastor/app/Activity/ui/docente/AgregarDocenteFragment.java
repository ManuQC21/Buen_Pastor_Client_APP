package Buen.Pastor.app.Activity.ui.docente;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import Buen.P.App.R;
import Buen.P.App.databinding.FragmentAgregarDocenteBinding;
import Buen.Pastor.app.entity.service.Member;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.viewModel.UsuarioViewModel;

public class AgregarDocenteFragment extends Fragment {
    private FragmentAgregarDocenteBinding binding;
    private DocenteViewModel docenteViewModel;
    private UsuarioViewModel usuarioViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAgregarDocenteBinding.inflate(inflater, container, false);
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        setupUI();
        return binding.getRoot();
    }

    private void setupUI() {
        binding.btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        binding.btnAgregarDocente.setOnClickListener(v -> agregarDocente());

        View.OnClickListener dateClickListener = v -> mostrarDatePickerDialog();
        binding.txtFechaContratacionLayout.setEndIconOnClickListener(dateClickListener);
        binding.txtFechaContratacion.setOnClickListener(dateClickListener);

        // Filtros de validación
        binding.txtNombreCompleto.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        }});

        binding.txtPosicion.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        }});

        binding.txtDNI.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        binding.txtTelefono.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
        binding.txtDireccion.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        }});
    }

    private void mostrarDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            binding.txtFechaContratacion.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void agregarDocente() {
        String nombreCompleto = binding.txtNombreCompleto.getText().toString();
        String posicion = binding.txtPosicion.getText().toString();
        String dni = binding.txtDNI.getText().toString();
        String email = binding.txtEmail.getText().toString();
        String telefono = binding.txtTelefono.getText().toString();
        String direccion = binding.txtDireccion.getText().toString();
        String fechaContratacion = binding.txtFechaContratacion.getText().toString();
        boolean activo = binding.checkboxActivo.isChecked();

        if (!validarCampos(nombreCompleto, posicion, dni, email, telefono, direccion)) {
            return;
        }

        Teacher newTeacher = new Teacher(nombreCompleto, posicion, dni, email, telefono, direccion, fechaContratacion, activo);

        docenteViewModel.agregarDocente(newTeacher).observe(getViewLifecycleOwner(), response -> {
            if (response.getRpta() == 1 && response.getBody() != null) {
                Teacher registeredTeacher = response.getBody();
                Log.d("AgregarDocente", "Docente ID: " + registeredTeacher.getId());
                new Handler().postDelayed(() -> registrarUsuario(registeredTeacher.getId()), 500);
            } else {
                Log.e("AgregarDocente", "Error al agregar docente: " + response.getMessage());
                Toast.makeText(getContext(), "Error al agregar docente: " + response.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validarCampos(String nombreCompleto, String posicion, String dni, String email, String telefono, String direccion) {
        if (nombreCompleto.isEmpty() || posicion.isEmpty() || dni.isEmpty() || email.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(getContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!dni.matches("\\d{8}")) {
            Toast.makeText(getContext(), "El DNI debe tener 8 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Formato de correo inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!telefono.matches("\\d{9}")) {
            Toast.makeText(getContext(), "El teléfono debe tener 9 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void registrarUsuario(int teacherId) {
        String email = binding.txtEmail.getText().toString();
        String password = binding.txtContrasena.getText().toString(); // Asegúrate de que la ID en XML es correcta

        if (!email.isEmpty() && !password.isEmpty()) {
            Member newMember = new Member(email, password, true, teacherId);

            usuarioViewModel.register(newMember).observe(getViewLifecycleOwner(), userResponse -> {
                if (userResponse.getRpta() == 1) {
                    Toast.makeText(getContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                    if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                        getParentFragmentManager().popBackStack();
                    }
                } else {
                    Log.e("RegistrarUsuario", "Error al registrar usuario: " + userResponse.getMessage());
                    Toast.makeText(getContext(), "Error al registrar usuario: " + userResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Email o contraseña no pueden estar vacíos", Toast.LENGTH_LONG).show();
        }
    }
}
