package nl.themelvin.questlib.storage.types;

import com.google.gson.Gson;
import nl.themelvin.questlib.storage.QuestStorable;
import nl.themelvin.questlib.storage.QuestStorage;
import nl.themelvin.questlib.utils.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MySQL implements QuestStorage {

    private Connection connection;
    private String host, database, username, password;
    private int port;

    public MySQL(String host, String database, String username, String password, int port) {

        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;

        // Test connection
        try {

            Connection connection = this.getConnection();

            ResultSet resultSet = connection.createStatement().executeQuery("SELECT version()");
            Logger.log(Logger.Severity.INFO, "Connected to MySQL server running version " + resultSet.getString(1));

            boolean result = connection.createStatement().execute("CREATE TABLE IF NOT EXISTS `running_quests`(" +
                    "  `uuid` VARCHAR(36) NOT NULL," +
                    "  `identifier` VARCHAR(45) NOT NULL," +
                    "  `objective_identifier` VARCHAR(45) NOT NULL," +
                    "  `data` JSON NULL," +
                    "  PRIMARY KEY (`uuid`));");

            if(result) {
                Logger.log(Logger.Severity.INFO, "Succesfully created MySQL tables.");
            } else {
                Logger.log(Logger.Severity.ERROR, "Could not create necessary tables, please check everything before you continue using QuestLib.");
            }

            connection.close();

        } catch (SQLException e) {

            Logger.log(Logger.Severity.ERROR, "Could not execute test query, please check everything before you continue using QuestLib.");

        }

    }


    private Connection getConnection() {

        try {

            if (connection != null && !connection.isClosed()) {
                return this.connection;
            }

            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);

        } catch (ClassNotFoundException | SQLException e) {

            Logger.log(Logger.Severity.ERROR, "Error whilst connecting to MySQL database, please check everything before you continue using QuestLib.");
            e.printStackTrace();

        }

        return this.connection;

    }

    @Override
    public void newQuest(UUID playerId, String identifier, String objectiveIdentifier, HashMap<String, Object> data) {

    }

    @Override
    public void removeQuest(UUID playerId) {

    }

    @Override
    public void updateQuest(UUID playerId, String identifier, String objectiveIdentifier, HashMap<String, Object> data) {

    }

    @Override
    public boolean hasQuest(UUID playerId) {
        return false;
    }

    @Override
    public List<QuestStorable> getAllQuests() {

        String query = "SELECT * FROM running_quests";
        List<QuestStorable> quests = new ArrayList<>();

        try {

            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {

                QuestStorable storable = new QuestStorable();
                storable.setUuid(UUID.fromString(result.getString("uuid")));
                storable.setIdentifier(result.getString("identifier"));
                storable.setObjectiveIdentifier(result.getString("objective_identifier"));
                storable.setData(new Gson().fromJson(result.getString("data"), HashMap.class));

                quests.add(storable);

            }

            connection.close();

        } catch (SQLException e) {

            Logger.log(Logger.Severity.ERROR, "Could not get all quests, please check everything before you continue using QuestLib.");

        }

        return quests;

    }

}
