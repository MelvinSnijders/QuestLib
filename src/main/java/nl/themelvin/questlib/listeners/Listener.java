package nl.themelvin.questlib.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Listener<T> implements org.bukkit.event.Listener {

    /**
     * Listen to an event.
     * @param event The event that is called.
     */

    public abstract void listen(T event);

    /**
     * Register the event to the plugin.
     * @param plugin The plugin to register the event to.
     */

    public void register(JavaPlugin plugin) {

        Bukkit.getPluginManager().registerEvents(this, plugin);

    }

}
