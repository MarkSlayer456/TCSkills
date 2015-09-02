package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;

public class WoodElf extends Race{

	public ArrayList<UUID> WoodElves = new ArrayList<UUID>();
	
	static WoodElf instance = new WoodElf();
    
    public static WoodElf getInstance() {
            return instance;
    }
	
    @Override
	public String formatChat(String s) {
    	return s.replace(s, "[WoodElf]" + s);
	}
	
	public String details() {
		return "10% more bow damage and invisablity while sneaking (wears off if hit or if you hit someone)";
	}
	
	
}
