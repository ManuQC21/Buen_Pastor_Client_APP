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

    // Base URL de tu servidor backend
    public static final String baseUrlE = "http://10.0.2.2:8080";
    public static final String baseUrl = "http://192.168.43.141:8080";
    public static final String ipInstituto = "http://192.168.1.122:8080";
    private static Retrofit retrofit;
    private static String token = "";

    // Declaración de las interfaces de las API
    private static UsuarioApi usuarioApi;
    private static FotoApi fotoApi;
    private static EquipoApi equipoApi;

    private static EmpleadoApi empleadoApi;

    private static UbicacionApi ubicacionApi;

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
                .baseUrl(baseUrlE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build();
    }

    // Método para obtener el cliente HTTP con configuraciones específicas
    public static OkHttpClient getClient() {
        // Interceptor para registrar las respuestas y peticiones HTTP
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Interceptor para Stetho, herramienta de debug de Facebook
        StethoInterceptor stetho = new StethoInterceptor();

        // Configuración del cliente HTTP
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(stetho);

        return builder.build();
    }

    // Método para configurar el token de autenticación
    public static void setToken(String value) {
        token = value;
        initClient();
    }

    // Método para obtener la instancia de UsuarioApi
    public static UsuarioApi getUsuarioApi() {
        if (usuarioApi == null) {
            usuarioApi = retrofit.create(UsuarioApi.class);
        }
        return usuarioApi;
    }

    // Método para obtener la instancia de FotoApi
    public static FotoApi getFotoApi() {
        if (fotoApi == null) {
            fotoApi = retrofit.create(FotoApi.class);
        }
        return fotoApi;
    }
    // Método para obtener la instancia de EquipoApi
    public static EquipoApi getEquipoApi() {
        if (equipoApi == null) {
            equipoApi = retrofit.create(EquipoApi.class);
        }
        return equipoApi;
    }
    public static EmpleadoApi getEmpleadoApi() {
        if (empleadoApi == null) {
            empleadoApi = retrofit.create(EmpleadoApi.class);
        }
        return empleadoApi;
    }
    public static UbicacionApi getUbicacionApi() {
        if (ubicacionApi == null) {
            ubicacionApi = retrofit.create(UbicacionApi.class);
        }
        return ubicacionApi;
    }
}
