package nl.themelvin.questlib.abstracts;

import nl.themelvin.questlib.reward.Reward;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

import static nl.themelvin.questlib.utils.Logger.__;


public abstract class QuestObjective implements Listener {

    private Quest quest;

    /**
     * Start a new objective.
     * @param quest The quest where the objective is part from.
     */

    public void start(Quest quest) {

        this.quest = quest;

        if(this.startMessage != null) {

            this.quest.getPlayer().sendMessage(__(this.startMessage));

        }

    }

    /**
     * Complete the objective.
     */

    public void complete() {

        HandlerList.unregisterAll(this);

        if(this.completeMessage != null) {

            this.quest.getPlayer().sendMessage(__(this.completeMessage));

        }

        if(this.reward != null) {

            this.reward.reward(this.quest.getPlayer());

        }

        this.quest.completeObjective(this);

    }

    /**
     * Check if a player equals the quest 'owner'.
     * @param player The player to check.
     * @return Whether the quest 'owner' and the player are the same.
     */

    public boolean isFrom(Player player) {

        return this.quest.getPlayer().equals(player);

    }

    // Objective properties
    private String name;
    private String description;
    private Reward reward;

    private String startMessage;
    private String completeMessage;

    /**
     * Get all data as a map.
     * @return The map with data.
     */

    public HashMap<String, Object> getDataMap() {
        return data;
    }

    /**
     * Set all data as a map.
     * @param data The map with data.
     */

    public void setDataMap(HashMap<String, Object> data) {
        this.data = data;
    }

    private HashMap<String, Object> data = new HashMap<>();

    /**
     * Get stored data as a string.
     * @param key The key of the stored data.
     * @return The stored data.
     */

    public String getDataString(String key) {

        return (String) this.data.get(key);

    }

    /**
     * Get stored data as a number.
     * @param key The key of the stored data.
     * @return The stored data.
     */

    public double getDataNumber(String key) {

        return (double) this.data.get(key);

    }

    /**
     * Get stored data.
     * @param key The key of the stored data.
     * @return The stored data.
     */

    public Object getData(String key) {

        return this.data.get(key);

    }

    /**
     * Set data to a certain value.
     * @param key The key of the stored data.
     * @param value The actual data.
     */

    public void setData(String key, Object value) {

        this.data.put(key, value);

    }

    /**
     * Remove stored data.
     * @param key The key of the stored data.
     */

    public void removeData(String key) {

        this.data.remove(key);

    }

    /**
     * Increment a in data stored number.
     * @param key The key of the stored data.
     * @param incrementAmount The amount to add to the existing number.
     */

    public void incrementData(String key, double incrementAmount) {

        if(!this.data.containsKey(key)) {

            this.data.put(key, 1D);
            return;

        }

        this.data.replace(key, this.getDataNumber(key) + incrementAmount);

    }

    /**
     * Get the name of the objective.
     * @return The name of the objective.
     */

    public String getName() {
        return name;
    }

    /**
     * Set the name of the objective.
     * @param name The name of the objective.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the objective.
     * @return The description of the objective.
     */

    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the objective.
     * @return The description of the objective.
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the reward of the objective.
     * @return The reward of the objective.
     */

    public Reward getReward() {
        return reward;
    }

    /**
     * Set the reward of the objective.
     * @return The reward of the objective.
     */

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    /**
     * Get the start message of the objective.
     * @return The start message of the objective.
     */

    public String getStartMessage() {
        return startMessage;
    }

    /**
     * Set the start message of the objective.
     * @return The start message of the objective.
     */

    public void setStartMessage(String startMessage) {
        this.startMessage = startMessage;
    }

    /**
     * Get the complete message of the objective.
     * @return The complete message of the objective.
     */

    public String getCompleteMessage() {
        return completeMessage;
    }

    /**
     * Set the complete message of the objective.
     * @return The complete message of the objective.
     */

    public void setCompleteMessage(String completeMessage) {
        this.completeMessage = completeMessage;
    }

    /**
     * Get the quest where the objective is part from.
     * @return The quest.
     */

    public Quest getQuest() {
        return quest;
    }

    /**
     * Set the quest where the objective is part from.
     * @return The quest.
     */

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

}
