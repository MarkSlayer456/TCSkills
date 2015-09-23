package eu.tamrielcraft.TCSkills.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.Plugin;

public class OneHanded extends Skill {

	@Override
	public void onCraftEvent(CraftItemEvent event) { }

	public void playerHitByPlayer(EntityDamageByEntityEvent e, Player attacker, Plugin plugin){
		
		
		
	}

	@Override
	public String getSkillName() {
		
		return null;
	}
	
	
}
