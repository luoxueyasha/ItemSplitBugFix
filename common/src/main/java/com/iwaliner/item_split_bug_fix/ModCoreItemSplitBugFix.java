package com.iwaliner.item_split_bug_fix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.regex.Pattern;
import net.minecraft.world.item.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

#if MC_VER==MC_1_21_1
import net.minecraft.core.component.*;
import net.minecraft.world.item.component.CustomData;
#endif


public class ModCoreItemSplitBugFix{
    public static final String MODID = "item_split_bug_fix";

    public static List<Pattern> blacklistPattern = new ArrayList<>();
    public static Set<Item> blacklistCache = new HashSet<>();
    public static Set<Item> checkedItemsCache = new HashSet<>();

    public static boolean isSplitItemStack(ItemStack stack) {
#if MC_VER == MC_1_21_1
        return stack.get(DataComponents.CUSTOM_DATA) != null && Objects.equals(stack.get(DataComponents.CUSTOM_DATA), CustomData.EMPTY);
#else
        return stack.getTag() != null && stack.getTag().isEmpty();
#endif
    }

    public static void fixBug(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return;
        if (ModCoreItemSplitBugFix.isSplitItemStack(stack)) {
#if MC_VER == MC_1_21_1
            stack.remove(DataComponents.CUSTOM_DATA); // @debug, this may trigger problems in different environments. Needs more bug reports to see if this works correctly
#else
            stack.setTag(null);
#endif
        }
    }
    ////    @SubscribeEvent
////    public void ItemTooltipEvent(ItemTooltipEvent event) {
////        if (ModCoreItemSplitBugFix.isSplitItemStack(event.getItemStack())) {
////            event.getToolTip().add(Component.literal("[WARN by ItemSplitBugFix] This might provoke the split bug!").withStyle(ChatFormatting.LIGHT_PURPLE));
////        }
////    }
}
