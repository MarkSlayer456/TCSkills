package eu.tamrielcraft.TCSkills.skills;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public abstract class Skill {
	
	protected Map<Integer,Integer> levelMapping;	
	protected SettingsManager settings = SettingsManager.getInstance();
	
	public abstract void onCraftEvent(CraftItemEvent event);
	public abstract String getSkillName();
	public abstract void advancePerkLevel(String perkName, Player player);
	protected abstract void setPerkLevel(String perkName, int perkLevel, Player player);
	
	protected int getLevel(int experience){
		int start = 0; 
		int temp = start;
		int count = 0;
		
		while(experience > temp){
			temp = (int) Math.floor(temp * 1.1 + 250);
			count++;
		}
		 
		return count;
	}
	
	protected Boolean hasPerk(Skill skill, String perkName, int perkLevel, Player player){
		if(settings.getPerkLevel(skill.getSkillName(), perkName, player) >= perkLevel){
			return true;
		}else{ 
			return false;
		}
	}
}
