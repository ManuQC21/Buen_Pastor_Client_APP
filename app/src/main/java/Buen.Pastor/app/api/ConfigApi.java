package Buen.Pastor.app.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import Buen.Pastor.app.utils.DateSerializer;
import Buen.Pastor.app.utils.TimeSerializer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigApi {

    // Base URLs de tu servidor backend
    public static final String BASE_URL_MY_HOUSE = "http://192.168.0.14:8080";
    public static final String BASE_URL_LOCAL_HOST = "http://10.0.2.2:8080";
    public static final String BASE_URL_PRODUCTION = "https://elbuenpastor-back-production.up.railway.app";

    private static final String BASE_URL = BASE_URL_PRODUCTION; // Cambiar según el entorno

    private static Retrofit retrofit;

    // Declaración de las interfaces de las API
    private static UsuarioApi usuarioApi;
    private static EquipoApi equipoApi;
    private static DocenteApi docenteApi;
    private static AdministrativoApi administrativoApi;
    private static NotificacionesApi notificacionesApi;
    private static PagosApi pagosApi;
    private static UbicacionApi ubicacionApi;
    private static FotoApi fotoApi;

    // Bloque estático para inicializar el cliente Retrofit al cargar la clase
    static {
        initClient();
    }

    // Método para inicializar el cliente Retrofit
    private static void initClient() {
        // Configuración de Gson para manejar la serialización de fechas y tiempos específicos de SQL
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();

        // Construcción del cliente Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build();
    }

    // Método para obtener el cliente HTTP con configuraciones específicas
    private static OkHttpClient getClient() {
        // Interceptor para registrar las respuestas y peticiones HTTP
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Interceptor para Stetho, herramienta de debug de Facebook
        StethoInterceptor stetho = new StethoInterceptor();

        // Configuración del cliente HTTP
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(stetho)
                .build();
    }

    // Métodos para obtener la instancia de cada API
    public static UsuarioApi getUsuarioApi() {
        return usuarioApi != null ? usuarioApi : (usuarioApi = retrofit.create(UsuarioApi.class));
    }

    public static EquipoApi getEquipoApi() {
        return equipoApi != null ? equipoApi : (equipoApi = retrofit.create(EquipoApi.class));
    }

    public static DocenteApi getDocenteApi() {
        return docenteApi != null ? docenteApi : (docenteApi = retrofit.create(DocenteApi.class));
    }

    public static AdministrativoApi getAdministrativoApi() {
        return administrativoApi != null ? administrativoApi : (administrativoApi = retrofit.create(AdministrativoApi.class));
    }

    public static NotificacionesApi getNotificacionesApi() {
        return notificacionesApi != null ? notificacionesApi : (notificacionesApi = retrofit.create(NotificacionesApi.class));
    }

    public static PagosApi getPagosApi() {
        return pagosApi != null ? pagosApi : (pagosApi = retrofit.create(PagosApi.class));
    }

    public static UbicacionApi getUbicacionApi() {
        return ubicacionApi != null ? ubicacionApi : (ubicacionApi = retrofit.create(UbicacionApi.class));
    }

    public static FotoApi getFotoApi() {
        return fotoApi != null ? fotoApi : (fotoApi = retrofit.create(FotoApi.class));
    }
}
