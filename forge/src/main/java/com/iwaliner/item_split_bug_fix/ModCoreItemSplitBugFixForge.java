package com.iwaliner.item_split_bug_fix;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.List;
import java.util.regex.Pattern;

import static com.iwaliner.item_split_bug_fix.ModCoreItemSplitBugFix.*;


@Mod(ModCoreItemSplitBugFix.MODID)
public class ModCoreItemSplitBugFixForge{
    public static boolean isPrepared = false;

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
        if(blacklistCheckedItemsCache.contains(item)){
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
        blacklistCheckedItemsCache.add(item);
        return false;
    }

    static boolean isContainRemoveTag(CompoundTag tags){
        if(tags == null){
            return false;
        }
        if(!prepareRemoveTagList()){
            return false;
        }
        return containsRemoveTagRecursive(tags);

    }

    private static boolean containsRemoveTagRecursive(CompoundTag tags){
        for (String key : tags.getAllKeys()) {
            for (Pattern p : removeTagListPattern) {
                if (p.matcher(key).matches()) {
                    return true;
                }
            }

            // if the value is a CompoundTag, check recursively
            Tag value = tags.get(key);
            if (value instanceof CompoundTag) {
                if (containsRemoveTagRecursive((CompoundTag) value)) {
                    return true;
                }
            } else if (value instanceof ListTag list) {
                for (Tag element : list) {
                    if (element instanceof CompoundTag
                        && containsRemoveTagRecursive((CompoundTag) element)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static CompoundTag getProcessedTag(CompoundTag tag){
        if (tag == null) return null;
        prepareRemoveTagList();

        CompoundTag processed = processTagRecursive(tag);
        // if processed is null or empty, return null
        return (processed == null || processed.isEmpty()) ? null : processed;
    }

    private static CompoundTag processTagRecursive(CompoundTag tag) {
        CompoundTag out = new CompoundTag();

        for (String key : tag.getAllKeys()) {
            boolean matched = false;
            for (Pattern p : removeTagListPattern) {
                if (p.matcher(key).matches()) {
                    matched = true;
                    break;
                }
            }
            if (matched) continue; // this key should be removed

            Tag value = tag.get(key);
            if (value instanceof CompoundTag childCompound) {
                CompoundTag processedChild = processTagRecursive(childCompound);
                if (processedChild != null && !processedChild.isEmpty()) {
                    out.put(key, processedChild);
                }
            } else if (value instanceof ListTag list) {
                ListTag newList = new ListTag();
                for (Tag element : list) {
                    if (element instanceof CompoundTag elementCompound) {
                        CompoundTag processedElem = processTagRecursive(elementCompound);
                        if (processedElem != null && !processedElem.isEmpty()) {
                            newList.add(processedElem);
                        }
                    } else {
                        newList.add(element);
                    }
                }
                if (!newList.isEmpty()) {
                    out.put(key, newList);
                }
            } else {
                out.put(key, value);
            }
        }

        return out.isEmpty() ? null : out;
    }

    public static void prepareConfig(){
        if(isPrepared){
            return;
        }
        prepareBlacklist();
        prepareRemoveTagList();
        isPrepared = true;
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
            blacklistCheckedItemsCache.clear();
        }
    }

    public static boolean prepareRemoveTagList(){
        if(!removeTagListPattern.isEmpty()){
            return false;
        }
        List<? extends String> list = ConfigItemSplitBugFixForge.REMOVE_TAG_LIST.get();
        if(list != null) {
            for(String s : list) {
                String regex = s.replace("*", ".*");
                removeTagListPattern.add(Pattern.compile(regex));
            }
        }
        return true;
    }

}
