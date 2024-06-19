package me.oscarcusick.tokensmoke.Listeners;

import me.oscarcusick.tokensmoke.Data.Drugs.Ketamine;
import me.oscarcusick.tokensmoke.Data.Drugs.Morphine;
import me.oscarcusick.tokensmoke.Data.Items.CigarettePack;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.Plugin;

public class VillagerAddDrugsListener implements Listener {
    Plugin PluginInstance;
    public VillagerAddDrugsListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    /*
    ok so I don't really know how villager trades work but what I have done is;
    when the villager upgrades its level once, it will set the first trade to be cigarettes.
    this does not cause errors from what ive seen, so it's fine for now
     */

    @EventHandler
    public void AddDrugsToClericTrades(VillagerAcquireTradeEvent event) {
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

        if ((((int) (Math.floor(Math.random() * 2) + 1)) == 1)) { // 1 in 2 that villager will become a 'plug' and sell cigarettes

            MerchantRecipe NewCigRecipe = new MerchantRecipe(CP.GetFreshPack(2), 1, 20, false, 5, 1, 1, 3);
            NewCigRecipe.addIngredient(new ItemStack(Material.EMERALD, 6));

            event.getEntity().setRecipe(0, NewCigRecipe);
        }

        if (SpawnedVillager.getRecipes().get(0).getResult().equals(CP.GetFreshPack(2))) { // if the first trade is cigarettes add the rest

            Morphine MorphineClass = new Morphine(PluginInstance);
            MerchantRecipe NewMorphineRecipe = new MerchantRecipe(MorphineClass.GetMorphineItem(false), 1, 20, false, 5, 1, 0, 5);
            NewMorphineRecipe.addIngredient(new ItemStack(Material.EMERALD, 15));
            event.getEntity().setRecipe(1, NewMorphineRecipe);

            Ketamine KetamineClass = new Ketamine(PluginInstance);
            MerchantRecipe NewKetamineRecipe = new MerchantRecipe(KetamineClass.GetKetamineItem(false), 1, 20, false, 5, 1, 0, 5);
            NewKetamineRecipe.addIngredient(new ItemStack(Material.EMERALD, 15));
            event.getEntity().setRecipe(2, NewKetamineRecipe);

        }

    }

}
