package ga.ganma.minigames.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameTimeChangedEvent extends Event implements Cancellable {

	private int currentGameTime;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean isCancelled = false;

	public GameTimeChangedEvent(int currentGameTime) {
		this.currentGameTime = currentGameTime;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.isCancelled = cancelled;
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