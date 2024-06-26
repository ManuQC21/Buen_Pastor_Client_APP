package Buen.Pastor.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.sql.Date;
import java.sql.Time;
import Buen.P.App.R;
import Buen.Pastor.app.entity.service.App.MemberDTO;
import Buen.Pastor.app.utils.DateSerializer;
import Buen.Pastor.app.utils.TimeSerializer;
import Buen.Pastor.app.viewModel.UsuarioViewModel;

public class MainActivity extends AppCompatActivity {
    private EditText edtMail, edtPassword;
    private Button btnIniciarSesion;
    private UsuarioViewModel viewModel;
    private TextInputLayout txtInputUsuario, txtInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewModel();
        init();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }

    private void init() {
        edtMail = findViewById(R.id.edtMail);
        edtPassword = findViewById(R.id.edtPassword);
        txtInputUsuario = findViewById(R.id.txtInputUsuario);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        btnIniciarSesion.setOnClickListener(view -> {
            if (validar()) {
                viewModel.login(edtMail.getText().toString(), edtPassword.getText().toString()).observe(this, usuarioGenericResponse -> {
                    if (usuarioGenericResponse.getRpta() == 1) {
                        toastCorrecto(usuarioGenericResponse.getMessage());
                        MemberDTO u = usuarioGenericResponse.getBody();
                        saveUsuarioPreferences(u);
                        clearFields();
                        int userId = u.getId();
                        int teacherId = u.getTeacher() != null ? u.getTeacher().getId() : -1;
                        showIdsInToastAndLog(userId, teacherId);
                        // Redirección basada en el correo electrónico del usuario
                        Intent intent;
                            if ("admin@elbuenpastor.com".equals(u.getEmail())) {
                            intent = new Intent(this, InicioAdministrativoActivity.class);
                        } else {
                            intent = new Intent(this, InicioDocenteActivity.class);
                            intent.putExtra("userId", userId);
                            intent.putExtra("teacherId", teacherId);
                        }
                        intent.putExtra("UsuarioJson", new Gson().toJson(u, new TypeToken<MemberDTO>() {}.getType()));
                        startActivity(intent);
                    } else {
                        toastIncorrecto(usuarioGenericResponse.getMessage());
                    }
                });
            } else {
                toastIncorrecto("Por favor, complete todos los campos");
            }
        });

        edtMail.addTextChangedListener(new GenericTextWatcher(txtInputUsuario));
        edtPassword.addTextChangedListener(new GenericTextWatcher(txtInputPassword));
    }

    private void saveUsuarioPreferences(MemberDTO u) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();

        editor.putInt("userId", u.getId());
        if (u.getEmail().equals("admin@gmail.com")) {
            editor.putInt("teacherId", -1);  // No teacher ID for admin
        } else if (u.getTeacher() != null) {
            editor.putInt("teacherId", u.getTeacher().getId());
        }
        editor.putString("UsuarioJson", gson.toJson(u, new TypeToken<MemberDTO>() {}.getType()));
        editor.apply();
    }

    private void showIdsInToastAndLog(int userId, int teacherId) {
        String message = "User ID: " + userId + ", Teacher ID: " + teacherId;
        Log.d("UserDetails", message);
    }

    private void clearFields() {
        edtMail.setText("");
        edtPassword.setText("");
    }

    private boolean validar() {
        boolean valido = true;
        valido &= validarCampo(edtMail, txtInputUsuario, "Ingrese su usuario y/o correo electrónico");
        if (valido && !android.util.Patterns.EMAIL_ADDRESS.matcher(edtMail.getText().toString()).matches()) {
            txtInputUsuario.setError("Correo electrónico no válido");
            valido = false;
        }
        valido &= validarCampo(edtPassword, txtInputPassword, "Ingrese su contraseña");
        return valido;
    }

    private boolean validarCampo(EditText editText, TextInputLayout textInputLayout, String error) {
        if (editText.getText().toString().isEmpty()) {
            textInputLayout.setError(error);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private void showToast(String message, boolean isError) {
        int layoutId = isError ? R.layout.custom_toast_error : R.layout.custom_toast_ok;
        View layout = LayoutInflater.from(this).inflate(layoutId, (ViewGroup) findViewById(isError ? R.id.ll_custom_toast_error : R.id.ll_custom_toast_ok));
        ((TextView) layout.findViewById(isError ? R.id.txtMensajeToast2 : R.id.txtMensajeToast1)).setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void toastCorrecto(String msg) {
        showToast(msg, false);
    }

    public void toastIncorrecto(String msg) {
        showToast(msg, true);
    }

    private class GenericTextWatcher implements TextWatcher {
        private TextInputLayout layout;

        GenericTextWatcher(TextInputLayout layout) {
            this.layout = layout;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            layout.setErrorEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
