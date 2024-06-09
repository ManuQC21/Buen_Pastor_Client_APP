package Buen.Pastor.app.Activity.ui.docente;

import android.os.Bundle;
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
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.viewModel.DocenteViewModel;

public class AgregarDocenteFragment extends Fragment {
    private FragmentAgregarDocenteBinding binding;
    private DocenteViewModel docenteViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAgregarDocenteBinding.inflate(inflater, container, false);
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);

        setupUI();
        return binding.getRoot();
    }

    private void setupUI() {
        binding.btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        binding.btnAgregarDocente.setOnClickListener(v -> agregarDocente());

        // Configurar el DatePickerDialog para abrirse al hacer clic en el campo de fecha de contratación o en el ícono
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
        Teacher newTeacher = new Teacher(
                binding.txtNombreCompleto.getText().toString(),
                binding.txtPosicion.getText().toString(),
                binding.txtDNI.getText().toString(),
                binding.txtEmail.getText().toString(),
                binding.txtTelefono.getText().toString(),
                binding.txtDireccion.getText().toString(),
                binding.txtFechaContratacion.getText().toString(),
                binding.checkboxActivo.isChecked()
        );

        docenteViewModel.agregarDocente(newTeacher).observe(getViewLifecycleOwner(), response -> {
            if (response.getRpta() == 1) {
                Toast.makeText(getContext(), "Docente agregado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();  // Llamar al método para limpiar los campos
                // Cerrar este fragmento y volver al anterior en el stack.
                if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                    getParentFragmentManager().popBackStack();
                } else {
                    // En caso de no tener back stack, manejar otro flujo si es necesario.
                }
            } else {
                Toast.makeText(getContext(), "Error al agregar docente: " + response.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiarCampos() {
        binding.txtNombreCompleto.setText("");
        binding.txtPosicion.setText("");
        binding.txtDNI.setText("");
        binding.txtEmail.setText("");
        binding.txtTelefono.setText("");
        binding.txtDireccion.setText("");
        binding.txtFechaContratacion.setText("");
        binding.checkboxActivo.setChecked(true);  // Resetear a un estado activo por defecto si es necesario
    }


}
