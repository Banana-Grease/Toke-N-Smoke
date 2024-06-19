package me.oscarcusick.tokensmoke.Data.Drugs;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Ketamine {
    Plugin PluginInstance;
    public Ketamine(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetKetamineItem(boolean IsSyringe) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("Ketamine");
        Lore.add("A Potent Recovery Drug");

        if (IsSyringe) {
            Lore.set(0, Lore.get(0) + " Syringe");
        }

        if (IsSyringe) {
            return GU.GenerateItemStack(Material.CANDLE, ChatColor.GREEN + "Syringe", Lore, 1);
        }
        return GU.GenerateItemStack(Material.LIGHT_GRAY_DYE, ChatColor.GREEN + "Ketamine", Lore, 1);
    }
}
