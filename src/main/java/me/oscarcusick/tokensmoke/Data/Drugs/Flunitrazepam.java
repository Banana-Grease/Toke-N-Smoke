package me.oscarcusick.tokensmoke.Data.Drugs;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Flunitrazepam {
    Plugin PluginInstance;
    public Flunitrazepam(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetFlunitrazepamItem(boolean IsSyringe) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Flunitrazepam");
        Lore.add("A Chinese Research Chemical");

        if (IsSyringe) {
            Lore.set(0, Lore.get(0) + " Syringe");
        }

        if (IsSyringe) {
            return GU.GenerateItemStack(Material.GRAY_CANDLE, ChatColor.DARK_RED + "Syringe", Lore, 1);
        }
        return GU.GenerateItemStack(Material.IRON_NUGGET, ChatColor.DARK_RED + "Flunitrazepam", Lore, 1);
    }
}
