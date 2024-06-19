package me.oscarcusick.tokensmoke.Commands.TabCompleters;

import me.oscarcusick.tokensmoke.Utility.GeneralUtility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class DrugCommandTabCompleter implements TabCompleter {
    Plugin PluginInstance;
    public DrugCommandTabCompleter(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        List<String> OptionsList = new ArrayList();
        List<String> ReturnList = new ArrayList();

        OptionsList.add("Meth");

        OptionsList.add("Cocaine");

        OptionsList.add("Heroin");
        OptionsList.add("Heroin_Needle");

        OptionsList.add("Morphine");
        OptionsList.add("Morphine_Needle");

        OptionsList.add("Ketamine");
        OptionsList.add("Ketamine_Needle");

        OptionsList.add("Fentanyl");
        OptionsList.add("Fentanyl_Needle");

        OptionsList.add("Flunitrazepam"); // a common roofie-drug
        OptionsList.add("Flunitrazepam_Needle");

        GeneralUtility GU = new GeneralUtility(PluginInstance);

        for (String Element : OptionsList) {
            if (GU.Compare(Element, strings[strings.length-1])) {
                ReturnList.add(Element);
            }
        }

        if (ReturnList.isEmpty()) {
            ReturnList.add(" "); // add empty index to prevent error
        }
        return ReturnList;
    }
}
