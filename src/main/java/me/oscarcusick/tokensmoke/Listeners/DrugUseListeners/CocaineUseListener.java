package me.oscarcusick.tokensmoke.Listeners.DrugUseListeners;

import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CocaineUseListener implements Listener {
    Plugin PluginInstance;
    public CocaineUseListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void CocaineUseListener(PlayerInteractEvent event) {
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
        if (!event.getItem().getItemMeta().getLore().get(0).contains("Cocaine")) { // check for cigarette butt and cancel placing it
            return;
        }

        // apply affects of smoking meth (NOTE: Addiction & withdrawals will be added once all items are)
        int CurrentSpeedDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
            CurrentSpeedDuration = event.getPlayer().getPotionEffect(PotionEffectType.SPEED).getDuration();
        }
        int CurrentConfusionDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.CONFUSION)) {
            CurrentConfusionDuration = event.getPlayer().getPotionEffect(PotionEffectType.CONFUSION).getDuration();
        }
        int CurrentHungerDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.HUNGER)) {
            CurrentHungerDuration = event.getPlayer().getPotionEffect(PotionEffectType.HUNGER).getDuration();
        }

        // strength
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (60*20)+CurrentSpeedDuration, 1, false, false, false));
        event.getPlayer().removePotionEffect(PotionEffectType.SLOW); // remove opposing effect (if it's there)

        // Head-Spins food bar
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (6*20)+CurrentConfusionDuration, 3, false, false, false));

        // Hunger
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (30*20)+CurrentHungerDuration, 3, false, false, false));

        event.getItem().setAmount(event.getItem().getAmount() - 1);
    }


}
