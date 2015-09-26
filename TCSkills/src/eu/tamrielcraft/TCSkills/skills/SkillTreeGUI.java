package eu.tamrielcraft.TCSkills.skills;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class SkillTreeGUI {
	
	
	public static SettingsManager settings = SettingsManager.getInstance();
	
	public static ArrayList<String> diamondSwordLore = new ArrayList<String>(); //HAVE ONE OF THESE FOR EACH ITEM AND ADD THE DETAILS TO THE ON ENABLE	
	public static ArrayList<String> anvilLore = new ArrayList<String>();
	
	
	public static ArrayList<String> armsmanLore = new ArrayList<String>();
	
	
	public static void skillTreeOpen(Player player) {
		Inventory inv = Bukkit.createInventory(player, 45, "SkillTree");
		ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta diamondSwordMeta = diamondSword.getItemMeta();
		ItemStack anvil = new ItemStack(Material.ANVIL);
		ItemMeta anvilMeta = anvil.getItemMeta();
		
		
		anvilMeta.setDisplayName("Smithing");
		anvilMeta.setLore(anvilLore);
		anvil.setItemMeta(anvilMeta);
		
		diamondSwordMeta.setDisplayName("OneHanded");
		diamondSwordMeta.setLore(diamondSwordLore);
		diamondSword.setItemMeta(diamondSwordMeta);
		inv.setItem(0, diamondSword);
		
		
		player.openInventory(inv);
	}
	
	public static void skillTreeOpenOneHanded(Player player) {
	Inventory inv = Bukkit.createInventory(player, 54, "OneHanded");
	ItemStack armsman = new ItemStack(Material.BOOK);
	
	ItemMeta armsmanMeta = armsman.getItemMeta();
	
	armsmanMeta.setDisplayName("armsman");
	setGUILore(armsman, player, "armsman");
	armsmanMeta.setLore(armsmanLore);
	armsman.setItemMeta(armsmanMeta);
	
	inv.setItem(49, armsman);
	
	player.openInventory(inv);
	
	
	}
	
	public static void setGUILore(ItemStack item, Player player, String perkName) { //tells the player what level they are!
		if(armsmanLore.contains(ChatColor.RED + "Level : " + settings.getPerkLevel(perkName, player) + " / " + settings.getPerkMaxLevel(perkName))) {
			return;
		}
		armsmanLore.add(ChatColor.RED + "Level : " + settings.getPerkLevel(perkName, player) + " / " + settings.getPerkMaxLevel(perkName));
	
	}
	
	

}
