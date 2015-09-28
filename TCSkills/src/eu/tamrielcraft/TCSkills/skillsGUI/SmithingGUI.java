package eu.tamrielcraft.TCSkills.skillsGUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SmithingGUI extends SkillGUI {
	private static SmithingGUI instance = new SmithingGUI();
	
	public static SmithingGUI getInstance(){
		return instance;
	}
	
	public void createSkillInventory(Player player){
		// Center basic skill
		Inventory inv = Bukkit.createInventory(player, 18, "Smithing");
		ItemStack basicSmithing = new ItemStack(Material.ANVIL);
		ItemMeta basicSmithingMeta = basicSmithing.getItemMeta();
		basicSmithingMeta.setDisplayName("Basic Smithing");
		setGUILore(basicSmithing, player, "Basic Smithing");
		basicSmithing.setItemMeta(basicSmithingMeta);
		inv.setItem(4, basicSmithing);
		
		// Left tree (weapons)
		ItemStack goldenStrike = new ItemStack(Material.ANVIL);
		ItemMeta goldenStrikeMeta = goldenStrike.getItemMeta();
		goldenStrikeMeta.setDisplayName("Basic Smithing");
		setGUILore(goldenStrike, player, "Basic Smithing");
		goldenStrike.setItemMeta(goldenStrikeMeta);
		inv.setItem(2, goldenStrike);
		
		ItemStack stonification = new ItemStack(Material.STONE_SWORD);
		ItemMeta stonificationMeta = stonification.getItemMeta();
		stonificationMeta.setDisplayName("Stonification");
		setGUILore(stonification, player, "Stonification");
		stonification.setItemMeta(stonificationMeta);
		inv.setItem(11, stonification);
		
		
		player.openInventory(inv);
	}
	
	@Override
	public void itemClicked(Player player, String displayName){
		
	}

	@Override
	public void setGUILore(ItemStack item, Player player, String perkName) {
		
	}
}
