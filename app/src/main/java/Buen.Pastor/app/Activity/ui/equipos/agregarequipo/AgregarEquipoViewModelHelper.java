package Buen.Pastor.app.Activity.ui.equipos.agregarequipo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.lifecycle.LifecycleOwner;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;
import Buen.Pastor.app.Activity.InicioAdministrativoActivity;
import Buen.Pastor.app.entity.service.App.TeacherDTO;
import Buen.Pastor.app.entity.service.Equipment;
import Buen.Pastor.app.entity.service.Location;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.viewModel.EquipoViewModel;
import Buen.Pastor.app.viewModel.UbicacionViewModel;

public class AgregarEquipoViewModelHelper {

    public static void cargarResponsables(Context context, DocenteViewModel docenteViewModel, LifecycleOwner lifecycleOwner, AutoCompleteTextView dropdownResponsable) {
        docenteViewModel.listarDocentes().observe(lifecycleOwner, response -> {
            if (response.getRpta() == 1) {
                List<String> nombres = new ArrayList<>();
                for (TeacherDTO teacher : response.getBody()) {
                    nombres.add(teacher.getFullName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, nombres);
                dropdownResponsable.setAdapter(adapter);
            } else {
                Log.e("AgregarFragment", "Error al cargar responsables: " + response.getMessage());
            }
        });
    }

    public static void cargarUbicaciones(Context context, UbicacionViewModel ubicacionViewModel, LifecycleOwner lifecycleOwner, AutoCompleteTextView dropdownUbicacion) {
        ubicacionViewModel.listarUbicaciones().observe(lifecycleOwner, response -> {
            if (response.getRpta() == 1) {
                List<String> ubicaciones = new ArrayList<>();
                for (Location ubicacion : response.getBody()) {
                    ubicaciones.add(ubicacion.getRoom());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, ubicaciones);
                dropdownUbicacion.setAdapter(adapter);
            }
        });
    }

    public static void establecerResponsableYUbicacion(Context context, Equipment equipo, DocenteViewModel docenteViewModel, UbicacionViewModel ubicacionViewModel, String nombreResponsable, String ambienteUbicacion, LifecycleOwner lifecycleOwner, EquipoViewModel equipoViewModel) {
        docenteViewModel.listarDocentes().observe(lifecycleOwner, response -> {
            if (response.getRpta() == 1 && response.getBody() != null) {
                for (TeacherDTO teacher : response.getBody()) {
                    if (teacher.getFullName().equals(nombreResponsable)) {
                        equipo.setResponsible(new Teacher(teacher.getId()));  // Make sure Teacher has a constructor accepting id
                        break;
                    }
                }
                ubicacionViewModel.listarUbicaciones().observe(lifecycleOwner, ubicacionResponse -> {
                    if (ubicacionResponse.getRpta() == 1) {
                        for (Location ubicacion : ubicacionResponse.getBody()) {
                            if (ubicacion.getRoom().equals(ambienteUbicacion)) {
                                equipo.setLocation(ubicacion);
                                break;
                            }
                        }
                        equipoViewModel.addEquipo(equipo).observe(lifecycleOwner, equipoResponse -> {
                            if (equipoResponse.getRpta() == 1) {
                                Toast.makeText(context, "Equipo agregado con éxito", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                                Intent intent = new Intent(context, InicioAdministrativoActivity.class);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "Error al agregar equipo: " + equipoResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            } else {
                Log.e("AgregarFragment", "Error al cargar docentes: " + response.getMessage());
                Toast.makeText(context, "Error al cargar docentes: " + response.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private static void limpiarCampos(TextInputEditText... fields) {
        for (TextInputEditText field : fields) {
            field.setText("");
        }
    }
}
