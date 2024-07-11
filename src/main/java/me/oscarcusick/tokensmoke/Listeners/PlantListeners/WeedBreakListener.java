package me.oscarcusick.tokensmoke.Listeners.PlantListeners;

import me.oscarcusick.tokensmoke.Data.Items.WeedBud;
import me.oscarcusick.tokensmoke.Data.Items.WeedSeed;
import me.oscarcusick.tokensmoke.Data.Items.WeedStem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class WeedBreakListener implements Listener {
    Plugin PluginInstance; // needed to do shit with API\
    public WeedBreakListener(Plugin PluginInstance) {
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

        if (event.getClickedBlock().hasMetadata("WeedTier1")) {
            event.getPlayer().sendMessage("Tier 1 Weed");
        }

        if (event.getClickedBlock().hasMetadata("WeedTier2")) {
            event.getPlayer().sendMessage("Tier 2 Weed");
        }
    }


    // remove MetaDataTag When any block gets broken
    @EventHandler(priority = EventPriority.NORMAL)
    public void RemoveMetaData(BlockBreakEvent event) {
        Block BlockAbove = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY()+1, event.getBlock().getLocation().getBlockZ());
        Block BlockBelow = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY()-1, event.getBlock().getLocation().getBlockZ());

        //harvest
        if (event.getBlock().hasMetadata("WeedTier1") || event.getBlock().hasMetadata("WeedTier2")) {
            WeedBud WB = new WeedBud(PluginInstance);
            WeedSeed WS = new WeedSeed(PluginInstance);
            WeedStem WStem = new WeedStem(PluginInstance);
            if (event.getBlock().getType().equals(Material.LARGE_FERN)) { // THIS CHECK WILL NOT WORK
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), WB.GetItem(1, ((int) (Math.floor(Math.random() * 12) + 3)))); // weed bud
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), WS.GetItem(1, ((int) (Math.floor(Math.random() * 5) + 1)))); // seed
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), WStem.GetItem(((int) (Math.floor(Math.random() * 6) + 1)))); // stem
            }
            else if (event.getBlock().getType().equals(Material.LILAC)) {
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), WB.GetItem(2, ((int) (Math.floor(Math.random() * 12) + 3)))); // weed bud
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), WS.GetItem(2, ((int) (Math.floor(Math.random() * 5) + 1)))); // seed
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), WStem.GetItem(((int) (Math.floor(Math.random() * 8) + 1)))); // stem
            }
        }

        // remove the metadata on the block and above / below
        if (event.getBlock().hasMetadata("WeedTier1")) {
            event.getBlock().removeMetadata("WeedTier1", PluginInstance);
        }
        if (event.getBlock().hasMetadata("WeedTier2")) {
            event.getBlock().removeMetadata("WeedTier2", PluginInstance);
        }

        if (BlockAbove.hasMetadata("WeedTier1")) {
            BlockAbove.removeMetadata("WeedTier1", PluginInstance);
        }
        if (BlockAbove.hasMetadata("WeedTier2")) {
            BlockAbove.removeMetadata("WeedTier2", PluginInstance);
        }

        if (BlockBelow.hasMetadata("WeedTier1")) {
            BlockBelow.removeMetadata("WeedTier1", PluginInstance);
        }
        if (BlockBelow.hasMetadata("WeedTier2")) {
            BlockBelow.removeMetadata("WeedTier2", PluginInstance);
        }

        // from this point on, if the block is not a weed block return
        if (!(event.getBlock().getType().equals(Material.LARGE_FERN) || event.getBlock().getType().equals(Material.LILAC))) {
            return;
        } event.setCancelled(true);

        // remove other plant blocks
        event.getBlock().setType(Material.AIR, false);
        if (BlockAbove.getType().equals(Material.LARGE_FERN) || BlockAbove.getType().equals(Material.LILAC)) {
            BlockAbove.setType(Material.AIR, false);
        }
        if (BlockBelow.getType().equals(Material.LARGE_FERN) || BlockBelow.getType().equals(Material.LILAC)) {
            BlockBelow.setType(Material.AIR, false);
        }

    }

    // remove MetaDataTag When any block gets broken, and prevent entire row of plants breaking instantly due to physics check
    @EventHandler(priority = EventPriority.LOW)
    public void RemoveMetaData(BlockPhysicsEvent event) {

        if (!(event.getBlock().getType().equals(Material.LARGE_FERN) || event.getBlock().getType().equals(Material.LILAC))) {
            return;
        }

        event.setCancelled(true);

        Block BlockAbove = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY()+1, event.getBlock().getLocation().getBlockZ());
        Block BlockBelow = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY()-1, event.getBlock().getLocation().getBlockZ());

        if (event.getBlock().hasMetadata("WeedTier1")) {
            event.getBlock().removeMetadata("WeedTier1", PluginInstance);
        }
        if (event.getBlock().hasMetadata("WeedTier2")) {
            event.getBlock().removeMetadata("WeedTier2", PluginInstance);
        }

        if (BlockAbove.hasMetadata("WeedTier1")) {
            BlockAbove.removeMetadata("WeedTier1", PluginInstance);
        }
        if (BlockAbove.hasMetadata("WeedTier2")) {
            BlockAbove.removeMetadata("WeedTier2", PluginInstance);
        }

        if (BlockBelow.hasMetadata("WeedTier1")) {
            BlockBelow.removeMetadata("WeedTier1", PluginInstance);
        }
        if (BlockBelow.hasMetadata("WeedTier2")) {
            BlockBelow.removeMetadata("WeedTier2", PluginInstance);
        }
    }

    // Get seeds from breaking grass
    @EventHandler
    public void DropSeeds(BlockBreakEvent event) {
        if (!event.getBlock().getType().equals(Material.TALL_GRASS)) {
            return;
        }

        // only drop if this chance was good or something (it's 3:08 AM)
        if ((((int) (Math.floor(Math.random() * 15) + 1)) == 1)) {
            WeedSeed WS = new WeedSeed(PluginInstance);
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), WS.GetItem(1));
        }
    }
}
