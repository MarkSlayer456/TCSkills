package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

public class Knight extends Classes{
	public ArrayList<UUID> Knights = new ArrayList<UUID>();
	
	static Knight instance = new Knight();
    
    public static Knight getInstance() {
            return instance;
    }
    
    @Override
	public String className() {
		return "Knight";
	}

	@Override
	public String classNameChat() {
		return "[" + className() + "]";
	}

	@Override
	public String getDetails() {
		return "A knight is a class that is superior in face-to-face combat and is specialized in heavy armor";
	}
}
