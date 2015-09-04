package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Knight extends Classes{
	public ArrayList<UUID> Knights = new ArrayList<UUID>();
	
	static Knight instance = new Knight();
    
    public static Knight getInstance() {
            return instance;
    }
}
