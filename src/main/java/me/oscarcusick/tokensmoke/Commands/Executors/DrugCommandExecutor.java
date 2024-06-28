package me.oscarcusick.tokensmoke.Commands.Executors;

import me.oscarcusick.tokensmoke.Data.Drugs.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class DrugCommandExecutor implements CommandExecutor {
    Plugin PluginInstance;
    public DrugCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    public void GiveItem(Player Player, ItemStack Item) {
        Player.getWorld().dropItem(Player.getLocation(), Item);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length != 1) {
            return false;
        }

        switch (strings[0].toLowerCase()) {
            case "joint":
                Joint JointClass = new Joint(PluginInstance);
                GiveItem((Player) commandSender, JointClass.GetJointItem(1));
                break;
            case "quality_joint":
                Joint QualityJointClass = new Joint(PluginInstance);
                GiveItem((Player) commandSender, QualityJointClass.GetJointItem(2));
                break;
            case "meth":
                CrystalMeth Meth = new CrystalMeth(PluginInstance);
                GiveItem((Player) commandSender, Meth.GetMethItem());
                break;
            case "cocaine":
                Cocaine Coke = new Cocaine(PluginInstance);
                GiveItem((Player) commandSender, Coke.GetCocaineItem());
                break;
            case "morphine":
                Morphine Morphine = new Morphine(PluginInstance);
                GiveItem((Player) commandSender, Morphine.GetMorphineItem(false));
                break;
            case "morphine_needle":
                Morphine Morphine_Needle = new Morphine(PluginInstance);
                GiveItem((Player) commandSender, Morphine_Needle.GetMorphineItem(true));
                break;
            case "heroin":
                Heroin Heroin = new Heroin(PluginInstance);
                GiveItem((Player) commandSender, Heroin.GetHeroinItem(false));
                break;
            case "heroin_needle":
                Heroin Heroin_Needle = new Heroin(PluginInstance);
                GiveItem((Player) commandSender, Heroin_Needle.GetHeroinItem(true));
                break;
            case "ketamine":
                Ketamine Ketamine = new Ketamine(PluginInstance);
                GiveItem((Player) commandSender, Ketamine.GetKetamineItem(false));
                break;
            case "ketamine_needle":
                Ketamine Ketamine_Needle = new Ketamine(PluginInstance);
                GiveItem((Player) commandSender, Ketamine_Needle.GetKetamineItem(true));
                break;
            case "fentanyl":
                Fentanyl Fentanyl = new Fentanyl(PluginInstance);
                GiveItem((Player) commandSender, Fentanyl.GetFentanylItem(false));
                break;
            case "fentanyl_needle":
                Fentanyl Fentanyl_Needle = new Fentanyl(PluginInstance);
                GiveItem((Player) commandSender, Fentanyl_Needle.GetFentanylItem(true));
                break;
            case "flunitrazepam":
                Flunitrazepam Flunitrazepam = new Flunitrazepam(PluginInstance);
                GiveItem((Player) commandSender, Flunitrazepam.GetFlunitrazepamItem(false));
                break;
            case "flunitrazepam_needle":
                Flunitrazepam Flunitrazepam_Needle = new Flunitrazepam(PluginInstance);
                GiveItem((Player) commandSender, Flunitrazepam_Needle.GetFlunitrazepamItem(true));
                break;
            default:
                return false;
        }

        return true;
    }
}
