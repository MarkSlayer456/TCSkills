package eu.tamrielcraft.TCSkills.races;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class DarkElf extends Race implements Listener {
	static SettingsManager settings = SettingsManager.getInstance();
	
	static DarkElf instance = new DarkElf();
    
    public static DarkElf getInstance() {
            return instance;
    }
	
	@Override
	public String formatChat(String s) {
		return s.replace(s, "[DarkElf]" + s);
	}
	
	@Override
    public void sendWelcome(Player player){
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[DarkElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
	
	public static String details() {
		return "50% resistance to fire and lava";
		
	}
	
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
			
	}
}
