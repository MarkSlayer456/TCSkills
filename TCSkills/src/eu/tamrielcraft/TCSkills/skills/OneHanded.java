package eu.tamrielcraft.TCSkills.skills;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.Plugin;


public class OneHanded extends Skill {
	
	private static OneHanded instance = new OneHanded();
	
	Map<Material, OneHandedPerk> skillperks;
	Map<OneHandedPerk, Integer> perkLevels;
	public static Map<OneHandedPerk, OneHandedPerk> perkDependencies;
	
	public static Map<OneHandedPerk, Boolean> hasPerk;
	
	public static Map<Integer, Integer> armsman;
	public static Map<Integer, Integer> hackandslash;
	public static Map<Integer, Integer> bonebreaker;
	public static Map<Integer, Integer> dualflurry;
	public static Map<Integer, Integer> dualsavagery;
	public static Map<Integer, Integer> fightingstance;
	public static Map<Integer, Integer> savagestrike;
	public static Map<Integer, Integer> criticalcharge;
	public static Map<Integer, Integer> paralyzingstrike;
	
	
	public static OneHanded getInstance() {
		return instance;
	}
	
	public enum OneHandedPerk {
		ARMSMAN, HACKANDSLASH, BONEBREAKER, DUALFLURRY, DUALSAVAGERY, FIGHTINGSTANCE,
		SAVAGESTRIKE, CRITICALCHARGE, PARALYZINGSTRIKE
	}
	
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
		
		//Level dependencies
		armsman.put(1, 0);
		armsman.put(2, 20);
		armsman.put(3, 40);
		armsman.put(4, 60);
		armsman.put(5, 80);
		
		hackandslash.put(1, 30);
		hackandslash.put(2, 60);
		hackandslash.put(3, 90);
		
		bonebreaker.put(1, 30);
		
		dualflurry.put(1, 30);
		
		dualsavagery.put(1, 70);
		
		fightingstance.put(1, 20);
		
		savagestrike.put(1, 50);
		
		criticalcharge.put(1, 50);
		
		paralyzingstrike.put(1, 100);
		
		
		
		
		
		
		//Perk dependencies
		perkDependencies.put(OneHandedPerk.ARMSMAN, null);
		perkDependencies.put(OneHandedPerk.HACKANDSLASH, OneHandedPerk.ARMSMAN);
		perkDependencies.put(OneHandedPerk.BONEBREAKER, OneHandedPerk.ARMSMAN);
		perkDependencies.put(OneHandedPerk.DUALFLURRY, OneHandedPerk.ARMSMAN);
		perkDependencies.put(OneHandedPerk.DUALFLURRY, OneHandedPerk.DUALSAVAGERY);
		perkDependencies.put(OneHandedPerk.FIGHTINGSTANCE, OneHandedPerk.ARMSMAN);
		perkDependencies.put(OneHandedPerk.SAVAGESTRIKE, OneHandedPerk.FIGHTINGSTANCE);
		perkDependencies.put(OneHandedPerk.CRITICALCHARGE, OneHandedPerk.FIGHTINGSTANCE);
		perkDependencies.put(OneHandedPerk.CRITICALCHARGE, OneHandedPerk.PARALYZINGSTRIKE); //TODO this one has 2 perk dependencies so what should i do?
		
		
		
	}
	

	@Override
	public String getSkillName() {
		return "OneHanded";
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
