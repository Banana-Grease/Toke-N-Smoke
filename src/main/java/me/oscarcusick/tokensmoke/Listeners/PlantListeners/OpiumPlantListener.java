package me.oscarcusick.tokensmoke.Listeners.PlantListeners;

import me.oscarcusick.tokensmoke.Data.Items.OpiumSeed;
import me.oscarcusick.tokensmoke.Data.Items.WeedSeed;
import me.oscarcusick.tokensmoke.Data.Plants.Crops.PlantCrop;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class OpiumPlantListener implements Listener {
    Plugin PluginInstance; // needed to do shit with API\

    PlantCrop OpiumPlant;
    Block TargetBlock;

    public OpiumPlantListener(Plugin PluginInstance, PlantCrop OpiumPlant) {
        this.PluginInstance = PluginInstance;
        this.OpiumPlant = OpiumPlant;
    }

    // this listener prevent pumpkin stems from growing if they have either MetaData tag attached
    // it also grows the plant as needed
    @EventHandler
    public void OpiumGrowth(BlockGrowEvent event) {
        Block GrowBlock = event.getBlock();
        if (!(GrowBlock.getType().equals(OpiumPlant.Stage1Block) || GrowBlock.getType().equals(OpiumPlant.Stage2Block))) {
            return;
        }
        Ageable BlockData = (Ageable) GrowBlock.getBlockData();

        // make sure its growing on podzol
        if (!GrowBlock.getWorld().getBlockAt(GrowBlock.getX(), GrowBlock.getY()-1, GrowBlock.getZ()).getType().equals(Material.PODZOL)) {
            return;
        }
        // make sure it's Opium
        if (BlockData.getAge() == OpiumPlant.Stage1BlockAge || BlockData.getAge() == OpiumPlant.Stage2BlockAge) {
            event.setCancelled(true);

            { // age to third and last stage
                if (BlockData.getAge() == OpiumPlant.Stage2BlockAge) {
                    GrowBlock.setType(Material.ALLIUM, false);
                    GrowBlock.setMetadata("Opium", new FixedMetadataValue(PluginInstance, "Value"));
                }
            }

            { // age to second stage
                if (BlockData.getAge() == OpiumPlant.Stage1BlockAge) {
                    BlockData.setAge(OpiumPlant.Stage2BlockAge);
                    GrowBlock.setBlockData(BlockData);
                }
            }
        }

    }

    @EventHandler(priority = EventPriority.LOW) // run this event last
    public void OpiumPlantListener(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (event.getItem() == null) {
            return;
        }
        OpiumSeed OS = new OpiumSeed(PluginInstance);
        if (event.getItem().isSimilar(OS.GetItem())) {
            event.setCancelled(true);
        }
        if (!event.getPlayer().getInventory().getItemInMainHand().isSimilar(OS.GetItem())) { // make sure it's an opium seed
            return;
        }
        if (!event.getClickedBlock().getType().equals(Material.PODZOL)) { // make sure it's Podzol
            return;
        }
        TargetBlock = event.getPlayer().getWorld().getBlockAt(event.getClickedBlock().getLocation().getBlockX(), event.getClickedBlock().getLocation().getBlockY()+1, event.getClickedBlock().getLocation().getBlockZ());

        // check to see if any directly touching plants in any singular axis are next to it (diagonals are fine), if so stop planting

        // Pos X
        if (event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()+1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("Opium") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()+1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("WeedTier1") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()+1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("WeedTier2")) {
            event.setCancelled(true);
            return;
        }
        // Neg X
        else if (event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()-1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("Opium") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()-1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("WeedTier1") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX()-1, TargetBlock.getY(), TargetBlock.getZ()).hasMetadata("WeedTier2")) {
            event.setCancelled(true);
            return;
        }
        // Pos Z
        else if (event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()+1).hasMetadata("Opium") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()+1).hasMetadata("WeedTier1") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()+1).hasMetadata("WeedTier2")) {
            event.setCancelled(true);
            return;
        }
        // Neg Z
        else if (event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()-1).hasMetadata("Opium") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()-1).hasMetadata("WeedTier1") || event.getPlayer().getWorld().getBlockAt(TargetBlock.getX(), TargetBlock.getY(), TargetBlock.getZ()-1).hasMetadata("WeedTier2")) {
            event.setCancelled(true);
            return;
        } // now it is confirmed that it is a valid spot to plant

        TargetBlock.setType(OpiumPlant.Stage1Block, false);
        // increment age
        Ageable OpiumData = (Ageable) TargetBlock.getBlockData();
        OpiumData.setAge(OpiumPlant.Stage1BlockAge);
        TargetBlock.setBlockData(OpiumData);
        TargetBlock.setMetadata("Opium", new FixedMetadataValue(PluginInstance, "Value"));

        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1); // remove 1 seed
    }
}
