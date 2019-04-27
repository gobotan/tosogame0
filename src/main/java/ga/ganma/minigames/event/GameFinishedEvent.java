package ga.ganma.minigames.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameFinishedEvent extends Event {

	private FinishCause cause;
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public GameFinishedEvent(FinishCause cause) {
		this.cause = cause;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	public FinishCause getFinishCause() {
		return cause;
	}

	public enum FinishCause {
		ELIMINATED_RUNNER, TIME_LIMIT
	}
}