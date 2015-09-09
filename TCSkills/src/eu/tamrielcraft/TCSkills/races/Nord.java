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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Nord extends Race {

	public ArrayList<UUID> Nords = new ArrayList<UUID>();
	
	static Nord instance = new Nord();
    
    public static Nord getInstance() {
            return instance;
    }
    
    @Override
	public String raceName() {
		return "Nord";
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
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Nord] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
	
	public String details() {
		return "50% more resistance to negative potion effects";
	}
	
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
			
	}
	
	@Override
	public void playerEnchantEvent(PlayerLevelChangeEvent e) { }

	@Override
	public void potionThrowEvent(PotionSplashEvent e, Player player) {
		 for(PotionEffect p : e.getPotion().getEffects()) {
			 if(p.getType().equals(PotionEffectType.POISON) || p.getType().equals(PotionEffectType.CONFUSION) 
					 || p.getType().equals(PotionEffectType.SLOW) || p.getType().equals(PotionEffectType.HUNGER)
					 || p.getType().equals(PotionEffectType.SLOW_DIGGING) || p.getType().equals(PotionEffectType.WEAKNESS)
							 || p.getType().equals(PotionEffectType.WITHER)) {
				 player.sendMessage(ChatColor.RED + "You feel your skin trying to block the potion effect");
					 e.setIntensity(player, e.getIntensity(player) * 0.5); 
			 }
		 }
		
	}

	@Override
	public void playerMoveEvent(PlayerMoveEvent e, Player player) {
		
		
	}

	@Override
	public void onPlayerJoinEvent(PlayerJoinEvent e, Player player) {
		
		
	}

	@Override
	public void playerBurnEvent(EntityDamageEvent e, Player player) { }
}
