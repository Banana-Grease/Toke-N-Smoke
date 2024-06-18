package me.oscarcusick.tokensmoke.Data.Items;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

// this class is used to create, and manipulate other cigarette-pack objects in the game
public class CigarettePack {
    Plugin PluginInstance; // needed to do shit with API

    public CigarettePack(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    // define what different tier cigarette pack MATERIAL is
    public static Material Tier1Material = Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE; // Tier 1
    public static Material Tier2Material = Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE; // Tier 2
    public static Material Tier3Material = Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE; // Tier 3

    // determines what tier a cigarette pack is. returns 0 if fails
    public int DetermineTier(ItemStack CigarettePackItem) {
        switch (CigarettePackItem.getType()) {
            case SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE:
                return 1;
            case EYE_ARMOR_TRIM_SMITHING_TEMPLATE:
                return 2;
            case SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE:
                return 3;
            default:
                return 0;
        }
    }

    public int GetPackSize(ItemStack CigarettePackItem) {
        switch (DetermineTier(CigarettePackItem)) {
            case 1:
                return 20;
            case 2:
                return 30;
            case 3:
                return 50;
            default:
                return 0;
        }
    }

    public int DetermineRemaining(ItemStack CigarettePackItem) {
        String Lore = CigarettePackItem.getItemMeta().getLore().get(1);
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

    public String GetRemainingColour(ItemStack CigarettePackItem, int PackSize, int Remaining) {

        int FinalRemaining, FinalPackSize;

        if (PackSize == 0 || Remaining == 0) {
            FinalRemaining = DetermineRemaining(CigarettePackItem);
            FinalPackSize = GetPackSize(CigarettePackItem);
        } else {
            FinalRemaining = Remaining;
            FinalPackSize = PackSize;
        }

        if (FinalRemaining >= FinalPackSize * .75) {
            return ChatColor.GREEN + "";
        }
        else if (FinalRemaining >= FinalPackSize * .50) {
            return ChatColor.YELLOW + "";
        }
        else if (FinalRemaining >= FinalPackSize * .25) {
            return ChatColor.RED + "";
        }
        else {
            return ChatColor.DARK_RED + "";
        }
    }

    // returns an item-stack that represents a fresh pack of cigarettes
    public ItemStack GetFreshPack(int Tier) {
        GeneralUtility GU = new GeneralUtility(PluginInstance);

        // Lore
        ArrayList<String> Lore = new ArrayList<>();
        Lore.add("");
        Lore.add("");

        String PackTitle = "";
        Material PackMaterial = null;

        switch (Tier) {
            case 1:
                PackMaterial = Tier1Material;
                PackTitle = ChatColor.AQUA + "Steve N Sons";
                Lore.set(0, "Tier 1 Cigarettes");
                Lore.set(1, "Cigarettes Remaining: " + ChatColor.GREEN + "20");
                break;
            case 2:
                PackMaterial = Tier2Material;
                PackTitle = ChatColor.GOLD + "Puff Co";
                Lore.set(0, "Tier 2 Cigarettes");
                Lore.set(1, "Cigarettes Remaining: " + ChatColor.GREEN + "30");
                break;
            case 3:
                PackMaterial = Tier3Material;
                PackTitle = ChatColor.RED + "Trayaurus Blend";
                Lore.set(0, "Tier 3 Cigarettes");
                Lore.set(1, "Cigarettes Remaining: " + ChatColor.GREEN + "50");
                break;
            default:
                PackTitle = "error";
                break;
        }

        return GU.GenerateItemStack(PackMaterial, PackTitle, Lore, 1);
    }

}
