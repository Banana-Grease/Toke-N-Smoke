package me.oscarcusick.tokensmoke.Listeners.DrugUseListeners;

import me.oscarcusick.tokensmoke.Data.Drugs.Cigarette;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class CigaretteUseListener implements Listener {
    Plugin PluginInstance;
    public CigaretteUseListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void CigaretteUseListener(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) { // make sure player interaction was a right click
            return;
        }
        if (event.getItem() == null) { // make sure are holding item
            return;
        }
        if (!event.getItem().hasItemMeta()) {
            return;
        }
        if (!event.getItem().getItemMeta().hasLore()) {
            return;
        }
        if (event.getItem().getItemMeta().getLore().get(0).contains("Butt")) { // check for cigarette butt and cancel placing it
            event.setCancelled(true);
            return;
        }
        if (!event.getItem().getItemMeta().getLore().get(0).contains("Tier")) { // make sure the item lore contains the word tier
            return;
        }
        if (!event.getItem().getItemMeta().getLore().get(1).contains("Puffs")) { // make sure the item lore does contain the word 'Puffs'
            return;
        }

        Cigarette CigaretteClass = new Cigarette(PluginInstance);
        //int CigaretteSize = CigaretteClass.DetermineCigaretteSize(event.getItem());
        int PuffsRemaining = CigaretteClass.DetermineRemainingPuffs(event.getItem());

        ItemMeta Meta = event.getItem().getItemMeta();
        ArrayList<String> Lore = (ArrayList<String>) Meta.getLore();

        // smoke from players' mouth (I want better later on but this is a good start)
        if (CigaretteClass.DetermineTier(event.getItem()) == 1) { // if T1 cigarette, black smoke
            event.getPlayer().getWorld().spawnParticle(Particle.SMOKE_NORMAL, event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getY() + 1.5, event.getPlayer().getLocation().getZ(), 15, 0, 0, 0, .03, null, false);
        } else { // else the smoke is white to symbolise quality
            event.getPlayer().getWorld().spawnParticle(Particle.WHITE_SMOKE, event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getY() + 1.5, event.getPlayer().getLocation().getZ(), 15, 0, 0, 0, .03, null, false);
        }

        if (PuffsRemaining <= 1) { // if the cigarette has one puff left turn it's item type into a wooden button and change lore / name
            // change item lore

            Meta.setDisplayName(ChatColor.DARK_GRAY + "Cigarette Butt");

            Lore.set(0, "A Cigarette Butt");
            Lore.remove(1);

            Meta.setLore(Lore);
            event.getItem().setItemMeta(Meta);

            event.getItem().setType(Material.OAK_BUTTON);

            event.setCancelled(true); // prevent player from placing the button on the ground

            return;
        }

        // reset item lore
        Lore.set(1, "Puffs Remaining: " + CigaretteClass.GetRemainingColour(event.getItem()) + (PuffsRemaining - 1)); // rename item meta to depict how many cigarettes are left
        Meta.setLore(Lore);
        event.getItem().setItemMeta(Meta);

        // apply affects of smoking (NOTE: Addiction & withdrawals will be added once all items are)
        int CurrentSpeedDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
            CurrentSpeedDuration = event.getPlayer().getPotionEffect(PotionEffectType.SPEED).getDuration();
        }
        int CurrentRegenDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.REGENERATION)) {
            CurrentRegenDuration = event.getPlayer().getPotionEffect(PotionEffectType.REGENERATION).getDuration();
        }
        int CurrentConfusionDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.CONFUSION)) {
            CurrentConfusionDuration = event.getPlayer().getPotionEffect(PotionEffectType.CONFUSION).getDuration();
        }
        switch (CigaretteClass.DetermineTier(event.getItem())) {
            case 1:
                // speed effect
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int)(3.5*20)+CurrentSpeedDuration, 0, false, false, false));
                event.getPlayer().removePotionEffect(PotionEffectType.SLOW); // remove opposing effect (if it's there)
                // increase food bar
                if (event.getPlayer().getFoodLevel() < 20) {
                    event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 1);
                }
                // head-spin (prevent spamming)
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (2*20)+CurrentConfusionDuration, 1, false, false, false));
                break;
            case 2:
                // speed effect
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (5*20)+CurrentSpeedDuration, 0, false, false, false));
                event.getPlayer().removePotionEffect(PotionEffectType.SLOW); // remove opposing effect (if it's there)
                // regeneration effect
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (2*20)+CurrentRegenDuration, 1, false, false, false));
                event.getPlayer().removePotionEffect(PotionEffectType.POISON); // remove opposing effect (if it's there)
                // increase food bar
                if (event.getPlayer().getFoodLevel() < 20) {
                    event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 2);
                }
                // head-spin (prevent spamming)
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (2*20)+CurrentConfusionDuration, 1, false, false, false));
                break;
            case 3:
                // speed effect
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (7*20)+CurrentSpeedDuration, 1, false, false, false));
                event.getPlayer().removePotionEffect(PotionEffectType.SLOW); // remove opposing effect (if it's there)
                // regeneration effect
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int)(1.5*20)+CurrentRegenDuration, 2, false, false, false));
                event.getPlayer().removePotionEffect(PotionEffectType.POISON); // remove opposing effect (if it's there)
                // increase food bar
                if (event.getPlayer().getFoodLevel() < 20) {
                    event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 2);
                }
                // head-spin (prevent spamming)
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (int)(1.5*20)+CurrentConfusionDuration, 2, false, false, false));
                break;
            default:
                System.out.println("Error Swtich Statement (CigaretteUseListener.java)");
                break;
        }

        event.setCancelled(true); // prevent player from placing the torches

    }
}
