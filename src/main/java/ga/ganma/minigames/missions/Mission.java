package ga.ganma.minigames.missions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Mission {

	abstract public void startMission(Location loc);

	abstract public void onGameTimeChanged(int currentTime);

	abstract public Location getMissionLocation();

	abstract public void missionComplete(Player p);

	abstract public String getDescription();
}
