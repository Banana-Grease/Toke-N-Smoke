package me.oscarcusick.tokensmoke.Data.Items;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
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
        Lore.add("Papaver Somniferum Seeds" );
        Lore.add("Commonly Grown For It's Medicating Effects");

        return GU.GenerateItemStack(Material.MELON_SEEDS,  ChatColor.RESET +"Opium Seeds", Lore, Amount);
    }

    public ItemStack GetItem() {
        return GetItem(1);
    }
}
