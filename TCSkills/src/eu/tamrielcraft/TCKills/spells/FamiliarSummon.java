package eu.tamrielcraft.TCKills.spells;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.material.Mushroom;
import org.bukkit.scoreboard.Score;

import eu.tamrielcraft.TCSkills.main.Magic;

public class FamiliarSummon extends Spells {
	
	
	static FamiliarSummon instance = new FamiliarSummon();
	
	public static FamiliarSummon getInstance() {
		return instance;
	}
	
	public static void setupFamiliar(Player player, Score score) {
		HashMap<Player, Boolean> wolfB = Magic.wolfB;
		HashMap<Player, Wolf> wolfP = Magic.wolfP;
		HashMap<Wolf, Integer> wolfTime = Magic.wolfTime;
		HashMap<Wolf, Player> wolfHM = Magic.wolfHM;
		
		wolfB.put(player, false);
		double ox = player.getLocation().getX();
		double oy = player.getLocation().getY();
		double oz = player.getLocation().getZ();
		World ow = player.getWorld();
		wolfP.put(player, player.getWorld().spawn(new Location(ow, ox + 2, oy + 1, oz), Wolf.class));
		Wolf wolf = wolfP.get(player);
		wolf.setTamed(true);
		wolf.setOwner(player);
		wolf.setLeashHolder(player);
		wolfTime.put(wolf, 600); //multipled because runs 20 times a second
		wolfHM.put(wolf, player);
		wolf.setMaxHealth(21);
		wolf.setHealth(21);
		score.setScore(score.getScore() - 25);
	}
	
	public static void runSystems(Player player, int systems) {
		HashMap<Player, Wolf> wolfP = Magic.wolfP;
		HashMap<Wolf, Integer> wolfTime = Magic.wolfTime;
		HashMap<Wolf, Player> wolfHM = Magic.wolfHM;
		Wolf wolf = wolfP.get(player);
		if(Magic.wolfSystemsIntHM.get(player) == null || Magic.wolfSystemsIntHM.get(player) == 0) {
			Magic.wolfSystemsIntHM.put(player, systems);
		}
		if(wolfTime.get(wolf) <= 0) {
			player.sendMessage(ChatColor.RED + "Your familiar has run out of time!");
			disableFamiliar(player);
			return;
		} else {
			wolfTime.put(wolf, wolfTime.get(wolf) - 1);
		}
		if(wolf.getHealth() <= 1) {
			wolf.setLeashHolder(null);
			player.sendMessage(ChatColor.RED + "Your familiar was slain!");
			disableFamiliar(player);
		}
		for(Entity entity : wolf.getNearbyEntities(7, 7, 7)) {
			if(entity instanceof Player) {
				Player defender = ((Player) entity).getPlayer();
				if(wolfHM.get(wolf) != defender) {
					wolf.setTarget(defender);
				}
				if(defender.isDead() == true) {
					wolf.setTarget(null);
					break;
				}
			} else if(entity instanceof Creature) {
				Creature mob = (Creature) entity;
				if(mob instanceof Cow || mob instanceof Sheep || mob instanceof Pig || mob instanceof Bat
						|| mob instanceof Chicken || mob instanceof Rabbit || mob instanceof EnderDragon
						|| mob instanceof Creeper || mob instanceof Horse || mob instanceof Mushroom
						|| mob instanceof Ocelot || mob instanceof Villager || mob instanceof IronGolem) { //TODO might add on to this
					
				} else {
				if(wolfHM.get(wolf) != mob) {
					wolf.setTarget(mob);
				}
				}
			}
			}
	}
	
	public static void disableFamiliar(Player player) {
		HashMap<Player, Wolf> wolfP = Magic.wolfP;
		HashMap<Wolf, Integer> wolfTime = Magic.wolfTime;
		HashMap<Wolf, Player> wolfHM = Magic.wolfHM;
		Wolf wolf = wolfP.get(player);
		
		wolf.setLeashHolder(null);
		Bukkit.getScheduler().cancelTask(Magic.wolfSystemsIntHM.get(player));
		Magic.golemSystemsIntHM.put(player, 0);
		if(!wolf.isDead()) {
			wolf.setHealth(0);
		}
		if(wolfTime.containsKey(wolf)) {
			wolfTime.remove(wolf);
		}
		if(wolfHM.containsKey(wolf)) {
			wolfHM.remove(wolf);
		}
		return;
		
	}

	@Override
	public void cast() {
		
		
	}

	@Override
	public void setActive() {
		
		
	}

}
