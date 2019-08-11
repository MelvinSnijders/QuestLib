package nl.themelvin.questlib.example;

import nl.themelvin.questlib.QuestLib;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        if(!QuestLib.getInstance().hasQuest(event.getPlayer())) {

            QuestLib.getInstance().startQuest(new BreakQuest(), event.getPlayer());

        }

    }

}
