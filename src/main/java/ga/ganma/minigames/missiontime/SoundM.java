package ga.ganma.minigames.missiontime;

import ga.ganma.minigames.mission;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SoundM extends BukkitRunnable {
    @Override
    public void run() {
        for (Player p: Bukkit.getOnlinePlayers()){
            if(!mission.mission2B.get(p)){
                p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER,3,1);
            }
        }
    }
}
