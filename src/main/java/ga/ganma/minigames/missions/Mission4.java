package ga.ganma.minigames.missions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import ga.ganma.minigames.MissionManager;
import ga.ganma.minigames.listeners.GameListeners;

public class Mission4 implements Mission {

	private Location loc = null;

	@Override
	public void startMission(Location location) {
		this.loc = location.clone();

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED + "ミッションが発動された...", "賞金増加のチャンス！", 10, 60, 20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1, 2);
		}

		GameListeners.chat = false;
		this.loc.subtract(1, 0, 0);
		this.loc.getBlock().setType(Material.DIAMOND_BLOCK);
	}

	private int secFromStart = 0;

	@Override
	public void onGameTimeChanged(int currentTime) {
		secFromStart++;
		if (secFromStart >= 600) {
			MissionManager.completeMission(true, null);
			GameListeners.chat = true;
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "誰も押さなかったため、賞金単価は上がらなかった...", "", 0, 100, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
			loc.getBlock().setType(Material.AIR);
		}
	}

	@Override
	public Location getMissionLocation() {
		return loc;
	}

	@Override
	public void missionComplete(Player p) {
		GameListeners.chat = true;
		String subTitle = "単価が300円に上がったがハンターが強化された！";
		if (p != null)
			subTitle = p.getName() + "により、" + subTitle;
		for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
			allp.sendTitle(ChatColor.RED + "ハンター強化！", subTitle, 0, 100, 0);
			allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
		}
	}

	@Override
	public String getDescription() {
		return "ある地点にクリックすると賞金単価を1秒300円にするブロックを設置した。" + "しかし、そのブロックをクリックするとハンターが20分間強化されてしまう！"
				+ "押さなくても何もペナルティは無い。押すか押さないかはプレイヤーの自由だ！";
	}
}
