package eu.tamrielcraft.TCSkills.main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import eu.tamrielcraft.TCSkills.classes.Archer;
import eu.tamrielcraft.TCSkills.classes.Barbarian;
import eu.tamrielcraft.TCSkills.classes.Classes;
import eu.tamrielcraft.TCSkills.classes.Knight;
import eu.tamrielcraft.TCSkills.classes.Mage;
import eu.tamrielcraft.TCSkills.classes.Rogue;
import eu.tamrielcraft.TCSkills.races.Argonian;
import eu.tamrielcraft.TCSkills.races.Breton;
import eu.tamrielcraft.TCSkills.races.DarkElf;
import eu.tamrielcraft.TCSkills.races.EmptyRace;
import eu.tamrielcraft.TCSkills.races.HighElf;
import eu.tamrielcraft.TCSkills.races.Imperial;
import eu.tamrielcraft.TCSkills.races.Khajiit;
import eu.tamrielcraft.TCSkills.races.Nord;
import eu.tamrielcraft.TCSkills.races.Orc;
import eu.tamrielcraft.TCSkills.races.Race;
import eu.tamrielcraft.TCSkills.races.RedGuard;
import eu.tamrielcraft.TCSkills.races.WoodElf;
import eu.tamrielcraft.TCSkills.skills.OneHanded;
import eu.tamrielcraft.TCSkills.skills.Smithing;
import eu.tamrielcraft.TCSkills.skills.OneHanded.OneHandedPerk;
import eu.tamrielcraft.TCSkills.spells.FamiliarSummon;
import eu.tamrielcraft.TCSkills.spells.FastHealing;
import eu.tamrielcraft.TCSkills.spells.FireBall;
import eu.tamrielcraft.TCSkills.spells.FireRune;
import eu.tamrielcraft.TCSkills.spells.GolemSummon;
import eu.tamrielcraft.TCSkills.spells.IceBlast;
import eu.tamrielcraft.TCSkills.spells.IceRune;
import eu.tamrielcraft.TCSkills.spells.ShockRune;
import eu.tamrielcraft.TCSkills.spells.Spells;
import eu.tamrielcraft.TCSkills.spells.ThunderShock;

public class SettingsManager {
	 private SettingsManager() { }
     
     static SettingsManager instance = new SettingsManager();
     
     
     
    
     public static SettingsManager getInstance() {
             return instance;
     }
     
     Plugin p;
    
     FileConfiguration config;
     File cfile;
    
     FileConfiguration save;
     File sfile;
    
     public void playerLevelUp(Player player) {
    	addAbilityPoint(player);
     }
     
     public void setDefaults() {
    	 if(getConfig().get("enableAbilities") == null) {
    		 getConfig().set("enableAbilities", true);
        	 getConfig().set("enableMagic", true);
        	 getConfig().set("Header", "---===[TamerialCraft]===---");
        	 saveConfig();
    	 }
     }
     
     private boolean hasSkillPoint(Player player) {
    	 UUID id = player.getUniqueId();
    	 if(getSave().getInt(id + ".skillpoints") >= 1) {
    		 return true;
    	 }
    	 return false;
     }
     
     public void nextFavorite(Player player) {
    	 UUID id = player.getUniqueId();
    	 if(save.getInt(id + ".magic.favorites.on") >= 3) {
				save.set(id + ".magic.favorites.on", 0);
		 } else {
			 save.set(id + ".magic.favorites.on", save.getInt(id + ".magic.favorites.on") + 1);
		 }
    	 saveSave();
     }
     
     public void setFavSpell(String spellName, Player player, int numb) {
    	 UUID id = player.getUniqueId();
    	 save.set(id + ".magic.favorites.amount", save.getInt(id + ".magic.favorites.amount") + 1);
		 save.set(id + ".magic.favorites.holder." + save.getInt(id + ".magic.favorites.amount"), spellName);
		 saveSave();
     }
     
