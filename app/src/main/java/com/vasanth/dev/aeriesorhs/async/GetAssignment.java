package com.vasanth.dev.aeriesorhs.async;

import android.os.AsyncTask;
import android.util.Log;

import com.vasanth.dev.aeriesorhs.helpers.DataStorageAndParsing;

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
public class GetAssignment extends AsyncTask<String, Void, Void> {
    public static String TAG = "GetAssignment";
    private final String gradebookDefault = "https://parentportal.eduhsd.k12.ca.us/Aeries.Net/GradebookDetails.aspx";
    public static boolean done = false;

    @Override
    protected Void doInBackground(String... params) {
        while (!DataStorageAndParsing.doneLoadingClasses) {
        }
        Log.v(TAG, "GetAssignment starts");

        for (int i = 0; i < DataStorageAndParsing.classesAsArrayList.size(); i++) {
            String html_file = "";
            Document document = Jsoup.parse(DataStorageAndParsing.classesAsArrayList.get(i).getAssignmentLink());
            Elements links = document.select("a[href]");
            String requestURL = "";
            for (Element link : links) {
                Log.v(TAG, "URL: " + link.attr("href"));
                requestURL = "https://parentportal.eduhsd.k12.ca.us/Aeries.Net/" + link.attr("href");
            }
            if (links.size() > 0) {
                HttpClient client = PostData.httpClient;
                HttpGet request = new HttpGet(requestURL);
                Log.v(TAG, "Length of links list: " + links.size());
                HttpResponse response;
                try {
                    response = client.execute(request);
                    String inputLine;
                    BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    while ((inputLine = br.readLine()) != null) {
                        html_file += inputLine;
                    }
                    br.close();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
                if(html_file.contains("Perc&nbsp;of<br>Grade")){
                    DataStorageAndParsing.classesAsArrayList.get(i).setWeighted(true);
                }
                Document doc = Jsoup.parse(html_file);
                Elements content = doc.getElementsByClass("assignment-info");
                for (Element a : content) {
                    Log.w(TAG, a.getAllElements().get(4).text()); //name
                    Log.w(TAG, a.getAllElements().get(11).text());//pt value
                    Log.w(TAG, a.getAllElements().get(13).text());//out of
                    Log.w(TAG, a.getAllElements().get(21).text()); //percentage
                    Log.w(TAG, a.getAllElements().get(25).text()); //graded?
                    float x = 0;
                    float y = 0;
                    boolean isGraded = false;
                    try {
                        x = Float.valueOf(a.getAllElements().get(11).text());
                        y = Float.valueOf(a.getAllElements().get(13).text());
                        if(a.getAllElements().get(25).text().contains("y"))
                            isGraded = true;
                    } catch (Exception e) {
                        x = 0;
                        y = 0;
                        e.printStackTrace();
                    }
                    DataStorageAndParsing.classesAsArrayList.get(i).addAssignment(a.getAllElements().get(4).text(), x, y, x / y, isGraded);
                }
            }
            GetAssignment.done = true;
        }
        return null;
    }

    public static void resetData() {
        done = false;
    }
}
