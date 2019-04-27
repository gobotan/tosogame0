package ga.ganma.minigames.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameTimeChangedEvent extends Event {

	private int currentGameTime;
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public GameTimeChangedEvent(int currentGameTime) {
		this.currentGameTime = currentGameTime;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	public int getCurrentGameTime() {
		return currentGameTime;
	}
}