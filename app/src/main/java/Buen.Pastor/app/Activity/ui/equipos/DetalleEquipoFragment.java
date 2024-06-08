package Buen.Pastor.app.Activity.ui.equipos;

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
import Buen.Pastor.app.viewModel.EquipoViewModel;
import Buen.Pastor.app.entity.service.Equipment;

public class DetalleEquipoFragment extends Fragment {

    private EquipoViewModel equipoViewModel;
    private TextView txtTipoEquipo, txtCodigoBarra, txtNombreEquipo, txtMarca, txtModelo, txtSerie,
            txtNumeroOrden, txtDescripcion, txtEstado, txtCodigoPatrimonial, txtFechaCompra,
            txtNombreResponsable, txtCargoResponsable, txtAmbiente, txtUbicacionFisica;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        equipoViewModel = new ViewModelProvider(this).get(EquipoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_equipo, container, false);
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });
        txtTipoEquipo = view.findViewById(R.id.txtTipoEquipo);
        txtCodigoBarra = view.findViewById(R.id.txtCodigoBarra);
        txtNombreEquipo = view.findViewById(R.id.txtNombreEquipo);
        txtMarca = view.findViewById(R.id.txtMarca);
        txtModelo = view.findViewById(R.id.txtModelo);
        txtSerie = view.findViewById(R.id.txtSerie);
        txtNumeroOrden = view.findViewById(R.id.txtNumeroOrden);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtEstado = view.findViewById(R.id.txtEstado);
        txtCodigoPatrimonial = view.findViewById(R.id.txtCodigoPatrimonial);
        txtFechaCompra = view.findViewById(R.id.txtFechaCompra);
        txtNombreResponsable = view.findViewById(R.id.txtNombreResponsable);
        txtCargoResponsable = view.findViewById(R.id.txtCargoResponsable);
        txtAmbiente = view.findViewById(R.id.txtAmbiente);
        txtUbicacionFisica = view.findViewById(R.id.txtUbicacionFisica);

        if (getArguments() != null && getArguments().containsKey("equipoId")) {
            int equipoId = getArguments().getInt("equipoId");
            cargarDetalleEquipo(equipoId);
        }

        return view;
    }

    private void cargarDetalleEquipo(int equipoId) {
        equipoViewModel.getEquipoById(equipoId).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1) {
                mostrarDetalleEquipo(response.getBody());
            } else {
                mostrarError("Error al cargar los detalles del equipo");
            }
        });
    }

    private void mostrarDetalleEquipo(Equipment equipo) {
        txtTipoEquipo.setText(equipo.getEquipmentType() != null ? equipo.getEquipmentType() : "-");
        txtCodigoBarra.setText(equipo.getBarcode() != null ? equipo.getBarcode() : "-");
        txtNombreEquipo.setText(equipo.getEquipmentName() != null ? equipo.getEquipmentName() : "-");
        txtMarca.setText(equipo.getBrand() != null ? equipo.getBrand() : "-");
        txtModelo.setText(equipo.getModel() != null ? equipo.getModel() : "-");
        txtSerie.setText(equipo.getSerial() != null ? equipo.getSerial() : "-");
        txtNumeroOrden.setText(equipo.getOrderNumber() != null ? equipo.getOrderNumber() : "-");
        txtDescripcion.setText(equipo.getDescription() != null ? equipo.getDescription() : "-");
        txtEstado.setText(equipo.getStatus() != null ? equipo.getStatus() : "-");
        txtCodigoPatrimonial.setText(equipo.getAssetCode() != null ? equipo.getAssetCode() : "-");
        txtFechaCompra.setText(equipo.getPurchaseDate() != null ? equipo.getPurchaseDate() : "-");

        if (equipo.getResponsible() != null) {
            txtNombreResponsable.setText(equipo.getResponsible().getFirstName() != null ? equipo.getResponsible().getFirstName() : "-");
            txtCargoResponsable.setText(equipo.getResponsible().getPosition() != null ? equipo.getResponsible().getPosition() : "-");
        } else {
            txtNombreResponsable.setText("-");
            txtCargoResponsable.setText("-");
        }

        if (equipo.getLocation() != null) {
            txtAmbiente.setText(equipo.getLocation().getRoom() != null ? equipo.getLocation().getRoom() : "-");
            txtUbicacionFisica.setText(equipo.getLocation().getPhysicalLocation() != null ? equipo.getLocation().getPhysicalLocation() : "-");
        } else {
            txtAmbiente.setText("-");
            txtUbicacionFisica.setText("-");
        }
    }


    private void mostrarError(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }
}
