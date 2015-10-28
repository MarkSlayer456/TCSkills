package eu.tamrielcraft.TCSkills.skillsGUI;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.tamrielcraft.TCSkills.skills.Smithing;
import eu.tamrielcraft.TCSkills.skills.Smithing.SmithingPerk;

public class SmithingGUI extends SkillGUI {
	private static SmithingGUI instance = new SmithingGUI();
	private static Smithing smithingSkill = Smithing.getInstance();
	
	public static SmithingGUI getInstance(){
		return instance;
	}
	
	public void createSkillInventory(Player player){
		// Center basic skill
		Inventory inv = Bukkit.createInventory(player, 45, "Smithing");
		ItemStack basicSmithing = new ItemStack(Material.ANVIL);
		ItemMeta basicSmithingMeta = basicSmithing.getItemMeta();
		basicSmithingMeta.setDisplayName("Basic Smithing");
		setGUILore(basicSmithing, player, "Basic Smithing");
		basicSmithing.setItemMeta(basicSmithingMeta);
		inv.setItem(4, basicSmithing);
		
		ItemStack goldenStrike = new ItemStack(Material.GOLD_SWORD);
		ItemMeta goldenStrikeMeta = goldenStrike.getItemMeta();
		goldenStrikeMeta.setDisplayName("Golden Strike");
		setGUILore(goldenStrike, player, "GoldenStrike");
		goldenStrike.setItemMeta(goldenStrikeMeta);
		inv.setItem(12, goldenStrike);

		ItemStack stonification = new ItemStack(Material.STONE_SWORD);
		ItemMeta stonificationMeta = stonification.getItemMeta();
		stonificationMeta.setDisplayName("Stonification");
		setGUILore(stonification, player, "Stonification");
		stonification.setItemMeta(stonificationMeta);
		inv.setItem(21, stonification);
		
		ItemStack ironLegacy = new ItemStack(Material.IRON_SWORD);
		ItemMeta ironLegacyMeta = ironLegacy.getItemMeta();
		ironLegacyMeta.setDisplayName("Iron Legacy");
		setGUILore(ironLegacy, player, "Iron Legacy");
		ironLegacy.setItemMeta(ironLegacyMeta);
		inv.setItem(30, ironLegacy);
		
		
		player.openInventory(inv);
	}
	
	@Override
	public void itemClicked(Player player, String displayName){
		switch(displayName){
		case "Basic Smithing":
			smithingSkill.advancePerkLevel(SmithingPerk.BASICSMITHING.toString(), player); break;
		case "Golden Strike":
			smithingSkill.advancePerkLevel(SmithingPerk.GOLDENSTRIKE.toString(), player); break;
		case "Stonification":
			smithingSkill.advancePerkLevel(SmithingPerk.STONIFICATION.toString(), player); break;
		case "Iron Legacy":
			smithingSkill.advancePerkLevel(SmithingPerk.IRONLEGACY.toString(), player); break;
		default:
			
		}
	}

	@Override
	public void setGUILore(ItemStack item, Player player, String perkName) {
		
	}
}
