package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Rogue extends Classes{
	public ArrayList<UUID> Rogues = new ArrayList<UUID>();
	
	static Rogue instance = new Rogue();
    
    public static Rogue getInstance() {
            return instance;
    }
}
