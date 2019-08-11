package nl.themelvin.questlib;

import nl.themelvin.questlib.abstracts.Quest;
import nl.themelvin.questlib.abstracts.QuestObjective;
import nl.themelvin.questlib.listeners.JoinListener;
import nl.themelvin.questlib.listeners.QuitListener;
import nl.themelvin.questlib.storage.QuestStorable;
import nl.themelvin.questlib.storage.QuestStorage;
import nl.themelvin.questlib.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/* TODO
    Setup storage
        - running_quests
    Setup config for choosing storage
 */

public class QuestLib {

    private static QuestLib instance;

    public static QuestLib getInstance() {

        if(instance == null) {

            instance = new QuestLib();

        }

        return instance;

    }

    private JavaPlugin plugin;
    private QuestStorage storage;

    public void init(JavaPlugin plugin, QuestStorage storage) {

        this.plugin = plugin;

        new JoinListener().register(this.plugin);
        new QuitListener().register(this.plugin);

        this.storage = storage;

        try {

            this.loadAll();

        } catch (Exception e) {

            Logger.log(Logger.Severity.ERROR, "Could not load existing data, please check everything before you continue using QuestLib.");
            e.printStackTrace();

        }

        // Because literally no one listens to the /reload text and that it can be bad for your server.
        // AND BAD FOR THE PLUGINS
        for(Player online : Bukkit.getOnlinePlayers()) {

            new JoinListener().listen(new PlayerJoinEvent(online, null));

        }

    }

    public void disable() {

        this.saveAll();

        // Because literally no one listens to the /reload text and that it can be bad for your server.
        // AND BAD FOR THE PLUGINS
        for(Player online : Bukkit.getOnlinePlayers()) {

            new QuitListener().listen(new PlayerQuitEvent(online, null));

        }

    }

    private HashMap<String, Class<? extends Quest>> questTypes = new HashMap<>();
    private HashMap<String, Class<? extends QuestObjective>> objectiveTypes = new HashMap<>();

    public void registerQuest(String identifier, Class<? extends Quest> questClass) {

        questTypes.put(identifier, questClass);

    }

    public void registerObjective(String identifier, Class<? extends QuestObjective> objectiveClass) {

        objectiveTypes.put(identifier, objectiveClass);

    }

    private HashMap<UUID, Quest> runningQuests = new HashMap<>();

    public void startQuest(Quest quest, Player player) {

        this.runningQuests.put(player.getUniqueId(), quest);
        quest.start(player);

    }

    public void stopQuest(UUID uuid) {

        this.runningQuests.remove(uuid);
        this.storage.removeQuest(uuid);

    }

    public boolean hasQuest(Player player) {

        return this.hasQuest(player.getUniqueId());

    }

    public boolean hasQuest(UUID uuid) {

        return this.runningQuests.containsKey(uuid);

    }

    public Quest getQuest(Player player) {

        return this.getQuest(player.getUniqueId());

    }

    public Quest getQuest(UUID uuid) {

        return this.runningQuests.get(uuid);

    }

    public void saveAll() {

        for(Quest quest : this.runningQuests.values()) {

            Optional<Map.Entry<String, Class<? extends Quest>>> identifierOpt = this.questTypes.entrySet().stream().filter(e -> e.getValue().equals(quest.getClass())).findFirst();
            Optional<Map.Entry<String, Class<? extends QuestObjective>>> objectiveIdentifierOpt = this.objectiveTypes.entrySet().stream().filter(e -> e.getValue().equals(quest.getCurrentObjective().getClass())).findFirst();

            if(!identifierOpt.isPresent() || !objectiveIdentifierOpt.isPresent()) {

                Logger.log(Logger.Severity.ERROR, "QuestLib is not able to save an unregistered quest or objective, please register them.");
                Logger.log(Logger.Severity.ERROR, "Classes: " + quest.getClass() + " and/or " + quest.getCurrentObjective().getClass());
                return;

            }

            String identifier = identifierOpt.get().getKey();
            String objectiveIdentifier = objectiveIdentifierOpt.get().getKey();
            HashMap<String, Object> data = quest.getCurrentObjective().getDataMap();

            if (!this.storage.hasQuest(quest.getUuid())) {

                this.storage.newQuest(quest.getUuid(), identifier, objectiveIdentifier, data);
                return;

            }

            this.storage.updateQuest(quest.getUuid(), identifier, objectiveIdentifier, data);

        }

    }

    public void loadAll() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        List<QuestStorable> quests = this.storage.getAllQuests();

        for(QuestStorable storable : quests) {

            Class<? extends Quest> questClass = this.questTypes.get(storable.getIdentifier());
            Class<? extends QuestObjective> objectiveClass = this.objectiveTypes.get(storable.getObjectiveIdentifier());

            if(questClass == null || objectiveClass == null) {

                Logger.log(Logger.Severity.ERROR, "Could not load Quest or objective with identifiers '" + storable.getIdentifier() + "' and '" + storable.getObjectiveIdentifier() + "'. Did you register them?");
                return;

            }

            Quest quest = questClass.getConstructor().newInstance();

            for(QuestObjective objective : quest.getObjectives()) {

                if(objective.getClass().equals(objectiveClass)) {

                   break;

                }

                quest.removeObjective(objective);

            }

            QuestObjective newObjective = quest.getObjectives().get(0);
            quest.setCurrentObjective(newObjective);
            newObjective.setQuest(quest);
            newObjective.setDataMap(storable.getData());

            this.runningQuests.put(storable.getUuid(), quest);


        }

    }

    public QuestStorage getStorage() {

        return storage;

    }

    public JavaPlugin getPlugin() {

        return this.plugin;

    }

}
