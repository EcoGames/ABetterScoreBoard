package me.reprevise.betterscoreboard.scoreboard;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import static org.bukkit.scoreboard.DisplaySlot.SIDEBAR;

public class Board {

    private final Scoreboard scoreboard;
    private final Objective objective;

    public Board(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("Board", "dummy", ChatColor.translateAlternateColorCodes('&', title));

        this.objective.setDisplaySlot(SIDEBAR);
    }

    public Board(Player player) {
        this.scoreboard = player.getScoreboard();
        this.objective = scoreboard.getObjective(SIDEBAR);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public String getTitle() {
        return objective.getDisplayName();
    }

    public void setTitle(String name) {
        this.objective.setDisplayName(name);
    }

    public void set(int row, String text) {
        Validate.isTrue(16 > row, "Row can't be higher than 16");
        if (text.length() > 32) {
            text = text.substring(0, 32);
        }

        for (String entry : this.scoreboard.getEntries()) {
            if (this.objective.getScore(entry).getScore() == row) {
                this.scoreboard.resetScores(entry);
                break;
            }
        }

        this.objective.getScore(text).setScore(row);
    }

    public void remove(int row) {
        for (String entry : this.scoreboard.getEntries()) {
            if (this.objective.getScore(entry).getScore() == row) {
                this.scoreboard.resetScores(entry);
                break;
            }
        }
    }
}
