package org.e11eman.crackutilities.commands;

import com.google.gson.JsonObject;
import net.minecraft.text.Text;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.MessagePresets;
import org.e11eman.crackutilities.utilities.systems.CommandCoreSystem;
import org.e11eman.crackutilities.utilities.toolclasses.Command;
import org.e11eman.crackutilities.wrappers.Player;

import java.io.IOException;
import java.util.ArrayList;

public class PrivateMessageCommand extends Command {
    public PrivateMessageCommand() {super("msg", "message someone privately", "msg <Player / UUID> <message>");}

    @Override
    public void execute(ArrayList<String> arguments) throws IOException {
        if (arguments.size() < 2) {
            return;
        }
        String player = arguments.get(0);
        arguments.remove(0);
        String message = String.join(" ", arguments);
        JsonObject options = CClient.configSystem.getCategory(CClient.configSystem.getConfig(), "commandCoreSystem");
        if (!options.get("enabled").getAsBoolean()) {
            Player.alertClient(MessagePresets.errorTextPreset("commandCoreSystem is not enabled"));
            return;
        }
        CClient.commandCoreSystem.run("minecraft:tellraw " + player + " [{\"text\":\"[\",\"color\":\"gray\"}, {\"text\":\"PRIVATE\", \"color\":\"blue\", \"bold\":true}, {\"text\":\"] \", \"color\":\"gray\"}, {\"text\":\"" + Player.getUsername() + "\", \"color\":\"aqua\"}, {\"text\":\" > \", \"color\":\"dark_gray\"}, {\"text\":\"me\", \"color\":\"aqua\"}, {\"text\":\": " + message.replace("\\", "\\\\").replace("\"", "\\\"") + "\", \"color\":\"gray\"}]");
        CClient.commandCoreSystem.run("minecraft:tellraw " + Player.getUsername() + " [{\"text\":\"[\",\"color\":\"gray\"}, {\"text\":\"PRIVATE\", \"color\":\"blue\", \"bold\":true}, {\"text\":\"] \", \"color\":\"gray\"}, {\"text\":\"you\", \"color\":\"aqua\"}, {\"text\":\" > \", \"color\":\"dark_gray\"}, {\"text\":\"" + player + "\", \"color\":\"aqua\"}, {\"text\":\": " + message.replace("\\", "\\\\").replace("\"", "\\\"") + "\", \"color\":\"gray\"}]");
        CClient.commandCoreSystem.run("minecraft:execute at " + player + " run playsound minecraft:entity.experience_orb.pickup master " + player + " ~ ~ ~ 0.6 0.8");
    }
}
