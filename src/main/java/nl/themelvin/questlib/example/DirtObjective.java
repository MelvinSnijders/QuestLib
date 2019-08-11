package nl.themelvin.questlib.example;

import nl.themelvin.questlib.abstracts.QuestObjective;
import nl.themelvin.questlib.reward.types.ItemReward;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class DirtObjective extends QuestObjective {

    public DirtObjective() {

        this.setName("Dirt objective");
        this.setDescription("Break 5 dirt blocks.");
        this.setReward(new ItemReward(new ItemStack(Material.DIAMOND, 1), "&6You received &e1x Diamond &6as a reward."));

        this.setStartMessage("&b" + this.getName() + ": " + this.getDescription());
        this.setCompleteMessage("&aYou finished this objective.");

    }

    private int count;

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {

        if(!this.isFrom(event.getPlayer())) return;

        if(event.getBlock().getType() != Material.DIRT) {

            return;

        }

        this.incrementData("count", 1);

        if(this.getDataInt("count") >= 5) {

            this.complete();

        }

    }

}
