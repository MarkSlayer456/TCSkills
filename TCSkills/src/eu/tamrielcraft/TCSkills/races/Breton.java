package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

public class Breton extends Race {
	
	public ArrayList<UUID> Breton = new ArrayList<UUID>();
	static Breton instance = new Breton();
    
    public static Breton getInstance() {
            return instance;
    }
	
	@Override
	public String formatChat(String s) {
		return s.replace(s, "[Breton]" + s);
	}
	
	
	public void activate() {
		// TODO	
	}
	
	public String details() {
		return "50 points of extra magic";
	}
}
