package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class Nord extends Race {

	public ArrayList<UUID> Nords = new ArrayList<UUID>();
	
	static Nord instance = new Nord();
    
    public static Nord getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[Nord]" + s);
	}
    
    @Override
    public void sendWelcome(Player player){
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Nord] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
	
	public String details() {
		return "50% more resistance to negative potion effects";
	}
	
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
			
	}
}
