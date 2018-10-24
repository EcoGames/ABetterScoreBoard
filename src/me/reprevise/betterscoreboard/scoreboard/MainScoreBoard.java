package me.reprevise.betterscoreboard.scoreboard;

import me.reprevise.betterscoreboard.ABetterScoreBoardMain;

public class MainScoreBoard {

    private static Board scoreboard;
    private ABetterScoreBoardMain main;

    public MainScoreBoard(ABetterScoreBoardMain mainInstance) {
        this.main = mainInstance;
        scoreboard = new Board(main.getConfig().getString("title"));
    }

    public static Board getScoreboard() {
        return scoreboard;
    }

}
