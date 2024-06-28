package me.oscarcusick.tokensmoke.Data.Items;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class OpiumSeed {
    Plugin PluginInstance; // needed to do shit with API
    public OpiumSeed(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetItem(int Amount) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Opium Seeds");

        return GU.GenerateItemStack(Material.MELON_SEEDS, "Opium Seeds", Lore, Amount);
    }

    public ItemStack GetItem() {
        return GetItem(1);
    }
}
