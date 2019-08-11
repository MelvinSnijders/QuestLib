package nl.themelvin.questlib.listeners;

import nl.themelvin.questlib.QuestLib;
import nl.themelvin.questlib.abstracts.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener extends Listener<PlayerQuitEvent> {

    @EventHandler
    public void listen(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        QuestLib questLib = QuestLib.getInstance();

        if(questLib.hasQuest(player)) {

            Quest quest = questLib.getQuest(player);
            quest.pause();

        }

    }

}