     public void setupFavs(String spellName, Player player) {
    	 UUID id = player.getUniqueId();
    	 save.set(id + ".magic.favorites.amount", 1);
		 save.set(id + ".magic.favorites.holder." + save.getInt(id +  ".magic.favorites.amount"), spellName);
		 save.set(id + ".magic.favorites.on", 0);
		 saveSave();
     }
     
     public void setSpell(String spellName, Player player) {
    	 UUID id = player.getUniqueId();
    	 switch(spellName) {
    	 case "fireball":
    		 save.set(id + ".magic.activeSpell", "fireball");
    	 case "iceblast":
    		 save.set(id + ".magic.activeSpell", "iceblast");
    	 case "fasthealing":
    		 save.set(id + ".magic.activeSpell", "fasthealing");
    	 case "golemsummon":
    		 save.set(id + ".magic.activeSpell", "golemsummon");
    	 case "thundershock":
    		 save.set(id + ".magic.activeSpell", "thundershock");
    	 case "familiarsummon":
    		 save.set(id + ".magic.activeSpell", "familiar");
    	 case "firerune":
    		 save.set(id + ".magic.activeSpell", "firerune");
    	 case "icerune":
    		 save.set(id + ".magic.activeSpell", "icerune");
    	 case "shockrune":
    		 save.set(id + ".magic.activeSpell", "shockrune");
    	 case "":
    		 save.set(id + ".magic.activeSpell", null);
    	 }
    	 saveSave();
     }     
     
     public Boolean abilitiesEnable() {
    	 if(getConfig().getBoolean("enableAbilites") == true) {
    		 return true;
    	 }
    	 return false;
     }
     
     public Boolean magicEnabled() {
    	 if(getConfig().getBoolean("enableMagic") == false) {
    		 return true;
    	 }
    	 return false;
     }
     
     public int getSkillLevel(Player player, String skillName) { //TODO
		return 100;
    	 
     }
     
     public boolean canUpgradePerk(String skillName, String perkName, Player player) {
    	 if(hasSkillPoint(player) != true) {
    		 player.sendMessage(ChatColor.RED + "You need a skill point to upgrade skills!");
    		 return false;
    	 }
    	 
    	 switch(skillName) {
    	 case "onehanded":
    		 int onehandedLevel = getSkillLevel(player, "onehanded");
    	 switch(perkName) {
    	 case "armsman":
    		 int armsmanLvl = getPerkLevel(skillName, "armsman", player);
  			 int armsmanMax = getPerkMaxLevel("armsman");
  			 
  			if(armsmanLvl == armsmanMax) {
  				player.sendMessage(ChatColor.RED + "This skill is maxed out!");
  				return false;
  			}
  			
  				switch(armsmanLvl) {
  				case 5:
  					if(onehandedLevel >= getRequiredLevel("onehanded", "armsman", 5)) {
  						return true;
  					}
  				case 4:
  					if(onehandedLevel >= getRequiredLevel("onehanded", "armsman", 4)) {
  						return true;
  					}
  				case 3:
  					if(onehandedLevel >= getRequiredLevel("onehanded", "armsman", 3)) {
  						return true;
  					}
  				case 2:
  					if(onehandedLevel >= getRequiredLevel("onehanded", "armsman", 2)) {
  						return true;
  					}
  				case 1:
  					if(onehandedLevel >= getRequiredLevel("onehanded", "armsman", 1)) {
  						OneHanded.hasPerk.put(OneHandedPerk.ARMSMAN, true); //TODO put this on all the slot 1 for every perk
  						return true;
  					}
  				}
  				break;
  				
  				/*if(armsmanLvl == 5 && armsmanLvl >= getRequiredLevel("onehanded", "armsman", 5)) {
  					
  				} else if(armsmanLvl == 4 && armsmanLvl >= getRequiredLevel("onehanded", "armsman", 3)) {
  					return true;
  				} else if(armsmanLvl == 3 && armsmanLvl >= getRequiredLevel("onehaned", "armsman", 2)) {
  					return true;
  				} else if(armsmanLvl == 2 && armsmanLvl >= getRequiredLevel("onehanded", "armsman", 1)) {
  					return true;
  				} else if(armsmanLvl == 1 && armsmanLvl >= getRequiredLevel("onehanded", "armsman", 0)) {
  					return true;
  				} else {
  					player.closeInventory();
  					player.sendMessage(ChatColor.RED + "You can't upgrade that perk!");
  				}
  			break;*/
    	 case "hackandslash":
    		 int hackandslashLvl = getPerkLevel(skillName, "hackandslash", player);
   			 int hackandslashMax = getPerkMaxLevel("hackandslash");
   			if(hackandslashLvl == hackandslashMax) {
   				player.sendMessage(ChatColor.RED + "This skill is maxed out!");
   				return false;
   			}
   			OneHandedPerk perk = OneHanded.perkDependencies.get(OneHandedPerk.HACKANDSLASH);
   			if(OneHanded.hasPerk.get(perk) == false) {
   				player.sendMessage(ChatColor.RED + "You do not have the required perks to unlock this perk!");
   				return false;
   			}
   			
   			switch(hackandslashLvl) {
   				case 3:
   					if(onehandedLevel >= getRequiredLevel("onehanded", "hackandslash", 3)) {
  						return true;
  					}
   				case 2:
   					if(onehandedLevel >= getRequiredLevel("onehanded", "hackandslash", 2)) {
  						return true;
  					}
   				case 1:
   					if(onehandedLevel >= getRequiredLevel("onehanded", "hackandslash", 1)) {
  						return true;
  					}
   					break;
   			}
   			
   			
   				/*if(hackandslashLvl == 3 && hackandslashLvl >= getRequiredLevel("onehanded", "hackandslash", 2) && hasSkillPoint(player)) {
   				 if(hackandslashLvl == 2 && hackandslashLvl >= getRequiredLevel("onehanded", "hackandslash", 1) && hasSkillPoint(player)) {
   					return true;
   				} else if(hackandslashLvl == 1 && hackandslashLvl >= getRequiredLevel("onehanded", "hackandslash", 0) && hasSkillPoint(player)) {
   					return true;
   				} else {
   					player.closeInventory();
   					player.sendMessage(ChatColor.RED + "You can't upgrade that perk!");
   				}
    		 
   			}*/
    	 }
    	 case "smithing":
    		 
    	 }
    	 return false;
 	}
 	
