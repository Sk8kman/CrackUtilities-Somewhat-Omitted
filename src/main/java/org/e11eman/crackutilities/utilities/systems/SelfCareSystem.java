package org.e11eman.crackutilities.utilities.systems;

import com.google.gson.JsonObject;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.wrappers.Player;

import java.util.Timer;
import java.util.TimerTask;

public class SelfCareSystem {
    private Timer careLoop = new Timer();

    public SelfCareSystem() {
        CClient.events.register("openWorld", "selfCareOpenWorld", (Event) -> {
            JsonObject config = CClient.configSystem.getConfig();
            JsonObject category = CClient.configSystem.getCategory(config, "selfCareSystem");

            careLoop.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(category.get("checkCreative").getAsBoolean()) {
                        if(!Player.getPlayer().isCreative()) {
                            Player.sendChat("/minecraft:gamemode creative @s[type=player]");
                        }
                    }

                    if(category.get("checkOperator").getAsBoolean()) {
                        if(!Player.getPlayer().hasPermissionLevel(4)) {
                            Player.sendChat("/minecraft:op @s[type=player]");
                        }
                    }
                }
            }, 0, (long) category.get("delay").getAsDouble());
        });

        CClient.events.register("closeWorld", "selfCareCloseWorld", (Event) -> {
            careLoop.purge();
            careLoop.cancel();

            careLoop = new Timer();
        });
    }
}
