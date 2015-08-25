package eu.tamrielcraft.TCSkills.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import eu.tamrielcraft.TCSkills.event.EventListener;
import eu.tamrielcraft.TCSkills.event.LeavingListener;
import eu.tamrielcraft.TCSkills.races.Argonian;
import eu.tamrielcraft.TCSkills.races.Breton;
import eu.tamrielcraft.TCSkills.races.DarkElves;
import eu.tamrielcraft.TCSkills.races.HighElves;
import eu.tamrielcraft.TCSkills.races.Imperials;
import eu.tamrielcraft.TCSkills.races.Khajiit;
import eu.tamrielcraft.TCSkills.races.Nords;
import eu.tamrielcraft.TCSkills.races.Orcs;
import eu.tamrielcraft.TCSkills.races.RedGuard;
import eu.tamrielcraft.TCSkills.races.WoodElves;

public class Main extends JavaPlugin implements Listener {
	Logger logger = Logger.getLogger("Minecraft");
	
	static SettingsManager settings = SettingsManager.getInstance(); //this should still work even if private make private from now on!
	
	//TODO add groups so summons don't attack certain players!
	//TODO add usable scroll spells
	
	public static Main plugin;
	
	public void onEnable() {
		settings.setup(this);
		settings.getConfig().options().copyDefaults(false);
		settings.saveConfig();
		settings.getRaces().options().copyDefaults(false);
		settings.saveRaces();
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
