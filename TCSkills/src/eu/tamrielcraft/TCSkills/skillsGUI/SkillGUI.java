package eu.tamrielcraft.TCSkills.skillsGUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public abstract class SkillGUI {
	protected static SettingsManager settings = SettingsManager.getInstance();
	
	public abstract void createSkillInventory(Player player);
	public abstract void itemClicked(Player player, String displayName);
	public abstract void setGUILore(ItemStack item, Player player, String perkName);
}
