package org.e11eman.crackutilities.commands;

import org.e11eman.crackutilities.utilities.ArrayTools;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.packets.C2WSIrcMessagePacket;
import org.e11eman.crackutilities.utilities.toolclasses.Command;
import org.e11eman.crackutilities.wrappers.Player;

import java.util.ArrayList;

public class IrcCommand extends Command {
    public IrcCommand() {
        super("irc", "Communicate with the main IRC server", "\n irc <connect> \n irc <disconnect> \n irc <chat> <message> ");
    }

    @Override
    public void execute(ArrayList<String> arguments) {
        //Don't have the code required to make this work :(
        /*
        switch (arguments.get(0)) {
            case "connect" -> CClient.ircSystem.socket.connect();

            case "disconnect" -> CClient.ircSystem.socket.disconnect();

            case "chat" -> CClient.ircSystem.socket.send(new C2WSIrcMessagePacket(
                    Player.getUsername(),
                    Player.getAddress(),
                    true,
                    ArrayTools.join(ArrayTools.shift(arguments, 1), ' ')
            ).getPacket());
        }
         */
    }
}