package eu.tamrielcraft.TCKills.spells;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import eu.tamrielcraft.TCSkills.main.Magic;

public class GolemSummon extends Spells {

	
	
	
	static GolemSummon instance = new GolemSummon();
	
	public static GolemSummon getInstance() {
		return instance;
	}
	
	public static void disableGolem(Player player) {
		HashMap<IronGolem, Integer> golemTime = Magic.golemTime;
		HashMap<Player, IronGolem> golemP = Magic.golemP;
		HashMap<IronGolem, Player> golemHM = Magic.golemHM;
		IronGolem golem = golemP.get(player);
		golem.setLeashHolder(null);
		Bukkit.getScheduler().cancelTask(Magic.golemSystemsIntHM.get(player));
		Magic.golemSystemsIntHM.put(player, 0);
		if(golem.isDead()) {
		} else {
		golem.setHealth(0);
		}
		if(golemTime.containsKey(golem)) {
		golemTime.remove(golem);
		}
		if(golemHM.containsKey(golem)) {
		golemHM.remove(golem);
		}
		return;
}
	
	public static void setupGolem(Player player, Score score) {
		HashMap<IronGolem, Integer> golemTime = Magic.golemTime;
		HashMap<Player, IronGolem> golemP = Magic.golemP;
		HashMap<IronGolem, Player> golemHM = Magic.golemHM;
		
		
		Magic.golemB.put(player, false);
		double ox = player.getLocation().getX();
		double oy = player.getLocation().getY();
		double oz = player.getLocation().getZ();
		World ow = player.getWorld();
		golemP.put(player, player.getWorld().spawn(new Location(ow, ox + 2, oy + 1, oz), IronGolem.class));
		IronGolem golem = golemP.get(player);
		golem.setLeashHolder(player);
		golemTime.put(golem, 600); //THIS VAULE WAS MULTIPIED BY 20 BECAUSE THE EVENT OCCURS 20 TIMES A SECOND
		golemHM.put(golem, player);
		golem.setMaxHealth(300);
		golem.setHealth(300);
		score.setScore(score.getScore() - 100);
		
	}
	
	public static void runSystems(Player player, int systems) {
		HashMap<IronGolem, Integer> golemTime = Magic.golemTime;
		HashMap<Player, IronGolem> golemP = Magic.golemP;
		HashMap<IronGolem, Player> golemHM = Magic.golemHM;
		IronGolem golem = golemP.get(player);
		if(Magic.golemSystemsIntHM.get(player) == null || Magic.golemSystemsIntHM.get(player) == 0) {
			Magic.golemSystemsIntHM.put(player, systems);
			}
		
		if(golemTime.get(golem) <= 0) {
			player.sendMessage(ChatColor.RED + "Your golem has run out of time!");
			disableGolem(player);
			return;
		} else {
			//TODO: changed this golemTime.replace(golem, golemTime.get(golem) -1); to
			golemTime.put(golem, golemTime.get(golem) - 1);
		}
		if(golem.getHealth() <= 200) {
			golem.setLeashHolder(null);
			player.sendMessage(ChatColor.RED + "Your golem was slain!");
			disableGolem(player);
		}
		for(Entity entity : golem.getNearbyEntities(7, 7, 7)) {
			if(entity instanceof Player) {
				Player defender = ((Player) entity).getPlayer();
				if(golemHM.get(golem) != defender) {
					golem.setTarget(defender);
				}
				if(defender.isDead() == true) {
					golem.setTarget(null);
					break;
				}
			}
		}
	}

	@Override
	public void cast() {
		
		
	}

	@Override
	public void setActive() {
		
		
	}
	
}
