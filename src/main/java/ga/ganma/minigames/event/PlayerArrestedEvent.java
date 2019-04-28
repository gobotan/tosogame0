package ga.ganma.minigames.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerArrestedEvent extends Event {

	private Player arrested;
	private Player hunter;
	private static final HandlerList HANDLERS_LIST = new HandlerList();

	public PlayerArrestedEvent(Player arrested, Player hunter) {
		this.arrested = arrested;
		this.hunter = hunter;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS_LIST;
	}

	public Player getHunter() {
		return hunter;
	}

	public Player getArrested() {
		return arrested;
	}
}