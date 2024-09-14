package org.e11eman.crackutilities.mixins;

import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import org.e11eman.crackutilities.utilities.CClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.network.ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandler {
    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    public void onOnGameMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        CClient.events.invoke("serverGameMessage", packet, ci);
    }

    @Inject(method = "onPlaySound", at = @At("HEAD"))
    public void onPlaySound(PlaySoundS2CPacket packet, CallbackInfo ci) {
        CClient.events.invoke("playSound", packet);
    }
}
