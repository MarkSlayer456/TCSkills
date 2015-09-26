package eu.tamrielcraft.TCSkills.races;

import java.util.Collection;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class Imperial extends Race implements Listener {
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	static Imperial instance = new Imperial();
    
    public static Imperial getInstance() {
            return instance;
    }
    
    @Override
	public String raceName() {
		return "Imperial";
	}

	@Override
	public String raceNameChat() {
		return "[" + raceName() +"]";
	}
	
	@Override
	public String formatChat(String s) {
		return s.replace(s, raceNameChat() + s);
	}
    
    @Override
    public void sendWelcome(Player player){
    	player.sendMessage(ChatColor.GOLD + "Welcome back " + ChatColor.DARK_RED + "[Imperial] " + player.getName().toString() + ChatColor.GOLD + " to " + ChatColor.AQUA + "TamerialCraft!");
    }
    
    public static String details() {
		return "More luck";
	}
    
	// Events
	// This is not a eventListener. The EventListener uses this methods
	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
			
	}
	
	@Override
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {
	}
	
	
	@EventHandler
	public void playerBreakBlockEvent(BlockBreakEvent e) {
		Player player = (Player) e.getPlayer();
		if(settings.getSave().get("imperials." + player.getUniqueId()) != null) {
			if(e.getBlock().getType() == Material.DIAMOND_ORE || e.getBlock().getType() == Material.LAPIS_ORE
					|| e.getBlock().getType() == Material.EMERALD_ORE || e.getBlock().getType() == Material.QUARTZ_ORE 
					|| e.getBlock().getType() == Material.REDSTONE_ORE || e.getBlock().getType() == Material.COAL_ORE) {
			Collection<ItemStack> drops = e.getBlock().getDrops();
			Random r = new Random();
			int nr = r.nextInt(10) + 1; //could change this if you didn't want it so often!
			switch(nr) {
			case 1:
				for(ItemStack item : drops) {
					player.getInventory().addItem(item);
					player.updateInventory();
					player.sendMessage(ChatColor.GOLD + "You found extra material thanks to your good luck");
					}
			}
			}
		}
	}

	@Override
	public void potionThrowEvent(PotionSplashEvent e, Player player) { }

	@Override
	public void playerMoveEvent(PlayerMoveEvent e, Player player) {
		
		
	}

	@Override
	public void onPlayerJoinEvent(PlayerJoinEvent e, Player player) {
		
		
	}

	@Override
	public void playerBurnEvent(EntityDamageEvent e, Player player) { }

	@Override
	public void addStartingBonusses(Player player) {
		
		
	}
}
