package me.oscarcusick.tokensmoke.Listeners;

import me.oscarcusick.tokensmoke.Data.Items.CigarettePack;
import me.oscarcusick.tokensmoke.Data.Items.WeedSeed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.plugin.Plugin;

public class AddItemsToLootTableListener implements Listener {
    Plugin PluginInstance;
    public AddItemsToLootTableListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler
    public void AddCigarettesToLootTable(LootGenerateEvent event) {
        if ((((int) (Math.floor(Math.random() * 3) + 1)) == 1)) { // 1 in 3 that any loot-table based chest will have the smokes in
            CigarettePack CP = new CigarettePack(PluginInstance);
            event.getLoot().add(CP.GetFreshPack(3));
        }
    }

    @EventHandler
    public void AddWeedSeedsToLootTable(LootGenerateEvent event) {
        if (!event.getEntity().getWorld().getWorldFolder().getName().toLowerCase().contains("end")) { // only spawn in the end
            return;
        }
        if ((((int) (Math.floor(Math.random() * 5) + 1)) == 1)) { // 1 in 5 that any loot-table based chest will have the seeds in
            WeedSeed WS = new WeedSeed(PluginInstance);
            PluginInstance.getServer().broadcastMessage("Generated Weed Seeds");
            event.getLoot().add(WS.GetItem(2, (int) (Math.floor(Math.random() * 3) + 1)));
        }
    }

}
