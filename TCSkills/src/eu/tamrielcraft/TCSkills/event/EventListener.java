package eu.tamrielcraft.TCSkills.event;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
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
import eu.tamrielcraft.TCSkills.races.Breton;
import eu.tamrielcraft.TCSkills.races.HighElf;
import eu.tamrielcraft.TCSkills.races.Orc;
import eu.tamrielcraft.TCSkills.races.Race;
import eu.tamrielcraft.TCSkills.races.RedGuard;
import eu.tamrielcraft.TCSkills.skills.OneHanded;
import eu.tamrielcraft.TCSkills.skills.SkillTreeGUI;
import eu.tamrielcraft.TCSkills.skills.Smithing;

public class EventListener implements Listener {
	
	private Plugin plugin;
	private SettingsManager settings;
	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	
	// Material collections
	private List<Material> smithing = new ArrayList<Material>();

	public EventListener(Plugin plugin, SettingsManager settings) {
		this.settings = settings;
		this.plugin = plugin;
		addSmithingMaterials();
	}
		
	@EventHandler(priority = EventPriority.HIGH)
	public void onCraftItem(CraftItemEvent event){
		if(smithing.contains(event.getInventory().getResult().getType())){
			Smithing.getInstance().onCraftEvent(event);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		//TODO: This is not good code
		if(!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("SkillTree") ||
				ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("OneHanded")) {
			return;
		}
		Player player = (Player) e.getWhoClicked();
		e.setCancelled(true);
		if(e.getCurrentItem().getType() == null || e.getCurrentItem().getType() == Material.AIR) {
			return;
		}
		switch(e.getCurrentItem().getItemMeta().toString()) {
			case "OneHanded":
				SkillTreeGUI.skillTreeOpenOneHanded(player);
				break;
			case "armsman":
				
				if(OneHanded.canUpgradePerk("armsman", player) == true) {
					OneHanded.upgradePerk("armsman", player);
				}
		}
		
	}
	
	@EventHandler
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {
		if(settings.getConfig().getBoolean("enableAbilities") == false) {
			return;
		}
		Player player = (Player) e.getPlayer();
		Race race = settings.getRace(player);
		race.playerEnchantEvent(e);
	}
	
	@EventHandler
	public void playerBurnEvent(EntityDamageEvent e) {
		if(settings.getConfig().getBoolean("enableAbilities") == false) {
			return;
		}
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			Race race = settings.getRace(player);
			race.playerBurnEvent(e, player);
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		if(settings.getConfig().getBoolean("enableAbilities") == false) {
			return;
		}
		String format = e.getFormat();
		Player player = e.getPlayer();
		Race race = settings.getRace(player); 
		race.formatChat(format);		
		e.setFormat(format);
	}
	
	@EventHandler
	public void playerHitEvent(EntityDamageByEntityEvent e) {
		if(settings.getConfig().getBoolean("enableAbilities") == false) {
			return;
		}
		//TODO: move all to corresponding race classes
		if(e.getDamager() instanceof Player) {
			final Player attacker = (Player) e.getDamager();
			Race race = settings.getRace(attacker);
			race.playerHitByPlayer(e, attacker, plugin);
		} else if(e.getDamager() instanceof Projectile) {
				Projectile project = (Projectile) e.getDamager();
				if(project.getShooter() instanceof Player) {
					final Player player = (Player) project.getShooter(); //change this from shooter to player to make it work 
					Race race = settings.getRace(player);
					race.playerHitByPlayer(e, player, plugin);
				}
				/*if(project.getShooter() instanceof Player) { //TODO I think there is a bow hit event
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
				}*/
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
			race.playerHitByPlayer(e, player, plugin);
		} else {
			return;
		}
	}
	
	@EventHandler
	public void potionThrowEvent(PotionSplashEvent e) {
		if(settings.getConfig().getBoolean("enableAbilities") == false) {
			return;
		}
		for(LivingEntity entity : e.getAffectedEntities()) {
		if(entity instanceof Player) {
			 Player player = (Player) entity;
			 Race race = settings.getRace(player);
			 race.potionThrowEvent(e, player);
			 /*if(race == Argonian.getInstance() || race == Nord.getInstance()) {
				 for(PotionEffect p : e.getPotion().getEffects()) {
					 if(p.getType().equals(PotionEffectType.POISON) || p.getType().equals(PotionEffectType.CONFUSION) 
							 || p.getType().equals(PotionEffectType.SLOW) || p.getType().equals(PotionEffectType.HUNGER)
							 || p.getType().equals(PotionEffectType.SLOW_DIGGING) || p.getType().equals(PotionEffectType.WEAKNESS)
									 || p.getType().equals(PotionEffectType.WITHER)) {
						 player.sendMessage(ChatColor.RED + "You feel your skin trying to block the potion effect");
							 e.setIntensity(player, e.getIntensity(player) * 0.5); 
					 }
				 }
			 }*/ //TODO LEFT THIS BECAUSE I DON'T KNOW IF I DID IT CORRECTLY 
		}
		}
	}
	
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent e) {
		if(settings.getConfig().getBoolean("enableAbilities") == false) {
			return;
		}
		Player player = (Player) e.getPlayer();
		Race race = settings.getRace(player);
		race.playerMoveEvent(e, player);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(settings.getConfig().getBoolean("enableAbilities") == false) {
			return;
		}
		final Player player = (Player) e.getPlayer();
		final Race race = settings.getRace(player);
		
		if(race != null) {
			// Player has a race
			race.sendWelcome(player);
			
			//TODO If many more races have an initialize thingy, we should add this to each race class
			if(race == Orc.getInstance()) {
				player.setMaxHealth(24);
			}
			if(race == RedGuard.getInstance()) {
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
	
	public void addSmithingMaterials(){
		smithing.add(Material.LEATHER_BOOTS);
		smithing.add(Material.LEATHER_CHESTPLATE);
		smithing.add(Material.LEATHER_HELMET);
		smithing.add(Material.LEATHER_LEGGINGS);
		smithing.add(Material.CHAINMAIL_BOOTS);
		smithing.add(Material.CHAINMAIL_CHESTPLATE);
		smithing.add(Material.CHAINMAIL_HELMET);
		smithing.add(Material.CHAINMAIL_LEGGINGS);
		smithing.add(Material.IRON_BOOTS);
		smithing.add(Material.IRON_CHESTPLATE);
		smithing.add(Material.IRON_HELMET);
		smithing.add(Material.IRON_LEGGINGS);
		smithing.add(Material.GOLD_BOOTS);
		smithing.add(Material.GOLD_CHESTPLATE);
		smithing.add(Material.GOLD_HELMET);
		smithing.add(Material.GOLD_LEGGINGS);
		smithing.add(Material.DIAMOND_BOOTS);
		smithing.add(Material.DIAMOND_CHESTPLATE);
		smithing.add(Material.DIAMOND_HELMET);
		smithing.add(Material.DIAMOND_LEGGINGS);
		
		// Weapons
		smithing.add(Material.WOOD_AXE);
		smithing.add(Material.WOOD_SPADE);
		smithing.add(Material.WOOD_SWORD);
		smithing.add(Material.WOOD_HOE);
		smithing.add(Material.GOLD_AXE);
		smithing.add(Material.GOLD_SPADE);
		smithing.add(Material.GOLD_SWORD);
		smithing.add(Material.GOLD_HOE);
		smithing.add(Material.STONE_AXE);
		smithing.add(Material.STONE_SPADE);
		smithing.add(Material.STONE_SWORD);
		smithing.add(Material.STONE_HOE);
		smithing.add(Material.IRON_AXE);
		smithing.add(Material.IRON_AXE);
		smithing.add(Material.IRON_AXE);
		smithing.add(Material.IRON_AXE);
		smithing.add(Material.DIAMOND_AXE);
		smithing.add(Material.DIAMOND_SPADE);
		smithing.add(Material.DIAMOND_SWORD);
		smithing.add(Material.DIAMOND_HOE);
	}
}
