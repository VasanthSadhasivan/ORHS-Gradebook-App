package com.vasanth.dev.aeriesorhs.async;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.vasanth.dev.aeriesorhs.activities.ClassActivity;
import com.vasanth.dev.aeriesorhs.activities.LoginActivity;
import com.vasanth.dev.aeriesorhs.activities.MainActivity;
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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class GetAssignment extends AsyncTask<String, Void, Void> {
    public static String TAG = "GetAssignment";
    private final String gradebookDefault = "https://parentportal.eduhsd.k12.ca.us/Aeries.Net/GradebookDetails.aspx";
    public static boolean done = false;
    private Dialog loadingDialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.progressDialog.hide();
    }

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
                requestURL = "https://parentportal.eduhsd.k12.ca.us/Aeries.Net/" + link.attr("href");
            }
            if (links.size() > 0) {
                HttpClient client = PostData.httpClient;
                HttpGet request = new HttpGet(requestURL);
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
                if (html_file.contains("Perc&nbsp;of")) {
                    DataStorageAndParsing.classesAsArrayList.get(i).setWeighted(true);
                }
                Document doc = Jsoup.parse(html_file);
                Elements content = doc.getElementsByClass("assignment-info");
                for (Element a : content) {
                    //Log.w(TAG, a.getAllElements().get(4).text()); //name
                    //Log.w(TAG, a.getAllElements().get(11).text());//pt value
                    //Log.w(TAG, a.getAllElements().get(13).text());//out of
                    //Log.w(TAG, a.getAllElements().get(21).text()); //percentage
                    //Log.w(TAG, a.getAllElements().get(25).text()); //graded?
                    float x = 0;
                    float y = 0;
                    boolean isGraded = false;
                    String category = "";
                    try {
                        if (a.getAllElements().get(25).text().contains("y") || a.getAllElements().get(25).text().contains("Y"))
                            isGraded = true;
                        category = a.getAllElements().get(6).text();
                        x = Float.valueOf(a.getAllElements().get(11).text());
                        y = Float.valueOf(a.getAllElements().get(13).text());
                    } catch (Exception e) {
                        x = 0;
                        y = 0;
                        //e.printStackTrace();
                    }
                    DataStorageAndParsing.classesAsArrayList.get(i).addAssignment(a.getAllElements().get(4).text(), x, y, x / y, isGraded, category);
                }
                if (DataStorageAndParsing.classesAsArrayList.get(i).isWeighted()) {
                    try {
                        int numberOfCategories = 0;
                        HashMap hashMap = new HashMap();
                        ArrayList<Float> percentages = new ArrayList<Float>();
                        ArrayList<String> categories = new ArrayList<String>();
                        for (Element element : doc.getAllElements()) {
                            if (element.id().contains("tdPctOfGrade"))
                                numberOfCategories++;
                        }
                        numberOfCategories--;
                        int incrementor = 0;
                        for (Element element : doc.getAllElements()) {
                            if (element.id().contains("tdPctOfGrade")) {
                                percentages.add(Float.parseFloat(element.text().substring(0, element.text().length() - 1)));
                                incrementor++;
                            }
                            if (incrementor >= numberOfCategories)
                                break;

                        }
                        incrementor = 0;
                        for (Element element : doc.getAllElements()) {
                            if (element.id().contains("tdDESC")) {
                                categories.add(element.text());
                                incrementor++;
                            }
                            if (incrementor >= numberOfCategories)
                                break;
                        }
                        for (int a = 0; a < numberOfCategories; a++) {
                            hashMap.put(categories.get(a), percentages.get(a));
                        }
                        DataStorageAndParsing.classesAsArrayList.get(i).setHashMap(hashMap);
                    } catch (Exception e) {
                        Log.e("ERROR", "ERROR");
                        e.printStackTrace();
                    }
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
