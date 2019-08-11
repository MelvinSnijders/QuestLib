package nl.themelvin.questlib.storage;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public interface QuestStorage {

    void newQuest(UUID playerId, String identifier, String objectiveIdentifier, HashMap<String, Object> data);
    void removeQuest(UUID playerId);
    void updateQuest(UUID playerId, String identifier, String objectiveIdentifier, HashMap<String, Object> data);
    boolean hasQuest(UUID playerId);
    List<QuestStorable> getAllQuests();

}
