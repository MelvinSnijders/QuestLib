package nl.themelvin.questlib.abstracts;

import nl.themelvin.questlib.QuestLib;
import nl.themelvin.questlib.reward.Reward;
import nl.themelvin.questlib.storage.QuestStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;

import java.lang.reflect.Method;
import java.util.*;

import static nl.themelvin.questlib.utils.Logger.__;

public abstract class Quest {

    private List<QuestObjective> objectives = new ArrayList<>();
    private List<QuestObjective> finishedObjectives = new ArrayList<>();

    private QuestObjective currentObjective;

    private Player player;
    private UUID uuid;

    public void addObjective(QuestObjective objective) {

        this.objectives.add(objective);

    }

    public void removeObjective(QuestObjective objective) {

        this.objectives.remove(objective);

    }

    public List<QuestObjective> getObjectives() {

        return this.objectives;

    }

    public void completeObjective(QuestObjective objective) {

        this.finishedObjectives.add(objective);
        this.objectives.remove(objective);

        this.pause();
        this.nextObjective();

    }

    public void start(Player player) {

        this.player = player;
        this.uuid = player.getUniqueId();

        if(this.startMessage != null) {

            this.player.sendMessage(__(this.startMessage));

        }

        this.nextObjective();

    }

    private void completeQuest() {

        QuestLib.getInstance().stopQuest(this.uuid);

        // This shouldn't be possible, but we check anyways to prevent errors.
        if(Bukkit.getPlayer(this.uuid) == null) {
            return;
        }

        Player player = Bukkit.getPlayer(this.uuid);

        if(this.completeMessage != null) {

            player.sendMessage(__(this.completeMessage));

        }

        if(this.reward != null) {

            this.reward.reward(player);

        }

    }

    public void nextObjective() {

        if(this.objectives.size() == 0) {

            this.completeQuest();
            return;

        }

        // Start first objective
        QuestObjective objective = this.objectives.get(0);

        currentObjective = objective;
        this.initiate();

        objective.start(this);

    }

    public void initiate() {

        // Register objective events if present
        for(Method method : this.currentObjective.getClass().getMethods()) {

            if(!method.isAnnotationPresent(EventHandler.class)) {
                continue;
            }

            Bukkit.getPluginManager().registerEvents(this.currentObjective, QuestLib.getInstance().getPlugin());

        }

    }

    public void pause() {

        HandlerList.unregisterAll(this.currentObjective);

    }

    public Player getPlayer() {

        return this.player;

    }

    public void setPlayer(Player player) {

        this.uuid = player.getUniqueId();
        this.player = player;

    }

    // Quest properties
    private String name;
    private String description;
    private Reward reward;

    private String startMessage;
    private String completeMessage;

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

    public QuestObjective getCurrentObjective() {
        return currentObjective;
    }

    public void setCurrentObjective(QuestObjective currentObjective) {
        this.currentObjective = currentObjective;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
