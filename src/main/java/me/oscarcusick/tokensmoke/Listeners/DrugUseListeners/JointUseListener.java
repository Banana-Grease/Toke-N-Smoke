package me.oscarcusick.tokensmoke.Listeners.DrugUseListeners;

import me.oscarcusick.tokensmoke.Data.Drugs.Cigarette;
import me.oscarcusick.tokensmoke.Data.Drugs.Joint;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class JointUseListener implements Listener {
    Plugin PluginInstance;
    public JointUseListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler // copied and pasted from cigarette. then adapted. sorry if any naming mistakes are still present
    public void JointUseListener(PlayerInteractEvent event) {
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
        if (event.getItem().getItemMeta().getLore().get(0).contains("Butt")) { // check for butt and cancel placing it
            event.setCancelled(true);
            return;
        }
        if (!event.getItem().getItemMeta().getLore().get(0).contains("Joint")) { // make sure the item lore contains the word Joint
            return;
        }
        if (!event.getItem().getItemMeta().getLore().get(1).contains("Puffs")) { // make sure the item lore does contain the word 'Puffs'
            return;
        }

        Joint JointClass = new Joint(PluginInstance);
        int PuffsRemaining = JointClass.DetermineRemainingPuffs(event.getItem());

        ItemMeta Meta = event.getItem().getItemMeta();
        ArrayList<String> Lore = (ArrayList<String>) Meta.getLore();

        // smoke from players' mouth
        if (JointClass.DetermineTier(event.getItem()) == 1) {
            event.getPlayer().getWorld().spawnParticle(Particle.WHITE_SMOKE, event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getY() + 1.5, event.getPlayer().getLocation().getZ(), 15, 0, 0, 0, .03, null, false);
        } else {
            event.getPlayer().getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, event.getPlayer().getLocation().getX(), event.getPlayer().getLocation().getY() + 1.5, event.getPlayer().getLocation().getZ(), 15, 0, 0, 0, .03, null, false);
        }

        // reset item lore
        Lore.set(1, "Puffs Remaining: " + JointClass.GetRemainingColour(event.getItem()) + (PuffsRemaining - 1)); // rename item meta to depict how many cigarettes are left
        Meta.setLore(Lore);
        event.getItem().setItemMeta(Meta);

        // apply affects of smoking (NOTE: Addiction & withdrawals will be added once all items are)
        int CurrentLuckDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.LUCK)) {
            CurrentLuckDuration = event.getPlayer().getPotionEffect(PotionEffectType.LUCK).getDuration();
        }
        int CurrentNightVisionDuration = 0;
        if (event.getPlayer().hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            CurrentNightVisionDuration = event.getPlayer().getPotionEffect(PotionEffectType.NIGHT_VISION).getDuration();
        }
        // effects here
        switch(JointClass.DetermineTier(event.getItem())) {
            case 1:
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LUCK, (60*20*event.getItem().getAmount())+CurrentLuckDuration, 2, false, false, false));
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, (60*20*event.getItem().getAmount())+CurrentNightVisionDuration, 0, false, false, false));
                break;
            case 2:
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LUCK, (60*20*event.getItem().getAmount())+CurrentLuckDuration, 4, false, false, false));
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, (90*20*event.getItem().getAmount())+CurrentNightVisionDuration, 0, false, false, false));
                if (event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() < 28) { // don't infinitely stack health points lol
                    event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);
                }
                break;
        }

        if (PuffsRemaining <= 1) { // if the joint has one puff left turn it's item type into a wooden button and change lore / name
            // change item lore

            Meta.setDisplayName(ChatColor.DARK_GRAY + "Joint Butt");

            Lore.set(0, "A Joint Butt");
            Lore.remove(1);

            Meta.setLore(Lore);
            event.getItem().setItemMeta(Meta);

            event.getItem().setType(Material.OAK_BUTTON);

            event.setCancelled(true); // prevent player from placing the button on the ground

            return;
        }

        event.setCancelled(true); // prevent player from placing the torches

    }

    @EventHandler // this listener literally just sets players hearts to 20 when any night_vision potion effect stops. simple but functional
    public void RemoveHearts(EntityPotionEffectEvent event) {
        if (!event.getModifiedType().equals(PotionEffectType.NIGHT_VISION)) {
            return;
        }
        if (!(event.getAction().equals(EntityPotionEffectEvent.Action.REMOVED) || event.getAction().equals(EntityPotionEffectEvent.Action.CLEARED))) {
            return;
        }
        if (event.getEntity() instanceof Player) {
            Player P = (Player)event.getEntity();
            P.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
        }
    }
}
