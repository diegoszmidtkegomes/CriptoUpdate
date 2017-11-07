package ifrs.com.criptoupdate.app;

import android.app.Application;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by diego on 01/11/2016.
 */
public class CriptoUpdateApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        //Fabric.with(this, new Crashlytics());
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("criptoupdate.realm")
                //.encryptionKey(getKey())
                .schemaVersion(1)
                //.migration(new Migration())
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }


    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();
    }

    public byte[] getKey() {
        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);
        return key;
    }

}