 	public void upgradePerk(String skillName, String perkName, Player player) {
 		UUID id = player.getUniqueId();
 		switch(skillName) {
 		case "onehanded":
 		switch(perkName) {
 		case "armsman":
 			getSave().set(id + ".skills.onehanded.armsman", getSave().getInt(id + ".skills.onehanded.armsman") + 1);
 		case "":
 		}
 		}
 		saveSave();
 	}
     
     
     public int getRequiredLevel(String skillName, String perkName, int perkNumber) {
    	 switch(skillName) {
    	 case "onehanded":
    		 switch(perkName) {
    		 case "armsman":
    		 	 return OneHanded.armsman.get(perkNumber);
    		 case "hackandslash":
    			 return OneHanded.hackandslash.get(perkNumber);
    		 case "bonebreaker":
    			 return OneHanded.bonebreaker.get(perkNumber);
    		 case "dualflurry":
    			 return OneHanded.dualflurry.get(perkNumber);
    		 case "dualsavagery":
    			 return OneHanded.dualsavagery.get(perkNumber);
    		 case "fightingstance":
    			 return OneHanded.fightingstance.get(perkNumber);
    		 case "savagestrike":
    			 return OneHanded.savagestrike.get(perkNumber);
    		 case "criticalcharge":
    			 return OneHanded.criticalcharge.get(perkNumber);
    		 case "paralyzingstrike":
    			 return OneHanded.paralyzingstrike.get(perkNumber);
    		 }
    	 case "smithing":
    		 	 
    	 }
    	 return 0;
     }
     
     public int getPerkLevel(String skillName, String perkName, Player player) {
    	 UUID id = player.getUniqueId();
    	 skillName = skillName.toLowerCase();
    	 perkName = perkName.toLowerCase();
    	 int perkLevel;
    	 try{
    		 perkLevel = getSave().getInt(id + ".skills." + skillName + "." + perkName);
    	 }
    	 catch(Exception e){
    		 perkLevel = 0;
    	 }
    	 return perkLevel;
     }
     
