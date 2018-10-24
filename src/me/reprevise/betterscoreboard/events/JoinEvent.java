package me.reprevise.betterscoreboard.events;

import me.reprevise.betterscoreboard.scoreboard.MainScoreBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.getPlayer().setScoreboard(MainScoreBoard.getScoreboard().getScoreboard());

    }

}
