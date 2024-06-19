package me.oscarcusick.tokensmoke.Data.Drugs;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Morphine {
    Plugin PluginInstance;
    public Morphine(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetMorphineItem(boolean IsSyringe) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Morphine");
        Lore.add("A Potent Pain Killer");

        if (IsSyringe) {
            Lore.set(0, Lore.get(0) + " Syringe");
        }

        if (IsSyringe) {
            return GU.GenerateItemStack(Material.CANDLE, ChatColor.GREEN + "Syringe", Lore, 1);
        }
        return GU.GenerateItemStack(Material.WHITE_DYE, ChatColor.GREEN + "Morphine", Lore, 1);
    }
}
