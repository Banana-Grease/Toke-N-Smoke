package me.oscarcusick.tokensmoke.Commands.Executors;

import me.oscarcusick.tokensmoke.Data.Items.OpiumSeed;
import me.oscarcusick.tokensmoke.Data.Items.WeedSeed;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GetOpiumSeedsCommandExecutor implements CommandExecutor {
    Plugin PluginInstance; // needed to do shit with API\
    public GetOpiumSeedsCommandExecutor(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        if (strings.length != 0) {
            return false;
        }

        OpiumSeed OS = new OpiumSeed(PluginInstance);
        ((Player) commandSender).getWorld().dropItem(((Player) commandSender).getLocation(), OS.GetItem());
        return true;
    }
}
