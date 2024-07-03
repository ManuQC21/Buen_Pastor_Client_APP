package Buen.Pastor.app.Activity.ui.pagos.agregarpago;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.Teacher;
import Buen.Pastor.app.entity.service.TeacherPayment;
import Buen.Pastor.app.utils.DatePickerHelper;
import Buen.Pastor.app.viewModel.DocenteViewModel;
import Buen.Pastor.app.viewModel.PagoViewModel;
import Buen.Pastor.app.viewModel.FotoViewModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import Buen.Pastor.app.entity.service.App.TeacherDTO;
import Buen.Pastor.app.entity.service.Foto;

public class AgregarPagoFragment extends Fragment {

    private static final String TAG = "AgregarPagoFragment";
    private DocenteViewModel docenteViewModel;
    private PagoViewModel pagoViewModel;
    private FotoViewModel fotoViewModel;
    private AutoCompleteTextView dropdownDocente;
    private AutoCompleteTextView dropdownNivelEducacion;
    private TextInputEditText txtMonto, edtFechaPago, txtReferenciaPago, txtDiasTrabajo;
    private final Calendar calendar = Calendar.getInstance();
    private ImageView imageView;
    private Button btnSeleccionarImagen;
    private Uri imagenUri;
    private Long fotoId;
    private ActivityResultLauncher<Intent> seleccionarImagenLauncher;
    private ActivityResultLauncher<Intent> tomarFotoLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        seleccionarImagenLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            imageView.setImageURI(selectedImageUri);
                            imagenUri = selectedImageUri;
                        }
                    }
                });

        tomarFotoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        imageView.setImageBitmap(photo);
                        imagenUri = getImageUri(getContext(), photo);
                    }
                });
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_pago, container, false);
        docenteViewModel = new ViewModelProvider(this).get(DocenteViewModel.class);
        pagoViewModel = new ViewModelProvider(this).get(PagoViewModel.class);
        fotoViewModel = new ViewModelProvider(this).get(FotoViewModel.class);
        dropdownDocente = view.findViewById(R.id.dropdownDocente);
        dropdownNivelEducacion = view.findViewById(R.id.dropdownNivelEducacion);
        txtMonto = view.findViewById(R.id.txtMonto);
        edtFechaPago = view.findViewById(R.id.edtFechaPago);
        txtReferenciaPago = view.findViewById(R.id.txtReferenciaPago);
        txtDiasTrabajo = view.findViewById(R.id.txtDiasTrabajo);
        imageView = view.findViewById(R.id.imageView);
        btnSeleccionarImagen = view.findViewById(R.id.btnSeleccionarImagen);
        Button btnAgregarPago = view.findViewById(R.id.btnAgregarPago);
        cargarNombresDocentes();
        cargarNivelesEducacion();
        ImageView btnVolverAtras = view.findViewById(R.id.btnVolverAtras);
        btnVolverAtras.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        edtFechaPago.setOnClickListener(v -> DatePickerHelper.mostrarDatePickerDialog(getContext(), edtFechaPago, calendar));
        btnAgregarPago.setOnClickListener(v -> agregarPago());
        btnSeleccionarImagen.setOnClickListener(v -> mostrarOpcionesSeleccionImagen());
        txtMonto.addTextChangedListener(new TextWatcher() {
            private boolean isEditing = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (isEditing) return;
                isEditing = true;
                String originalString = s.toString();
                if (!originalString.startsWith("S/.")) {
                    txtMonto.setText("S/." + originalString);
                    txtMonto.setSelection(txtMonto.getText().length());
                }
                isEditing = false;
            }
        });
        txtReferenciaPago.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        }});
        txtDiasTrabajo.setFilters(new InputFilter[]{new InputFilterMinMax("1", "31")});
        return view;
    }
    private void cargarNombresDocentes() {
        docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1) {
                List<String> nombresDocentes = new ArrayList<>();
                for (TeacherDTO teacher : response.getBody()) {
                    nombresDocentes.add(teacher.getFullName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nombresDocentes);
                dropdownDocente.setAdapter(adapter);
            } else {
                String errorMessage = "Error al cargar los nombres de los docentes: " + (response != null ? response.getMessage() : "Respuesta nula");
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                Log.e(TAG, errorMessage);
            }
        });
    }
    private void cargarNivelesEducacion() {
        List<String> nivelesEducacion = new ArrayList<>();
        nivelesEducacion.add("Licenciatura");
        nivelesEducacion.add("Maestría");
        nivelesEducacion.add("Doctorado");
        nivelesEducacion.add("Postdoctorado");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, nivelesEducacion);
        dropdownNivelEducacion.setAdapter(adapter);
    }
    private void mostrarOpcionesSeleccionImagen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Seleccionar imagen")
                .setItems(new CharSequence[]{"Tomar foto", "Seleccionar desde galería"}, (dialog, which) -> {
                    if (which == 0) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tomarFotoLauncher.launch(takePictureIntent);
                    } else {
                        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        seleccionarImagenLauncher.launch(pickPhotoIntent);
                    }
                })
                .show();
    }
    private void agregarPago() {
        if (!AgregarPagoValidationHelper.validarEntradas(getContext(), txtMonto, edtFechaPago, txtReferenciaPago, txtDiasTrabajo, imagenUri)) {
            return;
        }
        if (imagenUri != null) {
            String realPath = getRealPathFromURI(getContext(), imagenUri);
            File file = new File(realPath);
            RequestBody rb = RequestBody.create(file, MediaType.parse("multipart/form-data"));
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), rb);
            String filename = "pago_" + System.currentTimeMillis();
            RequestBody nombre = RequestBody.create(filename, MediaType.parse("text/plain"));
            fotoViewModel.saveFoto(part, nombre).observe(getViewLifecycleOwner(), response -> {
                if (response.getRpta() == 1) {
                    Foto foto = response.getBody();
                    fotoId = foto.getId();
                    crearPagoConFoto();
                } else {
                    String errorMessage = "Error al subir la foto: " + response.getMessage();
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                    Log.e(TAG, errorMessage);
                }
            });
        } else {
            String errorMessage = "Debe seleccionar una imagen";
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
            Log.e(TAG, errorMessage);
        }
    }
    private void crearPagoConFoto() {
        TeacherPayment pago = new TeacherPayment();
        pago.setAmount(BigDecimal.valueOf(Double.parseDouble(txtMonto.getText().toString().replace("S/.", "").trim())));
        pago.setPaymentDate(edtFechaPago.getText().toString());
        pago.setPaymentReference(txtReferenciaPago.getText().toString());
        pago.setWorkDays(Integer.parseInt(txtDiasTrabajo.getText().toString()));
        pago.setEducationLevel(dropdownNivelEducacion.getText().toString());
        pago.setFoto(new Foto(fotoId));
        String nombreDocente = dropdownDocente.getText().toString();
        docenteViewModel.listarDocentes().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getRpta() == 1) {
                for (TeacherDTO teacher : response.getBody()) {
                    if (teacher.getFullName().equals(nombreDocente)) {
                        pago.setTeacher(new Teacher(teacher.getId()));
                        break;
                    }
                }
                pagoViewModel.agregarPago(pago).observe(getViewLifecycleOwner(), agregarResponse -> {
                    if (agregarResponse != null && agregarResponse.getRpta() == 1) {
                        Toast.makeText(getContext(), "Pago agregado con éxito", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Pago agregado con éxito");
                        getParentFragmentManager().popBackStack();
                    } else {
                        String errorMessage = "Error al agregar pago: " + (agregarResponse != null ? agregarResponse.getMessage() : "Error desconocido");
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
                        Log.e(TAG, errorMessage);
                    }
                });
            } else {
                String errorMessage = "Error al obtener los datos del docente: " + (response != null ? response.getMessage() : "Respuesta nula");
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                Log.e(TAG, errorMessage);
            }
        });
    }
    private Uri getImageUri(Context context, Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
    private String getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
    }
}
