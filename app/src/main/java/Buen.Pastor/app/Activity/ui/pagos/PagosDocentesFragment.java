package Buen.Pastor.app.Activity.ui.pagos;

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
import Buen.Pastor.app.adapter.PagosDocentesAdapter;
import Buen.Pastor.app.viewModel.PagoViewModel;

public class PagosDocentesFragment extends Fragment {

    private static final String ARG_USER_ID = "userId";
    private static final String ARG_TEACHER_ID = "teacherId";
    private PagoViewModel viewModel;
    private RecyclerView recyclerView;
    private PagosDocentesAdapter adapter;
    private TextView textNoPagos;

    public static PagosDocentesFragment newInstance(int userId, int teacherId) {
        PagosDocentesFragment fragment = new PagosDocentesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        args.putInt(ARG_TEACHER_ID, teacherId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PagoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pagos_docentes, container, false);
        setupRecyclerView(view);

        if (getArguments() != null) {
            int teacherId = getArguments().getInt(ARG_TEACHER_ID);
            viewModel.listarPagosPorDocenteId(teacherId).observe(getViewLifecycleOwner(), response -> {
                if (response != null && response.getRpta() == 1) {
                    if (response.getBody().isEmpty()) {
                        textNoPagos.setVisibility(View.VISIBLE);
                    } else {
                        textNoPagos.setVisibility(View.GONE);
                        adapter.updateData(response.getBody());
                    }
                } else {
                    Toast.makeText(getContext(), "Error al cargar los pagos", Toast.LENGTH_LONG).show();
                }
            });
        }
        return view;
    }

    private void setupRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_pagos_docentes);
        textNoPagos = view.findViewById(R.id.text_no_pagos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PagosDocentesAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
    }
}
