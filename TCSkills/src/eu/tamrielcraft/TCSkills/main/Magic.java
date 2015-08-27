package eu.tamrielcraft.TCSkills.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Mushroom;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class Magic implements Listener {
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	public static HashMap<IronGolem, Player> golemHM = new HashMap<IronGolem, Player>();
	public static HashMap<IronGolem, Integer> golemTime = new HashMap<IronGolem, Integer>();
	public static HashMap<Player, Integer> golemTimerSystemIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> golemHealthSystemIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> golemTargetingSystemIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, IronGolem> golemP = new HashMap<Player, IronGolem>();
	public static HashMap<Player, Boolean> golemB = new HashMap<Player, Boolean>();
	
	public static HashMap<Wolf, Player> wolfHM = new HashMap<Wolf, Player>();
	public static HashMap<Wolf, Integer> wolfTime = new HashMap<Wolf, Integer>();
	public static HashMap<Player, Integer> wolfTimerSystemIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> wolfHealthSystemIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> wolfTargetingSystemIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Wolf> wolfP = new HashMap<Player, Wolf>();
	public static HashMap<Player, Boolean> wolfB = new HashMap<Player, Boolean>();
	
	public static HashMap<Player, Item> runeP = new HashMap<Player, Item>();
	public static HashMap<Player, Integer> fireRuneTimerIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> iceRuneTimerIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> shockRuneTimerIntHM = new HashMap<Player, Integer>();
	public static ArrayList<Item> wasSpawnedIn = new ArrayList<Item>();
	public static HashMap<Item, Player> runeHM = new HashMap<Item, Player>();
	
	//TODO MAKE RUNES HAVE SEPERTATE HASHAMPS
	//TODO MAKE FAST HEALING CHECK TO SEE YOUR HEALTH
	//TODO MAKE RUNES BLOCK ENTITIES (FIRECHARGE)
	
	//TODO ---> !!!!WHAT ARE YOU DOING ABOUT THE GOLEMS DO YOU WANT TO MAKE METHODS TO DISABLE THEM? AND OTHER METHODS FOR THE TIMER AND THINGS?!!!! <---
	public void resetGolem() { //TODO if this wasn't what your plans were you can delete them
		
	}
	
	public void resetWolf() {
		
	}
	
	public boolean golemDisabled() {
		return true;
	}
	
	public boolean wolfDisabled() {
		return true;
	}
	
	
	private int fireRuneTimerInt;
	private int iceRuneTimerInt;
	private int shockRuneTimerInt;
	
	private int golemTimerSystemInt;
	private int golemHealthSystemInt;
	private int golemTargetingSystemInt;
	
	private int wolfTimerSystemInt;
	private int wolfHealthSystemInt;
	private int wolfTargetingSystemInt;
	
	@EventHandler	
	public void onPickUpEvent1(PlayerPickupItemEvent e) {
		Player player = (Player) e.getPlayer();
		if(runeP.containsKey(player)) {
			if(wasSpawnedIn.contains(e.getItem())) {
				e.setCancelled(true);
			}
		}
	}
	
	public boolean regeningMagic = false;
	
	@EventHandler
	public void playerUnleashEvent(PlayerUnleashEntityEvent e) {
		if(e.getEntity() instanceof IronGolem || e.getEntity() instanceof Wolf) {
			e.setCancelled(true);
			return;
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent e) {
		final Player player = (Player) e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR && player.getItemInHand().getType() == Material.STICK || e.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().getType() == Material.STICK) {
			if(settings.getRaces().get("magic." + player.getUniqueId() + ".favorites.holder." + 1) == null) {
				player.sendMessage(ChatColor.RED + "You have no spells favorited! Do /favorite spell <spell name> to favorite some!");
			} else {
					if(settings.getRaces().getInt("magic." + player.getUniqueId() + ".favorites.on") >= 3) {
						settings.getRaces().set("magic." + player.getUniqueId() + ".favorites.on", 0);
					}
				settings.getRaces().set("magic." + player.getUniqueId() + ".fireball", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".iceblast", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".fasthealing", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".golemsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".thundershock", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".familiarsummon", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".firerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".icerune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.getRaces().set("magic." + player.getUniqueId() + ".favorites.on", settings.getRaces().getInt("magic." + player.getUniqueId() + ".favorites.on") + 1);
				if(settings.getRaces().getInt("magic." + player.getUniqueId() + ".favorites.amount") < settings.getRaces().getInt("magic." + player.getUniqueId() + ".favorites.on")) {
					settings.getRaces().set("magic." + player.getUniqueId() + ".favorites.on", 1);
					settings.saveRaces();
				}
				String spell = settings.getRaces().getString("magic." + player.getUniqueId() + ".favorites.holder." + settings.getRaces().get("magic." + player.getUniqueId() + ".favorites.on"));
				settings.saveRaces();
				Bukkit.dispatchCommand(player, "spell " + spell);
			}
		}
		if(e.getAction() == Action.LEFT_CLICK_AIR && player.getItemInHand().getType() == Material.STICK) {
			if(player.getScoreboard() != null) {
				Scoreboard board = player.getScoreboard();
				Objective magic = board.getObjective("Magic");
				Score score = magic.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Magic:"));
			if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".fireball") != false) {
				if(score.getScore() >= 10) {
					player.launchProjectile(SmallFireball.class);
					score.setScore(score.getScore() - 10);
				} else {
					player.sendMessage(ChatColor.RED + "You need 10 magic to cast FIREBALL!");
				}
			} else if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".iceblast") != false) {
				if(score.getScore() >= 8) {
					player.launchProjectile(Snowball.class);
					score.setScore(score.getScore() - 8);
				} else {
					player.sendMessage(ChatColor.RED + "You need 8 magic to cast ICEBLAST!");
				}
			} else if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".fasthealing") != false) {
				if(score.getScore() >= 25) {
					player.setHealth(player.getHealth() + 2);
					score.setScore(score.getScore() - 25);
				} else {
					player.sendMessage(ChatColor.RED + "You need 25 magic to cast FASTHEALING!");
				}
				
			} else if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".golemsummon") != false) {
				if(score.getScore() >= 100) {
					if(golemHM.get(golemP.get(player)) == player) {
						player.sendMessage(ChatColor.RED + "You can only have one golem spawned at a time!");
						return;
					}
					golemB.put(player, false);
					double ox = player.getLocation().getX();
					double oy = player.getLocation().getY();
					double oz = player.getLocation().getZ();
					World ow = player.getWorld();
					golemP.put(player, player.getWorld().spawn(new Location(ow, ox + 2, oy + 1, oz), IronGolem.class));
					IronGolem golem = golemP.get(player);
					golem.setLeashHolder(player);
					golemTime.put(golem, 30);
					golemHM.put(golem, player);
					golem.setMaxHealth(300);
					golem.setHealth(300);
					score.setScore(score.getScore() - 100);
					
					golemTimerSystemInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() { //Timer
						@Override
						public void run() {
							/*if(golemTimerSystemIntHM.get(player) == null || golemTimerSystemIntHM.get(player) == 0) {
							golemTimerSystemIntHM.put(player, golemTimerSystemInt);
							}
							if(golemTime.get(golem) <= 0) {
								golem.setLeashHolder(null);
								if(golemB.get(player) == false) {
									golemB.replace(player, true);	
								Bukkit.getScheduler().cancelTask(golemTimerSystemIntHM.get(player));
								Bukkit.getScheduler().cancelTask(golemHealthSystemIntHM.get(player));
								Bukkit.getScheduler().cancelTask(golemTargetingSystemIntHM.get(player));
								golemTargetingSystemIntHM.put(player, 0);
								golemTimerSystemIntHM.put(player, 0);
								golemHealthSystemIntHM.put(player, 0);
								player.sendMessage(ChatColor.RED + "Your golem has run out of time!");
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
							} else {
								golemTime.replace(golem, golemTime.get(golem) - 1);
							}*/
						}
					}, 0, 20);
					golemTargetingSystemInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() { //IRON GOLEM TARGETING SYSTEM
						@Override
						public void run() { //DOESN'T NEED TO RESET GOLEM BECASUE IT'S JUST THE TARGETING SYSTEM
							/*if(golemTargetingSystemIntHM.get(player) == null || golemTargetingSystemIntHM.get(player) == 0) {
							golemTargetingSystemIntHM.put(player, golemTargetingSystemInt);
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
								if(golem.getTarget() == null) {
								}
							}*/
						}
					}, 0, 5);
					golemHealthSystemInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() { //HEALTH TRACKING SYSTEM
						@Override
						public void run() {
							/*if(golemHealthSystemIntHM.get(player) == null || golemHealthSystemIntHM.get(player) == 0) {
							golemHealthSystemIntHM.put(player, golemHealthSystemInt);
							}
							if(golem.getHealth() <= 200) {
								golem.setLeashHolder(null);
								if(golem.getKiller() != null) {
								player.sendMessage(ChatColor.DARK_RED + "Your IronGolem was slain by " + golem.getKiller().getName().toString());
								}
								if(golemB.get(player) == false) {
									golemB.replace(player, true);
									Bukkit.getScheduler().cancelTask(golemTimerSystemIntHM.get(player));
									Bukkit.getScheduler().cancelTask(golemHealthSystemIntHM.get(player));
									Bukkit.getScheduler().cancelTask(golemTargetingSystemIntHM.get(player));
									golemTargetingSystemIntHM.put(player, 0);
									golemTimerSystemIntHM.put(player, 0);
									golemHealthSystemIntHM.put(player, 0);
									player.sendMessage(ChatColor.RED + "Your golem has run out of time!");
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
							}*/
						}
					}, 0, 1);	
					
			} else {
				player.sendMessage(ChatColor.RED + "You need 100 magic to cast GOLEMSUMMON!");
			}
			} else if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".thundershock") != false) {
				if(score.getScore() >= 75) {
				Block block = player.getTargetBlock((HashSet<Byte>)null, 200);
				Location blockL = block.getLocation();
				player.getLocation().getWorld().strikeLightning(blockL);
				score.setScore(score.getScore() - 75);
				} else {
					player.sendMessage(ChatColor.RED + "You need 75 magic to cast THUNDERSHOCK!");
				}
			} else if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".familiarsummon") != false) {
				if(score.getScore() >= 25) {
					if(wolfHM.get(wolfP.get(player)) == player) {
						player.sendMessage(ChatColor.RED + "You can only have one familiar spawned at a time!");
						return;
					}
				//COPIED AND CHANGED FROM IRON GOLEM!
				wolfB.put(player, false);
				double ox = player.getLocation().getX();
				double oy = player.getLocation().getY();
				double oz = player.getLocation().getZ();
				World ow = player.getWorld();
				wolfP.put(player, player.getWorld().spawn(new Location(ow, ox + 2, oy + 1, oz), Wolf.class));
				final Wolf wolf = wolfP.get(player);
				wolf.setTamed(true);
				wolf.setOwner(player);
				wolf.setLeashHolder(player);
				wolfTime.put(wolf, 30);
				wolfHM.put(wolf, player);
				wolf.setMaxHealth(21);
				wolf.setHealth(21);
				score.setScore(score.getScore() - 25);
				
				wolfTimerSystemInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() { //WOLF TIMER
					@Override
					public void run() {
						if(wolfTimerSystemIntHM.get(player) == null || wolfTimerSystemIntHM.get(player) == 0) {
							wolfTimerSystemIntHM.put(player, wolfTimerSystemInt);
							}
						if(wolfTime.get(wolf) <= 0) {
							wolf.setLeashHolder(null);
							if(wolfB.get(player) == false) {
								//TODO: wolfB.replace(player, true);	
							Bukkit.getScheduler().cancelTask(wolfTimerSystemIntHM.get(player));
							Bukkit.getScheduler().cancelTask(wolfHealthSystemIntHM.get(player));
							Bukkit.getScheduler().cancelTask(wolfTargetingSystemIntHM.get(player));
							wolfTargetingSystemIntHM.put(player, 0);
							wolfTimerSystemIntHM.put(player, 0);
							wolfHealthSystemIntHM.put(player, 0);
							player.sendMessage(ChatColor.RED + "Your Familiar has run out of time!");
							if(wolf.isDead()) {
							} else {
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
						} else {
							// wolfTime.replace(wolf, wolfTime.get(wolf) - 1); //TODO
						}
						}
				}, 0, 20);
				wolfTargetingSystemInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() { //FAMILIAR TARGETING SYSTEM 
					@Override
					public void run() {
						if(wolfTargetingSystemIntHM.get(player) == null || wolfTargetingSystemIntHM.get(player) == 0) {
							wolfTargetingSystemIntHM.put(player, wolfTargetingSystemInt);
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
										|| mob instanceof Ocelot || mob instanceof Villager) { //TODO might add on to this
									
								} else {
								if(wolfHM.get(wolf) != mob) {
									wolf.setTarget(mob);
								}
								}
							}
							if(wolf.getTarget() == null) {
							}
						}
					}
				}, 0, 5);
				wolfHealthSystemInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() { //WOLF HEALTH SYSTEM
					@Override
					public void run() {
						if(wolfHealthSystemIntHM.get(player) == null || wolfHealthSystemIntHM.get(player) == 0) {
							wolfHealthSystemIntHM.put(player, wolfHealthSystemInt);
							}
						if(wolf.getHealth() <= 1) {
							wolf.setLeashHolder(null);
							if(wolf.getKiller() != null) {
							player.sendMessage(ChatColor.DARK_RED + "Your Wolf was slain by " + wolf.getKiller().getName().toString());
							}
							if(wolfB.get(player) == false) {
								//TODO: wolfB.replace(player, true);	
							Bukkit.getScheduler().cancelTask(wolfTimerSystemIntHM.get(player));
							Bukkit.getScheduler().cancelTask(wolfHealthSystemIntHM.get(player));
							Bukkit.getScheduler().cancelTask(wolfTargetingSystemIntHM.get(player));
							wolfTargetingSystemIntHM.put(player, 0);
							wolfTimerSystemIntHM.put(player, 0);
							wolfHealthSystemIntHM.put(player, 0);
							if(wolf.isDead()) {
							} else {
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
						}
					}
				}, 0, 1);
				} else {
					player.sendMessage(ChatColor.RED + "You need 25 magic to summon a familiar");
				}
			} else if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".firerune") != false) { //TODO setup commands for this!
				Block block1 = player.getTargetBlock((HashSet<Byte>)null, 200);
				Location blockL1 = block1.getLocation();
				ItemStack rune = new ItemStack(Material.FIREBALL);
				if(runeP.containsKey(player)) {
					player.sendMessage(ChatColor.RED + "You can only have one rune active at a time!");
					return;
				}
				if(score.getScore() >= 35) {
				final Item fireCharge = player.getWorld().dropItemNaturally(blockL1, rune);
				wasSpawnedIn.add(fireCharge);
				runeP.put(player, fireCharge);
				score.setScore(score.getScore() - 35);
				fireRuneTimerInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() { //RUNE TIMER AND TARGETTING
					@Override
					public void run() {
						fireRuneTimerIntHM.put(player, fireRuneTimerInt);
						if(wasSpawnedIn.contains(fireCharge)) {
							for(Entity entity : fireCharge.getNearbyEntities(5, 5, 5)) {
								if(entity instanceof LivingEntity) {
									if(runeHM.get(fireCharge) == player) {
									fireCharge.remove();
									entity.setFireTicks(200);
									((LivingEntity) entity).damage(2);
									wasSpawnedIn.remove(fireCharge);
										}
								}
							}
						} else { 
							Bukkit.getScheduler().cancelTask(fireRuneTimerIntHM.get(player));
							fireRuneTimerIntHM.remove(player);
							runeP.remove(player);
							player.sendMessage(ChatColor.GREEN + "Someone has trigged your fire rune!");
						}
					}
				}, 0, 1);
				} else {
					player.sendMessage(ChatColor.RED + "You need 35 magic to cast a fire rune!");
				}
				
			} else if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".icerune") != false) {
				if(runeP.containsKey(player)) {
					player.sendMessage(ChatColor.RED + "You can only have one rune active at a time!");
					return;
				}
				if(score.getScore() >= 35) {
				Block block = player.getTargetBlock((HashSet<Byte>)null, 200);
				Location blockL = block.getLocation();
				ItemStack rune = new ItemStack(Material.SNOW_BALL);
				final Item snowBall = player.getWorld().dropItemNaturally(blockL, rune);
				wasSpawnedIn.add(snowBall);
				runeP.put(player, snowBall);
				score.setScore(score.getScore() - 35);
				iceRuneTimerInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() { //RUNE TIMER AND TARGETTING
					@Override
					public void run() {
						iceRuneTimerIntHM.put(player, iceRuneTimerInt);
						if(wasSpawnedIn.contains(snowBall)) {
							for(Entity entity : snowBall.getNearbyEntities(5, 5, 5)) {
								if(entity instanceof LivingEntity) {
									if(runeHM.get(snowBall) == player) {
									((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
									((LivingEntity) entity).damage(2);
									wasSpawnedIn.remove(snowBall);
									snowBall.remove();
									}
								}
							}
						} else {
							Bukkit.getScheduler().cancelTask(iceRuneTimerIntHM.get(player));
							iceRuneTimerIntHM.remove(player);
							runeP.remove(player);
							player.sendMessage(ChatColor.GREEN + "Someone has trigged your ice rune!");
						}
					}
				}, 0, 1);
				} else {
					player.sendMessage(ChatColor.RED + "You need 35 magic to cast a ice rune!");
				}
			} else if(settings.getRaces().getBoolean("magic." + player.getUniqueId() + ".shockrune") != false) {
				if(runeP.containsKey(player)) {
					player.sendMessage(ChatColor.RED + "You can only have one rune active at a time!");
					return;
				}
				if(score.getScore() >= 35) {
				Block block = player.getTargetBlock((HashSet<Byte>)null, 200);
				Location blockL = block.getLocation();
				ItemStack rune = new ItemStack(Material.NETHER_STAR);
				final Item netherStar = player.getWorld().dropItemNaturally(blockL, rune);
				wasSpawnedIn.add(netherStar);
				runeP.put(player, netherStar);
				runeHM.put(netherStar, player);
				score.setScore(score.getScore() - 35);
				shockRuneTimerInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() { //RUNE TIMER AND TARGETTING
					@Override
					public void run() {
						shockRuneTimerIntHM.put(player, shockRuneTimerInt);
						if(wasSpawnedIn.contains(netherStar)) {
							for(Entity entity : netherStar.getNearbyEntities(5, 5, 5)) {
								if(entity instanceof LivingEntity) {
									if(entity instanceof Player) {
										if(runeHM.get(netherStar) == player) {
											entity.getWorld().strikeLightningEffect(entity.getLocation());
											((LivingEntity) entity).damage(4);
											wasSpawnedIn.remove(netherStar);
											netherStar.remove();
											Bukkit.getScheduler().cancelTask(shockRuneTimerIntHM.get(player));
											shockRuneTimerIntHM.remove(player);
											runeP.remove(player);
											player.sendMessage(ChatColor.GREEN + "Someone has trigged your shock rune!");
										Player defender = ((Player) entity).getPlayer();
										Scoreboard boardD = defender.getScoreboard();
										Objective magicD = boardD.getObjective("Magic");
										Score scoreD = magicD.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Magic:"));
										scoreD.setScore(scoreD.getScore() - 15);
										}
										
									} else {
										if(!runeP.containsKey(entity)) {
										entity.getWorld().strikeLightningEffect(entity.getLocation());
										((LivingEntity) entity).damage(4);
										wasSpawnedIn.remove(netherStar);
										netherStar.remove();
										Bukkit.getScheduler().cancelTask(shockRuneTimerIntHM.get(player));
										shockRuneTimerIntHM.remove(player);
										runeP.remove(player);
										player.sendMessage(ChatColor.GREEN + "Someone has trigged your shock rune!");
										}
										}
									
								}
							}
						}
					}
				}, 0, 1);
				} else {
					player.sendMessage(ChatColor.RED + "You need 35 magic to cast a shock rune!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have a spell selected!");
			}
			}
		}
		
		
		
	}

	
	
}

