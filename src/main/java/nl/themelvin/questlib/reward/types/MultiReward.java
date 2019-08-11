package nl.themelvin.questlib.reward.types;

import nl.themelvin.questlib.reward.Reward;
import org.bukkit.entity.Player;

public class MultiReward extends Reward {

    private Reward[] rewards;

    public MultiReward(Reward... rewards) {

        this.rewards = rewards;

    }

    @Override
    public void reward(Player player) {

        for(Reward reward : rewards) {

            reward.reward(player);

        }

    }

}
