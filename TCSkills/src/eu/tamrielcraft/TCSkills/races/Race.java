package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public abstract class Race {
	// Static variables needed in all the subclasses
	protected static ArrayList<String> sneakCoolDown = new ArrayList<String>();
	protected static ArrayList<String> sneakMessage = new ArrayList<String>();
	protected static ArrayList<String> sneaking = new ArrayList<String>();
	
	public abstract String raceName();
	public abstract String raceNameChat();
	public abstract String formatChat(String s);
	public abstract void sendWelcome(Player player);
	
	// Events
	public abstract void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin);
	public abstract void playerEnchantEvent(PlayerLevelChangeEvent e);
	public abstract void potionThrowEvent(PotionSplashEvent e, Player player);
	public abstract void playerMoveEvent(PlayerMoveEvent e, Player player);
	public abstract void onPlayerJoinEvent(PlayerJoinEvent e, Player player);
	public abstract void playerBurnEvent(EntityDamageEvent e, Player player);
	
}
