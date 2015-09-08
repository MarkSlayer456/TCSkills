package eu.tamrielcraft.TCSkills.event;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import eu.tamrielcraft.TCSkills.main.SettingsManager;
import eu.tamrielcraft.TCSkills.races.Argonian;
import eu.tamrielcraft.TCSkills.races.Breton;
import eu.tamrielcraft.TCSkills.races.DarkElf;
import eu.tamrielcraft.TCSkills.races.HighElf;
import eu.tamrielcraft.TCSkills.races.Khajiit;
import eu.tamrielcraft.TCSkills.races.Nord;
import eu.tamrielcraft.TCSkills.races.Orc;
import eu.tamrielcraft.TCSkills.races.Race;
import eu.tamrielcraft.TCSkills.races.RedGuard;
import eu.tamrielcraft.TCSkills.races.WoodElf;

public class EventListener implements Listener {
	
	//TODO: The getRace should be implemented in the settings and should give back an abstract Race object
	
	private Plugin plugin;
	private SettingsManager settings;
	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	private ArrayList<String> sneaking = new ArrayList<String>();
	private ArrayList<String> sneakCoolDown = new ArrayList<String>();
	private ArrayList<String> sneakMessage = new ArrayList<String>();
	
	public EventListener(Plugin plugin, SettingsManager settings) {
		this.settings = settings;
		this.plugin = plugin;
	}
	
	@EventHandler
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {
		Player player = (Player) e.getPlayer();
		Race race = settings.getRace(player);
		race.playerEnchantEvent(e);
		/*if(race == Orc.getInstance()) {
			if(e.getNewLevel() < e.getOldLevel()) {
			player.sendMessage(ChatColor.GOLD + "Your orc powers helped you keep a level!");
			player.setLevel(player.getLevel() + 1);
			player.updateInventory(); //TODO might not need this
			}
		}*/
	}
	
