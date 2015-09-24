package eu.tamrielcraft.TCSkills.skills;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.entity.*;

public class Smithing extends Skill {

	Map<Material,Integer> skillLevels, skillExp;
	Map<Entity,Integer> test;
	
	private static Smithing instance = new Smithing();
	
	private Smithing(){
		// Create hashmap of skillLevels
		skillLevels = new HashMap<Material,Integer>();
		skillExp = new HashMap<Material,Integer>();
		
		// Armor
		skillLevels.put(Material.LEATHER_BOOTS,0);
		skillLevels.put(Material.LEATHER_CHESTPLATE,0);
		skillLevels.put(Material.LEATHER_HELMET,0);
		skillLevels.put(Material.LEATHER_LEGGINGS,0);
		skillLevels.put(Material.CHAINMAIL_BOOTS,50);
		skillLevels.put(Material.CHAINMAIL_CHESTPLATE,50);
		skillLevels.put(Material.CHAINMAIL_HELMET,50);
		skillLevels.put(Material.CHAINMAIL_LEGGINGS,50);
		skillLevels.put(Material.IRON_BOOTS,70);
		skillLevels.put(Material.IRON_CHESTPLATE,70);
		skillLevels.put(Material.IRON_HELMET,70);
		skillLevels.put(Material.IRON_LEGGINGS,70);
		skillLevels.put(Material.GOLD_BOOTS,30);
		skillLevels.put(Material.GOLD_CHESTPLATE,30);
		skillLevels.put(Material.GOLD_HELMET,30);
		skillLevels.put(Material.GOLD_LEGGINGS,30);
		skillLevels.put(Material.DIAMOND_BOOTS,90);
		skillLevels.put(Material.DIAMOND_CHESTPLATE,90);
		skillLevels.put(Material.DIAMOND_HELMET, 90);
		skillLevels.put(Material.DIAMOND_LEGGINGS, 90);
		
		// Weapons
		skillLevels.put(Material.WOOD_AXE, 0);
		skillLevels.put(Material.WOOD_SPADE, 0);
		skillLevels.put(Material.WOOD_SWORD, 0);
		skillLevels.put(Material.WOOD_HOE, 0);
		skillLevels.put(Material.GOLD_AXE, 30);
		skillLevels.put(Material.GOLD_SPADE, 30);
		skillLevels.put(Material.GOLD_SWORD, 30);
		skillLevels.put(Material.GOLD_HOE, 30);
		skillLevels.put(Material.STONE_AXE, 50);
		skillLevels.put(Material.STONE_SPADE, 50);
		skillLevels.put(Material.STONE_SWORD, 50);
		skillLevels.put(Material.STONE_HOE, 50);
		skillLevels.put(Material.IRON_AXE, 70);
		skillLevels.put(Material.IRON_AXE, 70);
		skillLevels.put(Material.IRON_AXE, 70);
		skillLevels.put(Material.IRON_AXE, 70);
		skillLevels.put(Material.DIAMOND_AXE, 90);
		skillLevels.put(Material.DIAMOND_SPADE, 90);
		skillLevels.put(Material.DIAMOND_SWORD, 90);
		skillLevels.put(Material.DIAMOND_HOE, 90);
		
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
	}
	
	public static Smithing getInstance() {
		return instance;
	}
	
	@Override
	public void onCraftEvent(CraftItemEvent event) {
		if(getLevel(settings.getSmithingExp((Player)event.getWhoClicked())) < skillLevels.get(event.getRecipe().getResult().getType())){
			event.getWhoClicked().sendMessage("I don't know how to craft this");
			event.setCancelled(true);
		}else{
			// Player is able to craft this, continue and add experience
			int expGained = skillExp.get(event.getRecipe().getResult().getType());
			settings.setSkill((Player) event.getWhoClicked(), getSkillName(), settings.getSmithingExp((Player)event.getWhoClicked()) + expGained);
			event.getWhoClicked().sendMessage("You gained " + expGained + " experience towards " + getSkillName());
			
			//TODO: add more messages and display level,...
		}
		
	}

	@Override
	public String getSkillName() {
		return "smithing";
	}
}
