package ga.ganma.minigames.missiontime;

import ga.ganma.minigames.EventGet;
import ga.ganma.minigames.Minigames;
import ga.ganma.minigames.Mission;
import ga.ganma.minigames.TosoCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Mission4Time extends BukkitRunnable {

	@Override
	public void run() {
		if (Minigames.gameTime == Mission.mission4t) {
			Mission.isMission = false;
			this.cancel();
			EventGet.chat = true;
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "誰も押さなかったため、賞金単価は上がらなかった...", "", 0, 100, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
			TosoCommand.world.getBlockAt(Mission.blockL).setType(Material.AIR);
		} else if (!Mission.isMission) {
			this.cancel();
			EventGet.chat = true;
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター強化！", Mission.CLEARp.getName() + "により、単価が300円に上がったがハンターが強化された！", 0,
						100, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
	}
}
