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

import eu.tamrielcraft.TCKills.spells.FamiliarSummon;
import eu.tamrielcraft.TCKills.spells.FastHealing;
import eu.tamrielcraft.TCKills.spells.FireBall;
import eu.tamrielcraft.TCKills.spells.FireRune;
import eu.tamrielcraft.TCKills.spells.GolemSummon;
import eu.tamrielcraft.TCKills.spells.IceBlast;
import eu.tamrielcraft.TCKills.spells.IceRune;
import eu.tamrielcraft.TCKills.spells.ShockRune;
import eu.tamrielcraft.TCKills.spells.Spells;
import eu.tamrielcraft.TCKills.spells.ThunderShock;
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
import eu.tamrielcraft.TCSkills.skills.Smithing;

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
    	addSkillPoint(player);
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
     
     public void addSkillPoint(Player player) {
    	 UUID id = player.getUniqueId();
    	 if(getSave().get(id + ".skillpoints") == null) {
    		 getSave().set(id + ".skillpoints", 1);
    		 return;
    	 } else {
    		 getSave().set(id + ".skillpoints", getSave().get(id + ".skillpoints"));
    		 return;
    	 }
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
     
     public boolean canUpgradePerk(String skillName, String perkName, Player player) {
    	 switch(perkName) {
    	 case "armsman":
    		 int armsmanLvl = getPerkLevel(skillName, "armsman", player);
  			 int armsmanMax = getPerkMaxLevel("armsman");
  			
  			if(armsmanLvl < armsmanMax) {
  				if(armsmanLvl == 4 && armsmanLvl >= getRequiredLevel("armsman", 4) && hasSkillPoint(player)) {
  					
  				} else if(armsmanLvl == 3 && armsmanLvl >= getRequiredLevel("armsman", 3) && hasSkillPoint(player)) {
  					return true;
  				} else if(armsmanLvl == 2 && armsmanLvl >= getRequiredLevel("armsman", 2) && hasSkillPoint(player)) {
  					return true;
  				} else if(armsmanLvl == 1 && armsmanLvl >= getRequiredLevel("armsman", 1) && hasSkillPoint(player)) {
  					return true;
  				} else if(armsmanLvl == 0 && armsmanLvl >= getRequiredLevel("armsman", 0) && hasSkillPoint(player)) {
  					return true;
  				} else {
  					player.closeInventory();
  					player.sendMessage(ChatColor.RED + "You can't upgrade that perk!");
  				}
  			}
  			break;
    	 case "hackandslash":
    		 int hackandslashLvl = getPerkLevel(skillName, "hackandslash", player);
   			 int hackandslashMax = getPerkMaxLevel("hackandslash");
   			
   			if(hackandslashLvl < hackandslashMax) {
   				if(hackandslashLvl == 2 && hackandslashLvl >= getRequiredLevel("hackandslash", 2) && hasSkillPoint(player)) {
   				 if(hackandslashLvl == 1 && hackandslashLvl >= getRequiredLevel("hackandslash", 1) && hasSkillPoint(player)) {
   					return true;
   				} else if(hackandslashLvl == 0 && hackandslashLvl >= getRequiredLevel("hackandslash", 0) && hasSkillPoint(player)) {
   					return true;
   				} else {
   					player.closeInventory();
   					player.sendMessage(ChatColor.RED + "You can't upgrade that perk!");
   				}
    		 
   			}
    	 }
    	 }
    	 return false;
 	}
 	
 	public void upgradePerk(String perkName, Player player) {
 		UUID id = player.getUniqueId();
 		if(perkName.equalsIgnoreCase("armsman")) {
 		getSave().set(id + ".skills.onehanded.armsman", getSave().getInt(id + ".skills.onehanded.armsman") + 1);
 		} else if(perkName.equalsIgnoreCase("")) {
 			
 		}
 		saveSave();
 	}
     
     
     public int getRequiredLevel(String perkName, int perkNumber) {
    	 if(perkName.equalsIgnoreCase("armsman")) {
    		 if(perkNumber == 0) {
    			 return 0;
    		 } else if(perkNumber == 1) {
    			 return 20;
    		 } else if(perkNumber == 2) {
    			 return 40;
    		 } else if(perkNumber == 3) {
    			 return 60;
    		 } else if(perkNumber == 4) {
    			 return 80;
    		 }
    	 }
    	 return 0;
     }
     
     public int getPerkLevel(String skillName, String perkName, Player player) {
    	 UUID id = player.getUniqueId();
    	 int perkLevel;
    	 try{
    		 perkLevel = getSave().getInt(id + ".skills." + skillName + "." + perkName);
    	 }
    	 catch(Exception e){
    		 perkLevel = 0;
    	 }
    	 return perkLevel;
     }
     
     public int getPerkMaxLevel(String perkName) {
    	 return getSave().getInt("onehanded." + perkName + ".max");
     }
     
     public void setupPerks() { //NOTICE SHOULD ONLY BE CALLED ONE TIME!
    	//TODO put required lvls next to each skill
     	//TODO move this somewhere else!
     	//SORRY ABOUT THE WIERD TABBING HERE I DID THIS SO I KNOW WHAT SKILLS UNLOCK WHAT
    	 
    	//TODO: this should be saved in the config file
    	 
    	 
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
     			getSave().set("onehanded.daulsavagery.max", 1);
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
    	
    	/*
    	 * TODO
    	 * aliteration
    	 * restoration
    	 * conjuration
    	 * destrcution
    	 * lockpicking
    	 * alchemy
    	 */
    	
    	try{
    		getSave().set(id + ".name", player.getName());
    		getSave().set(id + ".race", race.raceName().toLowerCase());
        	getSave().set(id + ".class", classy.className().toLowerCase());
        	setSkills(id);
        	saveSave();
        	return true;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		return false;
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
 		
 		// Updates the player name to its latest 
 		//TODO: Additional test required as it gives an error (Mark)
 		/*if(!getSave().getString(id + ".name").equals(player.getName())){
 			getSave().set(id + ".name", player.getName());
 			saveSave();
 		}*/
 		
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

