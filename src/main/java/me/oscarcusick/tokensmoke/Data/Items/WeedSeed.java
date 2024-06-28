package me.oscarcusick.tokensmoke.Data.Items;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class WeedSeed {
    Plugin PluginInstance; // needed to do shit with API
    public WeedSeed(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public int DetermineTier(ItemStack Item) {
        switch (Item.getType()) {
            case WHEAT_SEEDS:
                return 1;
            case PUMPKIN_SEEDS:
                return 2;
            default:
                return -1;
        }
    }

    public ItemStack GetItem(int Tier, int Amount) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Marijuana Seeds");
        Lore.add("");

        Material FinalMaterial = null;

        switch (Tier) {
            case 1:
                FinalMaterial = Material.WHEAT_SEEDS;
                Lore.set(1, "Average Quality");
                break;
            case 2:
                FinalMaterial = Material.PUMPKIN_SEEDS;
                Lore.set(1, "High Quality");
                break;
            default:
                return null;
        }

        return GU.GenerateItemStack(FinalMaterial, "Weed Seeds", Lore, Amount);
    }

    public ItemStack GetItem(int Tier) {
        return GetItem(Tier, 1);
    }
}
