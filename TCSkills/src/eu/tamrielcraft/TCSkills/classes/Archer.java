package eu.tamrielcraft.TCSkills.classes;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Archer extends Classes{
	public ArrayList<UUID> Archers = new ArrayList<UUID>();
	
	static Archer instance = new Archer();
    
    public static Archer getInstance() {
            return instance;
    }
    
    @Override
	public String className() {
		return "Archer";
	}

	@Override
	public String classNameChat() {
		return "[" + className() + "]";
	}

	@Override
	public String getDetails() {
		return "An archer is a specialized in using a bow and arrows in a fight. This means his attacks are mostly from a long distance";
	}

	@Override
	public void addStartingBonusses(Player player) {
		
		
	}
}
