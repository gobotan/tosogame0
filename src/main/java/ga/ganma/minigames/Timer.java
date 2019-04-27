package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
	@Override
	public void run() {
		for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
			if (Minigames.isSprint.keySet().contains(pl)) {
				if (pl.getGameMode() != GameMode.CREATIVE) {
					if (Minigames.isSprint.get(pl)) {
						int food = pl.getFoodLevel();
						food--;
						pl.setFoodLevel(food);
					}
				}
			}
		}
	}
}