     public void setPerkLevel(String skillName, String perkName, Player player, int level){
    	 UUID id = player.getUniqueId();
    	 skillName = skillName.toLowerCase();
    	 perkName = perkName.toLowerCase();
    	 try{
    		 getSave().set(id + "." + skillName + "." + perkName, level);
    	 }
    	 catch(Exception e){
    		 player.sendMessage("Could not update perk level");
    	 }
     }
     
     public int getAbilityPoints(Player player){
    	 UUID id = player.getUniqueId();
    	 int abilityPoints = 0;
    	 try{
    		 getSave().get(id + ".AP");
    	 }
    	 catch(Exception e){
    		 player.sendMessage("Something went wrong with getting your ability points. Report this to an admin.");
    	 }
    	 return abilityPoints;
     }
     
     public void addAbilityPoint(Player player) {
    	 UUID id = player.getUniqueId();
    	 int currAP = 0;
    	 try{
    		 currAP = getSave().getInt(id + ".AP");
    		 getSave().set(id + ".AP", currAP + 1);
    		 saveSave();
    	 }
    	 catch(Exception e){
    		 // Player is not created so do nothing (TODO)
    	 }
    	 
    	 /*if(getSave().get(id + ".skillpoints") == null) {
    		 getSave().set(id + ".skillpoints", 1);
    		 return;
    	 } else {
    		 getSave().set(id + ".skillpoints", getSave().get(id + ".skillpoints"));
    		 return;
    	 }*/
     }
     
     //TODO should be moved to individual perks
     public int getPerkMaxLevel(String perkName) {
    	 return getSave().getInt("onehanded." + perkName + ".max");
     }
     
     public void setupPerks() { //NOTICE SHOULD ONLY BE CALLED ONE TIME!
    	 //TODO could put this in setdefaults!
     	//SORRY ABOUT THE WIERD TABBING HERE I DID THIS SO I KNOW WHAT SKILLS UNLOCK WHAT    	 
    	 
    	//TODO: this should be saved in the config file
    	//TODO Why? these are the default values for skyrim and it's setup to only work with 5...
    	 
    	 
     	getSave().set("onehanded.armsman.max", 5);
     	/*
     	 * lvls
     	 * 1: n/a
     	 * 2: 20
     	 * 3: 40
     	 * 4: 60
     	 * 5: 80
     	 */
     	
     		getSave().set("onehanded.hackandslash.max", 3);
     		/*
     		 * lvls
     		 * 1: 30
     		 * 2: 60
     		 * 3: 90
     		 */
     	
     		getSave().set("onehanded.bonebreaker.max", 3);
     		/*
     		 * lvls
     		 * 1: 30
     		 */
     	
     		getSave().set("onehanded.dualflurry.max", 3);
     		/*
     		 * lvls
     		 * 1: 30
     		 */
     			getSave().set("onehanded.dualsavagery.max", 1);
     			/*
     			 * lvls
     			 * 1: 70
     			 */
     	
     		getSave().set("onehanded.fightingstance.max", 1);
     		/*
     		 * lvls
     		 * 1: 20
     		 */
     			getSave().set("onehanded.savagestrike.max", 1);
     			/*
     			 * lvls
     			 * 1: 50
     			 */
     			getSave().set("onehanded.criticalcharge.max", 1);
     			/*
     			 * lvls
     			 * 1: 50
     			 */
     				getSave().set("onehanded.paralyzingstrike.max", 1); //need both tier 3 perks
     				/*
     				 * lvls
     				 * 1: 100
     				 */
     }
     
