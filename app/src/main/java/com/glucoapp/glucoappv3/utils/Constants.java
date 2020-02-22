package com.glucoapp.glucoappv3.utils;

public final class Constants {

    public static final int REQUEST_ENABLED_BT = 0;

    public static final int GRAY_COLOR =  0xFFC0C0C0;
    public static final int BEAUTYBLACK_COLOR = 0xFF464646;
    public static final int BLUE_COLOR = 0xFF056CF2;

    public static final int CERO_VALUE = 0;
    public static final int UNO_VALUE =1;
    public static final int TRES_VALUE =3;
    public static final int MAX_LENGTH_PASSWORD_VALUE = 6;
    public static final long SPLASH_SCREEN_TIMER = 2500;
    public static final int DIABETES_RANGE_VALUE = 140;

    public static final String UNIDAD_MEDIDA ="mmol/L";

    public static final String FORMAT_DATE = "dd/MM/yy";

    public static final String ESPACIO = " ";
    public static final String MY_USER = "myUser";

    public static final String TAG_FIRESTORE = "Firestore";
    public static final String TAG_VIEW = "View";

    public static final String LOGIN_EMAIL_ERROR = "Error: El email ingresado es incorrecto";
    public static final String LOGIN_PASSWORD_ERROR = "Error: La contraseña debe ser mayor a 6 caracteres";

    public static final String REGISTER_ERROR = "El usuario ya existe o no está ingresando un correo válido";
    public static final String REGISTER_DATABASE_ERROR = "Error al crear el perfil del usuario";
    public static final String UPDATE_DATABASE_ERROR = "Error al actualizar el perfil del usuario";
    public static final String FIELDS_VALIDATE_ERROR = "Porfavor ingrese todos los datos";
    public static final String LOGOUT_ERROR = "Error al deslogear. Intentelo de nuevo";
    public static final String VIEW_ERROR = "Error en el view";
    public static final String FIRESTORE_ERROR = "Error con firestore";
    public static final String DONT_HAVE_BT_ERROR = "Lo sentimos, tu dispisitivo no tiene Bluetooth";
    public static final String DONT_USE_BT_ERROR = "Por favor, para ingresar active su bluetooth";
    public static final String DONT_PAIRED_DEVICES_ERROR = "No tiene dispositivos emparejados...";
    public static final String DATA_TRANSFER_ERROR = "Error en la transferencia de datos";

    public static final String UPDATE_DATABASE_OK = "Se actualizó el prefil del usuario";

    public static final String NO_CHART_DATA = "Cargando los datos...";

    public static final String SUCCESS_GLUCO_DATA = "Su muestra ya se registro en su historial...";
}
