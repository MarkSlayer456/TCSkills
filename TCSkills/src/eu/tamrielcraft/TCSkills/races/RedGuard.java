package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class RedGuard extends Race{

	public ArrayList<UUID> RedGuard = new ArrayList<UUID>();
	
	static RedGuard instance = new RedGuard();
    
    public static RedGuard getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[RedGuard]" + s);
	}
    
    @Override
    public void sendWelcome(Player player){
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[RedGuard] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
    
	public String details() {
		return "Food doesn't drop below four and weapons do 5% more damage";
	}
	
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
		e.setDamage(e.getDamage() * 0.05 + e.getDamage());
	}
}
