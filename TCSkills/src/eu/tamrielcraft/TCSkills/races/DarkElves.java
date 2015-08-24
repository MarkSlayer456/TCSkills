package eu.tamrielcraft.TCSkills.races;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class DarkElves implements Listener {
	static SettingsManager settings = SettingsManager.getInstance();
	
	@EventHandler
	public void playerBurnEvent(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if(settings.getRaces().get("darkelves." + player.getUniqueId()) != null) {
				if(player.getFireTicks() != 0) {
					e.setDamage(e.getDamage() / 2);
				}
			}
		}
	}
	
	public static String details() {
		return "50% resistance to fire and lava";
		
	}
	
}
