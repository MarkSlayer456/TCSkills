package eu.tamrielcraft.TCSkills.races;

import java.util.Collection;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class Imperials extends Race implements Listener {
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	static Imperials instance = new Imperials();
    
    public static Imperials getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[Imperials]" + s);
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
	
	public static String details() {
		return "More luck";
	}
}
