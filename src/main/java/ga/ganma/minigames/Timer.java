package ga.ganma.minigames;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
    @Override
    public void run() {
        Player pl = Minigames.sprintpl;
        for (String a : pl.getScoreboardTags()) {
            if (a.equalsIgnoreCase("sprint")) {
                pl.setFoodLevel(pl.getFoodLevel() - 1);
            }
            else if(a.equalsIgnoreCase("walk")){
                this.cancel();
            }
        }
    }
}
