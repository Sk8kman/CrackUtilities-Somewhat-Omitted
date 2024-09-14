package org.e11eman.crackutilities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.utilities.SecureRandomStuff;
import org.e11eman.crackutilities.wrappers.Player;

public class InitializationPoint implements ModInitializer {
    public static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public void onInitialize() {
        CClient.initSystems();
        CClient.registerExtraEvents();

        if(CClient.isDevelopment()) {
            Player.setUsername("CrackDev" + SecureRandomStuff.getRandomString(4));
        }
    }
}
