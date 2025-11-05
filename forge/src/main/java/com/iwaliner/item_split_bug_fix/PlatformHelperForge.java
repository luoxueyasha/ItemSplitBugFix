package com.iwaliner.item_split_bug_fix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class PlatformHelperForge implements IPlatformHelper {
    public boolean isSplitItemStack(ItemStack stack) {
        ModCoreItemSplitBugFixForge.prepareBlacklist();
        if (ModCoreItemSplitBugFixForge.isItemOnBlacklist(stack))   return false;
        return ModCoreItemSplitBugFix.isSplitItemStack(stack);
    }

    public boolean fixBug(ItemStack stack) {
        // no need to check stack validity again, as already done in isItemOnBlacklist().
        if (ModCoreItemSplitBugFixForge.isItemOnBlacklist(stack))   return false;

        if(ModCoreItemSplitBugFix.isSplitItemStack(stack)) {
            return ModCoreItemSplitBugFix.fixBug(stack);
        }
        return false;
    }
}
