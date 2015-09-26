package eu.tamrielcraft.TCSkills.event;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import eu.tamrielcraft.TCKills.spells.FamiliarSummon;
import eu.tamrielcraft.TCKills.spells.FastHealing;
import eu.tamrielcraft.TCKills.spells.FireBall;
import eu.tamrielcraft.TCKills.spells.FireRune;
import eu.tamrielcraft.TCKills.spells.GolemSummon;
import eu.tamrielcraft.TCKills.spells.IceBlast;
import eu.tamrielcraft.TCKills.spells.IceRune;
import eu.tamrielcraft.TCKills.spells.ShockRune;
import eu.tamrielcraft.TCKills.spells.ThunderShock;
import eu.tamrielcraft.TCSkills.classes.Archer;
import eu.tamrielcraft.TCSkills.classes.Barbarian;
import eu.tamrielcraft.TCSkills.classes.Classes;
import eu.tamrielcraft.TCSkills.classes.Knight;
import eu.tamrielcraft.TCSkills.classes.Mage;
import eu.tamrielcraft.TCSkills.classes.Rogue;
import eu.tamrielcraft.TCSkills.main.SettingsManager;
import eu.tamrielcraft.TCSkills.races.Argonian;
import eu.tamrielcraft.TCSkills.races.Breton;
import eu.tamrielcraft.TCSkills.races.DarkElf;
import eu.tamrielcraft.TCSkills.races.HighElf;
import eu.tamrielcraft.TCSkills.races.Imperial;
import eu.tamrielcraft.TCSkills.races.Khajiit;
import eu.tamrielcraft.TCSkills.races.Nord;
import eu.tamrielcraft.TCSkills.races.Orc;
import eu.tamrielcraft.TCSkills.races.Race;
import eu.tamrielcraft.TCSkills.races.RedGuard;
import eu.tamrielcraft.TCSkills.races.WoodElf;
import eu.tamrielcraft.TCSkills.skills.SkillTreeGUI;

public class Commands implements CommandExecutor {
	
	private Plugin plugin;
	private SettingsManager settings;
	Argonian argonian = Argonian.getInstance();
	Breton breton = Breton.getInstance();
	HighElf highelves = HighElf.getInstance();
	Khajiit khajiit = Khajiit.getInstance();
	Nord nords = Nord.getInstance();
	RedGuard redguard = RedGuard.getInstance();
	WoodElf woodelves = WoodElf.getInstance();
	
	public static int redGuardFoodLoopInt;
	
