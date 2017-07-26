package eu.tamrielcraft.TCSkills.skills;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import eu.tamrielcraft.TCSkills.skills.Smithing.SmithingPerk;

public class Alchemy extends Skill {
	
	private static Alchemy instance = new Alchemy();
	Map<PotionType,AlchemyPerk> skillPerks;
	Map<AlchemyPerk,Integer> perkLevels, perkMaxLevel;
	Map<PotionType,Integer> skillExp, skillPerkLevel;
	
	Map<Integer, Integer> alchemist = new HashMap<Integer, Integer>();
	Map<Integer, Integer> poisoner = new HashMap<Integer, Integer>();
	
	public enum AlchemyPerk{
		ALCHEMIST, PHYSICIAN, BENEFACTOR, EXPERIMENTER, POISONER, CONCENTRATED,
		GREEN, SNAKEBLOOD, PURITY
	}

	public static Alchemy getInstance() {
		return instance;
	}
	
	private Alchemy(){
		skillPerks = new HashMap<PotionType,AlchemyPerk>();
		perkLevels = new HashMap<AlchemyPerk,Integer>();
		perkMaxLevel = new HashMap<AlchemyPerk,Integer>();
		skillExp = new HashMap<PotionType,Integer>();
		skillPerkLevel = new HashMap<PotionType,Integer>();
		
		// Alchemy
		skillPerks.put(PotionType.INSTANT_HEAL, AlchemyPerk.ALCHEMIST);
		skillPerks.put(PotionType.SPEED, AlchemyPerk.PHYSICIAN);
		skillPerks.put(PotionType.JUMP, AlchemyPerk.PHYSICIAN);
		skillPerks.put(PotionType.STRENGTH, AlchemyPerk.BENEFACTOR);
		skillPerks.put(PotionType.WATER_BREATHING, AlchemyPerk.EXPERIMENTER);
		skillPerks.put(PotionType.POISON, AlchemyPerk.POISONER);
		skillPerks.put(PotionType.WEAKNESS, AlchemyPerk.POISONER);
		skillPerks.put(PotionType.INSTANT_DAMAGE, AlchemyPerk.POISONER);
		skillPerks.put(PotionType.NIGHT_VISION, AlchemyPerk.CONCENTRATED);
		skillPerks.put(PotionType.FIRE_RESISTANCE, AlchemyPerk.GREEN);
		skillPerks.put(PotionType.REGEN, AlchemyPerk.SNAKEBLOOD);
		skillPerks.put(PotionType.INVISIBILITY, AlchemyPerk.PURITY);
		
		skillPerkLevel.put(PotionType.INSTANT_HEAL, 1);
		skillPerkLevel.put(PotionType.SPEED, 1);
		skillPerkLevel.put(PotionType.JUMP, 1);
		skillPerkLevel.put(PotionType.STRENGTH, 1);
		skillPerkLevel.put(PotionType.WATER_BREATHING, 1);
		skillPerkLevel.put(PotionType.POISON, 1);
		skillPerkLevel.put(PotionType.WEAKNESS, 2);
		skillPerkLevel.put(PotionType.INSTANT_DAMAGE, 3);
		skillPerkLevel.put(PotionType.NIGHT_VISION, 1);
		skillPerkLevel.put(PotionType.FIRE_RESISTANCE, 1);
		skillPerkLevel.put(PotionType.REGEN, 1);
		skillPerkLevel.put(PotionType.INVISIBILITY, 1);
		
		
		// Setup general perk level requirements
		perkLevels.put(AlchemyPerk.ALCHEMIST, 1);
		perkLevels.put(AlchemyPerk.PHYSICIAN, 20);
		perkLevels.put(AlchemyPerk.BENEFACTOR, 30);
		perkLevels.put(AlchemyPerk.EXPERIMENTER, 50);
		perkLevels.put(AlchemyPerk.POISONER, 30);
		perkLevels.put(AlchemyPerk.CONCENTRATED, 60);
		perkLevels.put(AlchemyPerk.GREEN, 70);
		perkLevels.put(AlchemyPerk.SNAKEBLOOD, 80);
		perkLevels.put(AlchemyPerk.PURITY, 100);
		
		// Setup specific perk level requirements
		alchemist.put(1, 1);
		alchemist.put(2, 20);
		alchemist.put(3, 40);
		alchemist.put(4, 60);
		alchemist.put(5, 80);
		
		poisoner.put(1, 50);
		poisoner.put(2, 70);
		poisoner.put(3, 90);
				
		// Setup Max level of perks
		perkMaxLevel.put(AlchemyPerk.ALCHEMIST, 5);
		perkMaxLevel.put(AlchemyPerk.PHYSICIAN, 1);
		perkMaxLevel.put(AlchemyPerk.BENEFACTOR, 1);
		perkMaxLevel.put(AlchemyPerk.EXPERIMENTER, 1);
		perkMaxLevel.put(AlchemyPerk.POISONER, 3);
		perkMaxLevel.put(AlchemyPerk.CONCENTRATED, 1);
		perkMaxLevel.put(AlchemyPerk.GREEN, 1);
		perkMaxLevel.put(AlchemyPerk.SNAKEBLOOD, 1);
		perkMaxLevel.put(AlchemyPerk.PURITY, 1);
		
		// Experience tables
		skillExp.put(PotionType.INSTANT_HEAL, 3);
		skillExp.put(PotionType.SPEED, 6);
		skillExp.put(PotionType.JUMP, 9);
		skillExp.put(PotionType.STRENGTH, 12);
		skillExp.put(PotionType.WATER_BREATHING, 19);
		skillExp.put(PotionType.POISON, 33);
		skillExp.put(PotionType.WEAKNESS, 55);
		skillExp.put(PotionType.INSTANT_DAMAGE, 69);
		skillExp.put(PotionType.NIGHT_VISION, 87);
		skillExp.put(PotionType.FIRE_RESISTANCE, 103);
		skillExp.put(PotionType.REGEN, 166);
		skillExp.put(PotionType.INVISIBILITY, 264);
	}

