package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer2 extends BukkitRunnable {
    @Override
    public void run() {
        if (Minigames.issprint != null) {
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                if (Minigames.issprint.get(pl).equals("walk")) {
                    int food = pl.getFoodLevel();
                    food++;
                    pl.setFoodLevel(food);
                }
            }
        }
    }
}
