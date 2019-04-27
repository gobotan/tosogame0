package ga.ganma.minigames.missiontime;

import ga.ganma.minigames.Minigames;
import ga.ganma.minigames.Mission;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SoundM extends BukkitRunnable {
    @Override
    public void run() {
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                if (!Mission.mission2B.get(p)) {
                    if (Minigames.Runner.getEntries().contains(p.getName())) {
                        p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 10, 1);
                    }
                }
            }
    }
}
