package ga.ganma.minigames.missiontime;

import ga.ganma.minigames.TosoNow;
import ga.ganma.minigames.Mission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Mission1Time extends BukkitRunnable {

	@Override
	public void run() {
		if (Mission.isMission && TosoNow.gameTime == 600) {
			TosoNow.gameTime = 1199;
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.RED + "ゲーム時間がループしてしまった！", "指定の座標に行き岩盤をクリックしろ！", 20, 60, 20);
			}
		} else if (!Mission.isMission) {
			this.cancel();
			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendTitle(ChatColor.BLUE + "ミッション成功！", Mission.CLEARp.getName() + "の活躍によりゲームエラーは直された...", 20, 100,
						20);
				p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 2);
			}
		}
	}
}
