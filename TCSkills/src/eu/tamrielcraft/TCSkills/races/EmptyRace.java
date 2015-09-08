package eu.tamrielcraft.TCSkills.races;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.Plugin;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class EmptyRace extends Race{
	static SettingsManager settings = SettingsManager.getInstance();
	
	static EmptyRace instance = new EmptyRace();
    
    public static EmptyRace getInstance() {
            return instance;
    }

	@Override
	public String formatChat(String s) {
		return "";
	}

	@Override
	public void sendWelcome(Player player) {
		player.sendMessage(ChatColor.RED + "You might want to join a race it gives you abilities do /race list to see a list of all the races and there abilities");
	}

	@Override
	public String raceName() {
		return "";
	}

	@Override
	public String raceNameChat() {
		return "";
	}

	@Override
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin) {	
	}
	
	@Override
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {
	}

}
