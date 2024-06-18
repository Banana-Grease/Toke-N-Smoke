package me.oscarcusick.tokensmoke.Data.Drugs;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Cigarette {
    Plugin PluginInstance; // needed to do shit with API

    public Cigarette(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    // define what different tier cigarette MATERIAL is
    public static Material Tier1Material = Material.TORCH; // Tier 1
    public static Material Tier2Material = Material.TORCH; // Tier 2
    public static Material Tier3Material = Material.SOUL_TORCH; // Tier 3

    // determines what tier a cigarette is. returns 0 if fails
    public int DetermineTier(ItemStack CigaretteItem) {
        if (CigaretteItem.getItemMeta().getDisplayName().contains(ChatColor.AQUA + "")) { // determine it's a t1 cigarette by the colour of the name
            return 1;
        }

        else if (CigaretteItem.getItemMeta().getDisplayName().contains(ChatColor.GOLD + "")) {
            return 2;
        }

        else if (CigaretteItem.getType().equals(Material.SOUL_TORCH)) { // if it's a soul torch then it's a t3 cigarette
            return 3;
        }

        else {
            return 0;
        }
    }

    // determine how many puffs are left
    public int DetermineRemainingPuffs(ItemStack CigaretteItem) {
        String Lore = CigaretteItem.getItemMeta().getLore().get(1);
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

    // determine how many puffs each tier of cigarette should have
    public int DetermineCigaretteSize(ItemStack CigaretteItem) {
        switch (DetermineTier(CigaretteItem)) {
            case 1:
                return 4;
            case 2:
                return 6;
            case 3:
                return 8;
            default:
                return 0;
        }
    }

    // get the colour that should be displayed before the amount of puffs remaining
    public String GetRemainingColour(ItemStack CigaretteItem) {
        int RemainingPuffs = DetermineRemainingPuffs(CigaretteItem);
        int CigaretteSize = DetermineCigaretteSize(CigaretteItem);

        if (RemainingPuffs >= CigaretteSize * .75) {
            return ChatColor.GREEN + "";
        }
        else if (RemainingPuffs >= CigaretteSize * .50) {
            return ChatColor.YELLOW + "";
        }
        else if (RemainingPuffs >= CigaretteSize * .25) {
            return ChatColor.RED + "";
        }
        else {
            return ChatColor.DARK_RED + "";
        }

    }

    public ItemStack GetNewCigaretteItem(int Tier) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("");
        Lore.add("");

        String CigaretteTitle = "";
        Material CigaretteMaterial = null;

        // determine material, title and lore
        switch (Tier) {
            case 1:
                Lore.set(0, "A Tier 1 Cigarette");
                Lore.set(1, "Puffs Remaining: " + ChatColor.GREEN + "4");
                CigaretteTitle = (ChatColor.AQUA + "Cigarette");
                CigaretteMaterial = Tier1Material;
                break;
            case 2:
                Lore.set(0, "A Tier 2 Cigarette");
                Lore.set(1, "Puffs Remaining: " + ChatColor.GREEN + "6");
                CigaretteTitle = (ChatColor.GOLD + "Cigarette");
                CigaretteMaterial = Tier2Material;
                break;
            case 3:
                Lore.set(0, "A Tier 3 Cigarette");
                Lore.set(1, "Puffs Remaining: " + ChatColor.GREEN + "8");
                CigaretteTitle = (ChatColor.RED + "Cigarette");
                CigaretteMaterial = Tier3Material;
                break;
            default:
                Lore.set(0, "Error");
                Lore.set(1, "Error");
                CigaretteTitle = ("Switch Error (Cigarette.java)");
                CigaretteMaterial = Tier1Material;
                break;
        }

        return GU.GenerateItemStack(CigaretteMaterial, CigaretteTitle, Lore, 1);
    }

}
