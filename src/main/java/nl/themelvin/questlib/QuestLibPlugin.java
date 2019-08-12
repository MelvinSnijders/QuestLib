package nl.themelvin.questlib;

import org.bukkit.plugin.java.JavaPlugin;

public class QuestLibPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

    }

    @Override
    public void onDisable() {

        QuestLib.getInstance().disable();

    }


}
