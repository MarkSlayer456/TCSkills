package eu.tamrielcraft.TCSkills.skillsGUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class SkillTreeGUI {
	private static SkillTreeGUI instance = new SkillTreeGUI();	
	public static SettingsManager settings = SettingsManager.getInstance();
	
	public static List<String> skillMenus;
	
	//public static ArrayList<String> diamondSwordLore = new ArrayList<String>(); //HAVE ONE OF THESE FOR EACH ITEM AND ADD THE DETAILS TO THE ON ENABLE	
	//public static ArrayList<String> anvilLore = new ArrayList<String>();
	//public static ArrayList<String> armsmanLore = new ArrayList<String>();
	
	//TODO: SHOULD DO IT WITH A HASHMAP
	
	private SkillTreeGUI(){
		// Fill in all lists etc
		// SkillNames
		skillMenus = new ArrayList<String>();
		skillMenus.add("SkillTree");
		skillMenus.add("OneHanded");
		skillMenus.add("Smithing");
	}
	
	public static SkillTreeGUI getInstance(){
		return instance;
	}
	
	public void skillTreeOpen(Player player) {
		// This is the basic Skill Tree window
		// Contains all top level skills
		Inventory inv = Bukkit.createInventory(player, 45, "SkillTree");
		
		// OneHanded
		ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta diamondSwordMeta = diamondSword.getItemMeta();
		diamondSwordMeta.setDisplayName("OneHanded");
		//diamondSwordMeta.setLore(diamondSwordLore);
		diamondSword.setItemMeta(diamondSwordMeta);
		inv.setItem(0, diamondSword);
		
		// Smithing
		ItemStack anvil = new ItemStack(Material.ANVIL);
		ItemMeta anvilMeta = anvil.getItemMeta();		
		anvilMeta.setDisplayName("Smithing");
		//anvilMeta.setLore(anvilLore);
		anvil.setItemMeta(anvilMeta);
		inv.setItem(1, anvil);
		
		player.openInventory(inv);
	}
	
	public void itemClicked(Player player, String menuName, String displayName){
		switch(menuName) {
		case "SkillTree":
			skillTreeOpenSubMenu(player, displayName);
		case "OneHanded":
			OneHandedGUI.getInstance().itemClicked(player, displayName);
			break;
		case "Smithing":
			SmithingGUI.getInstance().itemClicked(player, displayName);
			break;
		/*case "armsman":
			if(settings.canUpgradePerk(OneHanded.getInstance().getSkillName(), "armsman", player) == true) {
				settings.upgradePerk("armsman", player);
			}
			break;
		case "hackandslash":
			if(settings.canUpgradePerk(OneHanded.getInstance().getSkillName(), "hackandslash", player)) {
				settings.upgradePerk("hackandslash", player);
			}
			break;
		case "dualflurry":
			if(settings.canUpgradePerk(OneHanded.getInstance().getSkillName(), "dualflurry", player)) {
				settings.upgradePerk("dualflurry", player);
			} 
			break;*/
		case "":
		}
	}
	
	
	
	public void skillTreeOpenSubMenu(Player player, String displayName){
		switch(displayName){
		case "OneHanded":
			OneHandedGUI.getInstance().createSkillInventory(player);
			break;
		case "Smithing":
			SmithingGUI.getInstance().createSkillInventory(player);
		}
	}
}
