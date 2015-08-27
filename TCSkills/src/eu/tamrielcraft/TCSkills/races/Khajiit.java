package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

public class Khajiit extends Race{
	
	public ArrayList<UUID> Khajiit = new ArrayList<UUID>();
	
	static Khajiit instance = new Khajiit();
    
    public static Khajiit getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[Khajiit]" + s);
	}
	
	public void activate() {
		
	}
	
	public String details() {
		return "Fist do 100% more damage and invisablity while sneaking (wears off if hit or if you hit someone)";
	}
}
