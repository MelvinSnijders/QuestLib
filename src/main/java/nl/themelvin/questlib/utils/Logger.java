package nl.themelvin.questlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {

    public static String __(String message) {

        return ChatColor.translateAlternateColorCodes('&', message);

    }

    public static void log(Severity severity, String message) {

        Bukkit.getServer().getConsoleSender().sendMessage(__(severity.prefix + "&r" + message));

    }

    public enum Severity {

        INFO("&2[INFO] "),
        WARNING("&6[WARNING] "),
        ERROR("&4[DANGER] ");

        private String prefix;

        Severity(String prefix) {

            this.prefix = prefix;

        }

    }

}
