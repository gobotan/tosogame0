package ga.ganma.minigames.event;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartedEvent extends Event {

	private List<Player> hunters;
	private List<Player> runners;
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public GameStartedEvent(List<Player> hunters, List<Player> runners) {
		this.hunters = hunters;
		this.runners = runners;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	public List<Player> getHunters() {
		return hunters;
	}

	public List<Player> getRunners() {
		return runners;
	}
}