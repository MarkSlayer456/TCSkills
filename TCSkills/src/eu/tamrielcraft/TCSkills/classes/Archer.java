package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Archer extends Classes{
	public ArrayList<UUID> Archers = new ArrayList<UUID>();
	
	static Archer instance = new Archer();
    
    public static Archer getInstance() {
            return instance;
    }
}
