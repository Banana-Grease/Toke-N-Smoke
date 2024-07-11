package me.oscarcusick.tokensmoke.Listeners.PlantListeners;

import me.oscarcusick.tokensmoke.Data.Items.WeedSeed;
import me.oscarcusick.tokensmoke.Data.Plants.Crops.PlantCrop;
import org.bukkit.GameMode;
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
        Block GrowBlock = event.getBlock();
        if (!( (GrowBlock.getType().equals(WeedPlantTier1.Stage1Block) || GrowBlock.getType().equals(WeedPlantTier1.Stage2Block)) || (GrowBlock.getType().equals(WeedPlantTier2.Stage1Block) || GrowBlock.getType().equals(WeedPlantTier2.Stage2Block)) )) {
            return;
        }
        Ageable BlockData = (Ageable) GrowBlock.getBlockData();

        // make sure its growing on podzol
        if (!GrowBlock.getWorld().getBlockAt(GrowBlock.getX(), GrowBlock.getY()-1, GrowBlock.getZ()).getType().equals(Material.PODZOL)) {
            return;
        }
        // make sure its either type of weed
        if ((BlockData.getAge() == WeedPlantTier1.Stage1BlockAge || BlockData.getAge() == WeedPlantTier1.Stage2BlockAge) || (BlockData.getAge() == WeedPlantTier2.Stage1BlockAge || BlockData.getAge() == WeedPlantTier2.Stage2BlockAge)) {
            event.setCancelled(true);

            // check for this first to stop from growing all the way in one event
            { // age to thirst and last stage ( in curley brackets to keep neat )
                Block BlockAboveGrowBlock = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY()+1, event.getBlock().getLocation().getBlockZ());
                if (BlockData.getAge() == WeedPlantTier1.Stage2BlockAge) { // set new block materials
                    GrowBlock.setType(Material.LARGE_FERN, false);
                    BlockAboveGrowBlock.setType(Material.LARGE_FERN, false);
                    GrowBlock.setMetadata("WeedTier1", new FixedMetadataValue(PluginInstance, "Value1"));
                    BlockAboveGrowBlock.setMetadata("WeedTier1", new FixedMetadataValue(PluginInstance, "Value1"));
                }
                else if (BlockData.getAge() == WeedPlantTier2.Stage2BlockAge) {
                    GrowBlock.setType(Material.LILAC, false);
                    BlockAboveGrowBlock.setType(Material.LILAC, false);
                    GrowBlock.setMetadata("WeedTier2", new FixedMetadataValue(PluginInstance, "Value2"));
                    BlockAboveGrowBlock.setMetadata("WeedTier2", new FixedMetadataValue(PluginInstance, "Value2"));
                }
            }

            {   // age to second stage ( in curley brackets to keep neat )
                if (BlockData.getAge() == WeedPlantTier1.Stage1BlockAge) {
                    BlockData.setAge(WeedPlantTier1.Stage2BlockAge);
                    GrowBlock.setBlockData(BlockData);
                }
                else if (BlockData.getAge() == WeedPlantTier2.Stage1BlockAge) {
                    BlockData.setAge(WeedPlantTier2.Stage2BlockAge);
                    GrowBlock.setBlockData(BlockData);
                }
            }

        }
    }

    @EventHandler(priority = EventPriority.LOW) // run this event last
    public void WeedPlantListener(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (event.getItem() == null) {
            return;
        }
        WeedSeed WS = new WeedSeed(PluginInstance);
        if (event.getItem().isSimilar(WS.GetItem(1)) || event.getItem().isSimilar(WS.GetItem(2))) {
            event.setCancelled(true);
        }
        if (!(event.getPlayer().getInventory().getItemInMainHand().isSimilar(WS.GetItem(1)) || event.getPlayer().getInventory().getItemInMainHand().isSimilar((WS.GetItem(2))))) { // make sure it's a weed seed
            return;
        }
        if (!event.getClickedBlock().getType().equals(Material.PODZOL)) { // make sure it's Podzol
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
                Weed1Data.setAge(WeedPlantTier1.Stage1BlockAge); // set age of stem to pre-determined age in PlantCrop class
                TargetBlock.setBlockData(Weed1Data);
                TargetBlock.setMetadata("WeedTier1", new FixedMetadataValue(PluginInstance, "Value1"));
                break;
            case 2:
                TargetBlock.setType(WeedPlantTier2.Stage1Block, false);
                // increment age by 3
                Ageable Weed2Data = (Ageable) TargetBlock.getBlockData();
                Weed2Data.setAge(WeedPlantTier2.Stage1BlockAge); // set age of stem to pre-determined age in PlantCrop class
                TargetBlock.setBlockData(Weed2Data);
                TargetBlock.setMetadata("WeedTier2", new FixedMetadataValue(PluginInstance, "Value2"));
                break;
            default:
                return;
        }
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1); // remove 1 seed if not creative
    }
}
