package org.e11eman.crackutilities.utilities.systems;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.wrappers.Player;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChatQueueSystem {
    private final ArrayList<String> messages = new ArrayList<>();
    private Timer queue = new Timer();

    public ChatQueueSystem() {
        CClient.events.register("openWorld", "chatQueueOpenWorld",  (Event) -> queue.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!messages.isEmpty()) {
                    String message = messages.getLast();
                    if (message.startsWith("/")) {
                        Player.sendPacket(new CommandExecutionC2SPacket(message.substring(1)));
                    } else {
                        Player.sendChat(message);
                    }
                    messages.removeLast();
                }
            }
        }, 0, (long) CClient.configSystem.getCategory(CClient.configSystem.getConfig(), "chatQueueSystem").get("delay").getAsDouble()));

        CClient.events.register("closeWorld", "chatQueueCloseWorld",  (Event) -> {
           queue.purge();
           queue.cancel();

           messages.clear();

           queue = new Timer();
        });
    }

    public void addMessageToQueue(String message) {
        messages.add(message);
    }
}
