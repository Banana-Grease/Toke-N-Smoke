package me.oscarcusick.tokensmoke.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class NeedleCraftListener implements Listener {
    Plugin PluginInstance;
    public NeedleCraftListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }


    // prevent regular ingredients from crafting needle-varients.
    // to prevent this;
    // check if the result item has lore, if so;
    // check if at least one of the ingredients have lore.
    // if that fails cancel event
    @EventHandler
    public void NeedleCraftListener(CraftItemEvent event) {
    if (!event.getRecipe().getResult().hasItemMeta()) {
        return;
    }
    if (!event.getRecipe().getResult().getItemMeta().hasLore()) {
        return;
    }

    boolean FoundLore = false;
    for (ItemStack Ingredient : event.getInventory().getMatrix()) {
        if (Ingredient == null) {
            continue;
        }
        if (Ingredient.hasItemMeta()) {
            if (Ingredient.getItemMeta().hasLore()) {
                FoundLore = true;
                break;
            }
        }
    }

    if (!FoundLore) {
        event.setCancelled(true);
    }

    }
}
