package eu.tamrielcraft.TCSkills.races;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.event.Listener;

public class Argonian implements Listener {
	
	public ArrayList<UUID> Argonian = new ArrayList<UUID>();
	
	public String details() {
		return "Breathe longer under water, and take 50% less damage from negative potion effects";
	}
	
}
