package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

public class Nords extends Race{

	public ArrayList<UUID> Nords = new ArrayList<UUID>();
	
	static Nords instance = new Nords();
    
    public static Nords getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[Nord]" + s);
	}
	
	public void activate() {
		
	}
	
	public String details() {
		return "50% more resistance to negative potion effects";
	}
}
