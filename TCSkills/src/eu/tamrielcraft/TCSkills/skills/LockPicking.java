package eu.tamrielcraft.TCSkills.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;

public class LockPicking extends Skill {
	
	private static LockPicking instance = new LockPicking();
	
	public enum LockPickingPerk{
		NOVICE, APPRENTICE, QUICK, WAX, ADEPT, EXPERT, GOLDEN, TREASURE,
		LOCKSMITH, UNBREAKABLE, MASTER
	}

	public static LockPicking getInstance() {
		return instance;
	}
	
	@Override
	public void onCraftEvent(CraftItemEvent event) {
	}

	@Override
	public String getSkillName() {
		return "lockpicking";
	}

	@Override
	public void advancePerkLevel(String perkName, Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void upgradePerk(String perkName, Player player) {
		// TODO Auto-generated method stub

	}

}
