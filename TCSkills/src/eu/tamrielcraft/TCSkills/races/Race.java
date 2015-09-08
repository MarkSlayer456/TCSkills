package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.Plugin;

public abstract class Race {
	// Static variables needed in all the subclasses
	protected static ArrayList<String> sneakCoolDown = new ArrayList<String>();
	protected static ArrayList<String> sneakMessage = new ArrayList<String>();
	
	public abstract String raceName();
	public abstract String raceNameChat();
	public abstract String formatChat(String s);
	public abstract void sendWelcome(Player player);
	
	// Events
	public abstract void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin);
	public abstract void playerEnchantEvent(PlayerLevelChangeEvent e);
}
