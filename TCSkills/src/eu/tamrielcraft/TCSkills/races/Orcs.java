package eu.tamrielcraft.TCSkills.races;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class Orcs extends Race implements Listener {

	static SettingsManager settings = SettingsManager.getInstance();
	
	static Orcs instance = new Orcs();
    
    public static Orcs getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[Orc]" + s);
	}
	
	@EventHandler
	public void playerEnchantEvent(PlayerLevelChangeEvent e) {
		Player player = (Player) e.getPlayer();
		if(settings.getRaces().get("orcs." + player.getUniqueId()) != null) {
			if(e.getNewLevel() < e.getOldLevel()) {
			player.sendMessage(ChatColor.GOLD + "Your orc powers helped you keep a level!");
			player.setLevel(player.getLevel() + 1);
			player.updateInventory(); //TODO might not need this
			}
		}
	}
	
	
	
	public static String details() {
		return "More health and receive 1 lvl after enchanting and repairing"; //TODO spell check and see if possible
	}

}
