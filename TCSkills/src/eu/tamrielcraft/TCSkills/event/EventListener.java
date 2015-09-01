package eu.tamrielcraft.TCSkills.event;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
import eu.tamrielcraft.TCSkills.races.DarkElves;
import eu.tamrielcraft.TCSkills.races.HighElves;
import eu.tamrielcraft.TCSkills.races.Imperials;
import eu.tamrielcraft.TCSkills.races.Khajiit;
import eu.tamrielcraft.TCSkills.races.Nords;
import eu.tamrielcraft.TCSkills.races.Orcs;
import eu.tamrielcraft.TCSkills.races.RedGuard;
import eu.tamrielcraft.TCSkills.races.WoodElves;

public class EventListener implements Listener{
	
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
	
	/*@EventHandler //TODO
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {
		Player player = (Player) e.getPlayer();
		if(settings.race.equals(orc.getInstance()) {
			if(e.getNewLevel() < e.getOldLevel()) {
			player.sendMessage(ChatColor.GOLD + "Your orc powers helped you keep a level!");
			player.setLevel(player.getLevel() + 1);
			player.updateInventory();
			}
		}
	}*/
	
	FileConfiguration save = settings.getSave();
	
	
	@EventHandler
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {
		Player player = (Player) e.getPlayer();
		if(settings.getRace(player).equals(Orcs.getInstance())) {
			if(e.getNewLevel() < e.getOldLevel()) {
			player.sendMessage(ChatColor.GOLD + "Your orc powers helped you keep a level!");
			player.setLevel(player.getLevel() + 1);
			player.updateInventory(); //TODO might not need this
			}
		}
	}
	
	@EventHandler
	public void playerBurnEvent(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if(save.get("darkelves." + player.getUniqueId()) != null) {
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
		FileConfiguration save = settings.getSave();
		if(save.get("argonians." + player.getUniqueId()) != null) {
			format = Argonian.getInstance().formatChat(format);
		} else if(save.get("bretons." + player.getUniqueId()) != null) {
			format = Breton.getInstance().formatChat(format);
		} else if(save.get("darkelves." + player.getUniqueId()) != null) {
			format = DarkElves.getInstance().formatChat(format);
		} else if(save.get("highelves." + player.getUniqueId()) != null) {
			format = HighElves.getInstance().formatChat(format);
		} else if(save.get("imperials." + player.getUniqueId()) != null) {
			format = Imperials.getInstance().formatChat(format);
		} else if(save.get("khajiits." + player.getUniqueId()) != null) {
			format = Khajiit.getInstance().formatChat(format);
		} else if(save.get("nords." + player.getUniqueId()) != null) {
			format = Nords.getInstance().formatChat(format);
		} else if(save.get("orcs." + player.getUniqueId()) != null) {
			format = Orcs.getInstance().formatChat(format);
		} else if(save.get("redguards." + player.getUniqueId()) != null) {
			format = RedGuard.getInstance().formatChat(format);
		} else if(save.get("woodelves." + player.getUniqueId()) != null) {
			format = WoodElves.getInstance().formatChat(format);
		}
		e.setFormat(format);
	}
	
	@EventHandler
	public void playerHitEvent(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			final Player attacker = (Player) e.getDamager();
			 if(save.get("khajiits." + attacker.getUniqueId()) != null) {
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
			 if(save.get("redguards." + attacker.getUniqueId()) != null) {
				 e.setDamage(e.getDamage() * 0.05 + e.getDamage());
			 }
			 if(save.get("woodelves." + attacker.getUniqueId()) != null ||
						save.get("khajiits." + attacker.getUniqueId()) != null) {
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
		} else if(e.getDamager() instanceof Projectile) {
				Projectile project = (Projectile) e.getDamager();
				if(project.getShooter() instanceof Player) {
					final Player shooter = (Player) project.getShooter();
					if(save.get("khajiits." + shooter.getUniqueId()) != null ||
							save.get("woodelves." + shooter.getUniqueId()) != null) {
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
				if(save.get("woodelves." + shooter.getUniqueId()) != null) { //TODO make this so the player doesn't always get a headshot
					 if(shooter.getItemInHand().getType() == Material.BOW) {
						 shooter.sendMessage(ChatColor.RED + "Headshot! 10% more bow damage!");
						 e.setDamage(e.getDamage() * .10 + e.getDamage());
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
			if(save.get("woodelves." + player.getUniqueId()) != null ||
					save.get("khajiits." + player.getUniqueId()) != null) {
					
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
			 if(save.get("argonians." + player.getUniqueId()) != null ||
					 save.get("nords." + player.getUniqueId()) != null) {
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
		if(save.get("argonians." + player.getUniqueId()) != null) {
		if(e.getTo().getBlock().getType() == Material.WATER || e.getTo().getBlock().getType() == Material.STATIONARY_WATER) {
			player.addPotionEffect(new PotionEffect (PotionEffectType.WATER_BREATHING, 3600, 0));
		}
		
		}
		if(save.get("khajiits." + player.getUniqueId()) != null ||
				 save.get("woodelves." + player.getUniqueId()) != null) {
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
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player player = (Player) e.getPlayer();
		if(save.get("argonian." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Argonian] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(save.get("breton." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Breton] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(save.get("darkelves." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[DarkElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(save.get("highelves." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[HighElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(save.get("imperials." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Imperial] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(save.get("khajiits." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Khajiit] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(save.get("nords." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Nord] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(save.get("orcs." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Orc] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
			player.setMaxHealth(24);
		} else if(save.get("redguards." + player.getUniqueId()) != null) {
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
		} else if(save.get("woodelves." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[WoodElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else {
			player.sendMessage(ChatColor.RED + "You might want to join a race it gives you abilities do /race list to see a list of all the races and there abilities");
		}
		if(save.get("magic." + player.getUniqueId()) == null) {
			save.set("magic.amount" + player.getUniqueId(), 100);
		}
		//TODO NEED SOMETHING HERE TO CHECK IF PLAYER HAS A SCOREBOARD ENABLED!
		if(manager == null) {
			manager = Bukkit.getScoreboardManager();
		}
		Scoreboard board = manager.getNewScoreboard();						
		Objective magic = board.registerNewObjective("Magic", "dummy");		
		magic.setDisplayName("Magic");										
		magic.setDisplaySlot(DisplaySlot.SIDEBAR);							
		player.setScoreboard(board);										
		
		@SuppressWarnings("deprecation")
		final Score score = magic.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN +  "Magic:"));
			score.setScore(save.getInt("magic.amount." + player.getUniqueId()));
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() { //REGENS MAGIC
			@Override
			public void run() {
			if(save.get("bretons." + player.getUniqueId()) != null
					|| save.get("highelves." + player.getUniqueId()) != null) {
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
