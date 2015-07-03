package peladafc.com.murallocalizacao.gcm;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class BaseApplication extends Application {

    public static Context applicationContext;
    public static Resources resource;
    public static AssetManager assets;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        resource = getResources();
        assets = getAssets();
        new GCMUtils().registerGCM();
    }


    public static int getAppVersion() {
        try {
            PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get the package name: " + e);
        }
    }


    public static Boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

}
