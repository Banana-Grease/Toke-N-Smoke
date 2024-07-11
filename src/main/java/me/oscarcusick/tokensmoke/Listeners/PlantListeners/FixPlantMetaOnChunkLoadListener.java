package me.oscarcusick.tokensmoke.Listeners.PlantListeners;

import me.oscarcusick.tokensmoke.Data.Plants.Crops.PlantCrop;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.security.AllPermission;
import java.util.ArrayList;

// due to MetaData on blocks being reset after a server restart, and other possible bugs i haven't solved as of 2:08pm (AEST) 30.06.24
// this listener will look for each Podzol block in a chunk. if there is any custom plant block on-top, the listener will change the MetaData to the custom plant it should be
//     Opium will have melon stalks
//     weed tier 1 & 2 will be pumpkin stalks. The way they will be differentiated will be the stage of growth AKA size.

public class FixPlantMetaOnChunkLoadListener implements Listener {
    Plugin PluginInstance;
    ArrayList<PlantCrop> AllCustomPlants; // use this to check if a block is a custom plant or not
    public FixPlantMetaOnChunkLoadListener(Plugin PluginInstance, ArrayList<PlantCrop> AllCustomPlants) {
        this.PluginInstance = PluginInstance;
        this.AllCustomPlants = AllCustomPlants;
    }

    @EventHandler
    public void FixPlantMetaOnChunkLoad(ChunkLoadEvent event) {
        Chunk LoadedChunk = event.getChunk();

        // we know what order because we parse it in Toke_N_Smoke.java
        PlantCrop WeedTier1 = AllCustomPlants.get(0);
        PlantCrop WeedTier2 = AllCustomPlants.get(1);
        PlantCrop Opium = AllCustomPlants.get(2);

        // loop, we loop vertically (Y Axis) through the chunk for each X & Z Block.
        // if it's a podzol block, we check if it's a custom plant above.
        // if it is, we add the MetaData if it's not there
        for (int X = 0; X < 16; X++) {
            for (int Z = 0; Z < 16; Z++) {
                for (int Y = 0; Y < LoadedChunk.getWorld().getMaxHeight(); Y++) {
                    if (LoadedChunk.getBlock(X, Y, Z).getType().equals(Material.PODZOL)) {
                        // all the different custom plants have different ages if they share the same block for that growth stage
                        // What we need to do is; check if the block above's material type matches
                        //     And if it does; check to see which age stage it matches
                        Block BlockAbove = LoadedChunk.getBlock(X, Y+1, Z);
                        Block BlockAboveAbove = LoadedChunk.getBlock(X, Y+2, Z);

                        // check to see if the block above matches one of the grow materials of one of the registered plants
                        boolean MatchesAtleastOne = false;
                        for (PlantCrop Plant : AllCustomPlants) {
                            if (BlockAbove.getType().equals(Plant.Stage1Block) || BlockAbove.getType().equals(Plant.Stage2Block)) {
                                MatchesAtleastOne = true;
                            }
                        }

                        // if it didn't match at least one of the block materials, continue here
                        // also check for the full plants, due to cant cast full plants to Ageables (class cast exception)
                        if (!MatchesAtleastOne) {
                            // Weed Tier 1
                            if (BlockAbove.getType().equals(WeedTier1.Stage3Block) && BlockAboveAbove.getType().equals(WeedTier1.Stage3Block)) {
                                BlockAbove.setMetadata("WeedTier1", new FixedMetadataValue(PluginInstance, "Value1"));
                                BlockAboveAbove.setMetadata("WeedTier1", new FixedMetadataValue(PluginInstance, "Value1"));
                                continue;
                            }
                            // Weed Tier 2
                            if (BlockAbove.getType().equals(WeedTier2.Stage3Block) && BlockAboveAbove.getType().equals(WeedTier2.Stage3Block)) {
                                BlockAbove.setMetadata("WeedTier2", new FixedMetadataValue(PluginInstance, "Value2"));
                                BlockAboveAbove.setMetadata("WeedTier2", new FixedMetadataValue(PluginInstance, "Value2"));
                                continue;
                            }
                            // Opium
                            if (BlockAbove.getType().equals(Opium.Stage3Block)) {
                                BlockAbove.setMetadata("Opium", new FixedMetadataValue(PluginInstance, "Value"));
                            }
                            continue;
                        }

                        Ageable BlockData = (Ageable) BlockAbove.getBlockData();

                        // for weed, we need to do the sapling separate due to the final plant being 2 blocks tall
                        // Weed Tier 1 sapling
                        if (BlockData.getAge() == WeedTier1.Stage1BlockAge || BlockData.getAge() == WeedTier1.Stage2BlockAge) {
                            BlockAbove.setMetadata("WeedTier1", new FixedMetadataValue(PluginInstance, "Value1"));
                            continue;
                        }

                        // Weed Tier 2 sapling
                        else if (BlockData.getAge() == WeedTier2.Stage1BlockAge || BlockData.getAge() == WeedTier2.Stage2BlockAge) {
                            BlockAbove.setMetadata("WeedTier2", new FixedMetadataValue(PluginInstance, "Value2"));
                            continue;
                        }

                        // Opium sapling
                        else if (BlockData.getAge() == Opium.Stage1BlockAge || BlockData.getAge() == Opium.Stage2BlockAge) {
                            BlockAbove.setMetadata("Opium", new FixedMetadataValue(PluginInstance, "Opium"));
                            continue;
                        }
                    }
                }
            }
        }
    }
}
