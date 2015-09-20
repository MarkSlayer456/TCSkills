package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Mage extends Classes{
	public ArrayList<UUID> Mages = new ArrayList<UUID>();
	
	static Mage instance = new Mage();
    
    public static Mage getInstance() {
            return instance;
    }
    
    @Override
	public String className() {
		return "Mage";
	}

	@Override
	public String classNameChat() {
		return "[" + className() + "]";
	}

	@Override
	public String getDetails() {
		return "A mage is a class that uses magic spells as his main tool for attacking or defending";
	}
}
