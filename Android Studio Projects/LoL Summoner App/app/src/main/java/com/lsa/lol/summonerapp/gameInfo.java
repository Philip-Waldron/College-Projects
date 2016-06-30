package com.lsa.lol.summonerapp;

/**
 * Created by rober on 28/04/2016.
 */
public class gameInfo {
    private String gameMode;
    private boolean gameWon;
    private int gameTime;
    private int champID;
    private int kills;
    private int deaths;
    private int assists;
    private int minionsKilled;
    private int summoner1;
    private int summoner2;


    public gameInfo(String gameMode, boolean gameWon, int gameTime, int champID, int kills, int deaths, int assists,
                    int minionsKilled, int summoner1, int summoner2)
    {
        super();
        this.gameMode = gameMode;
        this.gameWon = gameWon;
        this.gameTime = gameTime;
        this.champID = champID;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.minionsKilled = minionsKilled;
        this.summoner1 = summoner1;
        this.summoner2 = summoner2;
    }

    public String getGameMode()
    {
        return gameMode;
    }

    public boolean getGameWon()
    {
        return gameWon;
    }

    public int getGameTime()
    {
        return gameTime;
    }

    public int getChampID()
    {
        return champID;
    }

    public int getKills()
    {
        return kills;
    }


    public int getDeaths()
    {
        return deaths;
    }
    public int getAssists()
    {
        return assists;
    }

    public int getMinionsKilled()
    {
        return minionsKilled;
    }

    public int getSummoner1()
    {
        return summoner1;
    }

    public int getSummoner2()
    {
        return summoner2;
    }
}
