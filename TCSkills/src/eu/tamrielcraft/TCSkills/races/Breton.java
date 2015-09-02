package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class Breton extends Race {
	
	public ArrayList<UUID> Breton = new ArrayList<UUID>();
	static Breton instance = new Breton();
    
    public static Breton getInstance() {
            return instance;
    }
	
	@Override
	public String formatChat(String s) {
		return s.replace(s, "[Breton]" + s);
	}
	
	@Override
    public void sendWelcome(Player player){
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Breton] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
	
	public String details() {
		return "50 points of extra magic";
	}
	
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
			
	}
}
