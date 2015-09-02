package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class Khajiit extends Race{
	
	public ArrayList<UUID> Khajiit = new ArrayList<UUID>();
	
	static Khajiit instance = new Khajiit();
    
    public static Khajiit getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[Khajiit]" + s);
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
	public void playerHitByPlayer(EntityDamageByEntityEvent e, final Player attacker, Plugin plugin){
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
}
