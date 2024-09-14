package org.e11eman.crackutilities.mixins;

import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.e11eman.crackutilities.utilities.CClient;
import org.e11eman.crackutilities.wrappers.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.network.ClientPlayerEntity.class)
public class ClientPlayerEntity {
    @Inject(method = "move", at = @At("HEAD"))
    public void move(MovementType movementType, Vec3d movement, CallbackInfo ci) {
        CClient.events.invoke("playerMovement", Player.getPlayer().getPos());
    }
}
