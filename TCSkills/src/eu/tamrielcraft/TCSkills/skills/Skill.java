package eu.tamrielcraft.TCSkills.skills;

import org.bukkit.event.inventory.CraftItemEvent;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public abstract class Skill {
	
	protected SettingsManager settings = SettingsManager.getInstance();
	
	public abstract void onCraftEvent(CraftItemEvent event);

}
