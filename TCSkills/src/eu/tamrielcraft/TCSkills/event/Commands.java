package eu.tamrielcraft.TCSkills.event;

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

public class Commands implements CommandExecutor{
	
	private Plugin plugin;
	private SettingsManager settings;
	Argonian argonian = new Argonian();
	Breton breton = new Breton();
	HighElves highelves = new HighElves();
	Khajiit khajiit = new Khajiit();
	Nords nords = new Nords();
	RedGuard redguard = new RedGuard();
	WoodElves woodelves = new WoodElves();
	
	public Commands(Plugin plugin, SettingsManager settings){
		this.plugin = plugin;
		this.settings = settings;
		Bukkit.getServer().getPluginManager().registerEvents(new Orcs(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new DarkElves(), plugin);
		Bukkit.getServer().getPluginManager().registerEvents(new Imperials(), plugin);
	}

	@Override
	public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
		final Player player = (Player) Sender;
		UUID id = player.getUniqueId(); //TODO change all player.getUniqueId() to just id no idea why i didn't do this from the start -_-
		FileConfiguration save = settings.getSave(); //lets just save ourselves from doing settings.getSave() a bunch XD
		
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
					if(save.get("magic." + player.getUniqueId() + ".favorites.amount") == null) {
						save.set("magic." + player.getUniqueId() + ".favorites.amount", 1);
						save.set("magic." + player.getUniqueId() + ".favorites.holder." + save.getInt("magic." + player.getUniqueId() + ".favorites.amount"), args[1]);
						player.sendMessage(ChatColor.GREEN + "Spell favorited, right click with a stick to cycle through your favorited spells!");
						settings.saveSave();
					} else {
					save.set("magic." + player.getUniqueId() + ".favorites.amount", save.getInt("magic." + player.getUniqueId() + ".favorites.amount") + 1);
					save.set("magic." + player.getUniqueId() + ".favorites.holder." + save.getInt("magic." + player.getUniqueId() + ".favorites.amount"), args[1]);
					player.sendMessage(ChatColor.GREEN + "Spell favorited, right click with a stick to cycle through your favorited spells!");
					settings.saveSave();
					}
					
				} else {
					player.sendMessage(ChatColor.RED + "Invalid spell name make sure you used the whole spell name! /favorite spell <full spell name>");
				}
			} else if(args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {
				if(save.get("magic." + player.getUniqueId() + ".favorites.holder.1") != null) {
				player.sendMessage(ChatColor.AQUA + "----- Favorites -----");
				player.sendMessage(ChatColor.GREEN + "1: " + save.get("magic." + player.getUniqueId() + ".favorites.holder.1"));
				} else {
					player.sendMessage(ChatColor.RED + "You have no spells favorited /favorite spell <full spell name>");
				}
				if(save.get("magic." + player.getUniqueId() + ".favorites.holder.2") != null) {
				player.sendMessage(ChatColor.GREEN + "2: " + save.get("magic." + player.getUniqueId() + ".favorites.holder.2"));
				}
				if(save.get("magic." + player.getUniqueId() + ".favorites.holder.3") != null) {
				player.sendMessage(ChatColor.GREEN + "3: " + save.get("magic." + player.getUniqueId() + ".favorites.holder.3"));
				}
				return true;
				
			} else if(args[0].equalsIgnoreCase("replace") || args[0].equalsIgnoreCase("r")) { //maybe make this /fav spell replace blah blah blah
				if(args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("3")) {
					if(args[2].equalsIgnoreCase("fireball") || args[2].equalsIgnoreCase("iceblast") ||
							args[2].equalsIgnoreCase("fasthealing") || args[2].equalsIgnoreCase("golemsummon") ||
							args[2].equalsIgnoreCase("familiarsummon") || args[2].equalsIgnoreCase("thundershock") 
							|| args[2].equalsIgnoreCase("firerune") || args[2].equalsIgnoreCase("icerune")
							|| args[2].equalsIgnoreCase("shockrune")) {
						save.set("magic." + player.getUniqueId() + ".favorites.holder." + args[1], args[2]);
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
				if(save.getBoolean("magic." + player.getUniqueId() + ".fireball") == true) {
					save.set("magic." + player.getUniqueId() + ".fireball", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set("magic." + player.getUniqueId() + ".fireball", true);
				save.set("magic." + player.getUniqueId() + ".iceblast", false);
				save.set("magic." + player.getUniqueId() + ".fasthealing", false);
				save.set("magic." + player.getUniqueId() + ".golemsummon", false);
				save.set("magic." + player.getUniqueId() + ".thundershock", false);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
				save.set("magic." + player.getUniqueId() + ".firerune", false);
				save.set("magic." + player.getUniqueId() + ".icerune", false);
				save.set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FIREBALL " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("iceblast") || args[0].equalsIgnoreCase("ib")) {
				if(save.getBoolean("magic." + player.getUniqueId() + ".iceblast") == true) {
					save.set("magic." + player.getUniqueId() + ".iceblast", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set("magic." + player.getUniqueId() + ".fireball", false);
				save.set("magic." + player.getUniqueId() + ".iceblast", true);
				save.set("magic." + player.getUniqueId() + ".fasthealing", false);
				save.set("magic." + player.getUniqueId() + ".golemsummon", false);
				save.set("magic." + player.getUniqueId() + ".thundershock", false);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
				save.set("magic." + player.getUniqueId() + ".firerune", false);
				save.set("magic." + player.getUniqueId() + ".icerune", false);
				save.set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "ICEBLAST " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("fasthealing") || args[0].equalsIgnoreCase("fh")) {
				if(save.getBoolean("magic." + player.getUniqueId() + ".fasthealing") == true) {
					save.set("magic." + player.getUniqueId() + ".fasthealing", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set("magic." + player.getUniqueId() + ".fireball", false);
				save.set("magic." + player.getUniqueId() + ".iceblast", false);
				save.set("magic." + player.getUniqueId() + ".fasthealing", true);
				save.set("magic." + player.getUniqueId() + ".golemsummon", false);
				save.set("magic." + player.getUniqueId() + ".thundershock", false);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
				save.set("magic." + player.getUniqueId() + ".firerune", false);
				save.set("magic." + player.getUniqueId() + ".icerune", false);
				save.set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FASTHEALING " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("golemsummon") || args[0].equalsIgnoreCase("gs")) {
				if(save.getBoolean("magic." + player.getUniqueId() + ".golemsummon") == true) {
					save.set("magic." + player.getUniqueId() + ".golemsummon", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set("magic." + player.getUniqueId() + ".fireball", false);
				save.set("magic." + player.getUniqueId() + ".iceblast", false);
				save.set("magic." + player.getUniqueId() + ".fasthealing", false);
				save.set("magic." + player.getUniqueId() + ".golemsummon", true);
				save.set("magic." + player.getUniqueId() + ".thundershock", false);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
				save.set("magic." + player.getUniqueId() + ".firerune", false);
				save.set("magic." + player.getUniqueId() + ".icerune", false);
				save.set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "GOLEMSUMMON " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("thundershock") || args[0].equalsIgnoreCase("ts")) {
				if(save.getBoolean("magic." + player.getUniqueId() + ".thundershock") == true) {
					save.set("magic." + player.getUniqueId() + ".thundershock", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set("magic." + player.getUniqueId() + ".fireball", false);
				save.set("magic." + player.getUniqueId() + ".iceblast", false);
				save.set("magic." + player.getUniqueId() + ".fasthealing", false);
				save.set("magic." + player.getUniqueId() + ".golemsummon", false);
				save.set("magic." + player.getUniqueId() + ".thundershock", true);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
				save.set("magic." + player.getUniqueId() + ".firerune", false);
				save.set("magic." + player.getUniqueId() + ".icerune", false);
				save.set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "THUNDERSHOCK " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("familiarsummon") || args[0].equalsIgnoreCase("fs")) {
				if(save.getBoolean("magic." + player.getUniqueId() + ".familiarsummon") == true) {
					save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set("magic." + player.getUniqueId() + ".fireball", false);
				save.set("magic." + player.getUniqueId() + ".iceblast", false);
				save.set("magic." + player.getUniqueId() + ".fasthealing", false);
				save.set("magic." + player.getUniqueId() + ".golemsummon", false);
				save.set("magic." + player.getUniqueId() + ".thundershock", false);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", true);
				save.set("magic." + player.getUniqueId() + ".firerune", false);
				save.set("magic." + player.getUniqueId() + ".icerune", false);
				save.set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FAMILIAR " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("firerune") || args[0].equalsIgnoreCase("fr"))  {
				if(save.getBoolean("magic." + player.getUniqueId() + ".firerune") == true) {
					save.set("magic." + player.getUniqueId() + ".firerune", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set("magic." + player.getUniqueId() + ".fireball", false);
				save.set("magic." + player.getUniqueId() + ".iceblast", false);
				save.set("magic." + player.getUniqueId() + ".fasthealing", false);
				save.set("magic." + player.getUniqueId() + ".golemsummon", false);
				save.set("magic." + player.getUniqueId() + ".thundershock", false);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
				save.set("magic." + player.getUniqueId() + ".firerune", true);
				save.set("magic." + player.getUniqueId() + ".icerune", false);
				save.set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "FIRERUNE " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("icerune") || args[0].equalsIgnoreCase("ir")) {
				if(save.getBoolean("magic." + player.getUniqueId() + ".icerune") == true) {
					save.set("magic." + player.getUniqueId() + ".icerune", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set("magic." + player.getUniqueId() + ".fireball", false);
				save.set("magic." + player.getUniqueId() + ".iceblast", false);
				save.set("magic." + player.getUniqueId() + ".fasthealing", false);
				save.set("magic." + player.getUniqueId() + ".golemsummon", false);
				save.set("magic." + player.getUniqueId() + ".thundershock", false);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
				save.set("magic." + player.getUniqueId() + ".firerune", false);
				save.set("magic." + player.getUniqueId() + ".icerune", true);
				save.set("magic." + player.getUniqueId() + ".shockrune", false);
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "ICERUNE " + ChatColor.GOLD + "spell");
			} else if(args[0].equalsIgnoreCase("shockrune") || args[0].equalsIgnoreCase("sr")) {
				if(save.getBoolean("magic." + player.getUniqueId() + ".shockrune") == true) {
					save.set("magic." + player.getUniqueId() + ".shockrune", false);
					player.sendMessage(ChatColor.GOLD + "Spell unequipped");
					return true;
				}
				save.set("magic." + player.getUniqueId() + ".fireball", false);
				save.set("magic." + player.getUniqueId() + ".iceblast", false);
				save.set("magic." + player.getUniqueId() + ".fasthealing", false);
				save.set("magic." + player.getUniqueId() + ".golemsummon", false);
				save.set("magic." + player.getUniqueId() + ".thundershock", false);
				save.set("magic." + player.getUniqueId() + ".familiarsummon", false);
				save.set("magic." + player.getUniqueId() + ".firerune", false);
				save.set("magic." + player.getUniqueId() + ".icerune", false);
				save.set("magic." + player.getUniqueId() + ".shockrune", true);
				settings.saveSave();
				player.sendMessage(ChatColor.GOLD + "You have selected the " + ChatColor.RED + "SHOCKRUNE " + ChatColor.GOLD + "spell");
				
			} else {
				player.sendMessage(ChatColor.RED + "Usage: /spell <spell name>");
			} 
		}
		
		//TODO finish /class command
		if(cmd.getName().equalsIgnoreCase("class") || cmd.getName().equalsIgnoreCase("c")) {
			if(args.length != 1) {
				player.sendMessage(ChatColor.AQUA + settings.getConfig().getString("Header"));
				player.sendMessage(ChatColor.RED + "Dwarf: " + "..."); //TODO NO IDEA WHAT YOU WANT FOR THIS SO I'LL JUST LEAVE IT UP TO YOU!
			} else {
				if(args[0].equalsIgnoreCase("dwarf") || args[0].equalsIgnoreCase("dwarves") || /* just in case someone doesn't know how to spell ^_^ */ args[0].equalsIgnoreCase("dwarfs")) {
					save.set(id + ".class", "dwarf");
				} else if(args[0].equalsIgnoreCase("<racename>")) {
					
					
				}
				
				
				
				/*  else if(args[0].equalsIgnoreCase("list") {
				 *  
				 *  
				 *  }
				 * 
				 */
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
					save.set("nords." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "nord");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Nord" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("orcs") || args[0].equalsIgnoreCase("orc")) {
					save.set("orcs." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "orc");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Orc" + ChatColor.GOLD + "!");
					player.setMaxHealth(24);
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("argonians") || args[0].equalsIgnoreCase("argonian")) {
					save.set("argonians." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "argonian");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Argonian" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("bretons") || args[0].equalsIgnoreCase("breton")) {
					save.set("bretons." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "breton");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Breton" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("darkelves") || args[0].equalsIgnoreCase("darkelf")) {
					save.set("darkelves." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "darkelf");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "DarkElf" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("highelves") || args[0].equalsIgnoreCase("highelf")) {
					save.set("highelves." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "highelf");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "HighElf" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;	
				} else if(args[0].equalsIgnoreCase("imperials") || args[0].equalsIgnoreCase("imperial")) {
					save.set("imperials." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "imperial");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Imperial" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("khajiits") || args[0].equalsIgnoreCase("khajiit")) {
					save.set("khajiits." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "khajiit");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "Khajiit" + ChatColor.GOLD + "!");
					settings.saveSave();
					return true;
				} else if(args[0].equalsIgnoreCase("redguards") || args[0].equalsIgnoreCase("redguard")) {
					save.set("redguards." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "redguard");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "RedGuard" + ChatColor.GOLD + "!");
					Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() { //TODO MAKE SURE THERE IS A WAY TO DISABLE THIS
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
					save.set("woodelves." + player.getUniqueId(), player.getName().toString());
					save.set(player.getUniqueId() + "", true);
					//TODO settings.getRaces().set(id + "." + race, "woodelf");
					player.sendMessage(ChatColor.GOLD + "You are now a " + ChatColor.RED + "WoodElf" + ChatColor.GOLD + "!");
					settings.saveSave();
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
