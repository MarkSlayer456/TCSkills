package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Barbarian extends Classes{
	public ArrayList<UUID> Barbarians = new ArrayList<UUID>();
	
	static Barbarian instance = new Barbarian();
    
    public static Barbarian getInstance() {
            return instance;
    }
    
    @Override
	public String className() {
		return "Barbarian";
	}

	@Override
	public String classNameChat() {
		return "[" + className() + "]";
	}

	@Override
	public String getDetails() {
		return "A barbarian also has something unique!";
	}
}
