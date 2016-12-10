package com.vasanth.dev.aeriesorhs.services;

/**
 * Created by Admin on 11/28/2016.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.vasanth.dev.aeriesorhs.async.GetAssignment;
import com.vasanth.dev.aeriesorhs.async.PostData;
import com.vasanth.dev.aeriesorhs.helpers.DataStorageAndParsing;
import com.vasanth.dev.aeriesorhs.objects.Class;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class NotificationService extends Service{

    private static String TAG = "NotificationService";

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Log.d(TAG, "FirstService started");
        PrimeThread p = new PrimeThread(this);
        p.start();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG, "FirstService destroyed");
    }


    class PrimeThread extends Thread {
        NotificationService notificationServiceObject;
        PrimeThread(NotificationService a) {
            this.notificationServiceObject = a;
        }

        public void run() {
            try {
                getAssignment();
            } catch (Exception e) {
                e.printStackTrace();
            }
            notificationServiceObject.stopSelf();
        }
        public void getAssignment() throws InterruptedException {
            ArrayList<Class> tempClassesAsArrayList = DataStorageAndParsing.classesAsArrayList;
            while (true) {
                Thread.sleep(5000);
                for (int i = 0; i < tempClassesAsArrayList.size(); i++) {
                    String state = "";
                    String html_file = "";
                    Document document = Jsoup.parse(tempClassesAsArrayList.get(i).getAssignmentLink());
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
                            tempClassesAsArrayList.get(i).setWeighted(true);
                        }
                        if (!html_file.equalsIgnoreCase(state) && state != "")
                            Log.v(TAG, "Status changed for class: " + tempClassesAsArrayList.get(i).getName());
                        state = html_file;
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
                            tempClassesAsArrayList.get(i).addAssignment(a.getAllElements().get(4).text(), x, y, x / y, isGraded, category);
                        }
                        if (tempClassesAsArrayList.get(i).isWeighted()) {
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
                                tempClassesAsArrayList.get(i).setHashMap(hashMap);
                            } catch (Exception e) {
                                Log.e("ERROR", "ERROR");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

}