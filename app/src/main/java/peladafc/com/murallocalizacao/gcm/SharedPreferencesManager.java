package peladafc.com.murallocalizacao.gcm;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesManager {

    private static final String APP_SETTINGS = "APP_SETTINGS";


    private SharedPreferencesManager() {
    }

    private static SharedPreferences getSharedPreferences() {
        return BaseApplication.applicationContext.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    public static String getString(EnumKeyPreferences key) {
        return getSharedPreferences().getString(key.getIdKey(), null);
    }

    public static void writeString(EnumKeyPreferences key, String value) {

        SharedPreferences.Editor editor = getEditor();
        editor.putString(key.getIdKey(), value);
        editor.apply();
    }


    public static int getInt(EnumKeyPreferences key) {
        return getSharedPreferences().getInt(key.getIdKey(), Integer.MIN_VALUE);
    }

    public static void writeInt(EnumKeyPreferences key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key.getIdKey(), value);
        editor.apply();
    }




    public enum EnumKeyPreferences {
        PROPERTY_REG_ID(1, "registration_id"),
        PROPERTY_APP_VERSION(2, "appVersion"),
        PROPERTY_OBJ_SESSION(3, "ObjSessionPersist");

        EnumKeyPreferences(final Integer id, final String idKey) {
            this.id = id;
            this.idKey = idKey;
        }

        private Integer id;

        public String getIdKey() {
            return idKey;
        }

        public void setIdKey(String idKey) {
            this.idKey = idKey;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        private String idKey;

    }


}
