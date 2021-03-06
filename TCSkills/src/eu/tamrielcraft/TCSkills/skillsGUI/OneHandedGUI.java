package eu.tamrielcraft.TCSkills.skillsGUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.tamrielcraft.TCSkills.skills.OneHanded;

public class OneHandedGUI extends SkillGUI {
	
	private static OneHandedGUI instance = new OneHandedGUI();
	private static OneHanded oh = OneHanded.getInstance();
	
	
	private List<String> armsmanLore = new ArrayList<String>();

	public static OneHandedGUI getInstance(){
		return instance;
	}
	
	public void createSkillInventory(Player player){
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
	
	@Override
	public void itemClicked(Player player, String displayName) {
		switch(displayName){
		case "armsman":
			oh.advancePerkLevel(displayName, player);
			break;
		case "hackandslash":
			oh.advancePerkLevel(displayName, player);
			break;
		case "dualflurry":
			oh.advancePerkLevel(displayName, player);
			break;
		}
	}

	@Override
	public void setGUILore(ItemStack item, Player player, String perkName) {
		if(armsmanLore.contains(ChatColor.RED + "Level : " + settings.getPerkLevel(OneHanded.getInstance().getSkillName(), perkName, player) + " / " + settings.getPerkMaxLevel(perkName))) {
			return;
		}
		armsmanLore.add(ChatColor.RED + "Level : " + settings.getPerkLevel(OneHanded.getInstance().getSkillName(), perkName, player) + " / " + settings.getPerkMaxLevel(perkName));
	}

}
