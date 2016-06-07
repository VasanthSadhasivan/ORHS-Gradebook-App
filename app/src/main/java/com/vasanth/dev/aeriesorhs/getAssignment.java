package com.vasanth.dev.aeriesorhs;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class getAssignment extends AsyncTask<String, Void, Void> {
    public static String TAG = "getAssignment";
    private final String gradebookDefault = "https://parentportal.eduhsd.k12.ca.us/Aeries.Net/GradebookDetails.aspx";
    public static boolean done = false;
    @Override
    protected Void doInBackground(String... params) {
        while (!MainActivity.doneLoadingClasses)
        {}
        Log.v(TAG, "getAssignment starts");

        for (int i=0; i<MainActivity.Classes.size(); i++)
        {
            String html_file = "";
            Document document = Jsoup.parse(MainActivity.Classes.get(i).getAssignmentLink());
            Elements links = document.select("a[href]");
            String requestURL = "";
            for(Element link : links) {
                Log.v(TAG, "URL: "+link.attr("href"));
                requestURL = "https://parentportal.eduhsd.k12.ca.us/Aeries.Net/"+link.attr("href");
                //requestURL = "https://parentportal.eduhsd.k12.ca.us/Aeries.Net/"+"Widgets/ClassSummary/RedirectToGradebook?GradebookNumber=3874982&Term=S";

            }
            HttpClient client = PostData.httpClient;
            HttpGet request = new HttpGet(requestURL);

            HttpResponse response;
            try {
                response = client.execute(request);
                //request = new HttpGet(gradebookDefault);
                //response = client.execute(request);
                Log.v(TAG, "Response: "+response.toString());
                String inputLine ;
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                try {
                    while ((inputLine = br.readLine()) != null) {
                        html_file+=inputLine;
                        //Log.d(TAG, inputLine);
                    }
                    br.close();
                } catch (IOException e) {
                    //Log.e(TAG, e.toString());
                }


            } catch (ClientProtocolException e) {
                //Log.e(TAG, e.toString());
            } catch (IOException e) {
                //Log.e(TAG, e.toString());
            }

            Document doc = Jsoup.parse(html_file);
            Elements content = doc.getElementsByClass("assignment-info");
            //Log.v(TAG, "Content: "+content.toString());
            for(Element a : content){
                Log.w(TAG, a.getAllElements().get(4).text());
                Log.w(TAG, a.getAllElements().get(11).text());
                Log.w(TAG, a.getAllElements().get(13).text());
                Log.w(TAG, a.getAllElements().get(21).text());
                float x=0;
                float y=0;
                try {
                    x = Float.valueOf(a.getAllElements().get(11).text());
                }catch (Exception e){
                    x=0;
                }
                try {
                    y = Float.valueOf(a.getAllElements().get(13).text());
                }catch (Exception e){
                    y = 0;
                }
                MainActivity.Classes.get(i).addAssignment(a.getAllElements().get(4).text(), x, y, x / y);
            }
        }

        getAssignment.done = true;
        return null;
    }
}
