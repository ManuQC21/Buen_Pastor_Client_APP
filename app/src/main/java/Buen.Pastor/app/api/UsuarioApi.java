package Buen.Pastor.app.api;

import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.App.MemberDTO;
import Buen.Pastor.app.entity.service.Member;
import retrofit2.Call;
import retrofit2.http.*;

public interface UsuarioApi {
    // Ruta base para todos los endpoints de la API de Usuario
    String base = "/api/usuario";

    // Método para realizar el login de usuario
    @FormUrlEncoded
    @POST(base + "/login")
    Call<BestGenericResponse<MemberDTO>> login(@Field("email") String email, @Field("password") String password);
    @POST(base + "/registro")
    Call<BestGenericResponse<MemberDTO>> register(@Body Member member);

    @DELETE( base + "/eliminar/{id}")
    Call<BestGenericResponse<String>> eliminarUsuario(@Path("id") int id);

    @GET(base + "/obtenerPorDocenteId/{docenteId}")
    Call<BestGenericResponse<MemberDTO>> obtenerUsuarioPorDocenteId(@Path("docenteId") int docenteId);

}
