package me.oscarcusick.tokensmoke.Listeners;

import me.oscarcusick.tokensmoke.Data.Items.CigarettePack;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class VillagerAddCigarettesListener implements Listener {
    Plugin PluginInstance;
    public VillagerAddCigarettesListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    /*
    ok so I don't really know how villager trades work but what I have done is;
    when the villager upgrades its level once, it will set the first trade to be cigarettes.
    this does not cause errors from what ive seen, so it's fine for now
     */

    @EventHandler
    public void AddCigarettesToClericTrades(VillagerAcquireTradeEvent event) {
        if (!event.getEntity().getType().equals(EntityType.VILLAGER)) { // make sure is villager
            return;
        }

        Villager SpawnedVillager = (Villager) event.getEntity();
        if (!SpawnedVillager.getProfession().equals(Villager.Profession.CLERIC)) { // make sure villager is cleric
            return;
        }

        if (event.getEntity().getRecipes().size() < 2) {
            return;
        }

        CigarettePack CP = new CigarettePack(PluginInstance);

        MerchantRecipe NewCigRecipe = new MerchantRecipe(CP.GetFreshPack(2), 1, 20, false, 5, 1, 0, 3);
        NewCigRecipe.addIngredient(new ItemStack(Material.EMERALD, 6));

        event.getEntity().setRecipe(0, NewCigRecipe);

    }

}
