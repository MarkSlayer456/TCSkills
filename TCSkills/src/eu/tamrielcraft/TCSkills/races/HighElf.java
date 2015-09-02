package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

public class HighElf extends Race{

	public ArrayList<UUID> HighElves = new ArrayList<UUID>();
	
	static HighElf instance = new HighElf();
    
    public static HighElf getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[HighElf]" + s);
	}
	
	public String details() {
		return "50 points of extra magic";
	}
}
