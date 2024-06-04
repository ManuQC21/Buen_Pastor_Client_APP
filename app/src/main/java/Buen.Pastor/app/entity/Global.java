package Buen.Pastor.app.entity;

public class Global {
    public enum Estado {
        A,
        I
    }

    public static final String TIPO_RESULT = "result";
    public static final String TIPO_DATA = "data";
    public static final String TIPO_AUTH = "auth";
    public static final String TIPO_ERROR = "ERROR";
    public static final String TIPO_CORRECTO = "SUCCESS";
    public static final String TIPO_CUIDADO = "WARNING";
    public static final int RPTA_OK = 1;
    public static final int RPTA_WARNING = 0;
    public static final int RPTA_ERROR = -1;
    public static final String OPERACION_CORRECTA = "Operación finalizada correctamente";
    public static final String OPERACION_INCORRECTA = "No se ha podido culminar la operación";
    public static final String OPERACION_ERRONEA = "Ha ocurrido un error al realizar la operación";
    public static final String AUTH_SECRET = "serviciosweb2";
}
