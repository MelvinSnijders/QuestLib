package nl.themelvin.questlib.reward.types;

import nl.themelvin.questlib.reward.Reward;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static nl.themelvin.questlib.utils.Logger.__;

public class ItemReward extends Reward {

    private ItemStack item;
    private String message;

    public ItemReward(ItemStack item, String message) {

        this.item = item;
        this.message = message;

    }

    public ItemReward(ItemStack item) {

        this.item = item;

    }

    public void reward(Player player) {

        player.getInventory().addItem(this.item);

        if(this.message != null) {

            player.sendMessage(__(this.message));

        }

    }

}
