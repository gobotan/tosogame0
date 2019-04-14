package ga.ganma.minigames.missiontime;

import ga.ganma.minigames.Minigames;
import ga.ganma.minigames.mission;
import org.bukkit.scheduler.BukkitRunnable;

public class mission4time extends BukkitRunnable {
    @Override
    public void run() {
        if(Minigames.gametime == mission.mission4t){
            mission.ismission = false;
            this.cancel();
        }
    }
}
