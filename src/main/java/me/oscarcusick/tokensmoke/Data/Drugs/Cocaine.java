package me.oscarcusick.tokensmoke.Data.Drugs;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Cocaine {
    Plugin PluginInstance;
    public Cocaine(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetCocaineItem() {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Cocaine");
        Lore.add("A Potent Speed Enhancing Drug");

        return GU.GenerateItemStack(Material.SUGAR, ChatColor.YELLOW + "Cocaine", Lore, 1);
    }
}
