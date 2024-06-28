package me.oscarcusick.tokensmoke.Listeners.PlantListeners;

import me.oscarcusick.tokensmoke.Data.Items.WeedSeed;
import me.oscarcusick.tokensmoke.Data.Plants.Crops.PlantCrop;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.MetadataValueAdapter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class WeedPlantListener implements Listener {
    Plugin PluginInstance; // needed to do shit with API\

    PlantCrop WeedPlantTier1, WeedPlantTier2;
    int PlantingTier;
    Block TargetBlock;

    public WeedPlantListener(Plugin PluginInstance, PlantCrop WeedPlantTier1, PlantCrop WeedPlantTier2) {
        this.PluginInstance = PluginInstance;
        this.WeedPlantTier1 = WeedPlantTier1;
        this.WeedPlantTier2 = WeedPlantTier2;
    }

    // this listener prevent pumpkin stems from growing if they have either MetaData tag attached
    // it also grows the plant as needed
    @EventHandler
    public void WeedGrowth(BlockGrowEvent event) {
        if (event.getBlock().hasMetadata("WeedTier1") || event.getBlock().hasMetadata("WeedTier2")) {
            event.setCancelled(true);
            Ageable BlockData = (Ageable) event.getBlock().getBlockData();

            if (BlockData.getAge() < BlockData.getMaximumAge()-1) { // AGE IT TO SECOND STAGE
                BlockData.setAge(BlockData.getMaximumAge()-1);
                event.getBlock().setBlockData(BlockData);
            }

            else if (BlockData.getAge() >= BlockData.getMaximumAge()-1) { // AGE IT TO THIRD AND LAST STAGE. NEED TO FIX UPPER BLOCK NOT LOOKING GOOD
                Block BlockAbove = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY()+1, event.getBlock().getLocation().getBlockZ());
                if (event.getBlock().hasMetadata("WeedTier1")) {
                    event.getBlock().setType(Material.LARGE_FERN, false);
                    BlockAbove.setType(Material.LARGE_FERN, false);
                    BlockAbove.setMetadata("WeedTier1", new FixedMetadataValue(PluginInstance, "Value1"));
                } else if (event.getBlock().hasMetadata("WeedTier2")) {
                    event.getBlock().setType(Material.LILAC, false);
                    BlockAbove.setType(Material.LILAC, false);
                    BlockAbove.setMetadata("WeedTier2", new FixedMetadataValue(PluginInstance, "Value2"));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW) // run this event last
    public void WeedPlantListener(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        WeedSeed WS = new WeedSeed(PluginInstance);
        if (!(event.getPlayer().getInventory().getItemInMainHand().equals(WS.GetItem(1)) || event.getPlayer().getInventory().getItemInMainHand().equals(WS.GetItem(2)))) { // make sure it's a weed seed
            return;
        }
        if (!event.getClickedBlock().getType().equals(Material.FARMLAND)) { // make sure its farmland
            return;
        }
        PlantingTier = WS.DetermineTier(event.getPlayer().getInventory().getItemInMainHand()); // which tier is the player trying to plant
        TargetBlock = event.getPlayer().getWorld().getBlockAt(event.getClickedBlock().getLocation().getBlockX(), event.getClickedBlock().getLocation().getBlockY()+1, event.getClickedBlock().getLocation().getBlockZ());

        // check to see if any directly touching plants in any singular axis are next to it (diagonals are fine), if so stop planting

        // Pos X
        if (event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()+1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("WeedTier1") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()+1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("WeedTier2") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()+1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("Opium")) {
            event.setCancelled(true);
            return;
        }
        // Neg X
        else if (event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()-1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("WeedTier1") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()-1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("WeedTier2") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()-1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("Opium")) {
            event.setCancelled(true);
            return;
        }
        // Pos Z
        else if (event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()+1).hasMetadata("WeedTier1") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()+1).hasMetadata("WeedTier2") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()+1).hasMetadata("Opium")) {
            event.setCancelled(true);
            return;
        }
        // Neg Z
        else if (event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()-1).hasMetadata("WeedTier1") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()-1).hasMetadata("WeedTier2") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()-1).hasMetadata("Opium")) {
            event.setCancelled(true);
            return;
        } // now it is confirmed that it is a valid spot to plant

        switch (PlantingTier) {
            case 1:
                TargetBlock.setType(WeedPlantTier1.Stage1Block, false);
                // increment age by 3
                Ageable Weed1Data = (Ageable) TargetBlock.getBlockData();
                Weed1Data.setAge(Weed1Data.getAge()+2);
                TargetBlock.setBlockData(Weed1Data);
                TargetBlock.setMetadata("WeedTier1", new FixedMetadataValue(PluginInstance, "Value1"));
                break;
            case 2:
                TargetBlock.setType(WeedPlantTier2.Stage1Block, false);
                // increment age by 3
                Ageable Weed2Data = (Ageable) TargetBlock.getBlockData();
                Weed2Data.setAge(Weed2Data.getAge()+2);
                TargetBlock.setBlockData(Weed2Data);
                TargetBlock.setMetadata("WeedTier2", new FixedMetadataValue(PluginInstance, "Value2"));
                break;
            default:
                return;
        }
    }
}
