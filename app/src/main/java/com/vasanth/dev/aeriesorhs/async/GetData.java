package com.vasanth.dev.aeriesorhs.async;

import android.os.AsyncTask;
import android.util.Log;

import com.vasanth.dev.aeriesorhs.helpers.DataStorageAndParsing;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class GetData extends AsyncTask<String, Void, Void> {
    public static String classes = "";
    public static boolean finished = false;
    private final String TAG = "GetData";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        finished = false;
    }

    protected Void doInBackground(String... urls) {
        while (PostData.finished == false)
        {

        }
        Log.v(TAG,"On GetData");



        HttpClient client = PostData.httpClient;
        HttpGet request = new HttpGet("https://parentportal.eduhsd.k12.ca.us/Aeries.Net/Widgets/ClassSummary/GetClassSummary?IsProfile=True&sort=Period-asc&group=TermGrouping-asc&filter=&_=1465256962598");
        HttpResponse response;
        try {
            response = client.execute(request);
            String inputLine ;
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            try {
                while ((inputLine = br.readLine()) != null) {
                    GetData.classes+=inputLine;
                    //Log.d(TAG, inputLine);
                }
                br.close();
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }


        } catch (ClientProtocolException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        DataStorageAndParsing.classesAsString = GetData.classes;
        finished=true;
        Log.v(TAG, "Get Data Done");
        return null;
    }
    protected void onPostExecute( ) {

    }
    public static void resetData(){
        classes = "";
        finished = false;
    }
}
