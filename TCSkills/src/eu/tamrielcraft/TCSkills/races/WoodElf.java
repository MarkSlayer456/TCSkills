package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class WoodElf extends Race{

	public ArrayList<UUID> WoodElves = new ArrayList<UUID>();
	
	static WoodElf instance = new WoodElf();
    
    public static WoodElf getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[WoodElf]" + s);
	}
    
    @Override
    public void sendWelcome(Player player){
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[WoodElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
	
	public String details() {
		return "10% more bow damage and invisablity while sneaking (wears off if hit or if you hit someone)";
	}
	
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, final Player attacker, Plugin plugin){
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
}
