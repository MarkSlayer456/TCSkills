package eu.tamrielcraft.TCSkills.skills;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;

public class Smithing extends Skill {

	Map<Material,SmithingPerk> skillPerks;
	Map<Material,Integer> skillExp;
	Map<SmithingPerk,Integer> perkLevels;
	Map<SmithingPerk,SmithingPerk> perkDependencies;
	
	public enum SmithingPerk{
		BASICSMITHING, GOLDENSTRIKE, STONIFICATION, IRONLEGACY, DIAMONDIFICATION,
		GOLDENADVANCEMENTS, CHAINING, IRONPLATING, DIAMONDWELDING
	}
	
	private static Smithing instance = new Smithing();
	
	private Smithing(){
		// Create hashmap of skillLevels
		skillPerks = new HashMap<Material,SmithingPerk>();
		skillExp = new HashMap<Material,Integer>();
		perkLevels = new HashMap<SmithingPerk,Integer>();
		perkDependencies = new HashMap<SmithingPerk,SmithingPerk>();
		
		// Armor
		skillPerks.put(Material.LEATHER_BOOTS, SmithingPerk.BASICSMITHING);
		skillPerks.put(Material.LEATHER_CHESTPLATE, SmithingPerk.BASICSMITHING);
		skillPerks.put(Material.LEATHER_HELMET, SmithingPerk.BASICSMITHING);
		skillPerks.put(Material.LEATHER_LEGGINGS, SmithingPerk.BASICSMITHING);
		skillPerks.put(Material.GOLD_BOOTS, SmithingPerk.GOLDENADVANCEMENTS);
		skillPerks.put(Material.GOLD_CHESTPLATE, SmithingPerk.GOLDENADVANCEMENTS);
		skillPerks.put(Material.GOLD_HELMET, SmithingPerk.GOLDENADVANCEMENTS);
		skillPerks.put(Material.GOLD_LEGGINGS, SmithingPerk.GOLDENADVANCEMENTS);
		skillPerks.put(Material.CHAINMAIL_BOOTS, SmithingPerk.CHAINING);
		skillPerks.put(Material.CHAINMAIL_CHESTPLATE, SmithingPerk.CHAINING);
		skillPerks.put(Material.CHAINMAIL_HELMET, SmithingPerk.CHAINING);
		skillPerks.put(Material.CHAINMAIL_LEGGINGS, SmithingPerk.CHAINING);
		skillPerks.put(Material.IRON_BOOTS, SmithingPerk.IRONPLATING);
		skillPerks.put(Material.IRON_CHESTPLATE, SmithingPerk.IRONPLATING);
		skillPerks.put(Material.IRON_HELMET, SmithingPerk.IRONPLATING);
		skillPerks.put(Material.IRON_LEGGINGS, SmithingPerk.IRONPLATING);
		skillPerks.put(Material.DIAMOND_BOOTS, SmithingPerk.DIAMONDWELDING);
		skillPerks.put(Material.DIAMOND_CHESTPLATE, SmithingPerk.DIAMONDWELDING);
		skillPerks.put(Material.DIAMOND_HELMET, SmithingPerk.DIAMONDWELDING);
		skillPerks.put(Material.DIAMOND_LEGGINGS, SmithingPerk.DIAMONDWELDING);
		
		// Weapons
		skillPerks.put(Material.WOOD_AXE, SmithingPerk.BASICSMITHING);
		skillPerks.put(Material.WOOD_SPADE, SmithingPerk.BASICSMITHING);
		skillPerks.put(Material.WOOD_SWORD, SmithingPerk.BASICSMITHING);
		skillPerks.put(Material.WOOD_HOE, SmithingPerk.BASICSMITHING);
		skillPerks.put(Material.GOLD_AXE, SmithingPerk.GOLDENSTRIKE);
		skillPerks.put(Material.GOLD_SPADE, SmithingPerk.GOLDENSTRIKE);
		skillPerks.put(Material.GOLD_SWORD, SmithingPerk.GOLDENSTRIKE);
		skillPerks.put(Material.GOLD_HOE, SmithingPerk.GOLDENSTRIKE);
		skillPerks.put(Material.STONE_AXE, SmithingPerk.STONIFICATION);
		skillPerks.put(Material.STONE_SPADE, SmithingPerk.STONIFICATION);
		skillPerks.put(Material.STONE_SWORD, SmithingPerk.STONIFICATION);
		skillPerks.put(Material.STONE_HOE, SmithingPerk.STONIFICATION);
		skillPerks.put(Material.IRON_AXE, SmithingPerk.IRONLEGACY);
		skillPerks.put(Material.IRON_AXE, SmithingPerk.IRONLEGACY);
		skillPerks.put(Material.IRON_AXE, SmithingPerk.IRONLEGACY);
		skillPerks.put(Material.IRON_AXE, SmithingPerk.IRONLEGACY);
		skillPerks.put(Material.DIAMOND_AXE, SmithingPerk.DIAMONDIFICATION);
		skillPerks.put(Material.DIAMOND_SPADE, SmithingPerk.DIAMONDIFICATION);
		skillPerks.put(Material.DIAMOND_SWORD, SmithingPerk.DIAMONDIFICATION);
		skillPerks.put(Material.DIAMOND_HOE, SmithingPerk.DIAMONDIFICATION);
		
		// Create hashmap of experience
		// Armor
		skillExp.put(Material.LEATHER_BOOTS,0);
		skillExp.put(Material.LEATHER_CHESTPLATE,0);
		skillExp.put(Material.LEATHER_HELMET,0);
		skillExp.put(Material.LEATHER_LEGGINGS,0);
		skillExp.put(Material.GOLD_BOOTS,30);
		skillExp.put(Material.GOLD_CHESTPLATE,30);
		skillExp.put(Material.GOLD_HELMET,30);
		skillExp.put(Material.GOLD_LEGGINGS,30);
		skillExp.put(Material.CHAINMAIL_BOOTS,50);
		skillExp.put(Material.CHAINMAIL_CHESTPLATE,50);
		skillExp.put(Material.CHAINMAIL_HELMET,50);
		skillExp.put(Material.CHAINMAIL_LEGGINGS,50);
		skillExp.put(Material.IRON_BOOTS,70);
		skillExp.put(Material.IRON_CHESTPLATE,70);
		skillExp.put(Material.IRON_HELMET,70);
		skillExp.put(Material.IRON_LEGGINGS,70);
		skillExp.put(Material.DIAMOND_BOOTS,90);
		skillExp.put(Material.DIAMOND_CHESTPLATE,90);
		skillExp.put(Material.DIAMOND_HELMET, 90);
		skillExp.put(Material.DIAMOND_LEGGINGS, 90);
		
		// Weapons
		skillExp.put(Material.WOOD_AXE, 13);
		skillExp.put(Material.WOOD_SPADE, 9);
		skillExp.put(Material.WOOD_SWORD, 12);
		skillExp.put(Material.WOOD_HOE, 12);
		skillExp.put(Material.GOLD_AXE, 30);
		skillExp.put(Material.GOLD_SPADE, 30);
		skillExp.put(Material.GOLD_SWORD, 30);
		skillExp.put(Material.GOLD_HOE, 30);
		skillExp.put(Material.STONE_AXE, 50);
		skillExp.put(Material.STONE_SPADE, 50);
		skillExp.put(Material.STONE_SWORD, 50);
		skillExp.put(Material.STONE_HOE, 50);
		skillExp.put(Material.IRON_AXE, 70);
		skillExp.put(Material.IRON_AXE, 70);
		skillExp.put(Material.IRON_AXE, 70);
		skillExp.put(Material.IRON_AXE, 70);
		skillExp.put(Material.DIAMOND_AXE, 90);
		skillExp.put(Material.DIAMOND_SPADE, 90);
		skillExp.put(Material.DIAMOND_SWORD, 90);
		skillExp.put(Material.DIAMOND_HOE, 90);
		
		// Setup perk level requirements
		perkLevels.put(SmithingPerk.BASICSMITHING, 0);
		perkLevels.put(SmithingPerk.GOLDENADVANCEMENTS, 30);
		perkLevels.put(SmithingPerk.CHAINING, 50);
		perkLevels.put(SmithingPerk.IRONPLATING, 70);
		perkLevels.put(SmithingPerk.DIAMONDWELDING, 90);
		perkLevels.put(SmithingPerk.GOLDENSTRIKE, 30);
		perkLevels.put(SmithingPerk.STONIFICATION, 50);
		perkLevels.put(SmithingPerk.IRONLEGACY, 70);
		perkLevels.put(SmithingPerk.DIAMONDIFICATION, 90);
		
		// Setup Dependencies of skilltree
		// Armor
		perkDependencies.put(SmithingPerk.BASICSMITHING, null);
		perkDependencies.put(SmithingPerk.GOLDENADVANCEMENTS, SmithingPerk.BASICSMITHING);
		perkDependencies.put(SmithingPerk.CHAINING, SmithingPerk.GOLDENADVANCEMENTS);
		perkDependencies.put(SmithingPerk.IRONPLATING, SmithingPerk.CHAINING);
		perkDependencies.put(SmithingPerk.DIAMONDWELDING, SmithingPerk.IRONPLATING);
		// Weapons
		perkDependencies.put(SmithingPerk.GOLDENSTRIKE, SmithingPerk.BASICSMITHING);
		perkDependencies.put(SmithingPerk.STONIFICATION, SmithingPerk.GOLDENSTRIKE);
		perkDependencies.put(SmithingPerk.IRONLEGACY, SmithingPerk.STONIFICATION);
		perkDependencies.put(SmithingPerk.DIAMONDIFICATION, SmithingPerk.IRONLEGACY);
	}
	
