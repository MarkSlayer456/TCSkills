package eu.tamrielcraft.TCSkills.skills;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.Plugin;


import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class OneHanded extends Skill {

	
	public static SettingsManager settings = SettingsManager.getInstance();
	
	@Override
	public void onCraftEvent(CraftItemEvent event) { }

	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
		
		
		
	}
	
	Map<String, Integer> skillExp = new HashMap<String, Integer>();
	Map<Material, Integer> skillLevels = new HashMap<Material, Integer>();
	
	
	private OneHanded() {
		//Weapons
		skillLevels.put(Material.WOOD_AXE, 0);
		skillLevels.put(Material.WOOD_SPADE, 0);
		skillLevels.put(Material.WOOD_SWORD, 0);
		skillLevels.put(Material.GOLD_AXE, 30);
		skillLevels.put(Material.GOLD_SPADE, 30);
		skillLevels.put(Material.GOLD_SWORD, 30);
		skillLevels.put(Material.STONE_AXE, 50);
		skillLevels.put(Material.STONE_SPADE, 50);
		skillLevels.put(Material.STONE_SWORD, 50);
		skillLevels.put(Material.IRON_AXE, 70);
		skillLevels.put(Material.IRON_AXE, 70);
		skillLevels.put(Material.IRON_AXE, 70);
		skillLevels.put(Material.IRON_AXE, 70);
		skillLevels.put(Material.DIAMOND_AXE, 90);
		skillLevels.put(Material.DIAMOND_SPADE, 90);
		skillLevels.put(Material.DIAMOND_SWORD, 90);
		
		//XP
		skillExp.put("zombie", 5);
		skillExp.put("skeleton", 5);
		skillExp.put("spider", 5);
		skillExp.put("creeper", 5);
		skillExp.put("witch", 5);
		skillExp.put("enderman", 10);
		skillExp.put("slime", 10);
	}
	

	@Override
	public String getSkillName() {
		
		return "OneHanded";
	}
	
	
	public static boolean canUpgradePerk(String perkName, Player player) {
		UUID id = player.getUniqueId();
		
		
		if(perkName.equalsIgnoreCase("armsman")) {
			int skillLvl = settings.getSave().getInt(id + ".skills.onehanded.armsman");
			int skillMax = settings.getSave().getInt(id + ".skills.onehanded.armsman");
			
			if(skillLvl < skillMax) { //TODO ALSO ADD A LVL CHECK HERE!
				if(settings.getSave().getInt(id + ".skills.onehanded.armsman") == 0) {
					
				} else if(settings.getSave().getInt(id + ".skills.onehanded.armsman") == 1) {
					
				} else if(settings.getSave().getInt(id + ".skills.onehanded.armsman") == 2) {
					
				} else if(settings.getSave().getInt(id + ".skills.onehanded.armsman") == 3) {
					
				} else if(settings.getSave().getInt(id + ".skills.onehanded.armsman") == 4) {
					
				} else if(settings.getSave().getInt(id + ".skills.onehanded.armsman") == 5) {
					
				}
				return true;
			}
			return true;
		}	
		return false;
	}
	
	public static void upgradePerk(String perkName, Player player) {
		UUID id = player.getUniqueId();
		if(perkName.equalsIgnoreCase("armsman")) {
		settings.getSave().set(id + ".skills.onehanded.armsman", settings.getSave().getInt(id + ".skills.onehanded.armsman") + 1);
		} else if(perkName.equalsIgnoreCase("")) {
			
		}
		settings.saveSave();
	}
	
	
	
}
