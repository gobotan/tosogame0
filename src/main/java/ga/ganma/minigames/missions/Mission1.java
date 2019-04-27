package ga.ganma.minigames.missions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import ga.ganma.minigames.GameManager;
import ga.ganma.minigames.MissionManager;

public class Mission1 implements Mission {

	private Location missionLoc = null;

	@Override
	public void startMission(Location loc) {
		missionLoc = loc.clone();

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED + "ミッションが発動された...", "ゲーム時間をループ状態から戻せ！", 10, 60, 20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1, 2);
		}

		missionLoc.subtract(1, 0, 0);
		missionLoc.getBlock().setType(Material.BEDROCK);
	}

	@Override
	public Location getMissionLocation() {
		return missionLoc;
	}

	@Override
	public void onGameTimeChanged(int currentTime) {
		if (MissionManager.isRunningMission() && currentTime == 600) {
			GameManager.setGameTime(1199, false);
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.RED + "ゲーム時間がループしてしまった！", "指定の座標に行き岩盤をクリックしろ！", 20, 60, 20);
			}
		}
	}

	@Override
	public void missionComplete(Player player) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.BLUE + "ミッション成功！", player.getName() + "の活躍によりゲームエラーは直された...", 20, 100, 20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 2);
		}
	}

	@Override
	public String getDescription() {
		return "ゲームエラーのため、ゲーム時間が1200秒から600秒の間でループするようになった！" + "これを直すために、ある地点にゲームエラーを修復するための岩盤を設置した。"
				+ "そのボタンを押し、ゲームエラーを直せ！";
	}
}
