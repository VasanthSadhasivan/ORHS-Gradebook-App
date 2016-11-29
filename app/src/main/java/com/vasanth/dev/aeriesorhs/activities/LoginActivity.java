package com.vasanth.dev.aeriesorhs.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.vasanth.dev.aeriesorhs.R;
import com.vasanth.dev.aeriesorhs.async.PostData;
import com.vasanth.dev.aeriesorhs.helpers.DataStorageAndParsing;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class LoginActivity extends AppCompatActivity {
    public static boolean done = false;
    final private int REQUEST_CODE = 123;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addPermissions();
        sharedPreferences = getPreferences(MODE_PRIVATE);

        try {
            String tempEmail = sharedPreferences.getString("email", "");
            String tempPassword = sharedPreferences.getString("password", "");
            if (!(tempEmail.equals("") || tempPassword.equals(""))) {
                ((EditText) findViewById(R.id.email)).setText(tempEmail);
                ((EditText) findViewById(R.id.pasword)).setText(tempPassword);
            }

        } catch (Exception e) {

        }


    }

    public void loginClicked(View view) {
        PostData.email = ((EditText) findViewById(R.id.email)).getText().toString();
        PostData.password = ((EditText) findViewById(R.id.pasword)).getText().toString();
        SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.putString("email", PostData.email);
        sharedPreferenceEditor.putString("password", PostData.password);
        sharedPreferenceEditor.commit();
        done = true;
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainIntent.putExtra("firstTime", true);
        startActivity(mainIntent);
    }


    public void addPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasInternetAccess = checkSelfPermission(Manifest.permission.INTERNET);
            int hasWifiAccess = checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE);
            int hasNetworkAccess = checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
            if (hasNetworkAccess != PackageManager.PERMISSION_GRANTED || hasInternetAccess != PackageManager.PERMISSION_GRANTED || hasWifiAccess != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.ACCESS_NETWORK_STATE},
                        REQUEST_CODE);
            }
        }
    }
    public static void resetData(){
        done = false;
    }

}
