package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
    @Override
    public void run() {
        for (Player pl:Bukkit.getServer().getOnlinePlayers()){
            if(Minigames.issprint.get(pl).equals("sprint")){
                int food = pl.getFoodLevel();
                food--;
                pl.setFoodLevel(food);
            }
        }
    }
}
