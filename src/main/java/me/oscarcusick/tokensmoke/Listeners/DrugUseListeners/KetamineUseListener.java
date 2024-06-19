package me.oscarcusick.tokensmoke.Listeners.DrugUseListeners;

import me.oscarcusick.tokensmoke.Data.Drugs.Heroin;
import me.oscarcusick.tokensmoke.Data.Drugs.Ketamine;
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

public class KetamineUseListener implements Listener {
    Plugin PluginInstance;
    public KetamineUseListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public void ApplyEffects(LivingEntity E, boolean IsSyringe) {

        int StartRegenerationDuration = 30, StartConfusionDuration = 20;

        if (IsSyringe) {
            StartRegenerationDuration = 55;
        }

        // apply effects (NOTE: Addiction & withdrawals will be added once all items are)
        int CurrentRegenerationDuration = 0;
        if (E.hasPotionEffect(PotionEffectType.REGENERATION)) {
            CurrentRegenerationDuration = E.getPotionEffect(PotionEffectType.REGENERATION).getDuration();
        }
        int CurrentConfusionDuration = 0;
        if (E.hasPotionEffect(PotionEffectType.CONFUSION)) {
            CurrentConfusionDuration = E.getPotionEffect(PotionEffectType.CONFUSION).getDuration();
        }

        // Regeneration
        E.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (StartRegenerationDuration*20)+CurrentRegenerationDuration, 3, false, false, false));

        // Confusion
        E.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (StartConfusionDuration*20)+CurrentConfusionDuration, 3, false, false, false));
    }

    @EventHandler // use on self
    public void KetamineUseSelfListener(PlayerInteractEvent event) {
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
        if (!event.getItem().getItemMeta().getLore().get(0).contains("Ketamine")) {
            return;
        }

        // apply effects (NOTE: Addiction & withdrawals will be added once all items are)
        if (event.getItem().getItemMeta().getLore().get(0).contains("Syringe")) {
            ApplyEffects(event.getPlayer(), true);
        } else {
            ApplyEffects(event.getPlayer(), false);
        }

        event.getItem().setAmount(event.getItem().getAmount() - 1);
    }

    @EventHandler // use on others
    public void KetamineUseOthersListener(EntityDamageEvent event) {
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
            return;
        }
        if (!event.getDamageSource().getCausingEntity().getType().equals(EntityType.PLAYER)) {
            return;
        }

        Player Attacker = (Player)event.getDamageSource().getCausingEntity();
        LivingEntity Defender = (LivingEntity) event.getEntity();

        Ketamine KetamineClass = new Ketamine(PluginInstance);

        if (!Attacker.getInventory().getItemInMainHand().equals(KetamineClass.GetKetamineItem(true))) {
            return;
        }

        // apply effects (NOTE: Addiction & withdrawals will be added once all items are)
        if (Attacker.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).contains("Syringe")) {
            ApplyEffects(Defender, true);
        } else {
            ApplyEffects(Defender, false);
        }

        Attacker.getInventory().getItemInMainHand().setAmount(Attacker.getInventory().getItemInMainHand().getAmount() - 1); // decrement item
    }



}