	public static Smithing getInstance() {
		return instance;
	}
	
	@Override
	public void onCraftEvent(CraftItemEvent event) {
		Player player = (Player) event.getWhoClicked();
		if(!hasPerk(this, skillPerks.get(event.getRecipe().getResult().getType()).toString().toLowerCase(), 1, player)){
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
		return "smithing";
	}
	
	@Override
	public void advancePerkLevel(String perkName, Player player) {
		// Player wants to advance a perk
		// First get wanted perk
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
		
		// Now check whether the player has all requirements
		SmithingPerk dependency = perkDependencies.get(perk);
		if(dependency != null){
			// Perk requirements
			if(!hasPerk(this, dependency.toString(), 1, player)){
				player.sendMessage("You haven't unlocked the necessary perks to unlock this perk");
				return;
			}
			// Level requirements
			if(perkLevels.get(perk) > settings.getPerkLevel(getSkillName(), perk.toString(), player)){
				player.sendMessage("I am not skilled enough in learning this. I might want to practice more.");
				return;
			}
		}
		// All clear, advance perk
		// Need to check level and add one if applicable
		setPerkLevel(perk.toString(), 1, player);		
	}

	@Override
	protected void setPerkLevel(String perkName, int perkLevel, Player player) {
		settings.getPerkLevel(getSkillName(), perkName, player);
	}
}
