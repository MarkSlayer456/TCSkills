package eu.tamrielcraft.TCSkills.races;

import org.bukkit.event.Listener;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class DarkElf extends Race implements Listener {
	static SettingsManager settings = SettingsManager.getInstance();
	
	static DarkElf instance = new DarkElf();
    
    public static DarkElf getInstance() {
            return instance;
    }
	
	@Override
	public String formatChat(String s) {
		return s.replace(s, "[DarkElf]" + s);
	}
	
	public static String details() {
		return "50% resistance to fire and lava";
		
	}
	
}