	@Override
	public void onCraftEvent(CraftItemEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(!hasPerk(this, skillPerks.get((Potion.fromItemStack((event.getRecipe().getResult())).getType())).toString().toLowerCase(), skillPerkLevel.get((Potion.fromItemStack((event.getRecipe().getResult())).getType())), player)){
			event.getWhoClicked().sendMessage("Euh, where should I start? I don't know how to craft this...");
			event.setCancelled(true);
		}else{
			// Next line is needed to avoid being able to mass right-click and not lose resources but gain experience
			if((event.getCursor().getType().getMaxStackSize() > 1 && event.getCursor().getType() == event.getRecipe().getResult().getType()) || event.getCursor().getType() == Material.AIR){
				// Player is able to craft this, continue and add experience
				int expGained = skillExp.get(event.getRecipe().getResult().getType());
				int previousExp = settings.getSkillExp(getSkillName(), player);
				int previousLevel = getLevel(previousExp);
				int nextExp = previousExp + expGained;
				int nextLevel = getLevel(nextExp);
				
				settings.setSkillExp(player, getSkillName(), nextExp);
				player.sendMessage("You gained " + expGained + " experience towards " + getSkillName());
				if(nextLevel > previousLevel){
					player.sendMessage("You gained a level in " + getSkillName() + ". You are now level " + nextLevel);
				}
			}else{
				player.sendMessage("I still have something in my hand!");
				event.setCancelled(true);
			}
		}

	}

	@Override
	public String getSkillName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void advancePerkLevel(String perkName, Player player) {
		/*// Player wants to advance a perk
		// First, check if player has any Ability Points
		if(settings.getAbilityPoints(player) <= 0){
			player.sendMessage("You don't have enough ability points to upgrade");
			return;
		}
		
		// Next get wanted perk
		SmithingPerk perk = null;
		for(SmithingPerk s : SmithingPerk.values()){
		if(perkName.equalsIgnoreCase(s.toString())){
				perk = s;
			}
		}	
		// Additional check
		if(perk == null){
			player.sendMessage("Something went wrong... There is no perk with this name.");
			return;
		}
		
		// Then, check if player is at max level of perk
		if(hasPerk(this, perk.toString(), perkMaxLevel.get(perk), player)){
			// Player is at max level so report and abort
			player.sendMessage("You are already a professional in this ability!");
			return;
		}
		
		// Now check whether the player has all requirements
		SmithingPerk dependency = perkDependencies.get(perk);
		if(dependency != null){
			// Perk requirements
			if(!hasPerk(this, dependency.toString().toLowerCase(), 1, player)){
				player.sendMessage("You haven't unlocked the necessary perks to unlock this perk");
				return;
			}
			// Level requirements
			if(perkLevels.get(perk) > settings.getPerkLevel(getSkillName(), perk.toString(), player)){
				player.sendMessage("I am not skilled enough to learn this. I might want to practice more.");
				return;
			}
		}
		// All clear, advance perk
		// Need to check level and add one if applicable
		upgradePerk(perk.toString(), player);
		player.sendMessage("Perklevel upgraded!");*/
	}
	
	@Override
	protected void upgradePerk(String perkName, Player player) {
		// TODO Auto-generated method stub

	}

}
