package me.oscarcusick.tokensmoke.Utility;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class GeneralUtility {
    Plugin PluginInstance;
    public GeneralUtility(Plugin PluginInstance) {
        this.PluginInstance = PluginInstance;
    }

    // argument flag signatures
    public String[] ArgumentTrueFlags = {"true", "t", "yes", "y", "positive", "p"}; // any of these indicates true
    public String[] ArgumentFalseFlags = {"false", "f", "no", "n", "negative", "n"}; // any of these indicates false

    // returns a list of all players that have a specified NBT
    public ArrayList<Player> AnyPlayersHaveNBT(String NBTKey) {
        ArrayList<Player> PlayersWithNBT = new ArrayList<>();
        for (Player P : PluginInstance.getServer().getOnlinePlayers()) {
            if (P.getPersistentDataContainer().has(new NamespacedKey(PluginInstance, NBTKey))) {
                PlayersWithNBT.add(P);
            }
        }
        return PlayersWithNBT;
    }

    // returns true if any string shows signs of a 'true' argument. else, returns false
    public boolean StringContainsTrue(String Flag) { // if no flag is found will return !!!!FALSE!!!!
        for (int i = 0; i < ArgumentTrueFlags.length; i++) {
            if (Flag.equalsIgnoreCase(ArgumentTrueFlags[i])) { // if Flag matches current argument flag
                return true;
            }
        }
        return false;
    }

    // SHA-256 Hash any string
    public String Hash256(String Plaintext) throws NoSuchAlgorithmException {
        MessageDigest HashMD= MessageDigest.getInstance("SHA-256");
        HashMD.update(Plaintext.getBytes());
        byte[] Digest = HashMD.digest();
        { //Converting the digest-byte arrays in to HexString format
            StringBuffer HexDigest = new StringBuffer();
            for (int i = 0; i < Digest.length; i++) {
                HexDigest.append(Integer.toHexString(0xFF & Digest[i]));
            }
            return HexDigest.toString();
        }
    }

    // generates an item stack
    public ItemStack GenerateItemStack(Material ItemType, String Name, ArrayList<String> Lore, int AmountOfItems) {
        ItemStack ResultButton = new ItemStack(ItemType, AmountOfItems);
        ItemMeta ResultButtonMeta = ResultButton.getItemMeta();
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_DYE); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_DESTROYS); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); // remove armour stats, etc.
        ResultButtonMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE); // remove armour stats, etc.
        ResultButtonMeta.setDisplayName(Name);
        ResultButtonMeta.setLore(Lore);
        ResultButton.setItemMeta(ResultButtonMeta);
        return ResultButton;
    }
}
