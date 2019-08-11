package nl.themelvin.questlib.example;

import nl.themelvin.questlib.abstracts.Quest;
import nl.themelvin.questlib.reward.types.ItemReward;
import nl.themelvin.questlib.reward.types.MultiReward;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BreakQuest extends Quest {

    public BreakQuest() {

        this.setName("Break Quest");
        this.setDescription("Break different kind of blocks");

        this.setStartMessage("&bQuest started: " + this.getName());
        this.setCompleteMessage("&bQuest finished: " + this.getName());

        this.setReward(new MultiReward(new ItemReward(new ItemStack(Material.EMERALD, 2)), new ItemReward((new ItemStack(Material.IRON_INGOT, 10)))));

        this.addObjective(new CobblestoneObjective());
        this.addObjective(new DirtObjective());

    }

}
