package org.e11eman.crackutilities.utilities.systems;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.MessagePresets;
import org.e11eman.crackutilities.wrappers.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigSystem {
    public final File configPath;
    private final Gson gson = new Gson();

    private final Path modConfigPath = Path.of(FabricLoader.getInstance().getConfigDir() + "/CrackUtilities/");
    private final File configFile = new File(modConfigPath.toFile() + "/cc.json");

    private JsonObject config;

    public ConfigSystem(File configPath) {
        this.configPath = configPath;
        updateConfig();

    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    private void createBrokenConfig() {
        try {
            configFile.createNewFile();
            FileWriter fileWriter = new FileWriter(configFile);
            fileWriter.write(getConfig().toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public void fixConfig() {
        try {
            Player.alertClient(MessagePresets.errorTextPreset("config needs to be fixed awwww :("));
            if (!configFile.exists()) {
                try {
                    configPath.mkdirs();
                    configFile.createNewFile();
                } catch (IOException ignored) {
                    throw new RuntimeException(ignored);
                }
            }
            FileWriter fixConfig = new FileWriter(configFile);

            fixConfig.write(jsonConfigDefault);
            fixConfig.close();

            Reader reader = Files.newBufferedReader(configFile.toPath());
            this.config = gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateConfig() {
        try {
            Reader reader = Files.newBufferedReader(configFile.toPath());
            this.config = gson.fromJson(reader, JsonObject.class);
            reader.close();
        } catch (IOException e) {
            fixConfig();
            updateConfig();
        }
    }

    public JsonObject getConfig() {
        if (!configPath.exists() || config == null) {
            fixConfig();
            return getConfig();
        }
        try {
            return config;
        } catch (Exception e) {
            fixConfig();
            return getConfig();
        }
    }

    public JsonObject getCategory(JsonObject config, String categoryName) {
        try {
            return config.getAsJsonObject(categoryName);
        } catch (Exception e) {
            fixConfig();
            return config.getAsJsonObject(categoryName);
        }
    }

    public String jsonConfigDefault = """
            {
              "commandSystem": {
                "prefix": "."
              },
              "commandCoreSystem": {
                "layers": 3,
                "maxDistance": 128,
                "enabled": false
              },
              "chatQueueSystem": {
                "delay": 150
              },
              "selfCareSystem": {
                "delay": 150,
                "checkCreative": false,
                "checkOperator": false
              },
              "chatFormatting": {
                "enabled": false,
                "formatting": [
                  {"color": "dark_gray", "text": "["},
                  {"color": "blue", "bold": true, "text": "CUtilities"},
                  {"color": "dark_gray", "text": "] ", "bold": false},
                  {"color": "gray", "text": "%username% "},
                  {"color": "dark_gray", "text": "> "},
                  {"color": "gray", "text": "%message%"}
                ]
              },
              "ircSystem": {
                "host": "",
                "port": 83424560,
                "timeout": 5000
              },
              "keyBindings": {
                "spin": {
                  "increment": 1
                }
              }
            }
            """;
}
