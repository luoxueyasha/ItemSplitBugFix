package com.iwaliner.item_split_bug_fix;

import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

// Configuration class for Forge
public class ConfigItemSplitBugFixForge {
    public static ConfigItemSplitBugFixForge CONFIG;
    public static ForgeConfigSpec CONFIG_SPEC;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_ITEMS;

    static {
        Pair<ConfigItemSplitBugFixForge, ForgeConfigSpec> pair =
            new ForgeConfigSpec.Builder().configure(ConfigItemSplitBugFixForge::new);
        //Store the resulting values
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }



    private ConfigItemSplitBugFixForge(ForgeConfigSpec.Builder builder) {
        builder.comment("Config file for ItemSplitBugFix");

        builder.push("blacklist_settings");

        BLACKLIST_ITEMS = builder
            .comment(" Items in this list will not be modified by ItemSplitBugFix.",
                " Supports wildcard '*'. For example:",
                "   - 'minecraft:diamond' matches exactly 'minecraft:diamond'",
                "   - '*:diamond*' matches any item from any mod that contains 'diamond'",
                "   - 'minecraft:*' matches all items from minecraft namespace",
                "   - '*nec*' matches any item that contains 'nec', eg. 'necromancy:abc', 'my_mod:necklace'")
            .defineList("blacklist", Arrays.asList(
                    "alexscaves:sack_of_sating",
                    "hexerei:*",
                    "spelunkery:*",
                    "minecraft:spyglass",
                    "technicalcores:*"
                ),
                entry -> true
            );

        builder.pop();
    }
}