    public Boolean createPlayer(Player player, Race race, Classes classy){
    	UUID id = player.getUniqueId();
    	
    	try{
    		getSave().set(id + ".name", player.getName());
    		getSave().set(id + ".race", race.raceName().toLowerCase());
        	getSave().set(id + ".class", classy.className().toLowerCase());
        	getSave().set(id + ".AP", 0);
        	setSkills(id);
        	saveSave();
        	//return true;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    	
    	//TODO: Should only add the default ones. Rest should be added on upgrade
    	//ONE HANDED
    	getSave().set(id + ".skills.onehanded", 15);
    	
    	getSave().set(id + ".skills.onehanded.armsman", 0);
    	
    	getSave().set(id + ".skills.onehanded.hackandslash", 0);
    	
    	getSave().set(id + ".skills.onehanded.bonebreaker", 0);
    	
    	getSave().set(id + ".skills.onehanded.dualflurry", 0);
    
    	getSave().set(id + ".skills.onehanded.fightingstance", 0);
    	
    	getSave().set(id + ".skills.onehanded.bladesman", 0);
    	
    	getSave().set(id + ".skills.onehanded.dualsavagery", 0);
    	
    	getSave().set(id + ".skills.onehanded.savagestrike", 0);
    	
    	getSave().set(id + ".skills.onehanded.criticalcharge", 0);
    	
    	getSave().set(id + ".skills.onehanded.paralyzingstrike", 0);
    	
    	getSave().set(id + ".skills.blocking", 15);
    	getSave().set(id + ".skills.archery", 15);
    	getSave().set(id + ".skill.sneak", 15);
    	getSave().set(id + ".skills.lightarmor", 15);
    	getSave().set(id + ".skills.heavyarmor", 15);
    	getSave().set(id + ".skills.Illusion", 15);    	
    	return true;
	}
    
    public void updatePlayerName(Player player){
    	// Updates the player name to its latest
    	UUID id = player.getUniqueId();
    	if(!getSave().getString(id + ".name").equals(player.getName())){
    		getSave().set(id + ".name", player.getName());
    	 	saveSave();
    	}
    }
    
    private void setSkills(UUID id){
    	getSave().set(id + ".skills." + Smithing.getInstance().getSkillName() + ".exp", 0);
    	getSave().set(id + ".skills." + Smithing.getInstance().getSkillName() + "." + Smithing.SmithingPerk.BASICSMITHING.toString().toLowerCase(), 1);
    }
    
     public Race getRace(Player player) {
 		UUID id = player.getUniqueId();
 		if(getSave().getString(id + ".race") == null) {
 			// This way no player record is created but the plugin doesn't crash
 			return EmptyRace.getInstance();
 		}
 		
 		String race = getSave().getString(id + ".race").toLowerCase();
 		if(race != null){
 			switch(race) {
 	 		case "argonian":
 	 			return Argonian.getInstance();
 	 		case "breton":
 	 			return Breton.getInstance();
 	 		case "darkelf":
 	 			return DarkElf.getInstance();
 	 		case "highelf":
 	 			return HighElf.getInstance();
 	 		case "imperial":
 	 			return Imperial.getInstance();
 	 		case "khajiit":
 	 			return Khajiit.getInstance();
 	 		case "nord":
 	 			return Nord.getInstance();
 	 		case "orcs":
 	 			return Orc.getInstance();
 	 		case "redguard":
 	 			return RedGuard.getInstance();
 	 		case "woodelf":
 	 			return WoodElf.getInstance();
 	 		case "empty":
 	 			return EmptyRace.getInstance();
 	 		default:
 	 			return EmptyRace.getInstance();
 	 		}
  		} else {
  			return null;
  		}
     }

	public Classes getClass(Player player) {
    	 UUID id = player.getUniqueId();
    	 
    	 switch (getSave().getString(id + ".class")){
    	 case "archer":
    		 return Archer.getInstance();
    	 case "barbarian":
    		 return Barbarian.getInstance();
    	 case "knight":
    		 return Knight.getInstance();
    	 case "mage":
    		 return Mage.getInstance();
    	 case "Rogue":
    		 return Rogue.getInstance();
    	 default:
    		 return null;
    	 }
     }
	
	public void setSkillExp(Player player, String skillName, int amount){
		UUID id = player.getUniqueId();
		getSave().set(id + ".skills." + skillName + ".exp", amount);
		saveSave();
	}
	
	public Integer getSkillExp(String skillName, Player player){
		UUID id = player.getUniqueId();
		return getSave().getInt(id + ".skills." + skillName + ".exp");
	}
	
	public Integer getMagic(Player player) {
		UUID id = player.getUniqueId();
		return getSave().getInt(id + ".magic.amount");
		
	}
	
	public Spells getSpell(Player player) {
		UUID id = player.getUniqueId();
		if(getSave().get(id + "magic.activeSpell") == null) {
			getSave().set(id + ".magic.activeSpell", null);
			return null;
		}
		if(getSave().get(id + ".magic.activeSpell") == "familiarsummon") {
			return FamiliarSummon.getInstance();
		} else if(getSave().get(id + ".magic.activeSpell") == "fasthealing") {
			return FastHealing.getInstance();
		} else if(getSave().get(id + ".magic.activeSpell") == "fireball") {
			return FireBall.getInstance();
		} else if(getSave().get(id + ".magic.activeSpell") == "firerune") {
			return FireRune.getInstance();
		} else if(getSave().get(id + ".magic.activeSpell") == "golemsummon") {
			return GolemSummon.getInstance();
		} else if(getSave().get(id + ".magic.activeSpell") == "iceblast") {
			return IceBlast.getInstance();
		} else if(getSave().get(id + ".magic.activeSpell") == "icerune") {
			return IceRune.getInstance();
		} else if(getSave().get(id + ".magic.activeSpell") == "shockrune") {
			return ShockRune.getInstance();
		} else if(getSave().get(id + ".magic.activeSpell") == "thundershock") {
			return ThunderShock.getInstance();
		}
		
		
		/*switch(Settings.get(id + ".magic.activeSpell")) { TODO also created errors
		case "familiarsummon":
			return FamiliarSummon.getInstance();
		case "fasthealing":
			return FastHealing.getInstance();
		case "fireball":
			return FireBall.getInstance();
		case "firerune":
			return FireRune.getInstance();
		case "golemsummon":
			return GolemSummon.getInstance();
		case "iceblast":
			return IceBlast.getInstance();
		case "icerune":
			return IceRune.getInstance();
		case "shockrune":
			return ShockRune.getInstance();
		case "thundershock":
			return ThunderShock.getInstance();
		}*/
		return null;
	}
	
	public boolean hasMagic(Player player) {
		UUID id = player.getUniqueId();
		if(getSave().getBoolean(id + "magic.hasMagic") == true) {
			return true;
		}	
		return false;
	} 
     
     
     public void setup(Plugin p) {
             cfile = new File(p.getDataFolder(), "config.yml");
             config = p.getConfig();
             config.options().copyDefaults(false);
             saveConfig();
            
             if (!p.getDataFolder().exists()) {
                     p.getDataFolder().mkdir();
             }
            
             sfile = new File(p.getDataFolder(), "save.yml");
            
             if (!sfile.exists()) {
                     try {
                             sfile.createNewFile();
                     }
                     catch (IOException e) {
                             Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create save.yml!");
                     }
             }
            
             save = YamlConfiguration.loadConfiguration(sfile);
             
             if(!p.getDataFolder().exists()) {
             	p.getDataFolder().mkdir();
             }
             
             if (!sfile.exists()) {
                 try {
                         sfile.createNewFile();
                 }
                 catch (IOException e) {
                         Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create save.yml!");
                 }
         }
             
     }
     
     public FileConfiguration getSave() {
             return save;
     }
    
     public void saveSave() {
             try {
                     save.save(sfile);
             }
             catch (IOException e) {
                     Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save save.yml!");
             }
     }
    
     public void reloadSave() {
            save = YamlConfiguration.loadConfiguration(sfile);
     }
    
     public FileConfiguration getConfig() {
             return config;
     }
    
     public void saveConfig() {
             try {
                    config.save(cfile);
             }
             catch (IOException e) {
                     Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
             }
     }
    
     public void reloadConfig() {
             config = YamlConfiguration.loadConfiguration(cfile);
     }
    
     public PluginDescriptionFile getDesc() {
             return p.getDescription();
     }
}

