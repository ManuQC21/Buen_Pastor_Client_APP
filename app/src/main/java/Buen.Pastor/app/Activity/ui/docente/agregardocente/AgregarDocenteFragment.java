package Buen.Pastor.app.Activity.ui.docente.agregardocente;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.Calendar;
import Buen.P.App.databinding.FragmentAgregarDocenteBinding;
import Buen.Pastor.app.entity.service.Member;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.utils.DatePickerHelper;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.viewModel.UsuarioViewModel;

public class AgregarDocenteFragment extends Fragment {
    private FragmentAgregarDocenteBinding binding;
    private DocenteViewModel docenteViewModel;
    private UsuarioViewModel usuarioViewModel;
    private final Calendar calendar = Calendar.getInstance();

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

        View.OnClickListener dateClickListener = v -> DatePickerHelper.mostrarDatePickerDialog(getContext(), binding.txtFechaContratacion, calendar);
        binding.txtFechaContratacionLayout.setEndIconOnClickListener(dateClickListener);
        binding.txtFechaContratacion.setOnClickListener(dateClickListener);

        // Filtros de validación
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

        binding.txtNombreCompleto.setFilters(new InputFilter[]{letraEspacioFilter, new InputFilter.LengthFilter(50)});
        binding.txtPosicion.setFilters(new InputFilter[]{letraEspacioFilter, new InputFilter.LengthFilter(50)});
        binding.txtDNI.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        binding.txtTelefono.setFilters(new InputFilter[]{
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
        binding.txtDireccion.setFilters(new InputFilter[]{
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


    private void agregarDocente() {
        String nombreCompleto = binding.txtNombreCompleto.getText().toString();
        String posicion = binding.txtPosicion.getText().toString();
        String dni = binding.txtDNI.getText().toString();
        String email = binding.txtEmail.getText().toString();
        String telefono = binding.txtTelefono.getText().toString();
        String direccion = binding.txtDireccion.getText().toString();
        String fechaContratacion = binding.txtFechaContratacion.getText().toString();
        boolean activo = binding.checkboxActivo.isChecked();

        if (!AgregarDocenteValidationHelper.validarEntradas(getContext(), binding.txtNombreCompleto, binding.txtPosicion, binding.txtDNI, binding.txtEmail, binding.txtTelefono, binding.txtDireccion, binding.txtFechaContratacion, binding.txtContrasena)) {
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
