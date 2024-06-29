package me.oscarcusick.tokensmoke.Data.Items;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.signature.qual.ArrayWithoutPackage;

import java.util.ArrayList;

public class WeedBud {
    Plugin PluginInstance; // needed to do shit with API\
    public WeedBud(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public int DetermineTier(ItemStack Item) {
        switch (Item.getType()) {
            case SUGAR_CANE:
                return 1;
            case POPPED_CHORUS_FRUIT:
                return 2;
            default:
                return -1;
        }
    }

    public ItemStack GetItem(int Tier, int Amount) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("A Marijuana Bud, Commonly Smoked");
        Lore.add("");

        Material FinalMaterial = null;

        switch (Tier) {
            case 1:
                FinalMaterial = Material.SUGAR_CANE;
                Lore.set(1, "Average Quality");
                break;
            case 2:
                FinalMaterial = Material.POPPED_CHORUS_FRUIT;
                Lore.set(1, "High Quality");
                break;
            default:
                return null;
        }

        return GU.GenerateItemStack(FinalMaterial, ChatColor.GREEN + "Weed Bud", Lore, Amount);
    }

    public ItemStack GetItem(int Tier) {
        return GetItem(Tier, 1);
    }

}
