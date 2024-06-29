package me.oscarcusick.tokensmoke.Listeners;

import me.oscarcusick.tokensmoke.Data.Drugs.*;
import me.oscarcusick.tokensmoke.Data.Items.Opium;
import me.oscarcusick.tokensmoke.Data.Items.WeedBud;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DrugSellListener implements Listener {
    Plugin PluginInstance;
    public DrugSellListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void DrugSell(PlayerInteractEntityEvent event) {
        if (!event.getRightClicked().getType().equals(EntityType.VILLAGER)) {
            return;
        }
        Villager ClickedVillager = (Villager)event.getRightClicked();
        if (!ClickedVillager.getProfession().equals(Villager.Profession.NITWIT)) {
            return;
        }

        // all sellable items to nitwits
        Heroin HeroinClass = new Heroin(PluginInstance);
        Fentanyl FentanylClass = new Fentanyl(PluginInstance);
        Cocaine CocaineClass = new Cocaine(PluginInstance);
        CrystalMeth MethClass =new CrystalMeth(PluginInstance);
        WeedBud WeedBudClass = new WeedBud(PluginInstance);
        Opium OpiumClass = new Opium(PluginInstance);
        Joint JointClass= new Joint(PluginInstance);

        float Payment = 0;
        for (ItemStack ItemSlot : event.getPlayer().getInventory()) {

            if (ItemSlot == null) { // skip empty slots
                continue;
            }

            // Heroin & Fentanyl
            if (ItemSlot.isSimilar(HeroinClass.GetHeroinItem(false)) || ItemSlot.isSimilar(FentanylClass.GetFentanylItem(false))) {
                Payment += 11 * ItemSlot.getAmount();
                ItemSlot.setAmount(0);
            }
            // Cocaine & Meth
            if (ItemSlot.isSimilar(CocaineClass.GetCocaineItem()) || ItemSlot.isSimilar(MethClass.GetMethItem())) {
                Payment += 8 * ItemSlot.getAmount();
                ItemSlot.setAmount(0);
            }
            // Opium
            if (ItemSlot.isSimilar(OpiumClass.GetOpiumItem())) {
                Payment += 2 * ItemSlot.getAmount();
                ItemSlot.setAmount(0);
            }

            // Weed Tier 1
            if (ItemSlot.isSimilar(WeedBudClass.GetItem(1))) {
                Payment += 1 * ItemSlot.getAmount();
                ItemSlot.setAmount(0);
            }
            // Weed Tier 2
            if (ItemSlot.isSimilar(WeedBudClass.GetItem(2))) {
                Payment += 3.5f * ItemSlot.getAmount();
                ItemSlot.setAmount(0);
            }

            // Joint
            if (ItemSlot.isSimilar(JointClass.GetJointItem(1))) {
                Payment += 4 * ItemSlot.getAmount();
                ItemSlot.setAmount(0);
            }
            // Quality Joint
            if (ItemSlot.isSimilar(JointClass.GetJointItem(2))) {
                Payment += 12 * ItemSlot.getAmount();
                ItemSlot.setAmount(0);
            }
        }

        // 25% variation each direction. affects final price only not for each item
        float PriceVariation = (int) (Math.floor(Math.random() * 51)) - 25;
        PriceVariation = Payment * (PriceVariation / 100);

        ClickedVillager.getWorld().dropItem(ClickedVillager.getLocation(), new ItemStack(Material.EMERALD, (int)(Payment + PriceVariation)));
    }

}
