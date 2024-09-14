package org.e11eman.crackutilities.utilities.systems;

import com.google.gson.JsonObject;
import org.e11eman.crackutilities.commands.*;
import org.e11eman.crackutilities.utilities.ArrayTools;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.MessagePresets;
import org.e11eman.crackutilities.utilities.toolclasses.Command;
import org.e11eman.crackutilities.wrappers.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandSystem {
    public final Command[] commands = {
            new ConfigCommand(),
            new HelpCommand(),
            new RunCommand(),
            new CloopCommand(),
            new ConnectionCommand(),
            //new UsernameCommand(), //BROKEN
            new RefillCommand(),
            //new IrcCommand(), //BROKEN
            new SudoAllCommand(),
            new ScreenShareCommand(),
            new ShuffleCommand(),
            new ItemImageRendererCommand(),
            new NukerCommand(),
            new PrivateMessageCommand()
    };

    public boolean executeCommandIfFound(String text) {
        JsonObject config = CClient.configSystem.getConfig();
        JsonObject category = CClient.configSystem.getCategory(config, "commandSystem");

        Pattern commandPattern = Pattern.compile("[" + category.get("prefix").getAsString() + "](.*)");
        Matcher matchCheck = commandPattern.matcher(text);

        if (matchCheck.matches()) {
            String[] parsedText = matchCheck.group(1).split(" ");

            for (Command command : commands) {
                if (command.name.matches(parsedText[0])) {
                    try {
                        command.execute(ArrayTools.shift(new ArrayList<>(Arrays.asList(parsedText)), 1));
                    } catch (Exception e) {
                        Player.alertClient(MessagePresets.errorTextPreset("Something has gone wrong with that command!"));
                        e.printStackTrace();
                    }
                    return true;
                }
            }

            return false;
        }

        return false;
    }
}
