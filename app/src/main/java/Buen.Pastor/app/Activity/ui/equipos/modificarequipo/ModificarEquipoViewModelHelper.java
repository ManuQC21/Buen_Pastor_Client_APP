package Buen.Pastor.app.Activity.ui.equipos.modificarequipo;

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

public class ModificarEquipoViewModelHelper {

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
                Log.e("ModificarFragment", "Error al cargar responsables: " + response.getMessage());
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

    public static void cargarDatosEquipo(int equipoId, EquipoViewModel equipoViewModel, LifecycleOwner lifecycleOwner,
                                         TextInputEditText txtTipoEquipo, TextInputEditText txtDescripcion, TextInputEditText txtMarca,
                                         TextInputEditText txtModelo, TextInputEditText txtNombreDeEquipo, TextInputEditText txtNumeroDeOrden,
                                         TextInputEditText txtNumeroDeSerie, TextInputEditText edtFechaCompra, AutoCompleteTextView dropdownEstado,
                                         AutoCompleteTextView dropdownResponsable, AutoCompleteTextView dropdownUbicacion) {
        if (equipoId != -1) {
            equipoViewModel.getEquipoById(equipoId).observe(lifecycleOwner, response -> {
                if (response != null && response.getRpta() == 1 && response.getBody() != null) {
                    Equipment equipo = response.getBody();
                    txtTipoEquipo.setText(equipo.getEquipmentType());
                    txtDescripcion.setText(equipo.getDescription());
                    txtMarca.setText(equipo.getBrand());
                    txtModelo.setText(equipo.getModel());
                    txtNombreDeEquipo.setText(equipo.getEquipmentName());
                    txtNumeroDeOrden.setText(equipo.getOrderNumber());
                    txtNumeroDeSerie.setText(equipo.getSerial());
                    edtFechaCompra.setText(equipo.getPurchaseDate());
                    dropdownEstado.setText(equipo.getStatus(), false);
                    if (equipo.getResponsible() != null) {
                        dropdownResponsable.setText(equipo.getResponsible().getFullName(), false);
                    }
                    if (equipo.getLocation() != null) {
                        dropdownUbicacion.setText(equipo.getLocation().getRoom(), false);
                    }
                } else {
                    Toast.makeText(((Context) lifecycleOwner).getApplicationContext(), "Error al cargar datos del equipo", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void modificarEquipo(Context context, int equipoId, EquipoViewModel equipoViewModel,
                                       DocenteViewModel docenteViewModel, UbicacionViewModel ubicacionViewModel,
                                       TextInputEditText txtTipoEquipo, TextInputEditText txtDescripcion, TextInputEditText txtMarca,
                                       TextInputEditText txtModelo, TextInputEditText txtNombreDeEquipo, TextInputEditText txtNumeroDeOrden,
                                       TextInputEditText txtNumeroDeSerie, TextInputEditText edtFechaCompra, AutoCompleteTextView dropdownEstado,
                                       AutoCompleteTextView dropdownResponsable, AutoCompleteTextView dropdownUbicacion) {
        if (!ModificarEquipoValidationHelper.validarEntradas(context, txtTipoEquipo, txtDescripcion, txtMarca, txtModelo,
                txtNombreDeEquipo, txtNumeroDeOrden, txtNumeroDeSerie, edtFechaCompra)) {
            return;
        }

        Equipment equipo = new Equipment();
        equipo.setId(equipoId);
        equipo.setEquipmentType(txtTipoEquipo.getText().toString());
        equipo.setDescription(txtDescripcion.getText().toString());
        equipo.setStatus(dropdownEstado.getText().toString());
        equipo.setPurchaseDate(edtFechaCompra.getText().toString());
        equipo.setBrand(txtMarca.getText().toString());
        equipo.setModel(txtModelo.getText().toString());
        equipo.setEquipmentName(txtNombreDeEquipo.getText().toString());
        equipo.setOrderNumber(txtNumeroDeOrden.getText().toString());
        equipo.setSerial(txtNumeroDeSerie.getText().toString());

        String nombreResponsable = dropdownResponsable.getText().toString();
        String ambienteUbicacion = dropdownUbicacion.getText().toString();

        establecerResponsableYUbicacion(context, equipo, docenteViewModel, ubicacionViewModel, nombreResponsable, ambienteUbicacion, (LifecycleOwner) context, equipoViewModel,
                txtTipoEquipo, txtDescripcion, txtMarca, txtModelo, txtNombreDeEquipo, txtNumeroDeOrden, txtNumeroDeSerie, edtFechaCompra, dropdownEstado, dropdownResponsable, dropdownUbicacion);
    }

    private static void establecerResponsableYUbicacion(Context context, Equipment equipo, DocenteViewModel docenteViewModel, UbicacionViewModel ubicacionViewModel, String nombreResponsable, String ambienteUbicacion, LifecycleOwner lifecycleOwner, EquipoViewModel equipoViewModel,
                                                        TextInputEditText txtTipoEquipo, TextInputEditText txtDescripcion, TextInputEditText txtMarca,
                                                        TextInputEditText txtModelo, TextInputEditText txtNombreDeEquipo, TextInputEditText txtNumeroDeOrden,
                                                        TextInputEditText txtNumeroDeSerie, TextInputEditText edtFechaCompra, AutoCompleteTextView dropdownEstado,
                                                        AutoCompleteTextView dropdownResponsable, AutoCompleteTextView dropdownUbicacion) {
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
                        equipoViewModel.updateEquipo(equipo).observe(lifecycleOwner, equipoResponse -> {
                            if (equipoResponse.getRpta() == 1) {
                                Toast.makeText(context, "Equipo modificado con éxito", Toast.LENGTH_SHORT).show();
                                limpiarCampos(txtTipoEquipo, txtDescripcion, txtMarca, txtModelo, txtNombreDeEquipo,
                                        txtNumeroDeOrden, txtNumeroDeSerie, edtFechaCompra, dropdownEstado, dropdownResponsable, dropdownUbicacion);
                                context.startActivity(new Intent(context, InicioAdministrativoActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                Toast.makeText(context, "Error al modificar equipo: " + equipoResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            } else {
                Log.e("ModificarFragment", "Error al cargar docentes: " + response.getMessage());
                Toast.makeText(context, "Error al cargar docentes: " + response.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private static void limpiarCampos(TextInputEditText txtTipoEquipo, TextInputEditText txtDescripcion, TextInputEditText txtMarca,
                                      TextInputEditText txtModelo, TextInputEditText txtNombreDeEquipo, TextInputEditText txtNumeroDeOrden,
                                      TextInputEditText txtNumeroDeSerie, TextInputEditText edtFechaCompra, AutoCompleteTextView dropdownEstado,
                                      AutoCompleteTextView dropdownResponsable, AutoCompleteTextView dropdownUbicacion) {
        txtTipoEquipo.setText("");
        txtDescripcion.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtNombreDeEquipo.setText("");
        txtNumeroDeOrden.setText("");
        txtNumeroDeSerie.setText("");
        edtFechaCompra.setText("");
        dropdownEstado.setText("");
        dropdownResponsable.setText("");
        dropdownUbicacion.setText("");
    }
}
