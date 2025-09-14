package com.iwaliner.item_split_bug_fix.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "runTick", at = @At("HEAD"))
    private void init(boolean bl, CallbackInfo ci) {
        String str = "runTick mixin from Mymod!";
        #if MC_VER == MC_1_19_2
            str += "1.19.2";
        #elif MC_VER == MC_1_20_1
            str += "1.20.1";
        #elif MC_VER == MC_1_21_1
            str += "1.21.1";
        #endif

        System.out.println(str);

    }

}
