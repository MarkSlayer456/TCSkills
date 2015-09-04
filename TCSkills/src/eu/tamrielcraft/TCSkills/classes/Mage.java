package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Mage extends Classes{
	public ArrayList<UUID> Mages = new ArrayList<UUID>();
	
	static Mage instance = new Mage();
    
    public static Mage getInstance() {
            return instance;
    }
}
