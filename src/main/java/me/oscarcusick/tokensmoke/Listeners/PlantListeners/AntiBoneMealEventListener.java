package me.oscarcusick.tokensmoke.Listeners.PlantListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.plugin.Plugin;

public class AntiBoneMealEventListener implements Listener {
    Plugin PluginInstance;
    public AntiBoneMealEventListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @EventHandler // doesn't work but whatever
    public void StopBoneMealOnWeedAndOpium(BlockFertilizeEvent event) {
        if (event.getBlock().hasMetadata("WeedTier1") || event.getBlock().hasMetadata("WeedTier2") || event.getBlock().hasMetadata("Opium")) {
            event.setCancelled(true);
            return;
        }
    }
}
