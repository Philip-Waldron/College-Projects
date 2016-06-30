package com.lsa.lol.summonerapp;

/**
 * Created by rober on 28/04/2016.
 */
public class playerInGame {
    private int championID;
    private int teamID;
    private int summonerID;
    private String summonerName;

    public playerInGame(int championID, int teamID, int summonerID)
    {
        super();
        this.championID = championID;
        this.teamID = teamID;
        this.summonerID = summonerID;
        this.summonerName = "";
    }

    public int getChampionID()
    {
        return championID;
    }

    public int getTeamID()
    {
        int tempTeamID = teamID;
        return tempTeamID;
    }

    public int getSummonerID()
    {
        return summonerID;
    }

    public String getSummonerName()
    {
        return summonerName;
    }

    public void setSummonerName(String inputSummonerName)
    {
        summonerName = inputSummonerName;
    }
}
