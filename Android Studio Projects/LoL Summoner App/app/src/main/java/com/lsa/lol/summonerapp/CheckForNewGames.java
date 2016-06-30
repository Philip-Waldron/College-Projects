package com.lsa.lol.summonerapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Date;

public class CheckForNewGames extends Service {
    String summoner = "Septimus Soul";
    String region = "EUW";
    static final String API_KEY = "api_key=add66013-808b-4498-8b34-30393d207991";
    boolean eventOccurred = false;
    String KDA;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        summoner = PreferenceManager.getDefaultSharedPreferences(CheckForNewGames.this).getString("summoner", "Septimus Soul");
        region = PreferenceManager.getDefaultSharedPreferences(CheckForNewGames.this).getString("region", "EUW");


        try {
            URL url = new URL("https://" + region.toLowerCase() + ".api.pvp.net/api/lol/" + region.toLowerCase() + "/v1.4/summoner/by-name/" + summoner + "?" + API_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200)
            {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    String jsonString = stringBuilder.toString();
                    JSONObject jo = new JSONObject(jsonString);
                    JSONArray test = jo.getJSONArray("games");
                    for(int i=0; i < test.length(); i++) {
                        JSONObject gameInfo = test.getJSONObject(i);
                        if(gameInfo.has("createDate"))
                        {
                            int timePlayed = gameInfo.getInt("createDate");
                            Date gamePlayedDate = new Date(timePlayed * 1000);
                            Date now = new Date(System.currentTimeMillis());
                            int result = now.compareTo(gamePlayedDate);
                            if (result > 0)
                            {
                                eventOccurred = true;
                            }

                            JSONObject stats = gameInfo.getJSONObject("stats");
                            int tempKills = 0;
                            if(stats.has("championsKilled"))
                            {
                                tempKills = stats.getInt("championsKilled");
                            }

                            int tempDeaths = 0;
                            if(stats.has("numDeaths"))
                            {
                                tempDeaths = stats.getInt("numDeaths");
                            }

                            int tempAssists = 0;
                            if(stats.has("assists"))
                            {
                                tempAssists = stats.getInt("assists");
                            }

                            KDA = tempKills + "/" + tempDeaths + "/" + tempAssists;
                        }

                        }
                } finally {
                    urlConnection.disconnect();
                }
            }
        }catch(Exception e){
            Log.e("ERROR", e.getMessage(), e);
        }
            if(eventOccurred)
            {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
                mBuilder.setSmallIcon(R.drawable.gamelogo);
                mBuilder.setContentTitle("LoL Game Update");
                mBuilder.setContentText(summoner + "finished a game with a score of " + KDA + ". Wow! Check it out?");
                Intent resultIntent = new Intent(this, MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                mNotificationManager.notify(9999, mBuilder.build());
            }

        return START_STICKY;
    }
}
