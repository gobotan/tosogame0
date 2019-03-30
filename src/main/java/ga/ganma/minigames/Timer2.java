package ga.ganma.minigames;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer2 extends BukkitRunnable {
    @Override
    public void run() {
        Player pl = Minigames.sprintpl;
        for (String a :pl.getScoreboardTags()) {
            if (a.equalsIgnoreCase("walk")) {
                if (pl.getFoodLevel() != 20) {
                    pl.setFoodLevel(pl.getFoodLevel() + 1);
                }
            }
            else if(a.equalsIgnoreCase("sprint")){
                this.cancel();
            }
        }
    }
}
