package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

public class Nord extends Race {

	public ArrayList<UUID> Nords = new ArrayList<UUID>();
	
	static Nord instance = new Nord();
    
    public static Nord getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[Nord]" + s);
	}
	
	public String details() {
		return "50% more resistance to negative potion effects";
	}
}
