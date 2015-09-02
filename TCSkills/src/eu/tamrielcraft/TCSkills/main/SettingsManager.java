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

import eu.tamrielcraft.TCKills.Spells.FamiliarSummon;
import eu.tamrielcraft.TCKills.Spells.FastHealing;
import eu.tamrielcraft.TCKills.Spells.FireBall;
import eu.tamrielcraft.TCKills.Spells.FireRune;
import eu.tamrielcraft.TCKills.Spells.GolemSummon;
import eu.tamrielcraft.TCKills.Spells.IceBlast;
import eu.tamrielcraft.TCKills.Spells.IceRune;
import eu.tamrielcraft.TCKills.Spells.ShockRune;
import eu.tamrielcraft.TCKills.Spells.Spells;
import eu.tamrielcraft.TCKills.Spells.ThunderShock;
import eu.tamrielcraft.TCSkills.classes.Classes;
import eu.tamrielcraft.TCSkills.classes.Dwarf;
import eu.tamrielcraft.TCSkills.races.Argonian;
import eu.tamrielcraft.TCSkills.races.Breton;
import eu.tamrielcraft.TCSkills.races.DarkElf;
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
    
     public Race getRace(Player player) {
 		UUID id = player.getUniqueId();
 		if(getSave().getString(id + ".race") == null) {
 			getSave().set(id + ".race", null);
 		}
 		/*if(getSave().getString(id + ".race") == "argonian") {
 			return Argonian.getInstance();
 		} else if(getSave().getString(id + ".race") == "breton") {
 			return Breton.getInstance();
 		} else if(getSave().getString(id + ".race") == "darkelf") {
 			return DarkElf.getInstance();
 		} else if(getSave().getString(id + ".race") == "highelf") {
 			return HighElf.getInstance();
 		} else if(getSave().getString(id + ".race") == "imperial") {
 			return Imperial.getInstance();
 		} else if(getSave().getString(id + ".race") == "khajiit") {
 			return Khajiit.getInstance();
 		} else if(getSave().getString(id + ".race") == "nord") {
 			return Nord.getInstance();
 		} else if(getSave().getString(id + ".race") == "orc") {
 			return Orc.getInstance();
 		} else if(getSave().getString(id + ".race") == "redguard") {
 			return RedGuard.getInstance();
 		} else if(getSave().getString(id + ".race") == "woodelf") {
 			return WoodElf.getInstance();
 		}*/
 		
 		switch(getSave().getString(id + ".race")) {// TODO this gives an error so i had to use a else and if statement
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
 		case "":
 			return null;
 		}
 		return null;
     }

	public Classes getClass(Player player) { //TODO UPDATE
    	 UUID id = player.getUniqueId();
    	 if(getSave().get(id + ".class").equals("dwarf")) {
    		 return Dwarf.getInstance();
    	 } else {
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

