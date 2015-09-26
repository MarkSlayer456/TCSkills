package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public abstract class Classes { //TODO sorry had to change the name of this class eclipse kept giving me errors 
	
	public static ArrayList<String> classes = new ArrayList<String>();
	
	public abstract String className();
	public abstract String classNameChat();
	public abstract String getDetails();
	public abstract void addStartingBonusses(Player player);
	
}
