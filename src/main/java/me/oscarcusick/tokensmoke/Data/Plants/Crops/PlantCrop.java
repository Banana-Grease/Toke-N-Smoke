package me.oscarcusick.tokensmoke.Data.Plants.Crops;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

public class PlantCrop {
    Plugin PluginInstance; // needed to do shit with API\

    // stuff initialized in constructor
    public String Name;
    public int Tier;
    public Material Stage1Block;
    public Material Stage2Block;
    public Material Stage3Block;

    public PlantCrop(Plugin PluginInstance, String Name, int Tier, Material Stage1Block, Material Stage2Block, Material Stage3Block) {
        this.PluginInstance = PluginInstance;
        this.Name = Name;
        this.Tier = Tier;
        this.Stage1Block = Stage1Block;
        this.Stage2Block = Stage2Block;
        this.Stage3Block = Stage3Block;
    }

}
