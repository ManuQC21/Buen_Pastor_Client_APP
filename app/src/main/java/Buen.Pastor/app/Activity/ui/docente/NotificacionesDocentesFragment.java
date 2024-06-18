package Buen.Pastor.app.Activity.ui.docente;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import Buen.P.App.R;
import Buen.Pastor.app.adapter.NotificacionesAdapter;
import Buen.Pastor.app.viewModel.NotificacionesViewModel;

public class NotificacionesDocentesFragment extends Fragment {

    private static final String ARG_USER_ID = "userId";
    private static final String ARG_TEACHER_ID = "teacherId";
    private NotificacionesViewModel viewModel;
    private RecyclerView recyclerView;
    private TextView noNotificacionesView;
    private NotificacionesAdapter adapter;

    public static NotificacionesDocentesFragment newInstance(int userId, int teacherId) {
        NotificacionesDocentesFragment fragment = new NotificacionesDocentesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putInt(ARG_TEACHER_ID, teacherId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificacionesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notificaciones_docentes, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_notificaciones);
        noNotificacionesView = view.findViewById(R.id.no_notificaciones_text);
        setupRecyclerView();
        loadNotificaciones();
        return view;
    }

    private void loadNotificaciones() {
        if (getArguments() != null) {
            int teacherId = getArguments().getInt(ARG_TEACHER_ID);
            viewModel.listarNotificacionesPorDocente(teacherId).observe(getViewLifecycleOwner(), response -> {
                if (response != null && response.getRpta() == 1 && !response.getBody().isEmpty()) {
                    adapter.updateData(response.getBody());
                    noNotificacionesView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    noNotificacionesView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            });
        }
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotificacionesAdapter(new ArrayList<>(), (notification, paymentId) -> {
            int teacherId = notification.getTeacher().getId();
            int notificationId = notification.getId();
            viewModel.aceptarNotificacionPago(teacherId, paymentId, notificationId).observe(getViewLifecycleOwner(), response -> {
                if (response != null && response.getRpta() == 1) {
                    Toast.makeText(getContext(), "Notificación aceptada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al aceptar la notificación: " + response.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
        recyclerView.setAdapter(adapter);
    }
}
