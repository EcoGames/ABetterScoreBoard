package me.reprevise.betterscoreboard.commands;

import me.reprevise.betterscoreboard.ABetterScoreBoardMain;
import me.reprevise.betterscoreboard.scoreboard.MainScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BsbCommand implements CommandExecutor {

    private ABetterScoreBoardMain mainInstance;

    public BsbCommand(ABetterScoreBoardMain mainInstance) {
        this.mainInstance = mainInstance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (checkPlayer(commandSender) == false) {

            commandSender.sendMessage(ChatColor.DARK_RED + "You must be a player to use this command!");
            return true;
        }

        Player sender = (Player) commandSender;

        if (args.length == 0) {

            sendHelpMessages(sender);

        } else if (args.length == 1) {

            if (args[0].equalsIgnoreCase("help"))
                sendHelpMessages(sender);
            else if (args[0].equalsIgnoreCase("removeline"))
                sender.sendMessage(ChatColor.RED + "You need to add the line number to remove. Ex. /bsb removeline 1");
            else if (args[0].equalsIgnoreCase("toggle")) {
                if (sender.getScoreboard() != null)
                    sender.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                else if (sender.getScoreboard() == null)
                    sender.setScoreboard(MainScoreBoard.getScoreboard().getScoreboard());
            } else if (args[0].equalsIgnoreCase("reload")) {
                mainInstance.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "The " + ChatColor.GOLD + ChatColor.BOLD + "ABetterScoreBoard"
                        + ChatColor.GREEN + " config has been reloaded!");
            } else if (args[0].equalsIgnoreCase("reloadscb")) {
                ArrayList<Object> lines = new ArrayList<>();
                for (Object p : mainInstance.getLINE_ARRAY().values().toArray()) {
                    lines.add(p.toString());
                }
                mainInstance.getConfig().set("lines", lines);
                mainInstance.saveConfig();
                mainInstance.reloadConfig();
                int rowNumber = 0;
                Log.info("Reload SCB rowNumber: " +rowNumber);
                for (String s : mainInstance.getConfig().getStringList("lines")) {
                    MainScoreBoard.getScoreboard().set(rowNumber, s);
                    rowNumber++;
                }
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    p.setScoreboard(MainScoreBoard.getScoreboard().getScoreboard());
                }
                sender.sendMessage(ChatColor.GREEN + "Scoreboard has been reloaded!");
            }


        } else if (args.length >= 2) { // checks if cmd has two OR MORE arguments

            if (args[0].equalsIgnoreCase("removeline") && args.length == 2) {
                try {
                    int rowNumber = Integer.parseInt(args[1]);
                    MainScoreBoard.getScoreboard().remove(rowNumber);
                    String s = mainInstance.getLINE_ARRAY().get(rowNumber);
                    mainInstance.getLINE_ARRAY().remove(rowNumber);
                    Log.info("Remove Line rowNumber: " +rowNumber + "   " + s);
                    ArrayList<Object> lines = new ArrayList<>();
                    for (Object p : mainInstance.getLINE_ARRAY().values().toArray()) {
                        lines.add(p.toString());
                    }
                    mainInstance.getConfig().set("lines", lines);
                    mainInstance.saveConfig();
                    mainInstance.reloadConfig();
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + args[2] + " is not a valid number!");
                }

            } else if (args[0].equalsIgnoreCase("addline")) {
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; ++i) {
                    sb.append(args[i]).append(' ');
                }
                String text = sb.toString();
                int rowNumber;
                try {
                    rowNumber = Integer.parseInt(args[1]);
                    MainScoreBoard.getScoreboard().set(rowNumber, text);
                    mainInstance.getLINE_ARRAY().put(rowNumber, text);
                    ArrayList<Object> lines = new ArrayList<>();
                    for (Object p : mainInstance.getLINE_ARRAY().values().toArray()) {
                        lines.add(p.toString());
                    }
                    mainInstance.getConfig().set("lines", lines);
                    Log.info("Add Line rowNumber: " +rowNumber);
                    mainInstance.saveConfig();
                    mainInstance.reloadConfig();
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + args[1] + " is not a valid number!");
                }
            }
        }

        return true;
    }

    private boolean checkPlayer(CommandSender sender) {
        if (sender instanceof Player) {

            return true;

        } else {

            return false;

        }
    }

    private void sendHelpMessages(Player player) {

        String[] helpMessages = new String[]{
                ChatColor.AQUA + "----- BSB Help -----",
                ChatColor.GOLD + "/bsb: Shows this menu.",
                ChatColor.GOLD + "/bsb removeline <row>: Corresponds to the config line number.",
                ChatColor.GOLD + "/bsb addline <row> <text>: Adds a line to the scoreboard",
                ChatColor.GOLD + "/bsb reload: Reloads the config.",
                ChatColor.GOLD + "/bsb reloadscb: Reloads the scoreboard."
        };

        player.sendMessage(helpMessages);

    }

}
