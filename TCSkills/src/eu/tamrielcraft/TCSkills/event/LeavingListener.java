package eu.tamrielcraft.TCSkills.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import eu.tamrielcraft.TCSkills.main.Magic;
import eu.tamrielcraft.TCSkills.main.SettingsManager;
import eu.tamrielcraft.TCSkills.races.RedGuard;

public class LeavingListener implements Listener {

	
	static SettingsManager settings = SettingsManager.getInstance();
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		Player player = (Player) e.getPlayer();
		
		Scoreboard board = player.getScoreboard();
		Objective magic = board.getObjective("Magic");
		
		@SuppressWarnings("deprecation")
		Score score = magic.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Magic:"));
		
		settings.getSave().set("magic.amount." + player.getUniqueId(), score.getScore());
		settings.saveSave();
		
		if(settings.getRace(player) == RedGuard.getInstance()) { //cancels the redguard food regeneration
			int task = Commands.redGuardLoopHM.get(player);
			Bukkit.getScheduler().cancelTask(task);
		}
		
		/*if(Magic.golemHM.containsValue(player)) {
			IronGolem golem = Magic.golemP.get(player);
			golem.setLeashHolder(null);
			golem.setHealth(0);
			Magic.golemTime.remove(golem);
			Magic.golemHM.remove(golem);
			Bukkit.getServer().getScheduler().cancelTask(Magic.golemSystemsIntHM.get(player));
			Magic.golemSystemsIntHM.put(player, 0);
		}
		
		
		if(Magic.runeP.containsKey(player)) {
			Magic.runeP.remove(player);
			for(Item runes : Magic.wasSpawnedIn) {
				Magic.wasSpawnedIn.remove(runes.getType());
				runes.remove();
				if(Magic.fireRuneTimerIntHM.containsKey(player)) {
				Bukkit.getScheduler().cancelTask(Magic.fireRuneTimerIntHM.get(player));
				Magic.fireRuneTimerIntHM.remove(player);
				}
				if(Magic.iceRuneTimerIntHM.containsKey(player)) {
				Bukkit.getScheduler().cancelTask(Magic.iceRuneTimerIntHM.get(player));
				Magic.iceRuneTimerIntHM.remove(player);
				}
				if(Magic.shockRuneTimerIntHM.containsKey(player)) {
				Bukkit.getScheduler().cancelTask(Magic.shockRuneTimerIntHM.get(player));
				Magic.shockRuneTimerIntHM.remove(player);
				}
			}
		}*/
		
		settings.getSave().set("magic." + player.getUniqueId() + ".favorites.on", 0);
	}
	
}
