package me.oscarcusick.tokensmoke.Commands.Executors;

import me.oscarcusick.tokensmoke.Data.Items.WeedSeed;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GetWeedSeedsCommandExecutor implements CommandExecutor {
    Plugin PluginInstance; // needed to do shit with API\
    public GetWeedSeedsCommandExecutor(Plugin PluginInstance) {
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
        if (Integer.parseInt(strings[0]) > 2 || Integer.parseInt(strings[0]) < 1) {
            return false;
        }

        WeedSeed WS = new WeedSeed(PluginInstance);
        ((Player) commandSender).getWorld().dropItem(((Player) commandSender).getLocation(), WS.GetItem(Integer.parseInt(strings[0])));
        return true;
    }
}
