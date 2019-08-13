package nl.themelvin.questlib.storage;

import java.util.HashMap;
import java.util.UUID;
import nl.themelvin.questlib.abstracts.*;

public class QuestStorable {

    private UUID uuid;
    private String identifier;
    private String objectiveIdentifier;
    private HashMap<String, Object> data;

    public QuestStorable(UUID uuid, String identifier, String objectiveIdentifier, HashMap<String, Object> data) {
        this.uuid = uuid;
        this.identifier = identifier;
        this.objectiveIdentifier = objectiveIdentifier;
        this.data = data;
    }

    public QuestStorable() {  }

    /**
     * Get the UUID of the player.
     * @return The UUID of the player.
     */

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Set the UUID of the player.
     * @param uuid The UUID of the player.
     */

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Get the identifier of the {@link Quest}.
     * @return The string identifier.
     */

    public String getIdentifier() {
        return identifier;
    }

    /**
     * Set the identifier of the {@link Quest}
     * @param identifier The string identifier.
     */

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Get the identifier of the {@link QuestObjective}
     * @return The string identifier.
     */

    public String getObjectiveIdentifier() {
        return objectiveIdentifier;
    }

    /**
     * Set the identifier of the {@link QuestObjective}
     * @param objectiveIdentifier The string identifier.
     */

    public void setObjectiveIdentifier(String objectiveIdentifier) {
        this.objectiveIdentifier = objectiveIdentifier;
    }

    /**
     * Get the data used by the {@link QuestObjective}
     * @return The data used.
     */

    public HashMap<String, Object> getData() {
        return data;
    }

    /**
     * Set the data used by the {@link QuestObjective}
     * @param data The data used.
     */

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

}
