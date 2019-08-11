package nl.themelvin.questlib.storage;

import java.util.HashMap;
import java.util.UUID;

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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getObjectiveIdentifier() {
        return objectiveIdentifier;
    }

    public void setObjectiveIdentifier(String objectiveIdentifier) {
        this.objectiveIdentifier = objectiveIdentifier;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

}
