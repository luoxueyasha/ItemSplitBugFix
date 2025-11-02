//package com.iwaliner.item_split_bug_fix;
//
//import net.minecraft.ChatFormatting;
//import net.minecraft.network.chat.Component;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.*;
//import net.neoforged.fml.*;
//import net.neoforged.fml.common.Mod;
//import net.neoforged.fml.config.ModConfig;
//import net.neoforged.neoforge.common.NeoForge;
//
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.regex.Pattern;
//
//import static com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix.blacklistPattern;
//import static com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix.blacklistCache;
//import static com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix.checkedItemsCache;
//
//
//@Mod(ModCoreItemSplitBugFix.MODID)
//public class ModCoreItemSplitBugFixNeoForge {
//    public ModCoreItemSplitBugFixNeoForge(ModContainer modContainer) {
//        modContainer.registerConfig(ModConfig.Type.COMMON, ConfigItemSplitBugFixNeoForge.CONFIG_SPEC,"ItemSplitBugFix.toml");
//        NeoForge.EVENT_BUS.register(this);
//    }
//    public static boolean isSplitItemStack(ItemStack stack) {
//
//        ModCoreItemSplitBugFixNeoForge.prepareBlacklist();
//        if (ModCoreItemSplitBugFixNeoForge.isItemOnBlacklist(stack))   return false;
//        return ModCoreItemSplitBugFix.isSplitItemStack(stack);
//    }
//
//    public static void fixBug(ItemStack stack) {
//        if (stack == null || stack.isEmpty()) return;
//        if (ModCoreItemSplitBugFixNeoForge.isItemOnBlacklist(stack))   return;
//
//        ModCoreItemSplitBugFix.fixBug(stack);
//    }
//
//    static boolean isItemOnBlacklist(ItemStack stack){
//        if (stack == null || stack.isEmpty() || blacklistPattern.isEmpty()) {
//            return true;
//        }
//        Item item = stack.getItem();
//        if(blacklistCache.contains(item)){
//            return true;
//        }
//        if(checkedItemsCache.contains(item)){
//            return false;
//        }
//
//        ResourceLocation RLItemId = ItemRegister.ITEMS.getKey(item);
//        if(RLItemId == null)
//            return true;
//        String id = RLItemId.toString();
//
//        prepareBlacklist();
//        for(Pattern p : blacklistPattern) {
//            if(p.matcher(id).matches()) {
//                blacklistCache.add(item);
//                return true;
//            }
//        }
//        checkedItemsCache.add(item);
//        return false;
//    }
//
//    public static void prepareBlacklist() {
//        if(!blacklistPattern.isEmpty()){
//            return;
//        }
//
//        List<? extends String> list = ConfigItemSplitBugFixNeoForge.BLACKLIST_ITEMS.get();
//        if(list != null) {
//            for(String s : list) {
//                String regex = s.replace("*", ".*");
//                blacklistPattern.add(Pattern.compile(regex));
//            }
//            blacklistCache.clear();
//            checkedItemsCache.clear();
//        }
//    }
//
//}
