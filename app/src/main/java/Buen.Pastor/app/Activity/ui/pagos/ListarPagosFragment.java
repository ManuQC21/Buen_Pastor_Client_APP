package Buen.Pastor.app.Activity.ui.pagos;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import Buen.P.App.R;
import Buen.Pastor.app.adapter.PagosAdapter;
import Buen.Pastor.app.viewModel.NotificacionesViewModel;
import Buen.Pastor.app.viewModel.PagoViewModel;
import Buen.Pastor.app.entity.service.App.TeacherPaymentDTO;
import java.util.ArrayList;
import java.util.List;

public class ListarPagosFragment extends Fragment implements PagosAdapter.OnItemClickListener {

    private PagoViewModel pagoViewModel;

    private NotificacionesViewModel notificacionesViewModel;
    private RecyclerView recyclerViewPagos;
    private PagosAdapter pagosAdapter;
    private ProgressBar progressBar;

    private static final String TAG = "ListarPagosFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagoViewModel = new ViewModelProvider(this).get(PagoViewModel.class);
        notificacionesViewModel = new ViewModelProvider(this).get(NotificacionesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_pagos, container, false);

        recyclerViewPagos = view.findViewById(R.id.recyclerViewPagos);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerViewPagos.setLayoutManager(new LinearLayoutManager(getContext()));

        pagosAdapter = new PagosAdapter(new ArrayList<>(), this);
        recyclerViewPagos.setAdapter(pagosAdapter);

        progressBar.setVisibility(View.VISIBLE);
        pagoViewModel.listarTodosLosPagos().observe(getViewLifecycleOwner(), response -> {
            progressBar.setVisibility(View.GONE);
            if (response != null && response.getRpta() == 1 && response.getBody() != null) {
                pagosAdapter.updateData(response.getBody());
            } else {
                String errorMessage = response != null ? response.getMessage() : "Unknown error";
                Toast.makeText(getContext(), "Error al cargar pagos: " + errorMessage, Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error al cargar pagos: " + errorMessage);
            }
        });

        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onNotificarClick(int pagoId) {
        Log.i(TAG, "Notificar pago ID: " + pagoId);
        notificacionesViewModel.enviarNotificacionPago(pagoId).observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1) {
                Toast.makeText(getContext(), "Notificación enviada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error al enviar la notificación", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error al enviar la notificación: " + (response != null ? response.getMessage() : "Respuesta nula"));
            }
        });
    }


    @Override
    public void onViewClick(int pagoId) {
        Log.i(TAG, "Ver detalles del pago ID: " + pagoId);
        DetallePagoFragment detallePagoFragment = new DetallePagoFragment();
        Bundle args = new Bundle();
        args.putInt("pagoId", pagoId);
        detallePagoFragment.setArguments(args);

        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detallePagoFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onEditClick(int pagoId) {
        Log.i(TAG, "Editar pago ID: " + pagoId);
        Toast.makeText(getContext(), "Editar ID: " + pagoId, Toast.LENGTH_SHORT).show();

        ModificarPagoFragment modificarPagoFragment = new ModificarPagoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pagoId", pagoId);
        modificarPagoFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, modificarPagoFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onDeleteClick(int pagoId) {
        Log.i(TAG, "Eliminar pago ID: " + pagoId);
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar Pago")
                .setMessage("¿Estás seguro de querer eliminar este pago?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    pagoViewModel.eliminarPago(pagoId).observe(getViewLifecycleOwner(), response -> {
                        if (response != null && response.getRpta() == 1) {
                            Toast.makeText(getContext(), "Pago eliminado correctamente", Toast.LENGTH_SHORT).show();
                            // Actualizar la lista de pagos después de la eliminación
                            pagoViewModel.listarTodosLosPagos().observe(getViewLifecycleOwner(), pagoResponse -> {
                                if (pagoResponse != null && pagoResponse.getRpta() == 1) {
                                    pagosAdapter.updateData(pagoResponse.getBody());
                                }
                            });
                        } else {
                            String errorMessage = response != null ? response.getMessage() : "Error desconocido";
                            Toast.makeText(getContext(), "Error al eliminar pago: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
