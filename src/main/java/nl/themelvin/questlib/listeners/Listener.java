package nl.themelvin.questlib.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Listener<T> implements org.bukkit.event.Listener {

    public abstract void listen(T event);

    public void register(JavaPlugin plugin) {

        Bukkit.getPluginManager().registerEvents(this, plugin);

    }

}
