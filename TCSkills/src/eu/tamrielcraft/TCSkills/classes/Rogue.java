package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Rogue extends Classes{
	public ArrayList<UUID> Rogues = new ArrayList<UUID>();
	
	static Rogue instance = new Rogue();
    
    public static Rogue getInstance() {
            return instance;
    }
    
    @Override
	public String className() {
		return "Rogue";
	}

	@Override
	public String classNameChat() {
		return "[" + className() + "]";
	}

	@Override
	public String getDetails() {
		return "A rogue is a class that uses stealth attacks to sneak upon his opponent";
	}
}
