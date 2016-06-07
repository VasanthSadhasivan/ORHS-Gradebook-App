package com.vasanth.dev.aeriesorhs;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
class PostData extends AsyncTask<String, Void, Void> {

    private Exception exception;
    public static HttpClient httpClient = new DefaultHttpClient();
    public static boolean finished = false;
    public static String email = "";
    public static String password = "";
    protected Void doInBackground(String... urls) {
        while (!LoginActivity.done) {
        }
        HttpPost httpPost = new HttpPost("https://parentportal.eduhsd.k12.ca.us/Aeries.Net/LoginParent.aspx");
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(8);
        nameValuePair.add(new BasicNameValuePair("checkCookiesEnabled","true"));
        nameValuePair.add(new BasicNameValuePair("checkMobileDevice","false"));
        nameValuePair.add(new BasicNameValuePair("checkStandaloneMode","false"));
        nameValuePair.add(new BasicNameValuePair("checkTabletDevice","false"));
        nameValuePair.add(new BasicNameValuePair("portalAccountUsername", email));
        nameValuePair.add(new BasicNameValuePair("portalAccountPassword", password));
        nameValuePair.add(new BasicNameValuePair("portalAccountUsernameLabel", ""));
        nameValuePair.add(new BasicNameValuePair("submit", ""));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }


        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            String inputLine ;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            try {
                while ((inputLine = br.readLine()) != null) {
                    Log.d("Response of Post", inputLine);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }






        httpPost = new HttpPost("https://parentportal.eduhsd.k12.ca.us/Aeries.Net/Widgets/ClassSummary/SetShowAllTermsOption");
        nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("ShowAllTerms","true"));
        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        try {
            httpClient.execute(httpPost);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }









        finished=true;
        return null;
    }














    protected void onPostExecute( ) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}