package ga.ganma.minigames.event;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HunterReleaseEvent extends Event {

	private List<Player> hunters;
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public HunterReleaseEvent(List<Player> hunters) {
		this.hunters = hunters;
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
}