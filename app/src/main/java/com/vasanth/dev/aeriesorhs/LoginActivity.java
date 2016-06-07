package com.vasanth.dev.aeriesorhs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class LoginActivity extends AppCompatActivity{
    public static boolean done = false;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getPreferences(MODE_PRIVATE);

        try{
            String tempEmail = sharedPreferences.getString("email","");
            String tempPassword = sharedPreferences.getString("password","");
            if (!(tempEmail.equals("") || tempPassword.equals(""))){
                ((EditText) findViewById(R.id.email)).setText(tempEmail);
                ((EditText) findViewById(R.id.pasword)).setText(tempPassword);
            }

        }catch(Exception e){

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
        startActivity(mainIntent);
    }
}
