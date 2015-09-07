package com.example.tt.notebook.Tool;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Thien on 9/1/2015.
 */
public class RealmTool {
    private static String LOG_TAG = "RealmTool";
    public static void exportDatabase(Context context,RealmConfiguration configuration) {

        // init realm
        Realm realm = Realm.getInstance(configuration);

        File exportRealmFile = null;
        try {
            // get or create an "export.realm" file
            exportRealmFile = new File(context.getExternalCacheDir(), "export.realm");

            // if "export.realm" already exists, delete
            exportRealmFile.delete();

            // copy current realm to "export.realm"
            realm.writeCopyTo(exportRealmFile);


        } catch (IOException e) {
            e.printStackTrace();
        }
        realm.close();

        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "YOUR MAIL");
        intent.putExtra(Intent.EXTRA_SUBJECT, "YOUR SUBJECT");
        intent.putExtra(Intent.EXTRA_TEXT, "YOUR TEXT");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        context.startActivity(Intent.createChooser(intent, "YOUR CHOOSER TITLE"));
    }

    public static RealmConfiguration importDatabase(Context context){
        RealmConfiguration defaultRealm = new RealmConfiguration.Builder(context).build();
        String dir = defaultRealm.getPath();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is;
            is = assetManager.open("import.realm");
            File dest = new File(dir);
            if (dest.exists())
                dest.delete();
            copy(is,dest);
        }catch (IOException e){
            Log.e(LOG_TAG,"import database error");
        }
        return defaultRealm;
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    public static void copy(InputStream in, File dst) throws IOException {

        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

}
