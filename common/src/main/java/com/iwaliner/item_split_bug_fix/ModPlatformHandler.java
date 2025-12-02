package com.iwaliner.item_split_bug_fix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

// platform handler to delegate platform-specific implementations
public class ModPlatformHandler {
    public static IPlatformHelper HELPER = null;

    public static boolean isSplitItemStack(ItemStack stack) {
        if (HELPER != null) {
            return HELPER.isSplitItemStack(stack);
        }

        return ModCoreItemSplitBugFix.isSplitItemStack(stack);
    }

    public static boolean fixBug(ItemStack stack) {
        if (HELPER != null) {
            return HELPER.fixBug(stack);
        }

        return ModCoreItemSplitBugFix.fixBug(stack);
    }


}
