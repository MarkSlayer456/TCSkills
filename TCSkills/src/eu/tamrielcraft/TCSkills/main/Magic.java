package eu.tamrielcraft.TCSkills.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import eu.tamrielcraft.TCKills.spells.FamiliarSummon;

public class Magic implements Listener {
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	public static HashMap<IronGolem, Player> golemHM = new HashMap<IronGolem, Player>();
	public static HashMap<IronGolem, Integer> golemTime = new HashMap<IronGolem, Integer>();
	public static HashMap<Player, Integer> golemSystemsIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, IronGolem> golemP = new HashMap<Player, IronGolem>();
	public static HashMap<Player, Boolean> golemB = new HashMap<Player, Boolean>();
	
	public static HashMap<Wolf, Player> wolfHM = new HashMap<Wolf, Player>();
	public static HashMap<Wolf, Integer> wolfTime = new HashMap<Wolf, Integer>();
	public static HashMap<Player, Integer> wolfSystemsIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Wolf> wolfP = new HashMap<Player, Wolf>();
	public static HashMap<Player, Boolean> wolfB = new HashMap<Player, Boolean>();
	
	public static HashMap<Player, Item> runeP = new HashMap<Player, Item>();
	public static HashMap<Player, Integer> fireRuneTimerIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> iceRuneTimerIntHM = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> shockRuneTimerIntHM = new HashMap<Player, Integer>();
	public static ArrayList<Item> wasSpawnedIn = new ArrayList<Item>();
	public static HashMap<Item, Player> runeHM = new HashMap<Item, Player>();
	
	//TODO MAKE RUNES BLOCK ENTITIES (FIRECHARGE)
	
	private int fireRuneTimerInt;
	private int iceRuneTimerInt;
	private int shockRuneTimerInt;
	
	static int golemSystemsInt;
	
	static int wolfSystemsInt;
	
	@EventHandler	
	public void onPickUpEvent1(PlayerPickupItemEvent e) {
		Player player = (Player) e.getPlayer();
		if(runeP.containsKey(player)) {
			if(wasSpawnedIn.contains(e.getItem())) {
				e.setCancelled(true);
			}
		}
	}
	
	//TODO public boolean regeningMagic = false; don't think this is needed
	
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
		UUID id = player.getUniqueId();
		FileConfiguration save = settings.getSave();
		if(e.getAction() == Action.RIGHT_CLICK_AIR && player.getItemInHand().getType() == Material.STICK || e.getAction() == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().getType() == Material.STICK) {
			if(settings.getConfig().getBoolean("enableSpells") == false) {
				return;
			}
			if(save.get(id + ".magic.favorites.holder.1") == null) {
				player.sendMessage(ChatColor.RED + "You have no spells favorited! Do /favorite spell <spell name> to favorite some!");
			} else {
					if(save.getInt(id + ".magic.favorites.on") >= 3) {
						save.set(id + ".magic.favorites.on", 0);
					}
					if(save.get(id + ".magic.favorites.on") == null) {
						save.set(id + ".magic.favorites.on", 0);
					}
				save.set(id + ".magic.activeSpell", null);
				save.set(id + ".magic.favorites.on", save.getInt(id + ".magic.favorites.on") + 1);
				if(save.getInt(id + ".magic.favorites.amount") < save.getInt(id + ".magic.favorites.on")) {
					save.set(id + "magic.favorites.on", 1);
					settings.saveSave();
				}
				String spell = save.getString(id + ".magic.favorites.holder." + save.get(id + ".magic.favorites.on"));
				settings.saveSave();
				Bukkit.dispatchCommand(player, "spell " + spell);
			}
		}
		if(e.getAction() == Action.LEFT_CLICK_AIR && player.getItemInHand().getType() == Material.STICK) {
			if(player.getScoreboard() != null) {
				Scoreboard board = player.getScoreboard();
				Objective magic = board.getObjective("Magic");
				Score score = magic.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN + "Magic:"));
			if(save.getString(id + ".magic.activeSpell").equalsIgnoreCase("fireball")) {
				if(score.getScore() >= 10) {
					player.launchProjectile(SmallFireball.class);
					score.setScore(score.getScore() - 10);
				} else {
					player.sendMessage(ChatColor.RED + "You need 10 magic to cast FIREBALL!");
				}
			} else if(save.getString(id + ".magic.activeSpell").equalsIgnoreCase("iceblast")) {
				if(score.getScore() >= 8) {
					player.launchProjectile(Snowball.class);
					score.setScore(score.getScore() - 8);
				} else {
					player.sendMessage(ChatColor.RED + "You need 8 magic to cast ICEBLAST!");
				}
			} else if(save.getString(id + ".magic.activeSpell").equalsIgnoreCase("fasthealing")) {
				if(score.getScore() >= 25) {
					if(player.getHealth() != player.getMaxHealth()) { //TODO probably a easier way to do this just to lazy it was really easy to just copy and paste XD
					player.setHealth(player.getHealth() + 1);
					score.setScore(score.getScore() - 25);
					}
					if(player.getHealth() != player.getMaxHealth()) {
						player.setHealth(player.getHealth() + 1);
						score.setScore(score.getScore() - 25);
						}
				} else {
					player.sendMessage(ChatColor.RED + "You need 25 magic to cast FASTHEALING!");
				}
				
			} else if(save.getString(id + ".magic.activeSpell").equalsIgnoreCase("golemsummon")) {
				if(score.getScore() >= 100) {
					if(golemHM.get(golemP.get(player)) == player) {
						player.sendMessage(ChatColor.RED + "You can only have one golem spawned at a time!");
						return;
					}
			} else {
				player.sendMessage(ChatColor.RED + "You need 100 magic to cast GOLEMSUMMON!");
			}
			} else if(save.getString(id + ".magic.activeSpell").equalsIgnoreCase("thundershock")) {
				if(score.getScore() >= 75) {
				Block block = player.getTargetBlock((HashSet<Byte>)null, 200);
				Location blockL = block.getLocation();
				player.getLocation().getWorld().strikeLightning(blockL);
				score.setScore(score.getScore() - 75);
				} else {
					player.sendMessage(ChatColor.RED + "You need 75 magic to cast THUNDERSHOCK!");
				}
			} else if(save.getString(id + ".magic.activeSpell").equalsIgnoreCase("familiarsummon")) {
				if(score.getScore() >= 25) {
					if(wolfHM.get(wolfP.get(player)) == player) {
						player.sendMessage(ChatColor.RED + "You can only have one familiar spawned at a time!");
						return;
					}
				FamiliarSummon.setupFamiliar(player, score);
				wolfSystemsInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
					@Override
					public void run() {
						FamiliarSummon.runSystems(player, wolfSystemsInt);
					}
				}, 0, 1);
				} else {
					player.sendMessage(ChatColor.RED + "You need 25 magic to summon a familiar");
				}
			} else if(save.getString(id + ".magic.activeSpell").equalsIgnoreCase("firerune")) { //TODO setup commands for this!
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
				
			} else if(save.getString(id + ".magic.activeSpell").equalsIgnoreCase("icerune")) {
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
			} else if(save.getString(id + ".magic.activeSpell").equalsIgnoreCase("shockrune")) {
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

