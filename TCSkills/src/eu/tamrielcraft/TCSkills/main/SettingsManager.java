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
     
    public Boolean createPlayer(Player player, Race race, Classes classy) {
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
    	
    	
    	
    	//TOOD put required lvls next to each skill
    	//TODO move this somewhere else!
    	//SORRY ABOUT THE WIERD TABBING HERE I DID THIS SO I KNOW WHAT SKILLS UNLOCK WHAT
    	getSave().set("oneHanded.armsman.max", 5);
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
    	
    	
    	
    	getSave().set(id + ".skills.smithing", 15);
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
    	
    	return false;
	}
    
     public Race getRace(Player player) {
 		UUID id = player.getUniqueId();
 		if(getSave().getString(id + ".race") == null) {
 			return EmptyRace.getInstance();
 		}
 		
 		// Updates the player name to its latest TODO this gave me an error
 		/*if(!getSave().getString(id + ".name").equals(player.getName())){
 			getSave().set(id + ".name", player.getName());
 			saveSave();
 		}*/
 		
 		String race = getSave().getString(id + ".race");
 		if(race != null) {
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

