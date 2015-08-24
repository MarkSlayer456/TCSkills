package eu.tamrielcraft.TCSkills.main;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {
	 private SettingsManager() { }
     
     static SettingsManager instance = new SettingsManager();
    
     public static SettingsManager getInstance() {
             return instance;
     }
    
     Plugin p;
    
     FileConfiguration config;
     File cfile;
    
     FileConfiguration races;
     File rfile;
    
     public void setup(Plugin p) {
             cfile = new File(p.getDataFolder(), "config.yml");
             config = p.getConfig();
             config.options().copyDefaults(false);
             saveConfig();
            
             if (!p.getDataFolder().exists()) {
                     p.getDataFolder().mkdir();
             }
            
             rfile = new File(p.getDataFolder(), "races.yml");
            
             if (!rfile.exists()) {
                     try {
                             rfile.createNewFile();
                     }
                     catch (IOException e) {
                             Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create races.yml!");
                     }
             }
            
             races = YamlConfiguration.loadConfiguration(rfile);
             
             if(!p.getDataFolder().exists()) {
             	p.getDataFolder().mkdir();
             }
             
             if (!rfile.exists()) {
                 try {
                         rfile.createNewFile();
                 }
                 catch (IOException e) {
                         Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create races.yml!");
                 }
         }
             
     }
     
     public FileConfiguration getRaces() {
             return races;
     }
    
     public void saveRaces() {
             try {
                     races.save(rfile);
             }
             catch (IOException e) {
                     Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save races.yml!");
             }
     }
    
     public void reloadRaces() {
            races = YamlConfiguration.loadConfiguration(rfile);
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

