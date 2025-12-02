package com.iwaliner.item_split_bug_fix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IPlatformHelper {
    boolean isSplitItemStack(ItemStack stack);
    boolean fixBug(ItemStack stack);

}
