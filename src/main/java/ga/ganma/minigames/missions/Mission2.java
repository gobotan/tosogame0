package ga.ganma.minigames.missions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ga.ganma.minigames.MissionManagerFixed;
import ga.ganma.minigames.TosoNow;

public class Mission2 implements Mission {

	private Location missionLoc = null;

	@Override
	public void startMission(Location loc) {
		missionLoc = loc.clone();
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED + "ミッションが発動された...", "プレイヤーの発光を阻止せよ", 10, 60, 20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1, 2);
		}

		missionLoc.subtract(1, 0, 0);
		missionLoc.getBlock().setType(Material.REDSTONE_BLOCK);
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
			MissionManagerFixed.completeMission(true, null);
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.RED + "ミッション失敗", "発光が30秒間ついてしまった！", 20, 100, 20);
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1, 2);
				if (TosoNow.Runner.getEntries().contains(p.getName())) {
					PotionEffect potion = new PotionEffect(PotionEffectType.GLOWING, 30 * 20, 1);
					p.addPotionEffect(potion);
				}
			}

			missionLoc.getBlock().setType(Material.AIR);
		}
	}

	@Override
	public void missionComplete(Player player) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.sendTitle(ChatColor.BLUE + "ミッション成功！", player.getName() + "の活躍により発光は阻止された...", 20, 100,
					20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 2);
		}
	}

	@Override
	public String getDescription() {
		return "今から10分後にプレイヤーが30秒間発光してしまう！" + "しかし、どこかにプレイヤーの発光を阻止するブロックを置いた。"
				+ "その場所を見つけ出し、その場所にあるアイテムを拾い、プレイヤーの発光を阻止せよ！" + "なお、そのアイテムを拾うと、使った者に対し逃走に有利な条件が付く！";
	}
}
