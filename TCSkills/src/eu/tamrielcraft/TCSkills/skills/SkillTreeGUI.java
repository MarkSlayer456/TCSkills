package eu.tamrielcraft.TCSkills.skills;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class SkillTreeGUI {
	
	
	public static SettingsManager settings = SettingsManager.getInstance();
	
	public static ArrayList<String> diamondSwordLore = new ArrayList<String>(); //HAVE ONE OF THESE FOR EACH ITEM AND ADD THE DETAILS TO THE ON ENABLE	
	
	
	public static void skillTreeOpen(Player player) {
		Inventory inv = Bukkit.createInventory(null, 45, "SkillTree");
		ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta diamondSwordMeta = diamondSword.getItemMeta();
		
		diamondSwordMeta.setDisplayName("OneHanded");
		diamondSwordMeta.setLore(diamondSwordLore);
		diamondSword.setItemMeta(diamondSwordMeta);
		inv.setItem(0, diamondSword);
		
		
		player.openInventory(inv);
	}
	
	public static void skillTreeOpenOneHanded(Player player) {
		UUID id = player.getUniqueId();
	Inventory inv = Bukkit.createInventory(null, 54, "One Handed");
	ItemStack armsman = new ItemStack(Material.BOOK);
	
	ItemMeta armsmanMeta = armsman.getItemMeta();
	
	armsmanMeta.setDisplayName("armsman " + settings.getSave().getInt(id + ".skills.onehanded.armsman") + " / " + settings.getSave().getInt("oneHanded.armsman.max"));
	armsman.setItemMeta(armsmanMeta);
	
	inv.setItem(49, armsman);
	
	player.openInventory(inv);
	
	
	}
	
	

}
