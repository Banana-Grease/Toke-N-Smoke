package me.oscarcusick.tokensmoke.Data.Items;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class WeedStem {
    Plugin PluginInstance; // needed to do shit with API\
    public WeedStem(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetItem(int Amount) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("The Stem Of A Marijuana Plant");

        return GU.GenerateItemStack(Material.BAMBOO, "Weed Stem", Lore, Amount);
    }
}
