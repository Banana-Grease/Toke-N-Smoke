package me.oscarcusick.tokensmoke;

import me.oscarcusick.tokensmoke.Commands.Executors.DrugCommandExecutor;
import me.oscarcusick.tokensmoke.Commands.Executors.FreshPackCommandExecutor;
import me.oscarcusick.tokensmoke.Commands.TabCompleters.DrugCommandTabCompleter;
import me.oscarcusick.tokensmoke.Data.Drugs.*;
import me.oscarcusick.tokensmoke.Data.Items.CigarettePack;
import me.oscarcusick.tokensmoke.Listeners.AddCigarettesToLootTableListener;
import me.oscarcusick.tokensmoke.Listeners.CigarettePackUseListener;
import me.oscarcusick.tokensmoke.Listeners.DrugUseListeners.*;
import me.oscarcusick.tokensmoke.Listeners.NeedleCraftListener;
import me.oscarcusick.tokensmoke.Listeners.VillagerAddDrugsListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class Toke_N_Smoke extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("FreshPack").setExecutor(new FreshPackCommandExecutor(this));

        getCommand("Drug").setExecutor(new DrugCommandExecutor(this));
        getCommand("Drug").setTabCompleter(new DrugCommandTabCompleter(this));

        getServer().getPluginManager().registerEvents(new CigarettePackUseListener(this), this);
        getServer().getPluginManager().registerEvents(new CigaretteUseListener(this), this);

        getServer().getPluginManager().registerEvents(new VillagerAddDrugsListener(this), this);
        getServer().getPluginManager().registerEvents(new AddCigarettesToLootTableListener(this), this);
        getServer().getPluginManager().registerEvents(new NeedleCraftListener(this), this);

        getServer().getPluginManager().registerEvents(new CrystalMethUseListener(this), this);
        getServer().getPluginManager().registerEvents(new CocaineUseListener(this), this);
        getServer().getPluginManager().registerEvents(new MorphineUseListener(this), this);
        getServer().getPluginManager().registerEvents(new HeroinUseListener(this), this);
        getServer().getPluginManager().registerEvents(new KetamineUseListener(this), this);
        getServer().getPluginManager().registerEvents(new FentanylUseListener(this), this);
        getServer().getPluginManager().registerEvents(new FlunitrazepamUseListener(this), this);

        // Recipes
        CigarettePack CP = new CigarettePack(this);
        ShapedRecipe SteveNSonsRecipe = new ShapedRecipe(new NamespacedKey(this, "SteveNSons"), CP.GetFreshPack(1));
        SteveNSonsRecipe.shape("AAA", "BBB", "AAA");
        SteveNSonsRecipe.setIngredient('A', Material.PAPER);
        SteveNSonsRecipe.setIngredient('B', Material.DRIED_KELP_BLOCK);
        Bukkit.addRecipe(SteveNSonsRecipe);

        Cocaine CocaineClass = new Cocaine(this);
        ShapelessRecipe CocaineRecipe = new ShapelessRecipe(new NamespacedKey(this, "Cocaine"), CocaineClass.GetCocaineItem());
        CocaineRecipe.addIngredient(Material.SUGAR);
        CocaineRecipe.addIngredient(Material.BONE_MEAL);
        CocaineRecipe.addIngredient(Material.CHARCOAL);
        Bukkit.addRecipe(CocaineRecipe);

        CrystalMeth MethClass = new CrystalMeth(this);
        ShapelessRecipe MethRecipe = new ShapelessRecipe(new NamespacedKey(this, "CrystalMeth"), MethClass.GetMethItem());
        MethRecipe.addIngredient(Material.SUGAR);
        MethRecipe.addIngredient(Material.GUNPOWDER);
        MethRecipe.addIngredient(Material.REDSTONE);
        Bukkit.addRecipe(MethRecipe);

        Heroin HeroinClass = new Heroin(this);
        ShapelessRecipe HeroinRecipe = new ShapelessRecipe(new NamespacedKey(this, "Heroin"), HeroinClass.GetHeroinItem(false));
        HeroinRecipe.addIngredient(Material.BLAZE_POWDER);
        HeroinRecipe.addIngredient(Material.SPIDER_EYE);
        HeroinRecipe.addIngredient(Material.GLOWSTONE_DUST);
        Bukkit.addRecipe(HeroinRecipe);

        // from powder to needle
        ShapelessRecipe Heroin_NeedleRecipe = new ShapelessRecipe(new NamespacedKey(this, "Heroin_Needle"), HeroinClass.GetHeroinItem(true));
        Heroin_NeedleRecipe.addIngredient(HeroinClass.GetHeroinItem(false).getType());
        Heroin_NeedleRecipe.addIngredient(Material.GLASS_BOTTLE);
        Bukkit.addRecipe(Heroin_NeedleRecipe);

        Fentanyl FentanylClass = new Fentanyl(this);
        ShapelessRecipe FentanylRecipe = new ShapelessRecipe(new NamespacedKey(this, "Fentanyl"), FentanylClass.GetFentanylItem(false));
        FentanylRecipe.addIngredient(Material.NETHER_WART);
        FentanylRecipe.addIngredient(Material.GUNPOWDER);
        FentanylRecipe.addIngredient(Material.BLAZE_POWDER);
        Bukkit.addRecipe(FentanylRecipe);

        // from powder to needle
        ShapelessRecipe Fentanyl_NeedleRecipe = new ShapelessRecipe(new NamespacedKey(this, "Fentanyl_Needle"), FentanylClass.GetFentanylItem(true));
        Fentanyl_NeedleRecipe.addIngredient(FentanylClass.GetFentanylItem(false).getType());
        Fentanyl_NeedleRecipe.addIngredient(Material.GLASS_BOTTLE);
        Bukkit.addRecipe(Fentanyl_NeedleRecipe);

        Flunitrazepam FlunitrazepamClass = new Flunitrazepam(this);
        ShapelessRecipe FlunitrazepamRecipe = new ShapelessRecipe(new NamespacedKey(this, "Flunitrazepam"), FlunitrazepamClass.GetFlunitrazepamItem(false));
        FlunitrazepamRecipe.addIngredient(Material.FERMENTED_SPIDER_EYE);
        FlunitrazepamRecipe.addIngredient(Material.GLASS_PANE);
        FlunitrazepamRecipe.addIngredient(Material.BLAZE_POWDER);
        FlunitrazepamRecipe.addIngredient(Material.ENDER_PEARL);
        FlunitrazepamRecipe.addIngredient(Material.CACTUS);
        FlunitrazepamRecipe.addIngredient(Material.GUNPOWDER);
        Bukkit.addRecipe(FlunitrazepamRecipe);

        // from powder to needle
        ShapelessRecipe Flunitrazepam_NeedleRecipe = new ShapelessRecipe(new NamespacedKey(this, "Flunitrazepam_Needle"), FlunitrazepamClass.GetFlunitrazepamItem(true));
        Flunitrazepam_NeedleRecipe.addIngredient(FlunitrazepamClass.GetFlunitrazepamItem(false).getType());
        Flunitrazepam_NeedleRecipe.addIngredient(Material.GLASS_BOTTLE);
        Bukkit.addRecipe(Flunitrazepam_NeedleRecipe);

        // from powder to needle
        Ketamine KetamineClass = new Ketamine(this);
        ShapelessRecipe Ketamine_NeedleRecipe = new ShapelessRecipe(new NamespacedKey(this, "Ketamine_Needle"), KetamineClass.GetKetamineItem(true));
        Ketamine_NeedleRecipe.addIngredient(KetamineClass.GetKetamineItem(false).getType());
        Ketamine_NeedleRecipe.addIngredient(Material.GLASS_BOTTLE);
        Bukkit.addRecipe(Ketamine_NeedleRecipe);

        // from powder to needle
        Morphine MorphineClass = new Morphine(this);
        ShapelessRecipe Morphine_NeedleRecipe = new ShapelessRecipe(new NamespacedKey(this, "Morphine_Needle"), MorphineClass.GetMorphineItem(true));
        Morphine_NeedleRecipe.addIngredient(MorphineClass.GetMorphineItem(false).getType());
        Morphine_NeedleRecipe.addIngredient(Material.GLASS_BOTTLE);
        Bukkit.addRecipe(Morphine_NeedleRecipe);

    }
}
