package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class jail extends BukkitRunnable {
    @Override
    public void run() {
        Player p = Minigames.fromplayer;
        p.teleport(Minigames.jailL);
        p.sendMessage("牢屋へテレポートしました。");
    }
}
