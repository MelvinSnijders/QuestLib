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

    /**
     * Add an objective to the quest.
     * @param objective The objective to add.
     */

    public void addObjective(QuestObjective objective) {

        this.objectives.add(objective);

    }

    /**
     * Remove an objective from the quest.
     * @param objective The objective to remove.
     */

    public void removeObjective(QuestObjective objective) {

        this.objectives.remove(objective);

    }

    /**
     * Get all registered objectives.
     * @return A list with all objectives from the quest.
     */

    public List<QuestObjective> getObjectives() {

        return this.objectives;

    }

    /**
     * Complete an objective.
     * @param objective The objective to complete.
     */

    public void completeObjective(QuestObjective objective) {

        this.finishedObjectives.add(objective);
        this.objectives.remove(objective);

        this.pause();
        this.nextObjective();

    }

    /**
     * Start the quest for a player.
     * @param player The player to start the quest for.
     */

    public void start(Player player) {

        this.player = player;
        this.uuid = player.getUniqueId();

        if(this.startMessage != null) {

            this.player.sendMessage(__(this.startMessage));

        }

        this.nextObjective();

    }

    /**
     * Complete the quest.
     */

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

    /**
     * Go to the next objective.
     */

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

    /**
     * Initiate the quest (register events, etc.).
     */

    public void initiate() {

        // Register objective events if present
        for(Method method : this.currentObjective.getClass().getMethods()) {

            if(!method.isAnnotationPresent(EventHandler.class)) {
                continue;
            }

            Bukkit.getPluginManager().registerEvents(this.currentObjective, QuestLib.getInstance().getPlugin());

        }

    }

    /**
     * Pause the quest.
     */

    public void pause() {

        HandlerList.unregisterAll(this.currentObjective);

    }

    /**
     * Get the player doing the quest.
     * @return The player doing the quest.
     */

    public Player getPlayer() {

        return this.player;

    }

    /**
     * Set the player doing the quest.
     * @param player The player doing the quest.
     */

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

    /**
     * Get the name of the quest.
     * @return The name of the quest.
     */

    public String getName() {
        return name;
    }

    /**
     * Set the name of the quest.
     * @param name The name of the quest.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the quest.
     * @return The description of the quest.
     */

    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the quest.
     * @return The description of the quest.
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the reward of the quest.
     * @return The reward of the quest.
     */

    public Reward getReward() {
        return reward;
    }

    /**
     * Set the reward of the quest.
     * @return The reward of the quest.
     */

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    /**
     * Get the start message of the quest.
     * @return The start message of the quest.
     */

    public String getStartMessage() {
        return startMessage;
    }

    /**
     * Set the start message of the quest.
     * @return The start message of the quest.
     */

    public void setStartMessage(String startMessage) {
        this.startMessage = startMessage;
    }

    /**
     * Get the complete message of the quest.
     * @return The complete message of the quest.
     */

    public String getCompleteMessage() {
        return completeMessage;
    }

    /**
     * Set the complete message of the quest.
     * @return The complete message of the quest.
     */

    public void setCompleteMessage(String completeMessage) {
        this.completeMessage = completeMessage;
    }

    /**
     * Get the current running objective.
     * @return The current running objective.
     */

    public QuestObjective getCurrentObjective() {
        return currentObjective;
    }

    /**
     * Set the current running objective.
     * @return The current running objective.
     */

    public void setCurrentObjective(QuestObjective currentObjective) {
        this.currentObjective = currentObjective;
    }

    /**
     * Get the UUID of the player doing the quest.
     * @return The UUID of the player.
     */

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Set the UUID of the player doing the quest.
     * @param uuid The UUID of the player.
     */

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    
}
