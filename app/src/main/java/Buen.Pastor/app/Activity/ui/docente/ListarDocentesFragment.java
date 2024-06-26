package Buen.Pastor.app.Activity.ui.docente;

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
import Buen.Pastor.app.Activity.ui.docente.modificardocente.ModificarDocenteFragment;
import Buen.Pastor.app.adapter.DocenteAdapter;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.viewModel.UsuarioViewModel;

import java.util.ArrayList;

public class ListarDocentesFragment extends Fragment implements DocenteAdapter.OnItemClickListener {

    private DocenteViewModel docenteViewModel;
    private RecyclerView recyclerViewDocentes;
    private DocenteAdapter docenteAdapter;
    private ProgressBar progressBar;

    private UsuarioViewModel usuarioViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listar_docentes, container, false);

        recyclerViewDocentes = view.findViewById(R.id.recyclerViewDocentes);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerViewDocentes.setLayoutManager(new LinearLayoutManager(getContext()));

        docenteAdapter = new DocenteAdapter(new ArrayList<>(), this);
        recyclerViewDocentes.setAdapter(docenteAdapter);

        progressBar.setVisibility(View.VISIBLE);
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), response -> {
            progressBar.setVisibility(View.GONE);
            if (response != null && response.getRpta() == 1 && response.getBody() != null) {
                docenteAdapter.updateData(response.getBody());
            } else {
                String errorMessage = response != null ? response.getMessage() : "Unknown error";
                Toast.makeText(getContext(), "Error al cargar docentes: " + errorMessage, Toast.LENGTH_LONG).show();
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
    public void onEditClick(int docenteId) {
        // Lógica para editar un docente
        ModificarDocenteFragment modificarDocenteFragment = new ModificarDocenteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("docenteId", docenteId);
        modificarDocenteFragment.setArguments(bundle);

        if (getParentFragmentManager() != null) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, modificarDocenteFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onViewClick(int docenteId) {
        DetalleDocenteFragment detalleDocenteFragment = new DetalleDocenteFragment();
        Bundle args = new Bundle();
        args.putInt("docenteId", docenteId);
        detalleDocenteFragment.setArguments(args);
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detalleDocenteFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getContext(), "Error: FragmentManager is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(int docenteId) {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar Docente")
                .setMessage("¿Estás seguro de querer eliminar este docente?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    usuarioViewModel.obtenerUsuarioPorDocenteId(docenteId).observe(getViewLifecycleOwner(), userResponse -> {
                        if (userResponse != null && userResponse.getRpta() == 1 && userResponse.getBody() != null) {
                            int userId = userResponse.getBody().getId();
                            usuarioViewModel.eliminarUsuario(userId).observe(getViewLifecycleOwner(), response -> {
                                if (response != null && response.getRpta() == 1) {
                                    docenteViewModel.eliminarDocente(docenteId).observe(getViewLifecycleOwner(), docenteResponse -> {
                                        if (docenteResponse != null && docenteResponse.getRpta() == 1) {
                                            Toast.makeText(getContext(), "Docente eliminado correctamente", Toast.LENGTH_SHORT).show();
                                            docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), listarResponse -> {
                                                if (listarResponse != null && listarResponse.getRpta() == 1) {
                                                    docenteAdapter.updateData(listarResponse.getBody());
                                                }
                                            });
                                        } else {
                                            String errorMessage = docenteResponse != null ? docenteResponse.getMessage() : "Error desconocido al eliminar docente";
                                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                                            Log.e("EliminarDocente", errorMessage);
                                        }
                                    });
                                } else {
                                    String errorMessage = response != null ? response.getMessage() : "Error desconocido al eliminar usuario";
                                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                                    Log.e("EliminarUsuario", errorMessage);
                                }
                            });
                        } else {
                            String errorMessage = userResponse != null ? userResponse.getMessage() : "Error desconocido al obtener usuario";
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                            Log.e("ObtenerUsuario", errorMessage);
                        }
                    });
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
