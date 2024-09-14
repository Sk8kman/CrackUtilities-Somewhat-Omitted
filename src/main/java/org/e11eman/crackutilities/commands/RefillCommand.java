package org.e11eman.crackutilities.commands;

import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.MessagePresets;
import org.e11eman.crackutilities.utilities.toolclasses.Command;
import org.e11eman.crackutilities.wrappers.Player;

import java.util.ArrayList;

public class RefillCommand extends Command {
    public RefillCommand() {
        super("refill", "Refill command core", "refill");
    }

    @Override
    public void execute(ArrayList<String> arguments) {
        CClient.commandCoreSystem.update();
        CClient.commandCoreSystem.fillCore();

        Player.alertClient(MessagePresets.normalTextPreset("Successfully refilled core"));
    }
}
