package org.e11eman.crackutilities.commands;

import org.e11eman.crackutilities.utilities.toolclasses.Command;
import org.e11eman.crackutilities.wrappers.Player;

import java.util.ArrayList;

public class UsernameCommand extends Command {
    public UsernameCommand() {
        super("username", "Swap your username instantly", "\n username <newUsername> ");
    }

    @Override
    public void execute(ArrayList<String> arguments) {
        Player.setUsername(arguments.get(0));
        Player.reconnectPlayer();
    }
}
