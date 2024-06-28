package me.oscarcusick.tokensmoke.Listeners;

import me.oscarcusick.tokensmoke.Data.Drugs.Cigarette;
import me.oscarcusick.tokensmoke.Data.Items.CigarettePack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class CigarettePackUseListener implements Listener {
    Plugin PluginInstance;
    public CigarettePackUseListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void CigarettePackUseListener(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) { // make sure player interaction was a right click
            return;
        }
        if (event.getItem() == null) { // make sure are holding item
            return;
        }
        if (!event.getItem().getItemMeta().hasLore()) {
            return;
        }
        if (!event.getItem().getItemMeta().getLore().get(0).contains("Tier")) { // make sure the item lore contains the word tier
            return;
        }
        if (event.getItem().getItemMeta().getLore().get(1).contains("Puffs")) { // make sure the item lore does not contain the word 'Puffs'
            return;
        }
        if (event.getItem().getAmount() > 1) {
            return;
        }

        CigarettePack CP = new CigarettePack(PluginInstance);
        int PackSize = CP.GetPackSize(event.getItem());
        int Remaining = CP.DetermineRemaining(event.getItem());

        if (Remaining <= 0) { // before we give the player a cigarette, make sure there are some left in the pack
            return;
        }

        // reset item lore
        ItemMeta Meta = event.getItem().getItemMeta();
        ArrayList<String> Lore = (ArrayList<String>) Meta.getLore();
        Lore.set(1, "Cigarettes Remaining: " + CP.GetRemainingColour(event.getItem(), PackSize, Remaining - 1) + (Remaining - 1)); // rename item meta to depict how many cigarettes are left

        Meta.setLore(Lore);
        event.getItem().setItemMeta(Meta);

        // give player a cigarette
        Cigarette CigaretteClass = new Cigarette(PluginInstance);
        event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), CigaretteClass.GetNewCigaretteItem(CP.DetermineTier(event.getItem())));

    }
}
