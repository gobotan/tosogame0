package ga.ganma.minigames;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

public class GameTimer extends BukkitRunnable {
	@Override
	public void run() {
		if (Minigames.gametime == 3600) {
			Bukkit.broadcastMessage(ChatColor.RED + "ハンターが放出されました！");
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.playSound(allp.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 2);
				allp.sendTitle(ChatColor.RED + "ハンター放出！", "時間が0秒になるまで逃げ切れ！", 20, 60, 40);
			}
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (Minigames.Hunter.getEntries().contains(p.getName())) {
					p.teleport(Minigames.hunterL);
					p.sendMessage(ChatColor.GRAY + "ハンターボックスにテレポートしました。");
					p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 2);
					p.sendTitle(ChatColor.RED + "ハンター放出！", "逃走者を全員捕まえろ！", 20, 60, 40);
				}
			}
		}
		if (Minigames.gametime == 3601) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "1", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3602) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "2", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3603) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "3", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3604) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "4", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3605) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "5", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3606) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "6", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3607) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "7", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3608) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "8", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3609) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "9", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3610) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "10", 0, 25, 0);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 3630) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "30秒前", 10, 50, 30);
				allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
		if (Minigames.gametime == 0) {
			for (Player allp : Bukkit.getServer().getOnlinePlayers()) {
				allp.sendTitle(ChatColor.AQUA + "逃走中が終了しました！", null, 10, 50, 30);
				allp.playSound(allp.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
				allp.setSneaking(false);
			}
			Bukkit.getServer().broadcastMessage(Minigames.GAME + "逃走中が終了しました！");
			Minigames.start = false;
			this.cancel();
		}
		if (Minigames.gametime == 3660) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (Minigames.Runner.getEntries().contains(p.getName())) {
					p.teleport(Minigames.resL);
				}
				p.setDisplayName(p.getPlayerListName().replace(p.getName(), ""));
				p.setCustomName("");
				p.setSneaking(true);
			}
		}
		if (Minigames.gametime <= 3600) {
			int pr = Minigames.prize;
			Minigames.prize = pr + Minigames.moneytanka;
		}
		if (Minigames.gametime == 1200) {
			Mission.ismission = true;
			Mission.mission1(1200, TosoCommand.mission1L);
		}
		if (Minigames.gametime == 3000) {
			Mission.ismission = true;
			if (TosoCommand.mission2int == 2) {
				Mission.mission2(3000, TosoCommand.mission2L);
			} else if (TosoCommand.mission2int == 3) {
				Mission.mission3(3000, TosoCommand.mission2L);
			} else if (TosoCommand.mission2int == 4) {
				Mission.mission4(3000, TosoCommand.mission2L);
			}
		}
		if (Minigames.gametime == 2100) {
			Mission.ismission = true;
			if (TosoCommand.mission3int == 2) {
				Mission.mission2(2100, TosoCommand.mission3L);
			} else if (TosoCommand.mission3int == 3) {
				Mission.mission3(2100, TosoCommand.mission3L);
			} else if (TosoCommand.mission3int == 4) {
				Mission.mission4(2100, TosoCommand.mission3L);
			}
		}

		Minigames.main.unregister();
		Minigames.main = Minigames.board.registerNewObjective("main", "dummy");
		Minigames.main.setDisplayName(Minigames.GAME);
		Minigames.main.setDisplaySlot(DisplaySlot.SIDEBAR);

		Minigames.Snull = Minigames.main.getScore("");
		Minigames.Snull.setScore(6);
		Minigames.Srunner = Minigames.main.getScore(ChatColor.AQUA + "残り人数" + ChatColor.RESET + "：" + ChatColor.AQUA
				+ Minigames.Runner.getEntries().size() + "人");
		Minigames.Srunner.setScore(5);
		Minigames.Stime = Minigames.main
				.getScore(ChatColor.GREEN + "残り時間" + ChatColor.WHITE + "：" + Minigames.gametime + "秒");
		Minigames.Stime.setScore(4);
		Minigames.Smoney = Minigames.main
				.getScore(ChatColor.GOLD + "賞金" + ChatColor.WHITE + "：" + Minigames.prize + "円");
		Minigames.Smoney.setScore(3);
		Minigames.tanka = Minigames.main.getScore("賞金単価：" + ChatColor.DARK_GREEN + "1秒" + ChatColor.WHITE
				+ Minigames.moneytanka + ChatColor.DARK_GREEN + "円");
		Minigames.tanka.setScore(2);
		Minigames.Snull2 = Minigames.main.getScore(" ");
		Minigames.Snull2.setScore(1);
		Minigames.Snull3 = Minigames.main.getScore("---------------------------");
		Minigames.Snull3.setScore(0);
		Minigames.Snull5 = Minigames.main.getScore("");
		Minigames.Snull5.setScore(-1);
		Minigames.Snull4 = Minigames.main.getScore(ChatColor.AQUA + "create by ganma_");
		Minigames.Snull4.setScore(-2);
		Minigames.serverinformation = Minigames.main.getScore(ChatColor.GOLD + "play the mc.zosukue.com");
		Minigames.serverinformation.setScore(-3);

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (Minigames.jailcount.keySet().contains(p) && Minigames.jailcount.get(p) != null) {
				if (Minigames.jailcount.get(p).equalsIgnoreCase("three")) {
					Minigames.jailcount.put(p, "two");
				} else if (Minigames.jailcount.get(p).equalsIgnoreCase("two")) {
					Minigames.jailcount.put(p, "one");
				} else if (Minigames.jailcount.get(p).equalsIgnoreCase("one")) {
					Minigames.jailcount.put(p, "zero");
				} else if (Minigames.jailcount.get(p).equalsIgnoreCase("zero")) {
					Minigames.jailcount.put(p, null);
					p.teleport(Minigames.jailL);
					p.sendMessage(ChatColor.GRAY + "牢屋にテレポートしました");
				}
			}
			if (Minigames.gametime % 60 < 10) {
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						TextComponent.fromLegacyText(Minigames.gametime / 60 + "：0" + Minigames.gametime % 60));
			} else {
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						TextComponent.fromLegacyText(Minigames.gametime / 60 + "：" + Minigames.gametime % 60));
			}
		}
		Minigames.gametime--;
	}
}
