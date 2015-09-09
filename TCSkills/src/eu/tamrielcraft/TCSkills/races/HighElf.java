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

public class HighElf extends Race{

	public ArrayList<UUID> HighElves = new ArrayList<UUID>();
	
	static HighElf instance = new HighElf();
    
    public static HighElf getInstance() {
            return instance;
    }
    
    @Override
	public String raceName() {
		return "HighElf";
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
    public void sendWelcome(Player player) {
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[HighElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
	
	public String details() {
		return "50 points of extra magic";
	}
	
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin) { }
	
	@Override
	public void playerEnchantEvent(PlayerLevelChangeEvent e) { }

	@Override
	public void potionThrowEvent(PotionSplashEvent e, Player player) { }

	@Override
	public void playerMoveEvent(PlayerMoveEvent e, Player player) { }

	@Override
	public void onPlayerJoinEvent(PlayerJoinEvent e, Player player) { }

	@Override
	public void playerBurnEvent(EntityDamageEvent e, Player player) { }
}
