package Buen.Pastor.app.Activity.ui.docente;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;

import Buen.P.App.R;
import Buen.Pastor.app.entity.service.App.TeacherDTO;
import Buen.Pastor.app.viewModel.DocenteViewModel;

public class DetalleDocenteFragment extends Fragment {

    private DocenteViewModel docenteViewModel;
    private TextView txtFullName, txtPosition, txtDNI, txtEmail, txtPhone, txtAddress, txtHiringDate, txtActive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_docente, container, false);
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });

        txtFullName = view.findViewById(R.id.txtFullName);
        txtPosition = view.findViewById(R.id.txtPosition);
        txtDNI = view.findViewById(R.id.txtDNI);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPhone = view.findViewById(R.id.txtPhone);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtHiringDate = view.findViewById(R.id.txtHiringDate);
        txtActive = view.findViewById(R.id.txtActive);

        if (getArguments() != null && getArguments().containsKey("docenteId")) {
            int docenteId = getArguments().getInt("docenteId");
            cargarDetalleDocente(docenteId);
        }

        return view;
    }

    private void cargarDetalleDocente(int docenteId) {
        docenteViewModel.obtenerDocentePorId(docenteId).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1) {
                mostrarDetalleDocente(response.getBody());
            } else {
                mostrarError("Error al cargar los detalles del docente");
            }
        });
    }

    private void mostrarDetalleDocente(TeacherDTO docente) {
        txtFullName.setText(docente.getFullName() != null ? docente.getFullName() : "-");
        txtPosition.setText(docente.getPosition() != null ? docente.getPosition() : "-");
        txtDNI.setText(docente.getDni() != null ? docente.getDni() : "-");
        txtEmail.setText(docente.getEmail() != null ? docente.getEmail() : "-");
        txtPhone.setText(docente.getPhone() != null ? docente.getPhone() : "-");
        txtAddress.setText(docente.getAddress() != null ? docente.getAddress() : "-");
        txtHiringDate.setText(docente.getHiringDate() != null ? docente.getHiringDate() : "-");
        txtActive.setText(docente.isActive() ? "Activo" : "Inactivo");
    }

    private void mostrarError(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}
