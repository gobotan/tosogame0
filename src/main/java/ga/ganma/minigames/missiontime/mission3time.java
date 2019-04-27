package ga.ganma.minigames.missiontime;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ga.ganma.minigames.Minigames;
import ga.ganma.minigames.Mission;

public class Mission3Time extends BukkitRunnable {

	@Override
	public void run() {
		if (Minigames.gametime == Mission.mission3t) {
			Mission.ismission = false;
			this.cancel();
			new SoundM().runTaskTimer(Minigames.plg, 0, 5);
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendTitle("時限装置からアラームが鳴り始めた！", "", 20, 100, 20);
				p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
	}
}
