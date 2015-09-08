package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.Plugin;

public class Argonian extends Race {
	
	public ArrayList<UUID> Argonians = new ArrayList<UUID>();
	
	static Argonian instance = new Argonian();
    
    public static Argonian getInstance() {
            return instance;
    }
    
    @Override
	public String raceName() {
		return "Argonian";
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
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Argonian] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
	
	public String details() {
		return "Breathe longer under water, and take 50% less damage from negative potion effects";
	}
	
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
		
	}

	@Override
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {		
	}
}
