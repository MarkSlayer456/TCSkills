package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

public class RedGuard extends Race{

	public ArrayList<UUID> RedGuard = new ArrayList<UUID>();
	
	static RedGuard instance = new RedGuard();
    
    public static RedGuard getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[RedGuard]" + s);
	}
	
	
	public void activate() {
		
	}
	
	public String details() {
		return "Food doesn't drop below four and weapons do 5% more damage";
	}
	
}
