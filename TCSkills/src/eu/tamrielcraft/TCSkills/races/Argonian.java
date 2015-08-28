package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

public class Argonian extends Race{
	
	public ArrayList<UUID> Argonian = new ArrayList<UUID>();
	
	static Argonian instance = new Argonian();
    
    public static Argonian getInstance() {
            return instance;
    }	
	
    @Override
	public String formatChat(String s) {
		return s.replace(s, "[Argonian]" + s);
	}
	
	public String details() {
		return "Breathe longer under water, and take 50% less damage from negative potion effects";
	}
	
}
