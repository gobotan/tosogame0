package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

import ga.ganma.minigames.missions.Mission;
import ga.ganma.minigames.missions.Mission1;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class GameTimer extends BukkitRunnable {

	@Override
	public void run() {
		if (TosoNow.gameTime == 3600) {
			Bukkit.broadcastMessage(ChatColor.RED + "ハンターが放出されました！");
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.playSound(allp.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 2);
				allp.sendTitle(ChatColor.RED + "ハンター放出！", "時間が0秒になるまで逃げ切れ！", 20, 60, 40);

				if (TosoNow.Hunter.getEntries().contains(allp.getName())) {
					allp.teleport(TosoNow.hunterLoc);
					allp.sendMessage(ChatColor.GRAY + "ハンターボックスにテレポートしました。");
					allp.playSound(allp.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 2);
					allp.sendTitle(ChatColor.RED + "ハンター放出！", "逃走者を全員捕まえろ！", 20, 60, 40);
				}
			}
		}
		if (TosoNow.gameTime >= 3601 && TosoNow.gameTime <= 3610) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "" + (TosoNow.gameTime - 3600), 0, 25,
						0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (TosoNow.gameTime == 3630) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "30秒前", 10, 50, 30);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (TosoNow.gameTime == 0) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.AQUA + "逃走中が終了しました！", null, 10, 50, 30);
				allp.playSound(allp.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
				allp.setSneaking(false);
			}

			Bukkit.getServer().broadcastMessage(TosoNow.PREFIX + "逃走中が終了しました！");
			TosoNow.start = false;
			this.cancel();
		}
		if (TosoNow.gameTime == 3660) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (TosoNow.Runner.getEntries().contains(p.getName())) {
					p.teleport(TosoNow.respawnLoc);
				}
				p.setDisplayName(p.getPlayerListName().replace(p.getName(), ""));
				p.setCustomName("");
				p.setSneaking(true);
			}
		}
		if (TosoNow.gameTime <= 3600) {
			int pr = TosoNow.prize;
			TosoNow.prize = pr + TosoNow.moneytanka;
		}
		if (TosoNow.gameTime == 1200) {
			Mission1 mission = new Mission1();
			mission.startMission(MissionManagerFixed.getLocation(2));
			MissionManagerFixed.runMission(mission);
		}
		if (TosoNow.gameTime == 3000 || TosoNow.gameTime == 2100) {
			int num = 0;
			if (TosoNow.gameTime == 2100) {
				num = 1;
			}

			Mission mission = MissionManagerFixed.getMission(num);
			mission.startMission(MissionManagerFixed.getLocation(num));
			MissionManagerFixed.runMission(mission);
		}

		TosoNow.main.unregister();
		TosoNow.main = TosoNow.board.registerNewObjective("main", "dummy");
		TosoNow.main.setDisplayName(TosoNow.PREFIX);
		TosoNow.main.setDisplaySlot(DisplaySlot.SIDEBAR);

		TosoNow.Snull = TosoNow.main.getScore("");
		TosoNow.Snull.setScore(6);
		TosoNow.Srunner = TosoNow.main.getScore(ChatColor.AQUA + "残り人数" + ChatColor.RESET + "：" + ChatColor.AQUA
				+ TosoNow.Runner.getEntries().size() + "人");
		TosoNow.Srunner.setScore(5);
		TosoNow.Stime = TosoNow.main
				.getScore(ChatColor.GREEN + "残り時間" + ChatColor.WHITE + "：" + TosoNow.gameTime + "秒");
		TosoNow.Stime.setScore(4);
		TosoNow.Smoney = TosoNow.main
				.getScore(ChatColor.GOLD + "賞金" + ChatColor.WHITE + "：" + TosoNow.prize + "円");
		TosoNow.Smoney.setScore(3);
		TosoNow.tanka = TosoNow.main.getScore("賞金単価：" + ChatColor.DARK_GREEN + "1秒" + ChatColor.WHITE
				+ TosoNow.moneytanka + ChatColor.DARK_GREEN + "円");
		TosoNow.tanka.setScore(2);
		TosoNow.Snull2 = TosoNow.main.getScore(" ");
		TosoNow.Snull2.setScore(1);
		TosoNow.Snull3 = TosoNow.main.getScore("---------------------------");
		TosoNow.Snull3.setScore(0);
		TosoNow.Snull5 = TosoNow.main.getScore("");
		TosoNow.Snull5.setScore(-1);
		TosoNow.Snull4 = TosoNow.main.getScore(ChatColor.AQUA + "create by ganma_");
		TosoNow.Snull4.setScore(-2);
		TosoNow.serverInformation = TosoNow.main.getScore(ChatColor.GOLD + "play the mc.zosukue.com");
		TosoNow.serverInformation.setScore(-3);

		for (Player p : Bukkit.getOnlinePlayers()) {

			if (TosoNow.jailCount.keySet().contains(p)) {
				if (TosoNow.jailCount.get(p) > 0) {
					TosoNow.jailCount.put(p, TosoNow.jailCount.get(p) - 1);
				} else {
					TosoNow.jailCount.remove(p);
					p.teleport(TosoNow.jailL);
					p.sendMessage(ChatColor.GRAY + "牢屋にテレポートしました");
				}
			}
			if (TosoNow.gameTime % 60 < 10) {
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						TextComponent.fromLegacyText(TosoNow.gameTime / 60 + "：0" + TosoNow.gameTime % 60));
			} else {
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						TextComponent.fromLegacyText(TosoNow.gameTime / 60 + "：" + TosoNow.gameTime % 60));
			}
		}
		TosoNow.gameTime--;
	}
}
