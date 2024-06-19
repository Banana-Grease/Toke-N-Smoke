package me.oscarcusick.tokensmoke.Listeners.DrugUseListeners;

import me.oscarcusick.tokensmoke.Data.Drugs.Morphine;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MorphineUseListener implements Listener {
    Plugin PluginInstance;
    public MorphineUseListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public void ApplyEffects(LivingEntity E) {
        // apply effects (NOTE: Addiction & withdrawals will be added once all items are)
        int CurrentResistanceDuration = 0;
        if (E.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            CurrentResistanceDuration = E.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getDuration();
        }
        int CurrentSlowDuration = 0;
        if (E.hasPotionEffect(PotionEffectType.SLOW)) {
            CurrentSlowDuration = E.getPotionEffect(PotionEffectType.SLOW).getDuration();
        }

        // Damage Resistance
        E.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, (75*20)+CurrentResistanceDuration, 2, false, false, false));

        // Slow
        E.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (6*20)+CurrentSlowDuration, 5, false, false, false));
    }

    @EventHandler // use on self
    public void MorphineUseSelfListener(PlayerInteractEvent event) {
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
        if (!event.getItem().getItemMeta().getLore().get(0).contains("Morphine Syringe")) {
            return;
        }

        // apply effects (NOTE: Addiction & withdrawals will be added once all items are)
        ApplyEffects(event.getPlayer());

        event.getItem().setAmount(event.getItem().getAmount() - 1);
    }

    @EventHandler // use on others
    public void MorphineUseOthersListener(EntityDamageEvent event) {
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            return;
        }
        if (!event.getDamageSource().getCausingEntity().getType().equals(EntityType.PLAYER)) {
            return;
        }

        Player Attacker = (Player)event.getDamageSource().getCausingEntity();
        LivingEntity Defender = (LivingEntity) event.getEntity();

        Morphine MorphineClass = new Morphine(PluginInstance);

        if (!Attacker.getInventory().getItemInMainHand().equals(MorphineClass.GetMorphineItem(true))) {
            return;
        }

        // apply effects (NOTE: Addiction & withdrawals will be added once all items are)
        ApplyEffects(Defender);

        Attacker.getInventory().getItemInMainHand().setAmount(Attacker.getInventory().getItemInMainHand().getAmount() - 1); // decrement item
    }



}
