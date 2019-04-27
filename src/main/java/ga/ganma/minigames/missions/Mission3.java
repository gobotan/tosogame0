package ga.ganma.minigames.missions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ga.ganma.minigames.GameManager;
import ga.ganma.minigames.ItemHelper;
import ga.ganma.minigames.MissionManager;
import ga.ganma.minigames.TosoNow;

public class Mission3 implements Mission {

	private Location missionLoc = null;

	private List<Player> complete = new ArrayList<>();

	@Override
	public void startMission(Location loc) {
		missionLoc = loc.clone();

		ItemStack key = ItemHelper.create(Material.TRIPWIRE_HOOK, MissionManager.getMission3KeyTitle());
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED + "ミッションが発動された...", "時限装置を解除せよ！", 10, 60, 20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1, 2);
			p.getInventory().setItem(0, key.clone());
		}
	}

	@Override
	public Location getMissionLocation() {
		return missionLoc;
	}

	private int secFromStart = 0;

	@Override
	public void onGameTimeChanged(int currentTime) {
		secFromStart++;
		if (secFromStart >= 600) {
			MissionManager.completeMission(true, null);

			List<UUID> uuidList = new ArrayList<>();

			for (Player p : Bukkit.getOnlinePlayers()) {
				if (GameManager.getRunners().contains(p)) {
					if (!complete.contains(p)) {
						uuidList.add(p.getUniqueId());
					}
				}
			}

			new Mission3Penalty(uuidList).runTaskTimer(TosoNow.plugin, 0, 5);

			for (Player p : Bukkit.getOnlinePlayers()) {
				p.sendTitle("時限装置からアラームが鳴り始めた！", "", 20, 100, 20);
				p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
	}

	@Override
	public void missionComplete(Player p) {
		return;
	}

	public boolean completePlayer(Player p) {
		if (!complete.contains(p)) {
			complete.add(p);
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "プレイヤー全員に鍵を渡した。今から10分後に時限装置が鳴り響くようになり、ハンターが集まってくる！"
				+ "解除するには、他のプレイヤーが持っている鍵を右クリックして貰う必要がある！" + "なお、鍵は何回でも使用可能である。";
	}
}
