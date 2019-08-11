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

    public void start(Quest quest) {

        this.quest = quest;

        if(this.startMessage != null) {

            this.quest.getPlayer().sendMessage(__(this.startMessage));

        }

    }

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

    public boolean isFrom(Player player) {

        return this.quest.getPlayer().equals(player);

    }

    // Objective properties
    private String name;
    private String description;
    private Reward reward;

    private String startMessage;
    private String completeMessage;

    public HashMap<String, Object> getDataMap() {
        return data;
    }

    public void setDataMap(HashMap<String, Object> data) {
        this.data = data;
    }

    private HashMap<String, Object> data = new HashMap<>();

    public void eventCall() {

    }

    public String getDataString(String key) {

        return (String) this.data.get(key);

    }

    public double getDataInt(String key) {

        return (double) this.data.get(key);

    }

    public Object getData(String key) {

        return this.data.get(key);

    }

    public void setData(String key, Object value) {

        this.data.put(key, value);

    }

    public void removeData(String key) {

        this.data.remove(key);

    }

    public void incrementData(String key, double incrementAmount) {

        if(!this.data.containsKey(key)) {

            this.data.put(key, 1D);
            return;

        }

        this.data.replace(key, this.getDataInt(key) + incrementAmount);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public String getStartMessage() {
        return startMessage;
    }

    public void setStartMessage(String startMessage) {
        this.startMessage = startMessage;
    }

    public String getCompleteMessage() {
        return completeMessage;
    }

    public void setCompleteMessage(String completeMessage) {
        this.completeMessage = completeMessage;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

}
