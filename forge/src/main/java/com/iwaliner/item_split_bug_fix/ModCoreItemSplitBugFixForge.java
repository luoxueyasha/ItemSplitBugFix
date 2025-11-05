package com.iwaliner.item_split_bug_fix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.List;
import java.util.regex.Pattern;

import static com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix.blacklistPattern;
import static com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix.blacklistCache;
import static com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix.checkedItemsCache;


@Mod(ModCoreItemSplitBugFix.MODID)
public class ModCoreItemSplitBugFixForge{
    public ModCoreItemSplitBugFixForge() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigItemSplitBugFixForge.CONFIG_SPEC,"ItemSplitBugFix.toml");
        MinecraftForge.EVENT_BUS.register(this);
        ModPlatformHandler.HELPER = new PlatformHelperForge();
    }

    static boolean isItemOnBlacklist(ItemStack stack){
        if (stack == null || stack.isEmpty() || blacklistPattern.isEmpty()) {
            return true;
        }
        Item item = stack.getItem();
        if(blacklistCache.contains(item)){
            return true;
        }
        if(checkedItemsCache.contains(item)){
            return false;
        }

        ResourceLocation RLItemId = ForgeRegistries.ITEMS.getKey(item);
        if(RLItemId == null)
            return true;
        String id = RLItemId.toString();

        prepareBlacklist();
        for(Pattern p : blacklistPattern) {
            if(p.matcher(id).matches()) {
                blacklistCache.add(item);
                return true;
            }
        }
        checkedItemsCache.add(item);
        return false;
    }

    public static void prepareBlacklist() {
        if(!blacklistPattern.isEmpty()){
            return;
        }

        List<? extends String> list = ConfigItemSplitBugFixForge.BLACKLIST_ITEMS.get();
        if(list != null) {
            for(String s : list) {
                String regex = s.replace("*", ".*");
                blacklistPattern.add(Pattern.compile(regex));
            }
            blacklistCache.clear();
            checkedItemsCache.clear();
        }
    }

}
