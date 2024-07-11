package me.oscarcusick.tokensmoke.Listeners.PlantListeners;

import me.oscarcusick.tokensmoke.Data.Items.Opium;
import me.oscarcusick.tokensmoke.Data.Items.OpiumSeed;
import me.oscarcusick.tokensmoke.Data.Items.WeedBud;
import me.oscarcusick.tokensmoke.Data.Items.WeedSeed;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class OpiumBreakListener implements Listener {
    Plugin PluginInstance; // needed to do shit with API\
    public OpiumBreakListener(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    // testing metadata
    @EventHandler(priority = EventPriority.HIGH)
    public void TestMetaData(PlayerInteractEvent event) {
        if (!event.getPlayer().isOp()) {
            return;
        }

        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            return;
        }

        if (event.getClickedBlock().hasMetadata("Opium")) {
            event.getPlayer().sendMessage("Opium");
        }
    }


    // remove MetaDataTag When any block gets broken
    @EventHandler(priority = EventPriority.NORMAL)
    public void RemoveMetaData(BlockBreakEvent event) {
        //harvest
        if (event.getBlock().hasMetadata("Opium")) {
            Opium OpiumClass = new Opium(PluginInstance);
            OpiumSeed OS = new OpiumSeed(PluginInstance);
            if (event.getBlock().getType().equals(Material.ALLIUM)) {
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), OpiumClass.GetOpiumItem(((int) (Math.floor(Math.random() * 5) + 2)))); // Opium (2 up to 5)
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), OS.GetItem(((int) (Math.floor(Math.random() * 3) + 1)))); // seed
            }
        }

        // remove the metadata on the block and above / below
        if (event.getBlock().hasMetadata("Opium")) {
            event.getBlock().removeMetadata("Opium", PluginInstance);
        }

    }

    // remove MetaDataTag When any block gets broken, and prevent entire row of plants breaking instantly due to physics check
    @EventHandler(priority = EventPriority.LOW)
    public void RemoveMetaData(BlockPhysicsEvent event) {

        if (!event.getBlock().getType().equals(Material.ALLIUM)) {
            return;
        }

        event.setCancelled(true);

        if (event.getBlock().hasMetadata("Opium")) {
            event.getBlock().removeMetadata("Opium", PluginInstance);
        }
    }

    // Get seeds from breaking grass
    @EventHandler
    public void DropSeeds(BlockBreakEvent event) {
        if (!event.getBlock().getType().equals(Material.SHORT_GRASS)) {
            return;
        }

        // only drop if this chance was good or something (it's 3:08 AM)
        if ((((int) (Math.floor(Math.random() * 80) + 1)) == 1)) {
            OpiumSeed OS = new OpiumSeed(PluginInstance);
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), OS.GetItem());
        }
    }
}
