package besco.corporation.toutimmobilier.Functions;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;


import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.io.File;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED;


/**
 * Created by root on 05/11/15.
 */
public class Functions {


    public static String hashPassword(String salt, String clearPassword) {
        String hash = "";
        try {
            //Log.d("AuthProvider", "start hashing password...");
            String salted = null;
            if (salt == null || "".equals(salt)) {
                salted = clearPassword;
            } else {
                salted = clearPassword + "{" + salt + "}";
            }
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte sha[] = md.digest(salted.getBytes());
            for (int i = 1; i < 5000; i++) {
                byte c[] = new byte[sha.length + salted.getBytes().length];
                System.arraycopy(sha, 0, c, 0, sha.length);
                System.arraycopy(salted.getBytes(), 0, c, sha.length, salted.getBytes().length);
                sha = md.digest(c);
            }
            hash = new String(Base64.encode(sha, Base64.NO_WRAP));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //do something with this exception
        }
        //Log.d("AuthProvider", "hashing password is done!");
        return hash;
    }

    //Get imei for the device
    @SuppressLint("MissingPermission")
    public String getIMEI(Context context) {

        String imei = null;
        TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        assert mngr != null;
        imei = mngr.getDeviceId();
        return imei;

    }

    //Check if email is valid
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Remove accents
    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    //Get gmail account email
    public String getUsername(Context context) {

        AccountManager manager = AccountManager.get(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null)
                return parts[0];
            else
                return null;
        } else
            return null;
    }

    public boolean isConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    //Check if URL is valid
    public static boolean checkValidUrl(String potentialUrl)
    {
        boolean result ;
        result = Patterns.WEB_URL.matcher(potentialUrl).matches();
        return result;
    }

    public void deleteSugarDB(Context context)
    {
        SugarContext.terminate();
        SchemaGenerator schemaGenerator = new SchemaGenerator(context);
        schemaGenerator.deleteTables(new SugarDb(context).getDB());
        SugarContext.init(context);
        schemaGenerator.createDatabase(new SugarDb(context).getDB());
    }


    //Make a random code
    public static int getCodeRandom()
    {
        int code;
        int min = 1000;
        int max = 9998;
        Random r = new Random();
        code = r.nextInt(max - min + 1) + min;

        return code;
    }



    public static long getFolderSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFolderSize(file);
            }
        } else {
            size=f.length();
        }
        return size;
    }

    public boolean  createfolder() {//Create Folder
        boolean reponse ;

        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/OpenAddress");
        reponse = !folder.exists() && folder.mkdirs();

        return reponse;
    }


    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        }
        return false;
    }







}
