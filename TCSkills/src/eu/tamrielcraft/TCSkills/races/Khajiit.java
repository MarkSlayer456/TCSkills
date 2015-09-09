package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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


public class Khajiit extends Race {
	
	public ArrayList<UUID> Khajiit = new ArrayList<UUID>();
	
	static Khajiit instance = new Khajiit();
    
    public static Khajiit getInstance() {
            return instance;
    }
    
    @Override
	public String raceName() {
		return "Khajiit";
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
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Khajiit] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
	
	public String details() {
		return "Fist do 100% more damage and invisablity while sneaking (wears off if hit or if you hit someone)";
	}
	
	// Events
	// This is not a eventListener. The EventListener uses these methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, final Player attacker, Plugin plugin) {
		if(attacker.getItemInHand().getType() == Material.AIR) {
			Random r = new Random();
			int numb = r.nextInt(4) + 1;
			switch(numb) {
			case 1:
				e.setDamage(4);
				attacker.sendMessage(ChatColor.RED + "You claw your enemy in the face!");
			case 2:
				e.setDamage(2);
			case 3:
				e.setDamage(2);
			case 4:
				e.setDamage(2);
			}
		}
		
		if(attacker.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			attacker.removePotionEffect(PotionEffectType.INVISIBILITY);
			attacker.removePotionEffect(PotionEffectType.SPEED);
		}
		if(sneakCoolDown.contains(attacker.getName().toString())) {
			return;
		}
		sneakCoolDown.add(attacker.getName().toString());
		if(!sneakMessage.contains(attacker.getName().toString())) {
		attacker.sendMessage(ChatColor.RED + "You are in combat please wait 5 seconds before trying to turn invisible again!");
		attacker.sendMessage(ChatColor.RED + "This will happen every time you get hit!");
		sneakMessage.add(attacker.getName().toString());
		}
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
		     public void run() {
				sneakCoolDown.remove(attacker.getName().toString());
				attacker.sendMessage(ChatColor.GOLD + "You can sneak again!");
			}
		},  100);
	}
	
	@Override
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {
	}

	@Override
	public void potionThrowEvent(PotionSplashEvent e, Player player) {
		
		
	}

	@Override
	public void playerMoveEvent(PlayerMoveEvent e, Player player) {
		if(player.isSneaking()) {
			if(sneakCoolDown.contains(player.getName().toString())) {
				return;
			}
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 0));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3600, 1));
			sneaking.add(player.getName().toString());
		} else {
			if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			player.removePotionEffect(PotionEffectType.SPEED);
			sneaking.remove(player.getName().toString());
			}
		}
		
	}

	@Override
	public void onPlayerJoinEvent(PlayerJoinEvent e, Player player) {
		
		
	}

	@Override
	public void playerBurnEvent(EntityDamageEvent e, Player player) { }
}
