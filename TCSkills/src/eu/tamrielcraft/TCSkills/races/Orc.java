package eu.tamrielcraft.TCSkills.races;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.Plugin;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class Orc extends Race implements Listener {

	static SettingsManager settings = SettingsManager.getInstance();
	
	static Orc instance = new Orc();
    
    public static Orc getInstance() {
            return instance;
    }
    
    @Override
	public String raceName() {
		return "Orc";
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
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Orc] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
	
	
	public static String details() {
		return "More health and receive 1 lvl after enchanting and repairing"; //TODO spell check and see if possible
	}
	
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
			
	}
	
	@Override
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {
		Player player = (Player) e.getPlayer();
		if(e.getNewLevel() < e.getOldLevel()) {
			player.sendMessage(ChatColor.GOLD + "Your orc powers helped you keep a level!");
			player.setLevel(player.getLevel() + 1);
			player.updateInventory(); //TODO might not need this
		}
	}
}
