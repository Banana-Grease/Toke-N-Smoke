package me.oscarcusick.tokensmoke.Listeners;

import me.oscarcusick.tokensmoke.Data.Items.CigarettePack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;

public class AddCigarettesToLootTableListener implements Listener {
    Plugin PluginInstance;
    public AddCigarettesToLootTableListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void AddCigarettesToLootTable(LootGenerateEvent event) {
        if ((((int) (Math.floor(Math.random() * 3) + 1)) == 1)) { // 1 in 3 that any loot-table based chest will have the smokes in
            CigarettePack CP = new CigarettePack(PluginInstance);
            event.getLoot().add(CP.GetFreshPack(3));
        }
    }

}
