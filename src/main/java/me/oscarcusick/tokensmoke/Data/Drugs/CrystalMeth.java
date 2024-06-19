package me.oscarcusick.tokensmoke.Data.Drugs;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class CrystalMeth {
    Plugin PluginInstance;
    public CrystalMeth(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetMethItem() {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Crystal Meth");
        Lore.add("A Potent Strength Enhancing Drug");

        return GU.GenerateItemStack(Material.PRISMARINE_CRYSTALS, ChatColor.YELLOW + "Meth", Lore, 1);
    }
}
