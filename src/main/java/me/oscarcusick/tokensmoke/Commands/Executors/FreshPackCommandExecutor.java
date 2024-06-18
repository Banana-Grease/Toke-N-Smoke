package me.oscarcusick.tokensmoke.Commands.Executors;

import me.oscarcusick.tokensmoke.Data.Items.CigarettePack;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FreshPackCommandExecutor implements CommandExecutor {
    Plugin PluginInstance;
    public FreshPackCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length != 1) {
            return false;
        }
        if (Integer.parseInt(strings[0]) > 3 || Integer.parseInt(strings[0]) < 1) {
            return false;
        }

        CigarettePack CP = new CigarettePack(PluginInstance);
        Player User = (Player)commandSender;
        User.getWorld().dropItem(User.getLocation(), CP.GetFreshPack(Integer.parseInt(strings[0])));
        return true;
    }
}
