package nl.themelvin.questlib.reward;

import org.bukkit.entity.Player;

public abstract class Reward {

    /**
     * Reward a player (called when quest or objective finished).
     * @param player The player to reward.
     */

    public abstract void reward(Player player);

}
