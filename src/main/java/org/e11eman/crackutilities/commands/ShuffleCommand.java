package org.e11eman.crackutilities.commands;

import net.minecraft.client.network.PlayerListEntry;
import org.e11eman.crackutilities.utilities.ArrayTools;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.MessagePresets;
import org.e11eman.crackutilities.utilities.toolclasses.Command;
import org.e11eman.crackutilities.wrappers.Player;

import java.util.ArrayList;

public class ShuffleCommand extends Command {
    public ShuffleCommand() {
        super("shuffle", "Shuffle the locations of players on the server", "shuffle");
    }

    @Override
    public void execute(ArrayList<String> arguments) {
        Player.alertClient(MessagePresets.normalTextPreset("Shuffling players..."));

        for(PlayerListEntry player : Player.getPlayerList()) {
            if(player.getProfile().getName().matches(Player.getUsername())) return;

            PlayerListEntry randomPlayer = ArrayTools.getRandomInCollection(Player.getPlayerList());

            CClient.commandCoreSystem.run(String.format("sudo %s %s", player.getProfile().getName(), "tpo " + randomPlayer.getProfile().getName()));

            Player.alertClient(MessagePresets.normalTextPreset("Moving " + player.getProfile().getName() + " to " + randomPlayer.getProfile().getName()));
        }
    }
}
