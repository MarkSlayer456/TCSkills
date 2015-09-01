package eu.tamrielcraft.TCSkills.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import eu.tamrielcraft.TCSkills.event.EventListener;
import eu.tamrielcraft.TCSkills.event.LeavingListener;

public class Main extends JavaPlugin implements Listener {
	Logger logger = Logger.getLogger("Minecraft");
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	//TODO add groups so summons don't attack certain players!
	//TODO add usable scroll spells
	
	public static Main plugin;
	
	public void onEnable() {
		settings.setup(this);
		settings.getConfig().options().copyDefaults(false);
		settings.saveConfig();
		settings.getSave().options().copyDefaults(false);
		settings.saveSave();
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new Magic(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new LeavingListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EventListener(this, settings), this);
		
		getLogger().info("Enabling Elder Scroll Races");
		settings.getConfig().addDefault("Header", "---===[TamerialCraft]===---");
		plugin = this;
	}
	
	public void onDisable() {
		getLogger().info("Disabling Elder Scroll Races");
	}
	
	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	ArrayList<String> sneaking = new ArrayList<String>();
	ArrayList<String> sneakCoolDown = new ArrayList<String>();
	ArrayList<String> sneakMessage = new ArrayList<String>();
	
	ArrayList<Block> blocks = new ArrayList<Block>();
	final HashMap<BlockState, Material> material = new HashMap<BlockState, Material>();
	
}
