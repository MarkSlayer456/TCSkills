package eu.tamrielcraft.TCSkills.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class Main extends JavaPlugin implements Listener {
	Logger logger = Logger.getLogger("Minecraft");
	
	static SettingsManager settings = SettingsManager.getInstance(); //this should still work even if private make private from now on!
	
	Argonian argonian = new Argonian();
	Breton breton = new Breton();
	HighElves highelves = new HighElves();
	Khajiit khajiit = new Khajiit();
	Nords nords = new Nords();
	RedGuard redguard = new RedGuard();
	WoodElves woodelves = new WoodElves();
	
	//TODO add groups so summons don't attack certain players!
	//TODO add usable scroll spells
	
	public static Main plugin;
	
	public void onEnable() {
		settings.setup(this);
		settings.getConfig().options().copyDefaults(false);
		settings.saveConfig();
		settings.getRaces().options().copyDefaults(false);
		settings.saveRaces();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new Magic(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new LeavingListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Orcs(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DarkElves(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Imperials(), this);
		getLogger().info("Enabling Elder Scroll Races");
		settings.getConfig().addDefault("Header", "---===[TamerialCraft]===---");
		plugin = this;
	
	}
	
	public void onDisable() {
		getLogger().info("Disabling Elder Scroll Races");
	}
	
	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	ArrayList<String> sneaking = new ArrayList<String>();
	ArrayList<String> sneakCoolDown = new ArrayList<String>();
	ArrayList<String> sneakMessage = new ArrayList<String>();
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		String format = e.getFormat();
		Player player = e.getPlayer();
		if(settings.getRaces().get("argonians." + player.getUniqueId()) != null) {
			format = format.replace(format, "[Argonian]" + format);
		} else if(settings.getRaces().get("bretons." + player.getUniqueId()) != null) {
			format = format.replace(format, "[Breton]" + format);
		} else if(settings.getRaces().get("darkelves." + player.getUniqueId()) != null) {
			format = format.replace(format, "[DarkElf]" + format);
		} else if(settings.getRaces().get("highelves." + player.getUniqueId()) != null) {
			format = format.replace(format, "[HighElf]" + format);
		} else if(settings.getRaces().get("imperials." + player.getUniqueId()) != null) {
			format = format.replace(format, "[Imperial]" + format);
		} else if(settings.getRaces().get("khajiits." + player.getUniqueId()) != null) {
			format = format.replace(format, "[Khajiit]" + format);
		} else if(settings.getRaces().get("nords." + player.getUniqueId()) != null) {
			format = format.replace(format, "[Nord]" + format);
		} else if(settings.getRaces().get("orcs." + player.getUniqueId()) != null) {
			format = format.replace(format, "[Orc]" + format);
		} else if(settings.getRaces().get("redguards." + player.getUniqueId()) != null) {
			format = format.replace(format, "[RedGuard]" + format);
		} else if(settings.getRaces().get("woodelves." + player.getUniqueId()) != null) {
			format = format.replace(format, "[WoodElf]" + format);
		}
		e.setFormat(format);
		
	}

	@EventHandler
	public void playerHitEvent(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player attacker = (Player) e.getDamager();
			 if(settings.getRaces().get("khajiits." + attacker.getUniqueId()) != null) {
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
			 if(settings.getRaces().get("redguards." + attacker.getUniqueId()) != null) {
				 e.setDamage(e.getDamage() * 0.05 + e.getDamage());
			 }
			 if(settings.getRaces().get("woodelves." + attacker.getUniqueId()) != null ||
						settings.getRaces().get("khajiits." + attacker.getUniqueId()) != null) {
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
							this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
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
					Player shooter = (Player) project.getShooter();
					if(settings.getRaces().get("khajiits." + shooter.getUniqueId()) != null ||
							settings.getRaces().get("woodelves." + shooter.getUniqueId()) != null) {
						if(shooter.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
							shooter.removePotionEffect(PotionEffectType.INVISIBILITY);
							shooter.removePotionEffect(PotionEffectType.SPEED);
						}
						if(!sneakCoolDown.contains(shooter.getName().toString())) {
							sneakCoolDown.add(shooter.getName().toString());
						}
							this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
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
				if(settings.getRaces().get("woodelves." + shooter.getUniqueId()) != null) { //TODO make this so the player doesn't always get a headshot
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
			Player player = (Player) e.getEntity();
			if(settings.getRaces().get("woodelves." + player.getUniqueId()) != null ||
					settings.getRaces().get("khajiits." + player.getUniqueId()) != null) {
					
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
						this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
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
			 if(settings.getRaces().get("argonians." + player.getUniqueId()) != null ||
					 settings.getRaces().get("nords." + player.getUniqueId()) != null) {
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
	
	ArrayList<Block> blocks = new ArrayList<Block>();
	final HashMap<BlockState, Material> material = new HashMap<BlockState, Material>();
	
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent e) {
		Player player = (Player) e.getPlayer();
		if(settings.getRaces().get("argonians." + player.getUniqueId()) != null) {
		if(e.getTo().getBlock().getType() == Material.WATER || e.getTo().getBlock().getType() == Material.STATIONARY_WATER) {
			player.addPotionEffect(new PotionEffect (PotionEffectType.WATER_BREATHING, 3600, 0));
		}
		
		}
		if(settings.getRaces().get("khajiits." + player.getUniqueId()) != null ||
				 settings.getRaces().get("woodelves." + player.getUniqueId()) != null) {
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
		Player player = (Player) e.getPlayer();
		if(settings.getRaces().get("argonian." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Argonian] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(settings.getRaces().get("breton." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Breton] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(settings.getRaces().get("darkelves." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[DarkElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(settings.getRaces().get("highelves." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[HighElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(settings.getRaces().get("imperials." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Imperial] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(settings.getRaces().get("khajiits." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Khajiit] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(settings.getRaces().get("nords." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Nord] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else if(settings.getRaces().get("orcs." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Orc] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
			player.setMaxHealth(24);
		} else if(settings.getRaces().get("redguards." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[RedGuard] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
				@Override
				public void run() {
					if(player.getFoodLevel() < 8) {
						player.setFoodLevel(8);
						player.updateInventory();
					}
				}	
			}, 10, 10);
		} else if(settings.getRaces().get("woodelves." + player.getUniqueId()) != null) {
			player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[WoodElf] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
		} else {
			player.sendMessage(ChatColor.RED + "You might want to join a race it gives you abilities do /race list to see a list of all the races and there abilities");
		}
		if(settings.getRaces().get("magic." + player.getUniqueId()) == null) {
			settings.getRaces().set("magic.amount" + player.getUniqueId(), 100);
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
		Score score = magic.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN +  "Magic:"));
			score.setScore(settings.getRaces().getInt("magic.amount." + player.getUniqueId()));
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() { //REGENS MAGIC
			@Override
			public void run() {
			if(settings.getRaces().get("bretons." + player.getUniqueId()) != null
					|| settings.getRaces().get("highelves." + player.getUniqueId()) != null) {
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
	
	public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
		Player player = (Player) Sender;
		
		if(cmd.getName().equalsIgnoreCase("favorite") || cmd.getName().equalsIgnoreCase("fav")) {
			if(args.length == 0) {
				player.sendMessage(ChatColor.AQUA + settings.getConfig().getString("Header"));
				player.sendMessage(ChatColor.GREEN + "These are the commands you can do with /favorite");
				player.sendMessage(ChatColor.GREEN + "fav = favorite || s = spell || r = replace || l = list");
				player.sendMessage(ChatColor.RED + "/favorite list");
				player.sendMessage(ChatColor.RED + "/favorite spell <full spell name>");
				player.sendMessage(ChatColor.RED + "/favorite replace <number 1-3> <new full spell name>");
				return true;
			}
			if(args[0].equalsIgnoreCase("spell") || args[0].equalsIgnoreCase("s")) {
				if(args.length == 1) {
					player.sendMessage(ChatColor.RED + "Invalid spell name make sure you used the whole spell name! /favorite spell <full spell name>");
					return true;
				}
				if(args[1].equalsIgnoreCase("fireball") || args[1].equalsIgnoreCase("iceblast") ||
						args[1].equalsIgnoreCase("fasthealing") || args[1].equalsIgnoreCase("golemsummon") ||
						args[1].equalsIgnoreCase("familiarsummon") || args[1].equalsIgnoreCase("thundershock") 
						|| args[1].equalsIgnoreCase("firerune") || args[1].equalsIgnoreCase("icerune")
						|| args[1].equalsIgnoreCase("shockrune")) {
					if(settings.getRaces().get("magic." + player.getUniqueId() + ".favorites.amount") == null) {
						settings.getRaces().set("magic." + player.getUniqueId() + ".favorites.amount", 1);
						settings.getRaces().set("magic." + player.getUniqueId() + ".favorites.holder." + settings.getRaces().getInt("magic." + player.getUniqueId() + ".favorites.amount"), args[1]);
						player.sendMessage(ChatColor.GREEN + "Spell favorited, right click with a stick to cycle through your favorited spells!");
						settings.saveRaces();
					} else {
					settings.getRaces().set("magic." + player.getUniqueId() + ".favorites.amount", settings.getRaces().getInt("magic." + player.getUniqueId() + ".favorites.amount") + 1);
					settings.getRaces().set("magic." + player.getUniqueId() + ".favorites.holder." + settings.getRaces().getInt("magic." + player.getUniqueId() + ".favorites.amount"), args[1]);
					player.sendMessage(ChatColor.GREEN + "Spell favorited, right click with a stick to cycle through your favorited spells!");
					settings.saveRaces();
					}
					
				} else {
					player.sendMessage(ChatColor.RED + "Invalid spell name make sure you used the whole spell name! /favorite spell <full spell name>");
				}
			} else if(args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {
				if(settings.getRaces().get("magic." + player.getUniqueId() + ".favorites.holder.1") != null) {
				player.sendMessage(ChatColor.AQUA + "----- Favorites -----");
				player.sendMessage(ChatColor.GREEN + "1: " + settings.getRaces().get("magic." + player.getUniqueId() + ".favorites.holder.1"));
				} else {
					player.sendMessage(ChatColor.RED + "You have no spells favorited /favorite spell <full spell name>");
				}
				if(settings.getRaces().get("magic." + player.getUniqueId() + ".favorites.holder.2") != null) {
				player.sendMessage(ChatColor.GREEN + "2: " + settings.getRaces().get("magic." + player.getUniqueId() + ".favorites.holder.2"));
				}
				if(settings.getRaces().get("magic." + player.getUniqueId() + ".favorites.holder.3") != null) {
				player.sendMessage(ChatColor.GREEN + "3: " + settings.getRaces().get("magic." + player.getUniqueId() + ".favorites.holder.3"));
				}
				return true;
				
			} else if(args[0].equalsIgnoreCase("replace") || args[0].equalsIgnoreCase("r")) { //maybe make this /fav spell replace blah blah blah
				if(args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("3")) {
					if(args[2].equalsIgnoreCase("fireball") || args[2].equalsIgnoreCase("iceblast") ||
							args[2].equalsIgnoreCase("fasthealing") || args[2].equalsIgnoreCase("golemsummon") ||
							args[2].equalsIgnoreCase("familiarsummon") || args[2].equalsIgnoreCase("thundershock") 
							|| args[2].equalsIgnoreCase("firerune") || args[2].equalsIgnoreCase("icerune")
							|| args[2].equalsIgnoreCase("shockrune")) {
						settings.getRaces().set("magic." + player.getUniqueId() + ".favorites.holder." + args[1], args[2]);
						player.sendMessage(ChatColor.GREEN + "Spell replaced, right click with a stick to cycle through your favorited spells!");
						settings.saveRaces();
						return true;
						
					} else {
						player.sendMessage(ChatColor.RED + "Invalid spell name Usage: /favorite replace <number between 1 and 3> <new full spell name>");
						return true;
					}
				} else {
					player.sendMessage(ChatColor.RED + "Invalid number Usage: /favorite replace <number between 1 and 3> <new full spell name>");
					return true;
				}
			} else {
				player.sendMessage(ChatColor.RED + "Invaild Setup do /favorite to learn the setup!");
				return true;
			}
		} else if(cmd.getName().equalsIgnoreCase("spell") || cmd.getName().equalsIgnoreCase("s")) {
			if(args.length == 0 || args[0].equalsIgnoreCase("1")) {
				player.sendMessage(ChatColor.AQUA + settings.getConfig().getString("Header"));
				player.sendMessage(ChatColor.GOLD + "You can cast the following spells:");
				player.sendMessage(ChatColor.RED + "FireBall");
				player.sendMessage(ChatColor.RED + "IceBlast");
				player.sendMessage(ChatColor.RED + "FastHealing");
				player.sendMessage(ChatColor.RED + "GolemSummon");
				player.sendMessage(ChatColor.RED + "FamiliarSummon");
				player.sendMessage(ChatColor.RED + "ThunderShock");
				player.sendMessage(ChatColor.GOLD + "--- Page: 1/2 --- /s 2 for next page");
				return true;
			} else if(args[0].equals("2")) {
				player.sendMessage(ChatColor.AQUA + settings.getConfig().getString("Header"));
				player.sendMessage(ChatColor.GOLD + "You can cast the following spells:");
				player.sendMessage(ChatColor.RED + "FireRune");
				player.sendMessage(ChatColor.RED + "IceRune");
				player.sendMessage(ChatColor.RED + "ShockRune");
				player.sendMessage(ChatColor.GOLD + "--- Page: 2/2 ---");
			} else if(args[0].equalsIgnoreCase("fireball") || args[0].equalsIgnoreCase("fb")) {
				if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".fireball") == true) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", true);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveRaces();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FIREBALL " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("iceblast") || args[0].equalsIgnoreCase("ib")) {
				if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".iceblast") == true) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", true);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveRaces();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "ICEBLAST " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("fasthealing") || args[0].equalsIgnoreCase("fh")) {
				if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".fasthealing") == true) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", true);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveRaces();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FASTHEALING " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("golemsummon") || args[0].equalsIgnoreCase("gs")) {
				if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".golemsummon") == true) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", true);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveRaces();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "GOLEMSUMMON " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("thundershock") || args[0].equalsIgnoreCase("ts")) {
				if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".thundershock") == true) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", true);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveRaces();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "THUNDERSHOCK " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("familiarsummon") || args[0].equalsIgnoreCase("fs")) {
				if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".familiarsummon") == true) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", true);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveRaces();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FAMILIAR " + ChatColor.GOLD + "spell"); //TODO finish this! THINK THIS IS FINISHED DOUBLE CHECK
			} else if(args[0].equalsIgnoreCase("firerune") || args[0].equalsIgnoreCase("fr"))  {
				if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".firerune") == true) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", true);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveRaces();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FIRERUNE " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("icerune") || args[0].equalsIgnoreCase("ir")) {
				if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".icerune") == true) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", true);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveRaces();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "ICERUNE " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("shockrune") || args[0].equalsIgnoreCase("sr")) {
				if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".shockrune") == true) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", true);
				settings.saveRaces();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "SHOCKRUNE " + ChatColor.GOLD + "spell");
				
			} else {
				player.sendMessage(ChatColor.RED + "Usage: /spell <spell name>");
			} 
		}
		
		if(cmd.getName().equalsIgnoreCase("race") || cmd.getName().equalsIgnoreCase("r")) {
			//TODO remove when done testing!
			//if(settings.getRaces().getBoolean(player.getUniqueId() + "") == true) {
				//player.sendMessage(ChatColor.RED + "You have already picked your race!");
				//return true;
			//}
			
			if(args.length != 1) {
				player.sendMessage(ChatColor.AQUA + settings.getConfig().getString("Header"));
				player.sendMessage(ChatColor.GOLD + "You can be any of these races:");
				player.sendMessage(ChatColor.RED + "Argonian: " + ChatColor.GOLD + argonian.details() + "!");
				player.sendMessage(ChatColor.RED + "Breton: " + ChatColor.GOLD + breton.details() + "!");
				player.sendMessage(ChatColor.RED + "DarkElves: " + ChatColor.GOLD + DarkElves.details() + "!");
				player.sendMessage(ChatColor.RED + "HighElves: " + ChatColor.GOLD + highelves.details() + "!");
				player.sendMessage(ChatColor.RED + "Imperials: " + ChatColor.GOLD + Imperials.details() + "!");
				player.sendMessage(ChatColor.RED + "Khajiit: " + ChatColor.GOLD + khajiit.details() + "!");
				player.sendMessage(ChatColor.RED + "Nords: " + ChatColor.GOLD + nords.details() + "!");
				player.sendMessage(ChatColor.RED + "Orcs: " + ChatColor.GOLD + Orcs.details() + "!");
				player.sendMessage(ChatColor.RED + "RedGuard: " + ChatColor.GOLD + redguard.details() + "!");
				player.sendMessage(ChatColor.RED + "WoodElves: " + ChatColor.GOLD + woodelves.details() + "!");
				return true;
			} else {
				if(args[0].equalsIgnoreCase("nords") || args[0].equalsIgnoreCase("nord")) {
					settings.getRaces().set("nords." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Nord" + ChatColor.GOLD + "!");
					settings.saveRaces();
					return true;
				} else if(args[0].equalsIgnoreCase("orcs") || args[0].equalsIgnoreCase("orc")) {
					settings.getRaces().set("orcs." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Orc" + ChatColor.GOLD + "!");
					player.setMaxHealth(24);
					settings.saveRaces();
					return true;
				} else if(args[0].equalsIgnoreCase("argonians") || args[0].equalsIgnoreCase("argonian")) {
					settings.getRaces().set("argonians." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Argonian" + ChatColor.GOLD + "!");
					settings.saveRaces();
					return true;
				} else if(args[0].equalsIgnoreCase("bretons") || args[0].equalsIgnoreCase("breton")) {
					settings.getRaces().set("bretons." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Breton" + ChatColor.GOLD + "!");
					settings.saveRaces();
					return true;
				} else if(args[0].equalsIgnoreCase("darkelves") || args[0].equalsIgnoreCase("darkelf")) {
					settings.getRaces().set("darkelves." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "DarkElf" + ChatColor.GOLD + "!");
					settings.saveRaces();
					return true;
				} else if(args[0].equalsIgnoreCase("highelves") || args[0].equalsIgnoreCase("highelf")) {
					settings.getRaces().set("highelves." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "HighElf" + ChatColor.GOLD + "!");
					settings.saveRaces();
					return true;	
				} else if(args[0].equalsIgnoreCase("imperials") || args[0].equalsIgnoreCase("imperial")) {
					settings.getRaces().set("imperials." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Imperial" + ChatColor.GOLD + "!");
					settings.saveRaces();
					return true;
				} else if(args[0].equalsIgnoreCase("khajiits") || args[0].equalsIgnoreCase("khajiit")) {
					settings.getRaces().set("khajiits." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Khajiit" + ChatColor.GOLD + "!");
					settings.saveRaces();
					return true;
				} else if(args[0].equalsIgnoreCase("redguards") || args[0].equalsIgnoreCase("redguard")) {
					settings.getRaces().set("redguards." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "RedGuard" + ChatColor.GOLD + "!");
					Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
						@Override
						public void run() {
							if(player.getFoodLevel() < 8) {
								player.setFoodLevel(8);
								player.updateInventory();
							}
						}	
					}, 10, 10);
					settings.saveRaces();
					return true;
				} else if(args[0].equalsIgnoreCase("woodelves") || args[0].equalsIgnoreCase("woodelf")) {
					settings.getRaces().set("woodelves." + player.getUniqueId(), player.getName().toString());
					settings.getRaces().set(player.getUniqueId() + "", true);
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "WoodElf" + ChatColor.GOLD + "!");
					settings.saveRaces();
					return true;
				} else if(args[0].equalsIgnoreCase("list")) {
				player.sendMessage(ChatColor.AQUA + settings.getConfig().getString("Header"));
				player.sendMessage(ChatColor.GOLD + "You can be any of these races:");
				player.sendMessage(ChatColor.RED + "Argonian: " + ChatColor.GOLD + argonian.details() + "!");
				player.sendMessage(ChatColor.RED + "Breton: " + ChatColor.GOLD + breton.details() + "!");
				player.sendMessage(ChatColor.RED + "DarkElves: " + ChatColor.GOLD + DarkElves.details() + "!");
				player.sendMessage(ChatColor.RED + "HighElves: " + ChatColor.GOLD + highelves.details() + "!");
				player.sendMessage(ChatColor.RED + "Imperials: " + ChatColor.GOLD + Imperials.details() + "!");
				player.sendMessage(ChatColor.RED + "Khajiit: " + ChatColor.GOLD + khajiit.details() + "!");
				player.sendMessage(ChatColor.RED + "Nords: " + ChatColor.GOLD + nords.details() + "!");
				player.sendMessage(ChatColor.RED + "Orcs: " + ChatColor.GOLD + Orcs.details() + "!");
				player.sendMessage(ChatColor.RED + "RedGuard: " + ChatColor.GOLD + redguard.details() + "!");
				player.sendMessage(ChatColor.RED + "WoodElves: " + ChatColor.GOLD + woodelves.details() + "!");
				return true;
				} else {
					player.sendMessage(ChatColor.RED + "Usage: /race <racename> or /race list");
				}
			}
			
		}
		if(cmd.getName().equalsIgnoreCase("magicregen") || cmd.getName().equalsIgnoreCase("mr")) {
			if(player.isOp()) {
				Scoreboard board = player.getScoreboard();					
				Objective magic = board.getObjective("Magic");																	
				
				@SuppressWarnings("deprecation")
				Score score = magic.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN +  "Magic:"));
				
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() { //REGENS MAGIC
					@Override
					public void run() {
					if(settings.getRaces().get("bretons." + player.getUniqueId()) != null
							|| settings.getRaces().get("highelves." + player.getUniqueId()) != null) {
					if(score.getScore() <= 149) {	
					score.setScore(score.getScore() + 1);
					}
					} else {
						if(score.getScore() <= 99) {
							score.setScore(score.getScore() + 1);
						}
					}
					}
				}, 1, 1);
			} else {
				player.sendMessage(ChatColor.RED + "You don't have permission to use this command");
			}
			
		}
		
		return true;
	}
	
}

