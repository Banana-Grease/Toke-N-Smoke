package me.oscarcusick.tokensmoke;

import me.oscarcusick.tokensmoke.Commands.Executors.FreshPackCommandExecutor;
import me.oscarcusick.tokensmoke.Listeners.AddCigarettesToLootTableListener;
import me.oscarcusick.tokensmoke.Listeners.CigarettePackUseListener;
import me.oscarcusick.tokensmoke.Listeners.CigaretteUseListener;
import me.oscarcusick.tokensmoke.Listeners.VillagerAddCigarettesListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Toke_N_Smoke extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("FreshPack").setExecutor(new FreshPackCommandExecutor(this));

        getServer().getPluginManager().registerEvents(new CigarettePackUseListener(this), this);
        getServer().getPluginManager().registerEvents(new CigaretteUseListener(this), this);
        getServer().getPluginManager().registerEvents(new VillagerAddCigarettesListener(this), this);
        getServer().getPluginManager().registerEvents(new AddCigarettesToLootTableListener(this), this);
    }
}
