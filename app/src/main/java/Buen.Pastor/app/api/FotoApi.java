package Buen.Pastor.app.api;

import Buen.Pastor.app.entity.BestGenericResponse;
import Buen.Pastor.app.entity.service.Foto;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface FotoApi {

    String base = "api/foto";

    @GET(base)
    Call<BestGenericResponse<Iterable<Foto>>> listAllFotos();

    @GET(base + "/{id}")
    Call<BestGenericResponse<Foto>> findFotoById(@Path("id") Long id);

    @GET(base + "/download/filename/{fileName}")
    Call<ResponseBody> downloadFotoByFileName(@Path("fileName") String fileName);

    @GET(base + "/download/{id}")
    Call<ResponseBody> downloadFotoById(@Path("id") Long id);

    @Multipart
    @POST(base)
    Call<BestGenericResponse<Foto>> saveFoto(
            @Part MultipartBody.Part file,
            @Part("nombre") RequestBody nombre
    );

    @Multipart
    @PUT(base + "/{id}")
    Call<BestGenericResponse<Foto>> updateFoto(
            @Path("id") Long id,
            @Part MultipartBody.Part file,
            @Part("nombre") RequestBody nombre
    );

    @DELETE(base + "/{id}")
    Call<BestGenericResponse<Void>> deleteFoto(@Path("id") Long id);
}