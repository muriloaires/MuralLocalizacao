package peladafc.com.murallocalizacao.gcm;

import android.os.AsyncTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class GCMUtils {


    private String regId;

    private GoogleCloudMessaging gcm;


    public boolean registerGCM() {
        Boolean retorno = false;
        if (GCMUtils.googlePlayServicesIsAvailable()) {
            regId = getRegId();
            if (regId.isEmpty()) {
                registerWithGcm();
            }
            retorno = true;
        } else {

        }
        return retorno;
    }


    /**
     * Gets the registration ID from the GCM server.
     */
    public void registerWithGcm() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String message;

                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(BaseApplication.applicationContext);
                    }

                    regId = gcm.register(Constants.PROJECT_NUMBER);
                    message = "Device registered with GCM.\n\n" + regId;

                    storeRegistrationId(regId);
                } catch (IOException ex) {
                    message = "Error during GCM registration: " + ex.getMessage();
                }

                return message;
            }

            @Override
            protected void onPostExecute(String message) {

            }
        }.execute(null, null, null);
    }


    /**
     * Gets the current registration ID for the application on GCM service.
     * <p/>
     * If result is empty, the app will register.
     *
     * @return The current registration ID or an empty string if the app needs to register.
     */
    private String getRegId() {
        String registrationId = SharedPreferencesManager.getString(SharedPreferencesManager.EnumKeyPreferences.PROPERTY_REG_ID);

        if (appWasUpdated()) {
            registrationId = "";

        }
        return registrationId;
    }

    /**
     * Checks if the app was updated. If it was, it must clear the registration ID since the
     * existing ID is not guaranteed to work with the new version.
     *
     * @return Returns true if the app's current version does not match the registered version.
     */
    private boolean appWasUpdated() {
        int registeredVersion = SharedPreferencesManager.getInt(SharedPreferencesManager.EnumKeyPreferences.PROPERTY_APP_VERSION);
        int currentVersion = BaseApplication.getAppVersion();
        return (registeredVersion != currentVersion);
    }


    /**
     * Checks if Google Play Services are available on the device.
     *
     * @return Returns true if Google Play Services are available.
     */
    public static boolean googlePlayServicesIsAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(BaseApplication.applicationContext);

        if (resultCode != ConnectionResult.SUCCESS) {
            /*
            Se quiser exibir uma mensagem de erro do GooglePlayServices ao usuário, descomentar esta linha;
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000).show();
            } else {
                finish();
            }*/

            //return false;
        }

        return (resultCode == ConnectionResult.SUCCESS);
    }

    /**
     * Stores the registration ID and app versionCode in the application's shared preferences.
     *
     * @param registrationId GCM registration ID.
     */
    private void storeRegistrationId(String registrationId) {
        SharedPreferencesManager
                .writeString(SharedPreferencesManager.EnumKeyPreferences.PROPERTY_REG_ID, registrationId);
        SharedPreferencesManager
                .writeInt(SharedPreferencesManager.EnumKeyPreferences.PROPERTY_APP_VERSION, BaseApplication.getAppVersion());

    }
}
