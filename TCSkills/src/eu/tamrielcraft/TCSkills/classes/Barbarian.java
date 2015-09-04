package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Barbarian extends Classes{
	public ArrayList<UUID> Barbarians = new ArrayList<UUID>();
	
	static Barbarian instance = new Barbarian();
    
    public static Barbarian getInstance() {
            return instance;
    }
}
