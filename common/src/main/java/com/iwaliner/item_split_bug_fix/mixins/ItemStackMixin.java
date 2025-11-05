package com.iwaliner.item_split_bug_fix.mixins;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.iwaliner.item_split_bug_fix.ModPlatformHandler;

@Mixin(ItemStack.class)

public abstract class ItemStackMixin {


    @Inject(method = "split",at = @At("HEAD"), cancellable = true)
    private void splitInject(CallbackInfoReturnable<ItemStack> cir){
        ModPlatformHandler.fixBug(((ItemStack) (Object)this));
    }
    @Inject(method = "copy",at = @At("HEAD"), cancellable = true)
    private void copyInject(CallbackInfoReturnable<ItemStack> cir){
        ModPlatformHandler.fixBug(((ItemStack) (Object)this));
    }
    @Inject(method = "isSameItemSameTags",at = @At("HEAD"), cancellable = true)
    private static void isSameItemSameTagsInject(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Boolean> cir){
        ModPlatformHandler.fixBug(stack1);
        ModPlatformHandler.fixBug(stack2);
    }
    @Inject(method = "getCount",at = @At("HEAD"), cancellable = true)
    private void getCountInject(CallbackInfoReturnable<Integer> cir){
        ModPlatformHandler.fixBug(((ItemStack) (Object)this));
    }
    @Inject(method = "setCount",at = @At("HEAD"), cancellable = true)
    private void setCountInject(int p_41765_, CallbackInfo ci){
        ModPlatformHandler.fixBug(((ItemStack) (Object)this));
    }


#if MC_VER == MC_1_21_1 || MC_VER==MC_1_20_1
    @Inject(method = "isSameItem", at = @At("HEAD"), cancellable = true)
#elif MC_VER == MC_1_19_2
    @Inject(method = "isSame", at = @At("HEAD"), cancellable = true)
#endif
    private static void isSameInject(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Boolean> cir){
        ModPlatformHandler.fixBug(stack1);
        ModPlatformHandler.fixBug(stack2);
    }

    // @debug. this may cause issues
//#if MC_VER == MC_1_19_2
//    @Inject(method = "isSameIgnoreDurability",at = @At("HEAD"), cancellable = true)
//    private static void isSameIgnoreDurabilityInject(ItemStack stack1, ItemStack stack2, CallbackInfoReturnable<Boolean> cir){
//        ModPlatformHandler.fixBug(stack1);
//        ModPlatformHandler.fixBug(stack2);
//    }
//#endif


}
