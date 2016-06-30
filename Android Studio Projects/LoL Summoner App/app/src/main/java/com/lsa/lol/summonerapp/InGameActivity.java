package com.lsa.lol.summonerapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Ranks missing last two ranks in hexakill */
public class InGameActivity extends AppCompatActivity {

    String region;
    TextView responseView;
    ProgressBar progressBar;
    int summonerID;
    static final String API_KEY = "api_key=4796547d-72d3-4e4d-939d-f50a9b3ba598";
    boolean successfulQuery = true;
    String failQueryMessage;
    List<String> allNames = new ArrayList<>();
    List<Long> allIDs = new ArrayList<>();
    List<Long> allChampions = new ArrayList<>();
    List<Long> allBans = new ArrayList<>();
    List<Long> allTeams = new ArrayList<>();
    List<String> allRanks = new ArrayList<>();
    String tier;
    String division;
    int lp;
    String names = "";
    String names2 = "";
    int names1Count = 0;
    int names2Count = 0;
    boolean flag = false;
    final Map<Integer, String> champIDList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 1.0), (int) (height * 0.6));

        Intent intent = getIntent();
        responseView = (TextView) findViewById(R.id.responseView);
        region = intent.getStringExtra("REGION");
        summonerID = intent.getIntExtra("SUMMONERID", 0);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        new RetrieveFeedTask().execute();
    }

    public void compileChampList() {
        champIDList.put(1, "Annie");
        champIDList.put(2, "Olaf");
        champIDList.put(3, "Galio");
        champIDList.put(4, "Twisted Fate");
        champIDList.put(5, "Xin Zhao");
        champIDList.put(6, "Urgot");
        champIDList.put(7, "LeBlanc");
        champIDList.put(8, "Vladimir");
        champIDList.put(9, "Fiddlesticks");
        champIDList.put(10, "Kayle");
        champIDList.put(11, "Master Yi");
        champIDList.put(12, "Alistar");
        champIDList.put(13, "Ryze");
        champIDList.put(14, "Sion");
        champIDList.put(15, "Sivir");
        champIDList.put(16, "Soraka");
        champIDList.put(17, "Teemo");
        champIDList.put(18, "Tristana");
        champIDList.put(19, "Warwick");
        champIDList.put(20, "Nunu");
        champIDList.put(21, "Miss Fortune");
        champIDList.put(22, "Ashe");
        champIDList.put(23, "Tryndamere");
        champIDList.put(24, "Jax");
        champIDList.put(25, "Morgana");
        champIDList.put(26, "Zilean");
        champIDList.put(27, "Singed");
        champIDList.put(28, "Evelynn");
        champIDList.put(29, "Twitch");
        champIDList.put(30, "Karthus");
        champIDList.put(31, "Cho'Gath");
        champIDList.put(32, "Amumu");
        champIDList.put(33, "Raummus");
        champIDList.put(34, "Anivia");
        champIDList.put(35, "Shaco");
        champIDList.put(36, "Dr. Mundo");
        champIDList.put(37, "Sona");
        champIDList.put(38, "Kassadin");
        champIDList.put(39, "Irelia");
        champIDList.put(40, "Janna");
        champIDList.put(41, "Gangplank");
        champIDList.put(42, "Corki");
        champIDList.put(43, "Karma");
        champIDList.put(44, "Taric");
        champIDList.put(45, "Veigar");
        champIDList.put(48, "Trundle");
        champIDList.put(50, "Swain");
        champIDList.put(51, "Caitlyn");
        champIDList.put(53, "Blitzcrank");
        champIDList.put(54, "Malphite");
        champIDList.put(55, "Katarina");
        champIDList.put(56, "Nocturne");
        champIDList.put(57, "Maokai");
        champIDList.put(58, "Renekton");
        champIDList.put(59, "Jarvan IV");
        champIDList.put(60, "Elise");
        champIDList.put(61, "Orianna");
        champIDList.put(62, "Wukong");
        champIDList.put(63, "Brand");
        champIDList.put(64, "Lee Sin");
        champIDList.put(67, "Vayne");
        champIDList.put(68, "Rumble");
        champIDList.put(69, "Cassiopeia");
        champIDList.put(72, "Skarner");
        champIDList.put(74, "Heimerdinger");
        champIDList.put(75, "Nasus");
        champIDList.put(76, "Nidalee");
        champIDList.put(77, "Udyr");
        champIDList.put(78, "Poppy");
        champIDList.put(79, "Gragas");
        champIDList.put(80, "Pantheon");
        champIDList.put(81, "Ezreal");
        champIDList.put(82, "Mordekaiser");
        champIDList.put(83, "Yorick");
        champIDList.put(84, "Akali");
        champIDList.put(85, "Kennen");
        champIDList.put(86, "Garen");
        champIDList.put(89, "Leona");
        champIDList.put(90, "Malzahar");
        champIDList.put(91, "Talon");
        champIDList.put(92, "Riven");
        champIDList.put(96, "Kog'Maw");
        champIDList.put(98, "Shen");
        champIDList.put(99, "Lux");
        champIDList.put(101, "Xerath");
        champIDList.put(102, "Shyvana");
        champIDList.put(103, "Ahri");
        champIDList.put(104, "Graves");
        champIDList.put(105, "Fizz");
        champIDList.put(106, "Volibear");
        champIDList.put(107, "Rengar");
        champIDList.put(110, "Varus");
        champIDList.put(111, "Nautilus");
        champIDList.put(112, "Viktor");
        champIDList.put(113, "Sejuani");
        champIDList.put(114, "Fiora");
        champIDList.put(115, "Ziggs");
        champIDList.put(117, "Lulu");
        champIDList.put(119, "Draven");
        champIDList.put(120, "Hecarim");
        champIDList.put(121, "Kha'Zix");
        champIDList.put(122, "Darius");
        champIDList.put(126, "Jayce");
        champIDList.put(127, "Lissandra");
        champIDList.put(131, "Diana");
        champIDList.put(133, "Quinn");
        champIDList.put(134, "Syndra");
        champIDList.put(136, "Aurelion Sol");
        champIDList.put(143, "Zyra");
        champIDList.put(150, "Gnar");
        champIDList.put(154, "Zac");
        champIDList.put(157, "Yasuo");
        champIDList.put(161, "Vel'Koz");
        champIDList.put(201, "Braum");
        champIDList.put(202, "Jhin");
        champIDList.put(203, "Kindred");
        champIDList.put(222, "Jinx");
        champIDList.put(223, "Tahm Kench");
        champIDList.put(236, "Lucian");
        champIDList.put(238, "Zed");
        champIDList.put(245, "Ekko");
        champIDList.put(254, "Vi");
        champIDList.put(266, "Aatrox");
        champIDList.put(267, "Nami");
        champIDList.put(268, "Azir");
        champIDList.put(412, "Thresh");
        champIDList.put(420, "Illaoi");
        champIDList.put(421, "Rek'Sai");
        champIDList.put(429, "Kalista");
        champIDList.put(432, "Bard");
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText(R.string.loading);
        }

        protected String doInBackground(Void... urls) {
            String result = "";
            try {
                URL url = new URL("https://" + region.toLowerCase() + ".api.pvp.net/observer-mode/rest/consumer/getSpectatorGameInfo/" + region + "1/" + summonerID + "?" + API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
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

                        JSONArray playerList = jo.getJSONArray("participants");
                        for(int i = 0; i < playerList.length(); i++) {
                            JSONObject player = playerList.getJSONObject(i);

                            String summonerName = player.getString("summonerName");
                            allNames.add(summonerName);

                            Long summonerID = player.getLong("summonerId");
                            allIDs.add(summonerID);

                            Long championID = player.getLong("championId");
                            allChampions.add(championID);

                            Long teamID = player.getLong("teamId");
                            allTeams.add(teamID);
                        }
                        if (jo.getJSONArray("bannedChampions").length() != 0) {
                            JSONArray bannedChampions = jo.getJSONArray("bannedChampions");
                            for(int i = 0; i < bannedChampions.length(); i++) {
                                JSONObject bannedChampion = bannedChampions.getJSONObject(i);

                                Long ban = bannedChampion.getLong("championId");
                                allBans.add(ban);
                            }
                        }

                    } finally {
                        urlConnection.disconnect();
                    }

                    if(allIDs.size() > 10) {
                        flag = true;
                    }
                    for(int i = 0; i < allIDs.size(); i++) {
                        if(i > 9){
                            names2 += allIDs.get(i) +",";
                            names2Count++;
                        } else {
                            names += allIDs.get(i) + ",";
                            names1Count++;
                        }
                    }
                    if(flag){
                        names2 = names.substring(0, names.length() - 1);
                    }
                    names = names.substring(0, names.length() - 1);

                    url = new URL("https://" + region.toLowerCase() + ".api.pvp.net/api/lol/" + region.toLowerCase() + "/v2.5/league/by-summoner/" + names + "/entry?" + API_KEY);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    if (urlConnection.getResponseCode() == 200) {
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

                            for(int i = 0; i < names1Count; i++) {
                                String tempSummonerID = String.valueOf(allIDs.get(i));
                                if (jo.has(tempSummonerID)) {
                                    JSONArray test = jo.getJSONArray(tempSummonerID);
                                    JSONObject jsonObject = test.getJSONObject(0);

                                    tier = jsonObject.getString("tier");
                                    tier = tier.substring(0, 1) + tier.substring(1).toLowerCase();

                                    JSONArray entries = jsonObject.getJSONArray("entries");
                                    JSONObject details = entries.getJSONObject(0);
                                    lp = details.getInt("leaguePoints");
                                    division = details.getString("division");
                                    String rank = tier + " " + division + " (" + lp + " LP)";
                                    allRanks.add(rank);
                                } else {
                                    allRanks.add("Unranked");
                                }
                            }

                        } finally {
                            urlConnection.disconnect();
                        }

                    } else if (urlConnection.getResponseCode() == 404) {
                        for(int i = 0; i < names2Count; i++) {
                            allRanks.add("Unranked");
                        }
                        urlConnection.disconnect();
                    } else if (urlConnection.getResponseCode() == 500) {
                        failQueryMessage = "Could not make a connection with the server. Please check your internet settings and try again";
                        successfulQuery = false;
                        return result;
                    } else if (urlConnection.getResponseCode() == 503) {
                        failQueryMessage = "Could not complete the request as Riot servers are down. Please try again shortly";
                        successfulQuery = false;
                        return result;
                    }

                    if (flag) {
                        url = new URL("https://" + region.toLowerCase() + ".api.pvp.net/api/lol/" + region.toLowerCase() + "/v2.5/league/by-summoner/" + names2 + "/entry?" + API_KEY);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        if (urlConnection.getResponseCode() == 200) {
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

                                for (int i = 0; i < names2Count; i++) {
                                    String tempSummonerID = String.valueOf(allIDs.get(10 + i));
                                    if (jo.has(tempSummonerID)) {
                                        JSONArray test = jo.getJSONArray(tempSummonerID);
                                        JSONObject jsonObject = test.getJSONObject(0);

                                        tier = jsonObject.getString("tier");
                                        tier = tier.substring(0, 1) + tier.substring(1).toLowerCase();

                                        JSONArray entries = jsonObject.getJSONArray("entries");
                                        JSONObject details = entries.getJSONObject(0);
                                        lp = details.getInt("leaguePoints");
                                        division = details.getString("division");
                                        String rank = tier + " " + division + " (" + lp + " LP)";
                                        allRanks.add(rank);
                                    } else {
                                        allRanks.add("Unranked");
                                    }
                                }

                            } finally {
                                urlConnection.disconnect();
                            }
                        }

                    } else if (urlConnection.getResponseCode() == 404) {
                        for(int i = 0; i < names2Count; i++) {
                            allRanks.add("Unranked");
                        }
                        urlConnection.disconnect();
                    } else if (urlConnection.getResponseCode() == 500) {
                        failQueryMessage = "Could not make a connection with the server. Please check your internet settings and try again";
                        successfulQuery = false;
                        return result;
                    } else if (urlConnection.getResponseCode() == 503) {
                        failQueryMessage = "Could not complete the request as Riot servers are down. Please try again shortly";
                        successfulQuery = false;
                        return result;
                    }

                result = "Successful parse";
                compileChampList();
                return result;


                } else if (urlConnection.getResponseCode() == 404) {
                    failQueryMessage = "User is not in game at the moment.";
                    successfulQuery = false;
                    Log.i("INFO", "No game");

                } else if (urlConnection.getResponseCode() == 500) {
                    failQueryMessage = "Could not make a connection with the server. Please check your internet settings and try again";
                    successfulQuery = false;
                    Log.i("INFO", "No connection");

                } else if (urlConnection.getResponseCode() == 503) {
                    failQueryMessage = "Could not complete the request as Riot servers are down. Please try again shortly";
                    successfulQuery = false;
                    Log.i("INFO", "No server");

                } else {
                    successfulQuery = false;
                    return null;
                }

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                successfulQuery = false;
                return result;
            }

            return result;
        }


        protected void onPostExecute(String response) {
            if (response == null) {
                successfulQuery = false;
                failQueryMessage = "An unknown error has occurred";
            }

            if(response != null)
            {
                progressBar.setVisibility(View.GONE);
                Log.i("INFO", response);
                if(successfulQuery) {
                    responseView.setText("");
                    int sumResId;
                    int champResId;
                    int rankResId;
                    int banResId;
                    int blueTeamCount = 1;
                    int redTeamCount = 7;

                    for (int i = 0; i < allNames.size(); i++) {
                        if (allTeams.get(i) == 100) {
                            sumResId = getResources().getIdentifier("summoner" + (blueTeamCount), "id", getPackageName());
                            champResId = getResources().getIdentifier("champion" + (blueTeamCount), "id", getPackageName());
                            rankResId = getResources().getIdentifier("rank" + (blueTeamCount), "id", getPackageName());
                            blueTeamCount++;
                        } else {
                            sumResId = getResources().getIdentifier("summoner" + (redTeamCount), "id", getPackageName());
                            champResId = getResources().getIdentifier("champion" + (redTeamCount), "id", getPackageName());
                            rankResId = getResources().getIdentifier("rank" + (redTeamCount), "id", getPackageName());
                            redTeamCount++;
                        }

                        String champNameString = champIDList.get((int)(long)(allChampions.get(i)));
                        champNameString = champNameString.replaceAll("[^A-Za-z]+","");
                        champNameString = champNameString.toLowerCase();
                        String imageString = "champ_" + champNameString;
                        int id = getResources().getIdentifier(imageString, "drawable", getPackageName());
                        ImageView champion = (ImageView) findViewById(champResId);
                        champion.setImageResource(id);

                        TextView summoner = (TextView) findViewById(sumResId);
                        TextView rank = (TextView) findViewById(rankResId);
                        summoner.setText(allNames.get(i));
                        rank.setText(allRanks.get(i));

                    }

                    for (int i = 0; i < allBans.size(); i++) {
                        banResId = getResources().getIdentifier("ban" + (i + 1), "id", getPackageName());
                        String champNameString = champIDList.get((int)(long)(allBans.get(i)));
                        champNameString = champNameString.replaceAll("[^A-Za-z]+","");
                        champNameString = champNameString.toLowerCase();
                        String imageString = "champ_" + champNameString;
                        int id = getResources().getIdentifier(imageString, "drawable", getPackageName());
                        ImageView champion = (ImageView) findViewById(banResId);
                        champion.setImageResource(id);
                    }
                }
                else
                {
                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);

                    int width = dm.widthPixels;
                    int height = dm.heightPixels;

                    setContentView(R.layout.errormessage);
                    getWindow().setLayout(width, height);
                    ImageView errorImage = (ImageView)findViewById(R.id.imageView);
                    TextView errorText = (TextView)findViewById(R.id.errorMessage);
                    errorImage.setImageResource(R.drawable.errorimage);
                    errorText.setText(failQueryMessage);

                }

            }

        }

    }

}
