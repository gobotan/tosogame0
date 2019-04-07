package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer2 extends BukkitRunnable {
    @Override
    public void run() {
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                if(Minigames.issprint.keySet().contains(pl)){
                if (pl.getGameMode() != GameMode.CREATIVE) {
                    if (!(Minigames.issprint.get(pl))) {
                        int food = pl.getFoodLevel();
                        if (food < 20) {
                            food++;
                            pl.setFoodLevel(food);
                        }
                    }
                }
            }
        }
    }
}
