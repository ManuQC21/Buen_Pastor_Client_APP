package Buen.Pastor.app.Activity.ui.pagos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import Buen.P.App.R;
import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.TeacherPaymentDTO;
import Buen.Pastor.app.viewModel.PagoViewModel;

public class DetallePagoFragment extends Fragment {

    private static final String TAG = "DetallePagoFragment";
    private PagoViewModel pagoViewModel;
    private TextView txtNombreDocente, txtMonto, txtFechaPago, txtEstadoPago, txtReferencia, txtDiasTrabajo, txtNivelEducacion, txtCodigoModular;
    private ImageView btnVolverAtras;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagoViewModel = new ViewModelProvider(this).get(PagoViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalles_pago, container, false);

        txtNombreDocente = view.findViewById(R.id.txtNombreDocente);
        txtMonto = view.findViewById(R.id.txtMonto);
        txtFechaPago = view.findViewById(R.id.txtFechaPago);
        txtEstadoPago = view.findViewById(R.id.txtEstadoPago);
        txtReferencia = view.findViewById(R.id.txtReferenciaPago);
        txtDiasTrabajo = view.findViewById(R.id.txtDiasTrabajo);
        txtNivelEducacion = view.findViewById(R.id.txtNivelEducacion);
        txtCodigoModular = view.findViewById(R.id.txtCodigoModular);
        btnVolverAtras = view.findViewById(R.id.btnVolverAtras);

        // Obtener el ID del pago de los argumentos
        int pagoId = getArguments() != null ? getArguments().getInt("pagoId") : 0;

        btnVolverAtras.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        if (pagoId > 0) {
            cargarPago(pagoId);
        } else {
            Toast.makeText(getContext(), "ID de pago no proporcionado", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void cargarPago(int pagoId) {
        pagoViewModel.obtenerPagoPorId(pagoId).observe(getViewLifecycleOwner(), new Observer<BestGenericResponse<TeacherPaymentDTO>>() {
            @Override
            public void onChanged(BestGenericResponse<TeacherPaymentDTO> response) {
                if (response != null && response.getRpta() == 1 && response.getBody() != null) {
                    TeacherPaymentDTO pago = response.getBody();
                    txtNombreDocente.setText(pago.getTeacherName());
                    txtMonto.setText(String.format("S/. %.2f", pago.getAmount()));
                    txtFechaPago.setText(pago.getPaymentDate());
                    txtEstadoPago.setText(pago.getPaymentStatus());
                    txtReferencia.setText(pago.getPaymentReference());
                    txtDiasTrabajo.setText(String.valueOf(pago.getWorkDays()));
                    txtNivelEducacion.setText(pago.getEducationLevel());
                    txtCodigoModular.setText(pago.getModularCode());
                } else {
                    String errorMessage = response != null ? response.getMessage() : "Error desconocido";
                    Toast.makeText(getContext(), "Error al cargar el pago: " + errorMessage, Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Error al cargar el pago: " + errorMessage);
                }
            }
        });
    }
}
