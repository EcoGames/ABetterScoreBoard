package me.reprevise.betterscoreboard;

import me.reprevise.betterscoreboard.commands.BsbCommand;
import me.reprevise.betterscoreboard.events.JoinEvent;
import me.reprevise.betterscoreboard.scoreboard.MainScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class ABetterScoreBoardMain extends JavaPlugin {

    private final HashMap<Integer, String> LINE_MAP = new HashMap<Integer, String>();

    @Override
    public void onEnable() {

        giveInstances();

        saveDefaultConfig();

        int rowNumber = 0;
        for (String s : this.getConfig().getStringList("lines")) {
            LINE_MAP.put(rowNumber, s);
            MainScoreBoard.getScoreboard().set(rowNumber, s);
            rowNumber++;
        }

        registerEvents();
        registerCommands();

    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinEvent(), this);
    }

    private void registerCommands() {
        this.getCommand("bsb").setExecutor(new BsbCommand(this));
    }

    private void giveInstances() {
        new MainScoreBoard(this);
    }

    public HashMap<Integer, String> getLINE_ARRAY() {
        return LINE_MAP;
    }

}
