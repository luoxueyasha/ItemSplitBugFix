package com.iwaliner.item_split_bug_fix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.regex.Pattern;
import net.minecraft.world.item.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ModCoreItemSplitBugFix{
    public static final String MODID = "item_split_bug_fix";

    public static List<Pattern> blacklistPattern = new ArrayList<>();
    public static Set<Item> blacklistCache = new HashSet<>();
    public static Set<Item> checkedItemsCache = new HashSet<>();

    public static boolean isSplitItemStack(ItemStack stack) {
        return stack.getTag() != null && stack.getTag().isEmpty();
    }

    public static void fixBug(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;
        if (ModCoreItemSplitBugFix.isSplitItemStack(stack)) {
            stack.setTag(null);
        }
    }
    ////    @SubscribeEvent
////    public void ItemTooltipEvent(ItemTooltipEvent event) {
////        if (ModCoreItemSplitBugFix.isSplitItemStack(event.getItemStack())) {
////            event.getToolTip().add(Component.literal("[WARN by ItemSplitBugFix] This might provoke the split bug!").withStyle(ChatFormatting.LIGHT_PURPLE));
////        }
////    }
}
