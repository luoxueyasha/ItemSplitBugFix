package com.iwaliner.item_split_bug_fix;

import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class ConfigItemSplitBugFixForge {
    public static ConfigItemSplitBugFixForge CONFIG;
    public static ForgeConfigSpec CONFIG_SPEC;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLIST_ITEMS;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> REMOVE_NBT_TAGS;

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

        builder.push("nbt_cleanup_settings"); // @debug, todo

        REMOVE_NBT_TAGS = builder
            .comment(" A list of NBT tags that will be explicitly removed from an ItemStack's tag.",
                " Note: All tags will be cleaned up if the ItemStack's tag is empty after other bug fixes.",
                " Use this list to remove specific unwanted tags.",
                " Example: [\"item_stack_owner\", \"custom_tag\"]")
            .defineList("remove_tags", Arrays.asList("my_tag_to_remove_12345"),
                entry -> true
            );

        builder.pop();
    }
}
