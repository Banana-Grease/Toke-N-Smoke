package me.oscarcusick.tokensmoke.Data.Drugs;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Fentanyl {
    Plugin PluginInstance;
    public Fentanyl(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetFentanylItem(boolean IsSyringe) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Fentanyl");
        Lore.add("A Street Recovery Drug");

        if (IsSyringe) {
            Lore.set(0, Lore.get(0) + " Syringe");
        }

        if (IsSyringe) {
            return GU.GenerateItemStack(Material.LIGHT_GRAY_CANDLE, ChatColor.RED + "Syringe", Lore, 1);
        }
        return GU.GenerateItemStack(Material.IRON_NUGGET, ChatColor.RED + "Fentanyl", Lore, 1);
    }
}
