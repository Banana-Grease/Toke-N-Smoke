package me.oscarcusick.tokensmoke.Data.Drugs;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Joint {
    Plugin PluginInstance;
    public Joint(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public ItemStack GetJointItem(int Tier) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("");

        if (Tier == 2) {
            Lore.set(0, "High Quality ");
        }
        Lore.set(0, Lore.get(0) + "Joint");

        Lore.add("Puffs Remaining: " + ChatColor.GREEN + "4");

        return GU.GenerateItemStack(Material.TORCH, ChatColor.GREEN + "Joint", Lore, 1);
    }

    // determines what tier a Joint is
    public int DetermineTier(ItemStack JointItem) {
        if (JointItem.getItemMeta().getLore().get(0).contains("Quality")) {
            return 2;
        }
        else {
            return 1;
        }
    }

    // determine how many puffs are left
    public int DetermineRemainingPuffs(ItemStack JointItem) {
        String Lore = JointItem.getItemMeta().getLore().get(1);
        String Remaining = "";

        if (Lore.contains(ChatColor.GREEN + "")) {
            Remaining = Lore.split(ChatColor.GREEN + "")[1]; // split from the colour
        }
        else if (Lore.contains(ChatColor.YELLOW + "")) {
            Remaining = Lore.split(ChatColor.YELLOW + "")[1]; // split from the colour
        }
        else if (Lore.contains(ChatColor.RED + "")) {
            Remaining = Lore.split(ChatColor.RED + "")[1]; // split from the colour
        }
        else if (Lore.contains(ChatColor.DARK_RED + "")) {
            Remaining = Lore.split(ChatColor.DARK_RED + "")[1]; // split from the colour
        }

        return Integer.parseInt(Remaining);
    }

    // get the colour that should be displayed before the amount of puffs remaining
    public String GetRemainingColour(ItemStack CigaretteItem) {
        int RemainingPuffs = DetermineRemainingPuffs(CigaretteItem);

        if (RemainingPuffs >= 4 * .75) {
            return ChatColor.GREEN + "";
        }
        else if (RemainingPuffs >= 4 * .50) {
            return ChatColor.YELLOW + "";
        }
        else if (RemainingPuffs >= 4 * .25) {
            return ChatColor.RED + "";
        }
        else {
            return ChatColor.DARK_RED + "";
        }

    }
}
