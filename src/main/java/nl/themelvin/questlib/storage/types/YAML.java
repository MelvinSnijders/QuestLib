package nl.themelvin.questlib.storage.types;

import com.google.gson.Gson;
import nl.themelvin.questlib.storage.QuestStorable;
import nl.themelvin.questlib.storage.QuestStorage;
import nl.themelvin.questlib.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class YAML implements QuestStorage {

    private File file;
    private FileConfiguration fileConfiguration;

    public YAML(File file) {

        this.file = file;

        if (!this.file.exists()) {

            this.file.getParentFile().mkdirs();

            try {

                this.file.createNewFile();

            } catch (IOException e) {

                Logger.log(Logger.Severity.ERROR, "Error with creating YAML file, please check everything before you continue using QuestLib.");

            }

        }

        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);

    }

    @Override
    public void newQuest(UUID playerId, String identifier, String objectiveIdentifier, HashMap<String, Object> data) {

        this.fileConfiguration.set("running_quests." + playerId.toString() + ".identifier", identifier);
        this.fileConfiguration.set("running_quests." + playerId.toString() + ".objective_identifier", objectiveIdentifier);
        this.fileConfiguration.set("running_quests." + playerId.toString() + ".data", new Gson().toJson(data));

        this.save();

    }

    @Override
    public void removeQuest(UUID playerId) {

        this.fileConfiguration.set("running_quests." + playerId.toString(), null);

        this.save();

    }

    // Update method is same as new method, but is still used.

    @Override
    public void updateQuest(UUID playerId, String identifier, String objectiveIdentifier, HashMap<String, Object> data) {

        this.newQuest(playerId, identifier, objectiveIdentifier, data);

    }

    @Override
    public boolean hasQuest(UUID playerId) {

        return this.fileConfiguration.contains("running_quests." + playerId.toString());

    }

    @Override
    public List<QuestStorable> getAllQuests() {

        List<QuestStorable> quests = new ArrayList<>();

        if(this.fileConfiguration.getConfigurationSection("running_quests") == null) {

            return quests;

        }

        for (String key : this.fileConfiguration.getConfigurationSection("running_quests").getKeys(false)) {

            String identifier = this.fileConfiguration.getString("running_quests." + key + ".identifier");
            String objectiveIdentifier = this.fileConfiguration.getString("running_quests." + key + ".objective_identifier");
            String data = this.fileConfiguration.getString("running_quests." + key + ".data");

            HashMap<String, Object> formattedData = new Gson().fromJson(data, HashMap.class);

            QuestStorable storable = new QuestStorable(UUID.fromString(key), identifier, objectiveIdentifier, formattedData);
            quests.add(storable);

        }

        return quests;

    }

    /**
     * Save the file configuration.
     */

    public void save() {

        try {

            this.fileConfiguration.save(this.file);

        } catch (IOException e) {

            Logger.log(Logger.Severity.ERROR, "Error with saving YAML file, please check everything before you continue using QuestLib.");
            e.printStackTrace();

        }

    }

}
