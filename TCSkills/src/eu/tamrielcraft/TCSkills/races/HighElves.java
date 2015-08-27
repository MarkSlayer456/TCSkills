package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

public class HighElves extends Race{

	public ArrayList<UUID> HighElves = new ArrayList<UUID>();
	
	static HighElves instance = new HighElves();
    
    public static HighElves getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[HighElf]" + s);
	}
    
	public void activate() {
		
	}
	
	public String details() {
		return "50 points of extra magic";
	}
}
