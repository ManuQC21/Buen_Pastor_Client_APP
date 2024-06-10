package Buen.Pastor.app.Activity.ui.docente;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
    }

    private void mostrarDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        new android.app.DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            binding.txtFechaContratacion.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void agregarDocente() {
        String email = binding.txtEmail.getText().toString();
        if ("admin@gmail.com".equals(email)) {
            Toast.makeText(getContext(), "El uso de este email está restringido.", Toast.LENGTH_LONG).show();
            return;  // Salir del método si se intenta usar el email restringido
        }

        Teacher newTeacher = new Teacher(
                binding.txtNombreCompleto.getText().toString(),
                binding.txtPosicion.getText().toString(),
                binding.txtDNI.getText().toString(),
                email,
                binding.txtTelefono.getText().toString(),
                binding.txtDireccion.getText().toString(),
                binding.txtFechaContratacion.getText().toString(),
                binding.checkboxActivo.isChecked()
        );

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
            Member newMember = new Member(
                    email,
                    password,
                    true,
                    teacherId
            );

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


    private void limpiarCampos() {
        binding.txtNombreCompleto.setText("");
        binding.txtPosicion.setText("");
        binding.txtDNI.setText("");
        binding.txtEmail.setText("");
        binding.txtTelefono.setText("");
        binding.txtDireccion.setText("");
        binding.txtFechaContratacion.setText("");
        binding.checkboxActivo.setChecked(true);
    }
}