	@EventHandler
	public void playerBurnEvent(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			Race race = settings.getRace(player);
			if(race == DarkElf.getInstance()) {
				if(player.getFireTicks() != 0) {
					e.setDamage(e.getDamage() / 2);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		String format = e.getFormat();
		Player player = e.getPlayer();
		Race race = settings.getRace(player); 
		race.formatChat(format);		
		e.setFormat(format);
	}
	
	@EventHandler
	public void playerHitEvent(EntityDamageByEntityEvent e) {
		//TODO: move all to corresponding race classes
		if(e.getDamager() instanceof Player) {
			final Player attacker = (Player) e.getDamager();
			Race race = settings.getRace(attacker);
			// New code
			race.playerHitByPlayer(e, attacker, plugin);
			// Old Code
			 /*if(race == Khajiit.getInstance()) {
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
			 }
			 if(race == RedGuard.getInstance()) {
				 e.setDamage(e.getDamage() * 0.05 + e.getDamage());
			 }*/
			 /*if(race == Khajiit.getInstance() || race == WoodElf.getInstance()) {
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
				}*/
		} else if(e.getDamager() instanceof Projectile) {
				Projectile project = (Projectile) e.getDamager();
				if(project.getShooter() instanceof Player) {
					final Player shooter = (Player) project.getShooter();
					Race race = settings.getRace(shooter);
					if(race == Khajiit.getInstance() || race == WoodElf.getInstance()) {
						if(shooter.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							shooter.removePotionEffect(PotionEffectType.INVISIBILITY);
							shooter.removePotionEffect(PotionEffectType.SPEED);
						}
						if(!sneakCoolDown.contains(shooter.getName().toString())) {
							sneakCoolDown.add(shooter.getName().toString());
						}
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								@Override
							     public void run() {
									sneakCoolDown.remove(shooter.getName().toString());
									shooter.sendMessage(ChatColor.GOLD + "You can sneak again!");
							}
							},  100);
					}
				}
				if(project.getShooter() instanceof Player) {
				Player shooter = (Player) project.getShooter();
				Race race = settings.getRace(shooter);
				if(race == WoodElf.getInstance()) {
					Random r = new Random();
					int ran = r.nextInt(6) + 1;
					if(ran == 2) {
						 if(shooter.getItemInHand().getType() == Material.BOW) { //TODO error here someone could shoot and swap really fast and not have a chance of getting a headshot
							 shooter.sendMessage(ChatColor.RED + "Headshot! 10% more bow damage!");
							 e.setDamage(e.getDamage() * .10 + e.getDamage());
						 }
					}
				 }
				}
				if(e.getEntity() instanceof Player) {
				if(project.getType() == EntityType.SNOWBALL) {
				Player defender = (Player) e.getEntity();
					e.setDamage(1);
					defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 1));
				} 
				if(project.getType() == EntityType.SMALL_FIREBALL) {
					e.setDamage(1);
				}
				} else {
					LivingEntity mob = (LivingEntity) e.getEntity();
					if(project.getType() == EntityType.SNOWBALL) {
							e.setDamage(1);
							mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 1));
						} 
						if(project.getType() == EntityType.SMALL_FIREBALL) {
							e.setDamage(1);
						}
				}
			} 
			if(e.getEntity() instanceof Player) {
			final Player player = (Player) e.getEntity();
			Race race = settings.getRace(player);
			if(race == Khajiit.getInstance() || race == WoodElf.getInstance()) {
					
					if(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
						player.removePotionEffect(PotionEffectType.INVISIBILITY);
						player.removePotionEffect(PotionEffectType.SPEED);
					}
					if(sneakCoolDown.contains(player.getName().toString())) {
						return;
					}
						sneakCoolDown.add(player.getName().toString());
						if(!sneakMessage.contains(player.getName().toString())) {
							player.sendMessage(ChatColor.RED + "You are in combat please wait 5 seconds before trying to turn invisible again!");
							player.sendMessage(ChatColor.RED + "This will happen every time you get hit!");
							sneakMessage.add(player.getName().toString());
							}
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							@Override
						     public void run() {
								sneakCoolDown.remove(player.getName().toString());
								player.sendMessage(ChatColor.GOLD + "You can sneak again!");
						}
						},  100);
			}
		} else {
			return;
		}
	}
	
	@EventHandler
	public void potionThrowEvent(PotionSplashEvent e) {
		for(LivingEntity entity : e.getAffectedEntities()) {
		if(entity instanceof Player) {
			 Player player = (Player) entity;
			 Race race = settings.getRace(player);
			 if(race == Argonian.getInstance() || race == Nord.getInstance()) {
				 for(PotionEffect p : e.getPotion().getEffects()) {
					 if(p.getType().equals(PotionEffectType.POISON) || p.getType().equals(PotionEffectType.CONFUSION) 
							 || p.getType().equals(PotionEffectType.SLOW) || p.getType().equals(PotionEffectType.HUNGER)
							 || p.getType().equals(PotionEffectType.SLOW_DIGGING) || p.getType().equals(PotionEffectType.WEAKNESS)
									 || p.getType().equals(PotionEffectType.WITHER)) {
						 player.sendMessage(ChatColor.RED + "You feel your skin trying to block the potion effect");
							 e.setIntensity(player, e.getIntensity(player) * 0.5); 
					 }
				 }
			 }
		}
		}
	}
	
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent e) {
		Player player = (Player) e.getPlayer();
		if(settings.getRace(player) == Argonian.getInstance()) {
		if(e.getTo().getBlock().getType() == Material.WATER || e.getTo().getBlock().getType() == Material.STATIONARY_WATER) {
			player.addPotionEffect(new PotionEffect (PotionEffectType.WATER_BREATHING, 3600, 0));
		}
		
		}
		if(settings.getRace(player) == Khajiit.getInstance() || settings.getRace(player) == WoodElf.getInstance()) {
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
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player player = (Player) e.getPlayer();
		final Race race = settings.getRace(player);
		
		//TODO: same here: only one method call!
		/*if(race == Argonian.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Argonian] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(race == Breton.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Breton] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(race == DarkElf.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[DarkElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(race == HighElf.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[HighElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(race == Imperial.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Imperial] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(race == Khajiit.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Khajiit] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(race == Nord.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Nord] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(race == Orc.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Orc] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
			player.setMaxHealth(24);
		} else if(race == RedGuard.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[RedGuard] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				@Override
				public void run() {
					if(player.getFoodLevel() < 8) {
						player.setFoodLevel(8);
						player.updateInventory();
					}
				}	
			}, 10, 10);
		} else if(race == WoodElf.getInstance()) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[WoodElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else {
			settings.getSave().set(player.getUniqueId() + ".race", null);
			player.sendMessage(ChatColor.RED + "You might want to join a race it gives you abilities do /race list to see a list of all the races and there abilities");
		}*/
		
		if(race != null){
			// Player has a race
			race.sendWelcome(player);
			
			//TODO If many more races have an initialize thingy, we should add this to each race class
			if(race == Orc.getInstance()){
				player.setMaxHealth(24);
			}
			if(race == RedGuard.getInstance()){
				Commands.redGuardFoodLoopInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
					@Override
					public void run() {
						if(Commands.redGuardLoopHM.get(player) == null || Commands.redGuardLoopHM.get(player) == 1) {
							Commands.redGuardLoopHM.put(player, Commands.redGuardFoodLoopInt);
						}
						if(player.getFoodLevel() < 8) {
							player.setFoodLevel(8);
							player.updateInventory();
						}
					}	
				}, 10, 10);
			}
		} else {
			// Player has no race selected
			player.sendMessage(ChatColor.RED + "You might want to join a race it gives you abilities do /race list to see a list of all the races and there abilities");
		}
		
		if(settings.hasMagic(player) == false) {
			settings.getSave().set(player.getUniqueId() + ".magic.hasMagic", 100);
			settings.saveSave();
		}
		if(manager == null) {
			manager = Bukkit.getScoreboardManager();
		}
		if(player.getScoreboard() != null) {
			Scoreboard board = manager.getNewScoreboard();
			Objective magic = board.registerNewObjective("Magic", "dummy");		
			magic.setDisplayName("Magic");										
			magic.setDisplaySlot(DisplaySlot.SIDEBAR);							
			player.setScoreboard(board);
			@SuppressWarnings("deprecation")
			final Score score = magic.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN +  "Magic:"));
			score.setScore(settings.getMagic(player));
			
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() { //REGENS MAGIC
				@Override
				public void run() {
				if(race == Breton.getInstance() || race == HighElf.getInstance()) {
				if(score.getScore() <= 149) {	
				score.setScore(score.getScore() + 1);
				}
				} else {
					if(score.getScore() <= 99) {
						score.setScore(score.getScore() + 1);
					}
				}
				}
			}, 10, 10);
			
		} else {
		
		Scoreboard board = manager.getNewScoreboard();						
		Objective magic = board.registerNewObjective("Magic", "dummy");		
		magic.setDisplayName("Magic");										
		magic.setDisplaySlot(DisplaySlot.SIDEBAR);							
		player.setScoreboard(board);										
		@SuppressWarnings("deprecation")
		final Score score = magic.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN +  "Magic:"));
			score.setScore(settings.getMagic(player));
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() { //REGENS MAGIC
			@Override
			public void run() {
			if(race == Breton.getInstance() || race == HighElf.getInstance()) {
			if(score.getScore() <= 149) {	
			score.setScore(score.getScore() + 1);
			}
			} else {
				if(score.getScore() <= 99) {
					score.setScore(score.getScore() + 1);
				}
			}
			}
		}, 10, 10);
		}
	}
}
