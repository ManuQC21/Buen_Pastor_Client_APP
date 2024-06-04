package Buen.Pastor.app.api;

import Buen.Pastor.app.entity.GenericResponse;
import Buen.Pastor.app.entity.service.Usuario;
import retrofit2.Call;
import retrofit2.http.*;

public interface UsuarioApi {
    // Ruta base para todos los endpoints de la API de Usuario
    String base = "/usuario";

    // Método para realizar el login de usuario
    @FormUrlEncoded
    @POST(base + "/login")
    Call<GenericResponse<Usuario>> login(@Field("correo") String correo, @Field("clave") String clave);
}
