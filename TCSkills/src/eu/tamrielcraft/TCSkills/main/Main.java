package eu.tamrielcraft.TCSkills.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import eu.tamrielcraft.TCSkills.event.Commands;
import eu.tamrielcraft.TCSkills.event.EventListener;
import eu.tamrielcraft.TCSkills.event.LeavingListener;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;

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
		this.getCommand("race").setExecutor(new Commands(this, settings));
		this.getCommand("r").setExecutor(new Commands(this, settings));
		this.getCommand("favorite").setExecutor(new Commands(this, settings));
		this.getCommand("fav").setExecutor(new Commands(this, settings));
		this.getCommand("spell").setExecutor(new Commands(this, settings));
		this.getCommand("s").setExecutor(new Commands(this, settings));
		this.getCommand("magicregen").setExecutor(new Commands(this, settings));
		this.getCommand("mr").setExecutor(new Commands(this, settings));
		this.getCommand("class").setExecutor(new Commands(this, settings));
		this.getCommand("c").setExecutor(new Commands(this, settings));
		
		settings.getConfig().addDefault("Header", "---===[TamerialCraft]===---");
		settings.getConfig().addDefault("enableSpells", true);
		plugin = this;
		
		if(Bukkit.getPluginManager().isPluginEnabled(("PlaceholderAPI"))){
			addPlaceholderHooks();
		}
	}
	
	public void onDisable() {
	}
	
	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	private void addPlaceholderHooks(){
		int counter = 0;
		
		boolean hooked = PlaceholderAPI.registerPlaceholderHook(this, new PlaceholderHook() {
            @Override
            public String onPlaceholderRequest(Player p, String identifier) {
                // placeholder: %tcskills_identifier%
                if (identifier.equals("race")) {
                    return settings.getRace(p).raceNameChat();
                }
                return null;
            }
        });

		if (hooked) {
			counter++;
		}
		
		hooked = PlaceholderAPI.registerPlaceholderHook(this, new PlaceholderHook() {
            @Override
            public String onPlaceholderRequest(Player p, String identifier) {
                // placeholder: %tcskills_identifier%
                if (identifier.equals("class")) {
                    return settings.getClass(p).classNameChat();
                }
                return null;
            }
        });

		if (hooked) {
			counter++;
		}
		
		// Finally, report to console
		if(counter > 0){
			getLogger().info("Hooked into PlaceholderAPI and registered " + counter + " placeholder(s)");
		}
	}
	
}
