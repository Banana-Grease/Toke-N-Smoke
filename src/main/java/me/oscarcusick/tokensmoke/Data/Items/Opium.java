package me.oscarcusick.tokensmoke.Data.Items;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Opium {
    Plugin PluginInstance;
    public Opium(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetOpiumItem(int Amount) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Opium");
        Lore.add("Commonly Used To Manufacture Drugs");

        return GU.GenerateItemStack(Material.SNOWBALL, ChatColor.YELLOW + "Opium", Lore, Amount);
    }

    public ItemStack GetOpiumItem() {
        return GetOpiumItem(1);
    }
}
