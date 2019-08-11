package nl.themelvin.questlib;

import nl.themelvin.questlib.example.BreakQuest;
import nl.themelvin.questlib.example.CobblestoneObjective;
import nl.themelvin.questlib.example.DirtObjective;
import nl.themelvin.questlib.example.JoinListener;
import nl.themelvin.questlib.storage.types.YAML;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class QuestLibPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        QuestLib.getInstance().registerQuest("break", BreakQuest.class);
        QuestLib.getInstance().registerObjective("cobblestone", CobblestoneObjective.class);
        QuestLib.getInstance().registerObjective("dirt", DirtObjective.class);

        QuestLib.getInstance().init(this, new YAML(new File(this.getDataFolder() + "/data", "data.yml")));

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

    }

    @Override
    public void onDisable() {

        QuestLib.getInstance().disable();

    }


}
