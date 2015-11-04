package eu.tamrielcraft.TCSkills.skills;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;

public class Alchemy extends Skill {
	
	private static Alchemy instance = new Alchemy();
	Map<AlchemyPerk,Integer> perkLevels, perkMaxLevel;
	
	public enum AlchemyPerk{
		ALCHEMIST, PHYSICIAN, BENEFACTOR, EXPERIMENTER, POISONER, CONCENTRATED,
		GREEN, SNAKEBLOOD, PURITY
	}

	public static Alchemy getInstance() {
		return instance;
	}
	
	private Alchemy(){
		// Setup perk level requirements
		/*perkLevels.put(AlchemyPerk.ALCHEMIST, 1);
		perkLevels.put(AlchemyPerk.PHYSICIAN, 1);
		perkLevels.put(AlchemyPerk.BENEFACTOR, 1);
		perkLevels.put(AlchemyPerk.EXPERIMENTER, 3);
		perkLevels.put(AlchemyPerk.POISONER, 1);
		perkLevels.put(AlchemyPerk.CONCENTRATED, 1);
		perkLevels.put(AlchemyPerk.GREEN, 1);
		perkLevels.put(AlchemyPerk.SNAKEBLOOD, 1);
		perkLevels.put(AlchemyPerk.PURITY, 1);*/
				
		// Setup Max level of skills
		perkMaxLevel.put(AlchemyPerk.ALCHEMIST, 5);
		perkMaxLevel.put(AlchemyPerk.PHYSICIAN, 1);
		perkMaxLevel.put(AlchemyPerk.BENEFACTOR, 1);
		perkMaxLevel.put(AlchemyPerk.EXPERIMENTER, 3);
		perkMaxLevel.put(AlchemyPerk.POISONER, 1);
		perkMaxLevel.put(AlchemyPerk.CONCENTRATED, 1);
		perkMaxLevel.put(AlchemyPerk.GREEN, 1);
		perkMaxLevel.put(AlchemyPerk.SNAKEBLOOD, 1);
		perkMaxLevel.put(AlchemyPerk.PURITY, 1);
	}

	@Override
	public void onCraftEvent(CraftItemEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSkillName() {
		// TODO Auto-generated method stub
		return null;
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
