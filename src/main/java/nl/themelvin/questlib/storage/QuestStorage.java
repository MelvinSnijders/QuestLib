package nl.themelvin.questlib.storage;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface QuestStorage {

    /**
     * Create a new quest in the storage.
     * @param playerId The UUID of the player to create the quest for.
     * @param identifier The identifier of the current quest.
     * @param objectiveIdentifier The identifier of the current objective.
     * @param data The data that is used by the objective.
     */

    void newQuest(UUID playerId, String identifier, String objectiveIdentifier, HashMap<String, Object> data);

    /**
     * Remove a quest from the storage.
     * @param playerId The UUID of the player to remove the stored quest from.
     */

    void removeQuest(UUID playerId);

    /**
     * Update an existing quest in the storage.
     * @param playerId The UUID of the player to update the quest for.
     * @param identifier The new identifier of the current quest.
     * @param objectiveIdentifier The new identifier of the current objective.
     * @param data The new data that is used by the new current objective.
     */

    void updateQuest(UUID playerId, String identifier, String objectiveIdentifier, HashMap<String, Object> data);

    /**
     * Check if a player has a quest running/stored.
     * @param playerId The UUID of the player to check.
     * @return Whether the player has a quest running/stored.
     */

    boolean hasQuest(UUID playerId);

    /**
     * Get all quests stored.
     * @return A list of all quests stored in the storage.
     */

    List<QuestStorable> getAllQuests();

}
