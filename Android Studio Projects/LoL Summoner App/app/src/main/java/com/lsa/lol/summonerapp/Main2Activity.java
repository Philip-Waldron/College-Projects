package com.lsa.lol.summonerapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends Activity {

    String summoner;
    String summonerDisplayName;
    String region;
    TextView responseView;
    ProgressBar progressBar;
    int summonerID;
    int summonerLevel;
    int profileIcon;
    static final String API_KEY = "api_key=add66013-808b-4498-8b34-30393d207991";
    boolean successfulQuery = true;
    String failQueryMessage;
    String leagueName;
    String tier;
    String division;
    int wins;
    int losses;
    int lp;
    boolean matchHistorySuccess = true;
    ArrayList<ArrayList<playerInGame>> playersInGame = new ArrayList<>();
    ArrayList<gameInfo> gameStats = new ArrayList<>();
    final Map<Integer, String> champIDList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        responseView = (TextView)findViewById(R.id.responseView);
        summoner = intent.getStringExtra("SUMMONER");
        region = intent.getStringExtra("REGION");
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        summoner = summoner.replace(" ","%20");
        new RetrieveFeedTask().execute();

        Button gameButton = (Button) findViewById(R.id.currentMatchButton);
        gameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, InGameActivity.class);
                intent.putExtra("REGION", region);
                intent.putExtra("SUMMONERID", summonerID);
                startActivity(intent);
            }
        });
    }


    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("Loading");
        }

        protected String doInBackground(Void... urls) {
            String result = "";
            compileChampList();
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
                        String tempSummoner = summoner.replace("%20", "");
                        tempSummoner = tempSummoner.toLowerCase();
                        JSONObject test = jo.getJSONObject(tempSummoner);
                        summonerID = test.getInt("id");
                        summonerLevel = test.getInt("summonerLevel");
                        profileIcon = test.getInt("profileIconId");
                        summonerDisplayName = test.getString("name");
                    } finally {
                        urlConnection.disconnect();
                    }
                }
                else if(urlConnection.getResponseCode() == 404)
                {
                    failQueryMessage = "No summoner found with that name. Please ensure the region selected and name are correct.";
                    successfulQuery = false;
                    return result;
                }
                else if(urlConnection.getResponseCode() == 500)
                {
                    failQueryMessage = "Could not make a connection with the server. Please check your internet settings and try again";
                    successfulQuery = false;
                    return result;
                }
                else if(urlConnection.getResponseCode() == 503)
                {
                    failQueryMessage = "Could not complete the request as Riot servers are down. Please try again shortly";
                    successfulQuery = false;
                    return result;
                }

            }catch(Exception e){
                Log.e("ERROR", e.getMessage(), e);
                successfulQuery = false;
                return result;
            }

            try {
                URL url = new URL("https://" + region.toLowerCase() + ".api.pvp.net/api/lol/" + region.toLowerCase() + "/v2.5/league/by-summoner/" + summonerID + "/entry?" + API_KEY);
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
                        String tempSummonerID = String.valueOf(summonerID);
                        JSONArray test = jo.getJSONArray(tempSummonerID);
                        JSONObject jsonObject = test.getJSONObject(0);
                        leagueName = jsonObject.getString("name");
                        tier = jsonObject.getString("tier");
                        tier = tier.substring(0,1) + tier.substring(1).toLowerCase();
                        JSONArray entries = jsonObject.getJSONArray("entries");
                        JSONObject details = entries.getJSONObject(0);
                        lp = details.getInt("leaguePoints");
                        division = details.getString("division");
                        wins = details.getInt("wins");
                        losses = details.getInt("losses");
                    } finally {
                        urlConnection.disconnect();
                    }
                }
                else if(urlConnection.getResponseCode() == 404)
                {
                    leagueName = "";
                    tier = "Unranked";
                    division = "";

                }
                else if(urlConnection.getResponseCode() == 500)
                {
                    failQueryMessage = "Could not make a connection with the server. Please check your internet settings and try again";
                    successfulQuery = false;
                    return result;
                }
                else if(urlConnection.getResponseCode() == 503)
                {
                    failQueryMessage = "Could not complete the request as Riot servers are down. Please try again shortly";
                    successfulQuery = false;
                    return result;
                }

            }catch(Exception e){
                Log.e("ERROR", e.getMessage(), e);
                successfulQuery = false;
                return result;
            }

            try {
                URL url = new URL("https://" + region.toLowerCase() + ".api.pvp.net/api/lol/" + region.toLowerCase() + "/v1.3/game/by-summoner/" + summonerID + "/recent?" + API_KEY);
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
                            if(gameInfo.has("fellowPlayers"))
                            {
                                JSONArray fellowPlayers = gameInfo.getJSONArray("fellowPlayers");
                                ArrayList<playerInGame> playerSet = new ArrayList<>();
                                playerSet.add(new playerInGame(gameInfo.getInt("teamId"), gameInfo.getInt("championId"), summonerID));
                                playerSet.get(0).setSummonerName(summoner.replace("%20", " "));
                                for(int j=0; j < fellowPlayers.length(); j++)
                                {
                                    JSONObject playerData = fellowPlayers.getJSONObject(j);
                                    int tempChampID = playerData.getInt("championId");
                                    int tempTeamID = playerData.getInt("teamId");
                                    int tempSummonerID = playerData.getInt("summonerId");
                                    playerSet.add(new playerInGame(tempChampID, tempTeamID, tempSummonerID));
                                }
                                playersInGame.add(playerSet);
                            }
                            else
                            {
                                result = "No Match Data Found";
                                matchHistorySuccess = false;
                                return result;
                            }

                            JSONObject stats = gameInfo.getJSONObject("stats");
                            String tempGameMode = gameInfo.getString("subType");
                            boolean tempGameWon = stats.getBoolean("win");
                            int tempGameTime = stats.getInt("timePlayed");
                            int tempChampID = gameInfo.getInt("championId");
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

                            int tempMinionsKilled = 0;
                            if(stats.has("minionsKilled"))
                            {
                                tempMinionsKilled = stats.getInt("minionsKilled");
                            }

                            int tempSummoner1 = gameInfo.getInt("spell1");
                            int tempSummoner2 = gameInfo.getInt("spell2");
                            gameStats.add(new gameInfo(tempGameMode, tempGameWon, tempGameTime, tempChampID, tempKills, tempDeaths,
                                    tempAssists, tempMinionsKilled, tempSummoner1, tempSummoner2));

                        }

                    } finally {
                        urlConnection.disconnect();
                    }
                }
                else if(urlConnection.getResponseCode() == 404)
                {
                    failQueryMessage = "Game Data could not be found";
                    successfulQuery = false;
                    return result;

                }
                else if(urlConnection.getResponseCode() == 500)
                {
                    failQueryMessage = "Could not make a connection with the server. Please check your internet settings and try again";
                    successfulQuery = false;
                    return result;
                }
                else if(urlConnection.getResponseCode() == 503)
                {
                    failQueryMessage = "Could not complete the request as Riot servers are down. Please try again shortly";
                    successfulQuery = false;
                    return result;
                }

            }catch(Exception e){
                Log.e("ERROR", e.getMessage(), e);
                successfulQuery = false;
                return result;
            }

            for(int i = 0; i < gameStats.size(); i++)
            {
                try {
                    String summonerIDs = "";
                    for (int j = 0; j < playersInGame.get(i).size(); j++)
                    {
                        if(playersInGame != null && playersInGame.get(i).get(j) != null)
                        {
                            summonerIDs += playersInGame.get(i).get(j).getSummonerID() + ",";
                        }
                    }
                    summonerIDs = summonerIDs.substring(0, summonerIDs.length() - 1);
                    URL url = new URL("https://" + region.toLowerCase() + ".api.pvp.net/api/lol/" + region.toLowerCase() + "/v1.4/summoner/" +  summonerIDs + "?" + API_KEY);
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

                            for(int j = 0; j < playersInGame.get(i).size(); j++)
                            {
                                String tempSummonerID = String.valueOf(playersInGame.get(i).get(j).getSummonerID());
                                if(jo.has(tempSummonerID))
                                {

                                    JSONObject summonerInfo = jo.getJSONObject(tempSummonerID);
                                    String tempSummonerName = summonerInfo.getString("name");
                                    playersInGame.get(i).get(j).setSummonerName(tempSummonerName);
                                }
                            }

                        } finally {
                            urlConnection.disconnect();
                        }
                    }
                    else if(urlConnection.getResponseCode() == 404)
                    {
                        failQueryMessage = "Summoner Info could not be found";
                        successfulQuery = false;
                        return result;

                    }
                    else if(urlConnection.getResponseCode() == 500)
                    {
                        failQueryMessage = "Could not make a connection with the server. Please check your internet settings and try again";
                        successfulQuery = false;
                        return result;
                    }
                    else if(urlConnection.getResponseCode() == 503)
                    {
                        failQueryMessage = "Could not complete the request as Riot servers are down. Please try again shortly";
                        successfulQuery = false;
                        return result;
                    }

                }catch(Exception e){
                    Log.e("ERROR", e.getMessage(), e);
                    successfulQuery = false;
                    return result;
                }
            }

            return result;
        }

        protected void onPostExecute(String response) {

            if(successfulQuery)
            {
                ImageView profileImage = (ImageView) findViewById(R.id.profileImage);
                ImageView tierImage = (ImageView) findViewById(R.id.tierImage);
                TextView profileLevel = (TextView)findViewById(R.id.summonerLevel);
                TextView profileName = (TextView)findViewById(R.id.summonerName);
                TextView profileDivision = (TextView)findViewById(R.id.divisionAndTier);
                TextView rankedStats = (TextView)findViewById(R.id.stats);
                TextView leagueNameView =(TextView)findViewById(R.id.leagueName);
                TextView profileLevelSubText = (TextView)findViewById(R.id.summonerLevelSubtext);
                TextView profileRankSubText = (TextView)findViewById(R.id.summonerRankSubtext);

                String imageString = "profileicon_" + profileIcon;
                int id = getResources().getIdentifier(imageString, "drawable", getPackageName());
                profileImage.setImageResource(id);

                if(tier != null)
                {
                    if(tier.equals("Unranked"))
                    {
                        tierImage.setImageResource(R.drawable.provisional);
                        rankedStats.setText("");
                        leagueNameView.setText("");
                    }

                    else
                    {
                        int tierID = getResources().getIdentifier(tier.toLowerCase(), "drawable", getPackageName());
                        tierImage.setImageResource(tierID);
                        String rankedStatsString = lp + "LP " + wins + "W " + losses + "L ";
                        rankedStats.setText(rankedStatsString);

                        leagueNameView.setText(leagueName);
                    }
                }




                profileName.setText(summonerDisplayName);

                String DivisionAndTierString = tier + " " + division;
                profileDivision.setText(DivisionAndTierString);

                String summonerLevelAsString = String.valueOf(summonerLevel);
                profileLevel.setText(summonerLevelAsString);

                profileLevelSubText.setText("Level " + summonerLevelAsString);
                profileRankSubText.setText(tier + " " + division);


                final ListView listview = (ListView) findViewById(R.id.listview);
                final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(getApplicationContext(),gameStats,playersInGame);
                if(matchHistorySuccess)
                {
                    listview.setAdapter(adapter);
                }
                else
                {
                    TextView matchHistoryFail = (TextView)findViewById(R.id.noMatchHistoryFound);
                    matchHistoryFail.setText("No match history could be found for this user");
                }

                progressBar.setVisibility(View.GONE);
            }
            else
            {
                progressBar.setVisibility(View.GONE);
                setContentView(R.layout.errormessage);
                ImageView errorImage = (ImageView)findViewById(R.id.imageView);
                TextView errorText = (TextView)findViewById(R.id.errorMessage);
                errorImage.setImageResource(R.drawable.errorimage);
                errorText.setText(failQueryMessage);
            }

        }
    }




    public class MySimpleArrayAdapter extends BaseAdapter {

      private LayoutInflater inflater;
        ArrayList<gameInfo> gameStats = new ArrayList<>();
        ArrayList<ArrayList<playerInGame>> playersInGame = new ArrayList<>();

      public MySimpleArrayAdapter(Context context, ArrayList<gameInfo> gameStatsArray, ArrayList<ArrayList<playerInGame>> playersInGameArray ) {
        gameStats = gameStatsArray;
        playersInGame = playersInGameArray;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      }

      @Override
      public int getCount() {
        return 7;
      }

      @Override
      public Object getItem(int position) {
        return position;
      }

      @Override
      public long getItemId(int position) {
        return position;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        View currentView = convertView;

        if(currentView==null) {
          currentView = inflater.inflate(R.layout.listviewitem, parent, false);
        }
          String WinOrLoss;
          if(gameStats.get(position).getGameWon())
          {
              currentView.setBackgroundColor(Color.parseColor("#ccffcc"));
              WinOrLoss = "Victory";
          }
          else
          {
              currentView.setBackgroundColor(Color.parseColor("#ffd6cc"));
              WinOrLoss = "Defeat";
          }

          Collections.sort(playersInGame.get(position), new Comparator<playerInGame>() {
              @Override public int compare(playerInGame p1, playerInGame p2) {
                  return p1.getTeamID() - p2.getTeamID();
              }

          });

          TextView gameModeText = (TextView)currentView.findViewById(R.id.gameMode);
          TextView gameResultAndTimeText = (TextView)currentView.findViewById(R.id.gameResultAndTime);
          TextView kdaText = (TextView)currentView.findViewById(R.id.KillsDeathsAssists);
          TextView calculatedKDAText = (TextView)currentView.findViewById(R.id.KDA);
          TextView minionsKilledText = (TextView)currentView.findViewById(R.id.creepScore);
          ImageView mainChampionImage = (ImageView)currentView.findViewById(R.id.champImage);
          ImageView spell1Image = (ImageView)currentView.findViewById(R.id.spell1);
          ImageView spell2Image = (ImageView)currentView.findViewById(R.id.spell2);

          TextView player1Text = (TextView)currentView.findViewById(R.id.playerName1);
          TextView player2Text = (TextView)currentView.findViewById(R.id.playerName2);
          TextView player3Text = (TextView)currentView.findViewById(R.id.playerName3);
          TextView player4Text = (TextView)currentView.findViewById(R.id.playerName4);
          TextView player5Text = (TextView)currentView.findViewById(R.id.playerName5);
          TextView player6Text = (TextView)currentView.findViewById(R.id.playerName6);
          TextView player7Text = (TextView)currentView.findViewById(R.id.playerName7);
          TextView player8Text = (TextView)currentView.findViewById(R.id.playerName8);
          TextView player9Text = (TextView)currentView.findViewById(R.id.playerName9);
          TextView player10Text = (TextView)currentView.findViewById(R.id.playerName10);


          String champNameString = champIDList.get(gameStats.get(position).getChampID());
          if(champNameString != null)
          {
              champNameString = champNameString.replaceAll("[^A-Za-z]+","");
              champNameString = champNameString.toLowerCase();
              String imageString = "champ_" + champNameString;
              int id = getResources().getIdentifier(imageString, "drawable", getPackageName());
              mainChampionImage.setImageResource(id);
          }


          if (playersInGame.get(position).get(0).getSummonerName() != null)
          {
              player1Text.setText(playersInGame.get(position).get(0).getSummonerName());
          }

          if (playersInGame.get(position).get(1).getSummonerName() != null)
          {
              player2Text.setText(playersInGame.get(position).get(1).getSummonerName());
          }

          if (playersInGame.get(position).get(2).getSummonerName() != null)
          {
              player3Text.setText(playersInGame.get(position).get(2).getSummonerName());
          }

          if (playersInGame.get(position).get(3).getSummonerName() != null)
          {
              player4Text.setText(playersInGame.get(position).get(3).getSummonerName());
          }

          if (playersInGame.get(position).get(4).getSummonerName() != null)
          {
              player5Text.setText(playersInGame.get(position).get(4).getSummonerName());
          }

          if (playersInGame.get(position).get(5).getSummonerName() != null)
          {
              player6Text.setText(playersInGame.get(position).get(5).getSummonerName());
          }

          if (playersInGame.get(position).get(6).getSummonerName() != null)
          {
              player7Text.setText(playersInGame.get(position).get(6).getSummonerName());
          }

          if (playersInGame.get(position).get(7).getSummonerName() != null)
          {
              player8Text.setText(playersInGame.get(position).get(7).getSummonerName());
          }

          if (playersInGame.get(position).get(8).getSummonerName() != null)
          {
              player9Text.setText(playersInGame.get(position).get(8).getSummonerName());
          }

          if (playersInGame.get(position).get(9).getSummonerName() != null)
          {
              player10Text.setText(playersInGame.get(position).get(9).getSummonerName());
          }




          String gameMode = gameStats.get(position).getGameMode();
          String gameModeString;
          switch(gameMode)
          {
              case "NONE":
                  gameModeString = "Custom Game";
                  break;

              case "NORMAL":
                  gameModeString = "Normal Game";
                  break;

              case "BOT":
                  gameModeString = "Bot Game";
                  break;

              case "RANKED_SOLO_5x5":
                  gameModeString = "Solo Ranked";
                  break;

              case "RANKED_PREMADE_3x3":
                  gameModeString = "Twisted Treeline Ranked";
                  break;

              case "RANKED_PREMADE_5x5":
                  gameModeString = "Dynamic Ranked";
                  break;

              case "NORMAL_3x3":
                  gameModeString = "Unranked Twisted Treeline";
                  break;

              case "BOT_3x3":
                  gameModeString = "Twisted Treeline (Bot Game)";
                  break;

              case "ARAM_UNRANKED_5x5":
                  gameModeString = "ARAM";
                  break;

              case "ONEFORALL_5x5":
                  gameModeString = "One For All";
                  break;

              case " FIRSTBLOOD_1x1":
                  gameModeString = "Snowdown Showdown";
                  break;

              case "FIRSTBLOOD_2x2":
                  gameModeString = "Snowdown Showdown";
                  break;

              case "SR_6x6":
                  gameModeString = "Hexakill";
                  break;

              case "URF":
                  gameModeString = "URF";
                  break;

              case "NIGHTMARE_BOT":
                  gameModeString = "Doom Bots";
                  break;

              case "ASCENSION":
                  gameModeString = "Ascension";
                  break;

              case "HEXAKILL":
                  gameModeString = "Hexakill";
                  break;

              case "KING_PORO":
                  gameModeString = "Poro King";
                  break;

              case "COUNTER_PICK":
                  gameModeString = "Nemesis Draft";
                  break;

              case "BILGEWATER":
                  gameModeString = "Black Market Brawlers";
                  break;

              default :
                  gameModeString = "Unknown";

          }

          gameModeText.setText(gameModeString);

          int minutesPlayed = (gameStats.get(position).getGameTime() % 3600) / 60;
          int seconds = (gameStats.get(position).getGameTime() % 3600) % 60;
          String gameResultAndTime = WinOrLoss + " in " + minutesPlayed + "m " + seconds + "s";
          gameResultAndTimeText.setText(gameResultAndTime);

          String kdaAsString = gameStats.get(position).getKills() + "/" + gameStats.get(position).getDeaths() + "/" +
                               gameStats.get(position).getAssists();
          kdaText.setText(kdaAsString);

          float KDA;
          String calculatedKDAString = "";

          if(gameStats.get(position).getDeaths() == 0)
          {
              KDA = (float) gameStats.get(position).getKills() + gameStats.get(position).getAssists();
          }

          else KDA = (float) (gameStats.get(position).getKills() + gameStats.get(position).getAssists()) / gameStats.get(position).getDeaths();

          calculatedKDAString = calculatedKDAString.format("%.2f", KDA) + " KDA";
          calculatedKDAText.setText(calculatedKDAString);

          String minionsKilledString = gameStats.get(position).getMinionsKilled() + "cs";
          minionsKilledText.setText(minionsKilledString);

          String spell1String = String.valueOf(gameStats.get(position).getSummoner1());
          spell1String = "spell_" + spell1String;
          int spell1id = getResources().getIdentifier(spell1String, "drawable", getPackageName());
          spell1Image.setImageResource(spell1id);

          String spell2String = String.valueOf(gameStats.get(position).getSummoner2());
          spell2String = "spell_" + spell2String;
          int spell2id = getResources().getIdentifier(spell2String, "drawable", getPackageName());
          spell2Image.setImageResource(spell2id);


        return currentView;
      }
}


    public void compileChampList()
    {
        champIDList.put(1,"Annie");
        champIDList.put(2,"Olaf");
        champIDList.put(3,"Galio");
        champIDList.put(4,"Twisted Fate");
        champIDList.put(5,"Xin Zhao");
        champIDList.put(6,"Urgot");
        champIDList.put(7,"LeBlanc");
        champIDList.put(8,"Vladimir");
        champIDList.put(9,"Fiddlesticks");
        champIDList.put(10,"Kayle");
        champIDList.put(11,"Master Yi");
        champIDList.put(12,"Alistar");
        champIDList.put(13,"Ryze");
        champIDList.put(14,"Sion");
        champIDList.put(15,"Sivir");
        champIDList.put(16,"Soraka");
        champIDList.put(17,"Teemo");
        champIDList.put(18,"Tristana");
        champIDList.put(19,"Warwick");
        champIDList.put(20,"Nunu");
        champIDList.put(21,"Miss Fortune");
        champIDList.put(22,"Ashe");
        champIDList.put(23,"Tryndamere");
        champIDList.put(24,"Jax");
        champIDList.put(25,"Morgana");
        champIDList.put(26,"Zilean");
        champIDList.put(27,"Singed");
        champIDList.put(28,"Evelynn");
        champIDList.put(29,"Twitch");
        champIDList.put(30,"Karthus");
        champIDList.put(31,"Cho'Gath");
        champIDList.put(32,"Amumu");
        champIDList.put(33,"Raummus");
        champIDList.put(34,"Anivia");
        champIDList.put(35,"Shaco");
        champIDList.put(36,"Dr. Mundo");
        champIDList.put(37,"Sona");
        champIDList.put(38,"Kassadin");
        champIDList.put(39,"Irelia");
        champIDList.put(40,"Janna");
        champIDList.put(41,"Gangplank");
        champIDList.put(42,"Corki");
        champIDList.put(43,"Karma");
        champIDList.put(44,"Taric");
        champIDList.put(45,"Veigar");
        champIDList.put(48,"Trundle");
        champIDList.put(50,"Swain");
        champIDList.put(51,"Caitlyn");
        champIDList.put(53,"Blitzcrank");
        champIDList.put(54,"Malphite");
        champIDList.put(55,"Katarina");
        champIDList.put(56,"Nocturne");
        champIDList.put(57,"Maokai");
        champIDList.put(58,"Renekton");
        champIDList.put(59,"Jarvan IV");
        champIDList.put(60,"Elise");
        champIDList.put(61,"Orianna");
        champIDList.put(62,"Wukong");
        champIDList.put(63,"Brand");
        champIDList.put(64,"Lee Sin");
        champIDList.put(67,"Vayne");
        champIDList.put(68,"Rumble");
        champIDList.put(69,"Cassiopeia");
        champIDList.put(72,"Skarner");
        champIDList.put(74,"Heimerdinger");
        champIDList.put(75,"Nasus");
        champIDList.put(76,"Nidalee");
        champIDList.put(77,"Udyr");
        champIDList.put(78,"Poppy");
        champIDList.put(79,"Gragas");
        champIDList.put(80,"Pantheon");
        champIDList.put(81,"Ezreal");
        champIDList.put(82,"Mordekaiser");
        champIDList.put(83,"Yorick");
        champIDList.put(84,"Akali");
        champIDList.put(85,"Kennen");
        champIDList.put(86,"Garen");
        champIDList.put(89,"Leona");
        champIDList.put(90,"Malzahar");
        champIDList.put(91,"Talon");
        champIDList.put(92,"Riven");
        champIDList.put(96,"Kog'Maw");
        champIDList.put(98,"Shen");
        champIDList.put(99,"Lux");
        champIDList.put(101,"Xerath");
        champIDList.put(102,"Shyvana");
        champIDList.put(103,"Ahri");
        champIDList.put(104,"Graves");
        champIDList.put(105,"Fizz");
        champIDList.put(106,"Volibear");
        champIDList.put(107,"Rengar");
        champIDList.put(110,"Varus");
        champIDList.put(111,"Nautilus");
        champIDList.put(112,"Viktor");
        champIDList.put(113,"Sejuani");
        champIDList.put(114,"Fiora");
        champIDList.put(115,"Ziggs");
        champIDList.put(117,"Lulu");
        champIDList.put(119,"Draven");
        champIDList.put(120,"Hecarim");
        champIDList.put(121,"Kha'Zix");
        champIDList.put(122,"Darius");
        champIDList.put(126,"Jayce");
        champIDList.put(127,"Lissandra");
        champIDList.put(131,"Diana");
        champIDList.put(133,"Quinn");
        champIDList.put(134,"Syndra");
        champIDList.put(136,"Aurelion Sol");
        champIDList.put(143,"Zyra");
        champIDList.put(150,"Gnar");
        champIDList.put(154,"Zac");
        champIDList.put(157,"Yasuo");
        champIDList.put(161,"Vel'Koz");
        champIDList.put(201,"Braum");
        champIDList.put(202,"Jhin");
        champIDList.put(203,"Kindred");
        champIDList.put(222,"Jinx");
        champIDList.put(223,"Tahm Kench");
        champIDList.put(236,"Lucian");
        champIDList.put(238,"Zed");
        champIDList.put(245,"Ekko");
        champIDList.put(254,"Vi");
        champIDList.put(266,"Aatrox");
        champIDList.put(267,"Nami");
        champIDList.put(268,"Azir");
        champIDList.put(412,"Thresh");
        champIDList.put(420,"Illaoi");
        champIDList.put(421,"Rek'Sai");
        champIDList.put(429,"Kalista");
        champIDList.put(432,"Bard");


    }
}
