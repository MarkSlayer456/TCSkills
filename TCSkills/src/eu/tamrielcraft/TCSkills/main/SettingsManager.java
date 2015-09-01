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

import eu.tamrielcraft.TCSkills.classes.Classes;
import eu.tamrielcraft.TCSkills.classes.Dwarf;
import eu.tamrielcraft.TCSkills.races.Argonian;
import eu.tamrielcraft.TCSkills.races.Breton;
import eu.tamrielcraft.TCSkills.races.DarkElves;
import eu.tamrielcraft.TCSkills.races.HighElves;
import eu.tamrielcraft.TCSkills.races.Imperials;
import eu.tamrielcraft.TCSkills.races.Khajiit;
import eu.tamrielcraft.TCSkills.races.Nords;
import eu.tamrielcraft.TCSkills.races.Orcs;
import eu.tamrielcraft.TCSkills.races.Race;
import eu.tamrielcraft.TCSkills.races.RedGuard;
import eu.tamrielcraft.TCSkills.races.WoodElves;

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
 		if(getSave().get("argonians." + id) != null) {
 			return Argonian.getInstance();
 		} else if(getSave().get("bretons." + id) != null) {
 			return Breton.getInstance();
 		} else if(getSave().get("darkelves." + id) != null) {
 			return DarkElves.getInstance();
 		} else if(getSave().get("highelves." + id) != null) {
 			return HighElves.getInstance();
 		} else if(getSave().get("imperials." + id) != null) {
 			return Imperials.getInstance();
 		} else if(getSave().get("khajiits." + id) != null) {
 			return Khajiit.getInstance();
 		} else if(getSave().get("nords." + id) != null) {
 			return Nords.getInstance();
 		} else if(getSave().get("orcs." + id) != null) {
 			return Orcs.getInstance();
 		} else if(getSave().get("redguards." + id) != null) {
 			return RedGuard.getInstance();
 		} else if(getSave().get("woodelves." + id) != null) {
 			return WoodElves.getInstance();
 		} else {
 			return null;
 		}
     }
     
	public Classes getClass(Player player) {
    	 UUID id = player.getUniqueId();
    	 if(getSave().get(id + ".class").equals("dwarf")) {
    		 return Dwarf.getInstance();
    	 } else {
    		 return null;
    	 }
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

