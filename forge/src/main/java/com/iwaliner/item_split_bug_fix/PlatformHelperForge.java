package com.iwaliner.item_split_bug_fix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

// Platform-specific helper implementation for Forge
public class PlatformHelperForge implements IPlatformHelper {

    public boolean isSplitItemStack(ItemStack stack) {
        // ModCoreItemSplitBugFixForge.prepareConfig();
        //if (ModCoreItemSplitBugFixForge.isItemOnBlacklist(stack))   return false;
        return ModCoreItemSplitBugFix.isSplitItemStack(stack);
    }

    public boolean fixBug(ItemStack stack) {
        // no need to check stack validity, as already done in isItemOnBlacklist().
        ModCoreItemSplitBugFixForge.prepareConfig();
        if (ModCoreItemSplitBugFixForge.isItemOnBlacklist(stack))   return false;

        CompoundTag tag = stack.getTag();
        CompoundTag newtag = null;
        if (ModCoreItemSplitBugFixForge.isContainRemoveTag(tag)){
            newtag = ModCoreItemSplitBugFixForge.getProcessedTag(tag);
        }

        return ModCoreItemSplitBugFix.fixBug(stack, newtag);
    }
}
