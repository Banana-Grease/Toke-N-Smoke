package me.oscarcusick.tokensmoke.Data.Plants.Crops;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class PlantCrop {
    Plugin PluginInstance; // needed to do shit with API

    // stuff initialized in constructor
    public String Name;
    public int Tier;
    public Material Stage1Block;
    public int Stage1BlockAge; // If age-able block Material, what age should it be at stage 1? (0 if not applicable)
    public Material Stage2Block;
    public int Stage2BlockAge; // If age-able block Material, what age should it be at stage 2? (0 if not applicable)
    public Material Stage3Block;
    public int Stage3BlockAge; // If age-able block Material, what age should it be at stage 3? (0 if not applicable)

    public PlantCrop(Plugin PluginInstance, String Name, int Tier, Material Stage1Block, int Stage1BlockAge, Material Stage2Block, int Stage2BlockAge, Material Stage3Block, int Stage3BlockAge) {
        this.PluginInstance = PluginInstance;
        this.Name = Name;
        this.Tier = Tier;

        this.Stage1Block = Stage1Block;
        this.Stage1BlockAge = Stage1BlockAge;

        this.Stage2Block = Stage2Block;
        this.Stage2BlockAge = Stage2BlockAge;

        this.Stage3Block = Stage3Block;
        this.Stage3BlockAge = Stage3BlockAge;
    }

}