	public static HashMap<Player, Integer> redGuardLoopHM = new HashMap<Player, Integer>();
	
	
	public Commands(Plugin plugin, SettingsManager settings) {
		this.plugin = plugin;
		this.settings = settings;
		Bukkit.getServer().getPluginManager().registerEvents(new Orc(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new DarkElf(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new Imperial(), plugin);
	}
	private Race getTCRace(String s){
		String race = s.toLowerCase();
		if(race != null){
 			switch(race) {
 	 		case "argonian":
 	 			return Argonian.getInstance();
 	 		case "breton":
 	 			return Breton.getInstance();
 	 		case "darkelf":
 	 			return DarkElf.getInstance();
 	 		case "highelf":
 	 			return HighElf.getInstance();
 	 		case "imperial":
 	 			return Imperial.getInstance();
 	 		case "khajiit":
 	 			return Khajiit.getInstance();
 	 		case "nord":
 	 			return Nord.getInstance();
 	 		case "orcs":
 	 			return Orc.getInstance();
 	 		case "redguard":
 	 			return RedGuard.getInstance();
 	 		case "woodelf":
 	 			return WoodElf.getInstance();
 	 		default:
 	 			return null;
 	 		}
  		} else {
  			return null;
  		}
	}
	
	private Classes getTCClass(String s){
		String classy = s.toLowerCase();
		switch (classy){
		case "archer":
			return Archer.getInstance();
   	 	case "barbarian":
   	 		return Barbarian.getInstance();
   	 	case "knight":
   	 		return Knight.getInstance();
   	 	case "mage":
   	 		return Mage.getInstance();
   	 	case "Rogue":
   	 		return Rogue.getInstance();
   	 	default:
   	 		return null;
   	 	}
	}

	@Override
	public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
		if(!(Sender instanceof Player)) {
			Sender.sendMessage("Console is not allowed to do any Skills related things");
			return true;
		}
		final Player player = (Player) Sender;
		UUID id = player.getUniqueId();
		FileConfiguration save = settings.getSave();
		String command = cmd.getName().toLowerCase();
		
		switch(command){ //TODO this is a bad idea because now you can't use shortcuts to do commands you have to type the full word not the biggest deal but still
		case "skills":
			SkillTreeGUI.skillTreeOpen(player);
			return true;
		case "starttc":
			if(args.length == 2){
				Race race = getTCRace(args[0]);
				Classes classy = getTCClass(args[1]);
				if(race != null){
					if(classy != null){
						settings.createPlayer(player, race, classy);
						player.sendMessage(ChatColor.GOLD + "Your are now a " + ChatColor.RED + race.raceName() + " " 
								+ classy.className() + ChatColor.GOLD + " and are ready to play. Have fun!");
						return true;
					}else{
						player.sendMessage(ChatColor.RED + "This class does not exist. Check /class for all available classes");
					}
				}else{
					player.sendMessage(ChatColor.RED + "This race does not exist. Check /race for all available races");
				}
			}
		case "tcskills": //TODO RECODE THIS
			return false;
		case "favorite":
				if(settings.getConfig().getBoolean("enableSpells") == false) {
					player.sendMessage(ChatColor.RED + "Spells are disabled on this server, this could be due to the fact that they are not working!");
				}
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
						if(save.get(id + ".magic.favorites.amount") == null) {
							save.set(id + ".magic.favorites.amount", 1);
							save.set(id + ".magic.favorites.holder." + save.getInt(id +  ".magic.favorites.amount"), args[1]);
							player.sendMessage(ChatColor.GREEN + "Spell favorited, right click with a stick to cycle through your favorited spells!");
							settings.saveSave();
						} else {
						save.set(id + ".magic.favorites.amount", save.getInt(id + ".magic.favorites.amount") + 1);
						save.set(id + ".magic.favorites.holder." + save.getInt(id + ".magic.favorites.amount"), args[1]);
						player.sendMessage(ChatColor.GREEN + "Spell favorited, right click with a stick to cycle through your favorited spells!");
						settings.saveSave();
						}
						
					} else {
						player.sendMessage(ChatColor.RED + "Invalid spell name make sure you used the whole spell name! /favorite spell <full spell name>");
					}
				} else if(args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {
					if(save.get(id + ".magic.favorites.holder.1") != null) {
					player.sendMessage(ChatColor.AQUA + "----- Favorites -----");
					player.sendMessage(ChatColor.GREEN + "1: " + save.get(id + ".magic.favorites.holder.1"));
					} else {
						player.sendMessage(ChatColor.RED + "You have no spells favorited /favorite spell <full spell name>");
					}
					if(save.get(id + ".magic.favorites.holder.2") != null) {
					player.sendMessage(ChatColor.GREEN + "2: " + save.get(id + ".magic.favorites.holder.2"));
					}
					if(save.get(id + ".magic.favorites.holder.3") != null) {
					player.sendMessage(ChatColor.GREEN + "3: " + save.get(id + ".magic.favorites.holder.3"));
					}
					return true;
					
				} else if(args[0].equalsIgnoreCase("replace") || args[0].equalsIgnoreCase("r")) { //maybe make this /fav spell replace blah blah blah
					if(args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("3")) {
						if(args[2].equalsIgnoreCase("fireball") || args[2].equalsIgnoreCase("iceblast") ||
								args[2].equalsIgnoreCase("fasthealing") || args[2].equalsIgnoreCase("golemsummon") ||
								args[2].equalsIgnoreCase("familiarsummon") || args[2].equalsIgnoreCase("thundershock") 
								|| args[2].equalsIgnoreCase("firerune") || args[2].equalsIgnoreCase("icerune")
								|| args[2].equalsIgnoreCase("shockrune")) {
							save.set(id + ".magic.favorites.holder." + args[1], args[2]);
							player.sendMessage(ChatColor.GREEN + "Spell replaced, right click with a stick to cycle through your favorited spells!");
							settings.saveSave();
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
		case "spell":
			
		case "magicregen":	
			
		}
		
		//TODO: should put it all into one switch statement
		
		if(cmd.getName().equalsIgnoreCase("class") || cmd.getName().equalsIgnoreCase("c")) {
			if(args.length == 0) {
				player.sendMessage(ChatColor.AQUA + settings.getConfig().getString("Header")); //TODO add/change descriptions to all of these i don't know if you want them to have a abilities 
				player.sendMessage(ChatColor.RED + "Archer: " + ChatColor.GOLD + Archer.getInstance().getDetails());
				player.sendMessage(ChatColor.RED + "Barbarian: " + ChatColor.GOLD + Barbarian.getInstance().getDetails());
				player.sendMessage(ChatColor.RED + "Knight: " + ChatColor.GOLD + Knight.getInstance().getDetails());
				player.sendMessage(ChatColor.RED + "Mage: " + ChatColor.GOLD + Mage.getInstance().getDetails());
				player.sendMessage(ChatColor.RED + "Rogue: " + ChatColor.GOLD + Rogue.getInstance().getDetails());
				return true;
			}/* else if(args.length >= 2) {
				player.sendMessage(ChatColor.RED + "Usage: /class <class name>");
			}*/
			
			/*if(args[0].equalsIgnoreCase("archer") || args[0].equalsIgnoreCase("archers")) {
				save.set(id + ".class", "archer");
				player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Archer" + ChatColor.GOLD + "!");
			} else if(args[0].equalsIgnoreCase("barbarian") || args[0].equalsIgnoreCase("barbarians")) {
				save.set(id + ".class", "barbarian");
				player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Barbarian " + ChatColor.GOLD + "!");
			} else if(args[0].equalsIgnoreCase("knight") || args[0].equalsIgnoreCase("knights")) {
				save.set(id + ".class", "knight");
				player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Knight" + ChatColor.GOLD + "!");
			} else if(args[0].equalsIgnoreCase("mage") || args[0].equalsIgnoreCase("mages")) {
				save.set(id + ".class", "mage");
				player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Mage" + ChatColor.GOLD + "!");
			} else if(args[0].equalsIgnoreCase("rogue") || args[0].equalsIgnoreCase("rogues")) {
				save.set(id + ".class", "rogue");
				player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Rogues" + ChatColor.GOLD + "!");
			} else if(args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {
				player.sendMessage(ChatColor.AQUA + settings.getConfig().getString("Header")); //TODO add/change descriptions to all of these i don't know if you want them to have a abilities 
				player.sendMessage(ChatColor.RED + "Archer: " + ChatColor.GOLD + "");
				player.sendMessage(ChatColor.RED + "Barbarian: " + ChatColor.GOLD + "");
				player.sendMessage(ChatColor.RED + "Knight: " + ChatColor.GOLD + "");
				player.sendMessage(ChatColor.RED + "Mage: " + ChatColor.GOLD + "");
				player.sendMessage(ChatColor.RED + "Rogue: " + ChatColor.GOLD + "Now you see me... now you don't");
			} else {
				player.sendMessage(ChatColor.RED + "Usage: /class <class name>");
				return true;
			}*/
			//settings.saveSave();
			return false;
		}
		
		
		  if(cmd.getName().equalsIgnoreCase("spell") || cmd.getName().equalsIgnoreCase("s")) {
			if(settings.getConfig().getBoolean("enableSpells") == false) {
				player.sendMessage(ChatColor.RED + "Spells are disabled on this server, this could be due to the fact that they are not working!");
			}
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
				if(settings.getSpell(player) == FireBall.getInstance()) {
					save.set(id + ".magic.activeSpell", null);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				/*save.set("magic." + player.getUniqueId() + ".fireball", true);
				save.set("magic." + player.getUniqueId() + ".iceblast", false);
				save.set("magic." + player.getUniqueId() + ".fasthealing", false);
				save.set("magic." + player.getUniqueId() + ".golemsummon", false);
				save.set("magic." + player.getUniqueId() + ".thundershock", false);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
				save.set("magic." + player.getUniqueId() + ".firerune", false);
				save.set("magic." + player.getUniqueId() + ".icerune", false);
				save.set("magic." + player.getUniqueId() + ".shockrune", false);*/
				//shorten this
				save.set(id + ".magic.activeSpell", "fireball");
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FIREBALL " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("iceblast") || args[0].equalsIgnoreCase("ib")) {
				if(settings.getSpell(player) == IceBlast.getInstance()) {
					save.set(id + ".magic.activeSpell", null);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set(id + ".magic.activeSpell", "iceblast");
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "ICEBLAST " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("fasthealing") || args[0].equalsIgnoreCase("fh")) {
				if(settings.getSpell(player) == FastHealing.getInstance()) {
					save.set(id + ".magic.activeSpell", null);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set(id + ".magic.activeSpell", "fasthealing");
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FASTHEALING " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("golemsummon") || args[0].equalsIgnoreCase("gs")) {
				if(settings.getSpell(player) == GolemSummon.getInstance()) {
					save.set(id + ".magic.activeSpell", null);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set(id + ".magic.activeSpell", "golemsummon");
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "GOLEMSUMMON " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("thundershock") || args[0].equalsIgnoreCase("ts")) {
				if(settings.getSpell(player) == ThunderShock.getInstance()) {
					save.set(id + ".magic.activeSpell", null);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set(id + ".magic.activeSpell", "thundershock");
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "THUNDERSHOCK " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("familiarsummon") || args[0].equalsIgnoreCase("fs")) {
				if(settings.getSpell(player) == FamiliarSummon.getInstance()) {
					save.set(id + ".magic.activeSpell", null);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set(id + ".magic.activeSpell", "familiarsummon");
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FAMILIAR " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("firerune") || args[0].equalsIgnoreCase("fr"))  {
				if(settings.getSpell(player) == FireRune.getInstance()) {
					save.set(id + ".magic.activeSpell", null);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set(id + ".magic.activeSpell", "firerune");
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FIRERUNE " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("icerune") || args[0].equalsIgnoreCase("ir")) {
				if(settings.getSpell(player) == IceRune.getInstance()) {
					save.set(id + ".magic.activeSpell", null);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set(id + ".magic.activeSpell", "icerune");
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "ICERUNE " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("shockrune") || args[0].equalsIgnoreCase("sr")) {
				if(settings.getSpell(player) == ShockRune.getInstance()) {
					save.set(id + ".magic.activeSpell", null);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set(id + ".magic.activeSpell", "shockrune");
				settings.saveSave();
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
				player.sendMessage(ChatColor.RED + "DarkElves: " + ChatColor.GOLD + DarkElf.details() + "!");
				player.sendMessage(ChatColor.RED + "HighElves: " + ChatColor.GOLD + highelves.details() + "!");
				player.sendMessage(ChatColor.RED + "Imperials: " + ChatColor.GOLD + Imperial.details() + "!");
				player.sendMessage(ChatColor.RED + "Khajiit: " + ChatColor.GOLD + khajiit.details() + "!");
				player.sendMessage(ChatColor.RED + "Nords: " + ChatColor.GOLD + nords.details() + "!");
				player.sendMessage(ChatColor.RED + "Orcs: " + ChatColor.GOLD + Orc.details() + "!");
				player.sendMessage(ChatColor.RED + "RedGuard: " + ChatColor.GOLD + redguard.details() + "!");
				player.sendMessage(ChatColor.RED + "WoodElves: " + ChatColor.GOLD + woodelves.details() + "!");
				return true;
			} /*else {
				if(args[0].equalsIgnoreCase("nords") || args[0].equalsIgnoreCase("nord")) {
					save.set(id + ".race", "nord");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Nord" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("orcs") || args[0].equalsIgnoreCase("orc")) {
					save.set(id + ".race", "orc");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Orc" + ChatColor.GOLD + "!");
					player.setMaxHealth(24);
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("argonians") || args[0].equalsIgnoreCase("argonian")) {
					save.set(id + ".race", "argonian");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Argonian" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("bretons") || args[0].equalsIgnoreCase("breton")) {
					save.set(id + ".race", "breton");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Breton" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("darkelves") || args[0].equalsIgnoreCase("darkelf")) {
					save.set(id + ".race", "darkelf");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "DarkElf" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("highelves") || args[0].equalsIgnoreCase("highelf")) {
					save.set(id + ".race", "highelf");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "HighElf" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;	
				} else if(args[0].equalsIgnoreCase("imperials") || args[0].equalsIgnoreCase("imperial")) {
					save.set(id + ".race", "imperial");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Imperial" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("khajiits") || args[0].equalsIgnoreCase("khajiit")) {
					save.set(id + ".race", "khajiit");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Khajiit" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("redguards") || args[0].equalsIgnoreCase("redguard")) {
					save.set(id + ".race", "redguard");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "RedGuard" + ChatColor.GOLD + "!");
					redGuardFoodLoopInt = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
						@Override
						public void run() {
							if(player.getFoodLevel() < 8) {
								player.setFoodLevel(8);
								player.updateInventory();
							}
						}	
					}, 10, 10);
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("woodelves") || args[0].equalsIgnoreCase("woodelf")) {
					save.set(id + ".race", "woodelf");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "WoodElf" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else */if(args[0].equalsIgnoreCase("list")) {
				player.sendMessage(ChatColor.AQUA + settings.getConfig().getString("Header"));
				player.sendMessage(ChatColor.GOLD + "You can be any of these races:");
				player.sendMessage(ChatColor.RED + "Argonian: " + ChatColor.GOLD + argonian.details() + "!");
				player.sendMessage(ChatColor.RED + "Breton: " + ChatColor.GOLD + breton.details() + "!");
				player.sendMessage(ChatColor.RED + "DarkElves: " + ChatColor.GOLD + DarkElf.details() + "!");
				player.sendMessage(ChatColor.RED + "HighElves: " + ChatColor.GOLD + highelves.details() + "!");
				player.sendMessage(ChatColor.RED + "Imperials: " + ChatColor.GOLD + Imperial.details() + "!");
				player.sendMessage(ChatColor.RED + "Khajiit: " + ChatColor.GOLD + khajiit.details() + "!");
				player.sendMessage(ChatColor.RED + "Nords: " + ChatColor.GOLD + nords.details() + "!");
				player.sendMessage(ChatColor.RED + "Orcs: " + ChatColor.GOLD + Orc.details() + "!");
				player.sendMessage(ChatColor.RED + "RedGuard: " + ChatColor.GOLD + redguard.details() + "!");
				player.sendMessage(ChatColor.RED + "WoodElves: " + ChatColor.GOLD + woodelves.details() + "!");
				return true;
				} else {
					player.sendMessage(ChatColor.RED + "Usage: /race <racename> or /race list");
				//}
			}
			
		}
		if(cmd.getName().equalsIgnoreCase("magicregen") || cmd.getName().equalsIgnoreCase("mr")) {
			if(player.isOp()) {
				Scoreboard board = player.getScoreboard();					
				Objective magic = board.getObjective("Magic");																	
				
				@SuppressWarnings("deprecation")
				final Score score = magic.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN +  "Magic:"));
				
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() { //REGENS MAGIC
					@Override
					public void run() {
					if(settings.getSave().get("bretons." + player.getUniqueId()) != null
							|| settings.getSave().get("highelves." + player.getUniqueId()) != null) {
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
