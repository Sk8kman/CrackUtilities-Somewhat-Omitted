package org.e11eman.crackutilities.commands;

import org.e11eman.crackutilities.utilities.ArrayTools;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.toolclasses.Command;

import java.util.ArrayList;

public class RunCommand extends Command {
    public RunCommand() {
        super("run", "Use command core to run command", "run <command>");
    }

    @Override
    public void execute(ArrayList<String> arguments) {
        CClient.commandCoreSystem.run(ArrayTools.join(arguments, ' '));
    }
}
