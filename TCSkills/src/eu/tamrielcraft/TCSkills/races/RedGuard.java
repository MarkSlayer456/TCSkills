package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class RedGuard extends Race{

	public ArrayList<UUID> RedGuard = new ArrayList<UUID>();
	
	static RedGuard instance = new RedGuard();
    
    public static RedGuard getInstance() {
            return instance;
    }
    
    @Override
	public String raceName() {
		return "RedGuard";
	}

	@Override
	public String raceNameChat() {
		return "[" + raceName() +"]";
	}
	
	@Override
	public String formatChat(String s) {
		return s.replace(s, raceNameChat() + s);
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
	
	@Override
	public void playerEnchantEvent(PlayerLevelChangeEvent e) { }

	@Override
	public void potionThrowEvent(PotionSplashEvent e, Player player) { }

	@Override
	public void playerMoveEvent(PlayerMoveEvent e, Player player) {
	
		
	}

	@Override
	public void onPlayerJoinEvent(PlayerJoinEvent e, Player player) {
		
		
	}

	@Override
	public void playerBurnEvent(EntityDamageEvent e, Player player) { }
}
