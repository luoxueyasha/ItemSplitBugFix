package com.iwaliner.item_split_bug_fix.mixins;

import com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix;
import com.iwaliner.item_split_bug_fix.ModPlatformHandler;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

#if MC_VER==MC_1_21_1
import net.minecraft.core.component.*;
#endif

@Mixin(Slot.class)

public abstract class SlotMixin {
#if MC_VER==MC_1_21_1 || MC_VER==MC_1_20_1 || MC_VER==MC_1_19_2

    @Shadow @Final public Container container;

    @Shadow @Final private int slot;

    @Inject(method = "getItem",at = @At("HEAD"), cancellable = true)
    private void getItemInject(CallbackInfoReturnable<ItemStack> cir){
        if(this.container!=null) {
            ItemStack stack = container.getItem(this.slot);
            if(ModPlatformHandler.fixBug(stack)){
                cir.setReturnValue(stack);
            }
        }
    }
    @Inject(method = "set",at = @At("HEAD"), cancellable = true)
    private void setInject(ItemStack stack, CallbackInfo ci){
        ModPlatformHandler.fixBug(stack);
    }

    @Inject(method = "onQuickCraft",at = @At("HEAD"), cancellable = true)
    private void onQuickCraftInject(ItemStack stack1, ItemStack stack2,CallbackInfo ci){
        ModPlatformHandler.fixBug(stack1);
        ModPlatformHandler.fixBug(stack2);
    }
    @Inject(method = "onTake",at = @At("HEAD"), cancellable = true)
    private void onTakeInject(Player player, ItemStack stack, CallbackInfo ci){
        ModPlatformHandler.fixBug(stack);
    }
    @Inject(method = "mayPlace",at = @At("HEAD"), cancellable = true)
    private void mayPlaceInject(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        ModPlatformHandler.fixBug(stack);
    }
#endif

#if MC_VER == MC_1_21_1 || MC_VER == MC_1_20_1
    @Inject(method = "setByPlayer",at = @At("HEAD"), cancellable = true)
    private void setByPlayerInject(ItemStack stack, CallbackInfo ci){
        ModPlatformHandler.fixBug(stack);
    }
#endif
}
