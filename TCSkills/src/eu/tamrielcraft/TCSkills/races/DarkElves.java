package eu.tamrielcraft.TCSkills.races;

import org.bukkit.event.Listener;

import eu.tamrielcraft.TCSkills.main.SettingsManager;

public class DarkElves extends Race implements Listener {
	static SettingsManager settings = SettingsManager.getInstance();
	
	static DarkElves instance = new DarkElves();
    
    public static DarkElves getInstance() {
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
