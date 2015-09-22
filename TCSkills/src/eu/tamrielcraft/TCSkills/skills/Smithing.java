package eu.tamrielcraft.TCSkills.skills;

import org.bukkit.event.inventory.CraftItemEvent;

public class Smithing extends Skill {
	
	private static Smithing instance = new Smithing();
	
	public static Smithing getInstance() {
		return instance;
	}

	@Override
	public void onCraftEvent(CraftItemEvent event) {
		event.getWhoClicked().sendMessage("You tried to smith something");
	}
}
