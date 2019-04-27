package ga.ganma.minigames.missions;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Mission3Penalty extends BukkitRunnable {

	private List<UUID> uuidList;

	public Mission3Penalty(List<UUID> uuidList) {
		this.uuidList = uuidList;
	}

	@Override
	public void run() {
		for (UUID targetUUID : uuidList) {
			Player target = Bukkit.getPlayer(targetUUID);

			if (target == null)
				continue;

			Bukkit.getOnlinePlayers().forEach(p -> {
				p.playSound(target.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10f, 1f);
			});
		}
	}
}
