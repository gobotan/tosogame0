package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class FoodLevelTimers {

	private static BukkitTask increaseTask = null;
	private static BukkitTask decreaseTask = null;

	public static void runBothTask() {
		increaseTask = generateIncreaseTask().runTaskTimer(TosoNow.plugin, 0, 50);
		increaseTask = generateDecreaseTask().runTaskTimer(TosoNow.plugin, 0, 30);

	}

	public static void stopIncreaseTask() {
		if (increaseTask != null) {
			increaseTask.cancel();
			increaseTask = null;
		}
	}

	public static void stopDecreaseTask() {
		if (decreaseTask != null) {
			decreaseTask.cancel();
			decreaseTask = null;
		}
	}

	public static void stopBothTask() {
		stopIncreaseTask();
		stopDecreaseTask();
	}

	private static BukkitRunnable generateIncreaseTask() {
		return new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {

					if (!TosoNow.isSprint.containsKey(p)) {
						continue;
					}
					if (p.getGameMode() == GameMode.CREATIVE) {
						continue;
					}

					if (!TosoNow.isSprint.get(p)) {
						int food = p.getFoodLevel();
						if (food < 20) {
							food++;
							p.setFoodLevel(food);
						}
					}
				}
			}
		};
	}

	private static BukkitRunnable generateDecreaseTask() {
		return new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {

					if (TosoNow.isSprint.containsKey(p)) {
						continue;
					}
					if (p.getGameMode() == GameMode.CREATIVE) {
						continue;
					}

					if (TosoNow.isSprint.get(p)) {
						int food = p.getFoodLevel();
						food--;
						p.setFoodLevel(food);
					}
				}
			}
		};
	}
}
