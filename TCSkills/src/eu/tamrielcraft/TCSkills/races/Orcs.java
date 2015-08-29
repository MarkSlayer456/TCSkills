package eu.tamrielcraft.TCSkills.races;

import org.bukkit.event.Listener;

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
	
	
	public static String details() {
		return "More health and receive 1 lvl after enchanting and repairing"; //TODO spell check and see if possible
	}

}
