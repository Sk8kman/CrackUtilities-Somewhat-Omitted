package org.e11eman.crackutilities.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.e11eman.crackutilities.utilities.CClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.world.World.class)
public class World {
    @Inject(method = "onBlockChanged", at = @At("HEAD"))
    public void onBlockChanged(BlockPos pos, BlockState oldBlock, BlockState newBlock, CallbackInfo ci) {
        CClient.events.invoke("vanillaBlockBreak", pos, oldBlock, newBlock);
    }
}
