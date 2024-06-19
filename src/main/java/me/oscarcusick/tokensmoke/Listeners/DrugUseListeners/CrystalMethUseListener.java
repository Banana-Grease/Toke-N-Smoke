package me.oscarcusick.tokensmoke.Listeners.DrugUseListeners;

import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CrystalMethUseListener implements Listener {
    Plugin PluginInstance;
    public CrystalMethUseListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void MethUseListener(PlayerInteractEvent event) {
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
        if (!event.getItem().getItemMeta().getLore().get(0).contains("Meth")) { // check for cigarette butt and cancel placing it
            return;
        }

        // smoke from mouth
        event.getPlayer().getWorld().spawnParticle(Particle.SMOKE_NORMAL, event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getY() + 1.5, event.getPlayer().getLocation().getZ(), 15, 0, 0, 0, .03, null, false);

        // apply affects of smoking meth (NOTE: Addiction & withdrawals will be added once all items are)
        int CurrentStrengthDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
            CurrentStrengthDuration = event.getPlayer().getPotionEffect(PotionEffectType.INCREASE_DAMAGE).getDuration();
        }
        int CurrentMiningDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
            CurrentMiningDuration = event.getPlayer().getPotionEffect(PotionEffectType.FAST_DIGGING).getDuration();
        }
        int CurrentConfusionDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.CONFUSION)) {
            CurrentConfusionDuration = event.getPlayer().getPotionEffect(PotionEffectType.CONFUSION).getDuration();
        }

        // strength
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, (45*20)+CurrentStrengthDuration, 2, false, false, false));
        event.getPlayer().removePotionEffect(PotionEffectType.WEAKNESS); // remove opposing effect (if it's there)

        // Mining / Fast digging
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, (45*20)+CurrentMiningDuration, 1, false, false, false));
        event.getPlayer().removePotionEffect(PotionEffectType.SLOW_DIGGING); // remove opposing effect (if it's there)

        // decrease food bar
        event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() - 6);

        // Head-Spins food bar
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (6*20)+CurrentConfusionDuration, 3, false, false, false));

        event.getItem().setAmount(event.getItem().getAmount() - 1);
    }


}
