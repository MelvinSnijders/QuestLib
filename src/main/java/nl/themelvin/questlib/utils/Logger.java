package nl.themelvin.questlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {

    /**
     * Convert all colors in a message.
     * @param message The message to convert the color codes from.
     * @return The converted string.
     */

    public static String __(String message) {

        return ChatColor.translateAlternateColorCodes('&', message);

    }

    /**
     * Log a message to the console.
     * @param severity The severity of the message.
     * @param message The message to send.
     */

    public static void log(Severity severity, String message) {

        Bukkit.getServer().getConsoleSender().sendMessage(__(severity.prefix + "&r" + message));

    }

    /**
     * Enum with severities.
     */

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
