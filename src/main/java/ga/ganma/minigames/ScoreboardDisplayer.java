package ga.ganma.minigames;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import net.md_5.bungee.api.ChatColor;

public class ScoreboardDisplayer {

	private static Scoreboard board;

	public static void init(Scoreboard board) {
		ScoreboardDisplayer.board = board;
	}

	public static void updateScoreboard() {
		if (Bukkit.getOnlinePlayers().size() <= 0) {
			return;
		}

		clear();

		for (Player p : Bukkit.getOnlinePlayers()) {

			Objective obj = board.getObjective("main");

			if (obj == null) {
				obj = board.registerNewObjective("main", "dummy");
			}

			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(TosoNow.PREFIX);

			obj.getScore("").setScore(6);
			obj.getScore(ChatColor.AQUA + "残り人数" + ChatColor.RESET + "： " + ChatColor.AQUA
					+ GameManager.getRunners().size() + "人").setScore(5);
			obj.getScore(ChatColor.GREEN + "残り時間" + ChatColor.WHITE + "：" + GameManager.getCurrentGameTime() + "秒")
					.setScore(4);
			obj.getScore(ChatColor.GOLD + "賞金" + ChatColor.WHITE + "：" + GameManager.getCurrentTotalPrize() + "円")
					.setScore(3);
			obj.getScore("賞金単価：" + ChatColor.DARK_GREEN + "1秒" + ChatColor.WHITE + GameManager.getPrizePerSecond()
					+ ChatColor.DARK_GREEN + "円").setScore(2);
			obj.getScore(" ").setScore(1);
			obj.getScore(StringUtils.repeat("-", 20)).setScore(0);
			obj.getScore("  ").setScore(-1);
			obj.getScore(ChatColor.AQUA + "create by ganma_").setScore(-2);
			obj.getScore(ChatColor.GOLD + "play the mc.zosukue.com").setScore(-3);

			p.setScoreboard(board);
		}
	}

	public static void updateScoreboardWithWaiting() {
		if (Bukkit.getOnlinePlayers().size() <= 0) {
			return;
		}

		clear();

		for (Player p : Bukkit.getOnlinePlayers()) {

			Objective obj = board.getObjective("main");

			if (obj == null) {
				obj = board.registerNewObjective("main", "dummy");
			}

			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName(TosoNow.PREFIX);

			obj.getScore("").setScore(3);
			obj.getScore(ChatColor.RED + "待機中...").setScore(2);
			obj.getScore(" ").setScore(1);
			obj.getScore(StringUtils.repeat("-", 20)).setScore(0);
			obj.getScore("  ").setScore(-1);
			obj.getScore(ChatColor.AQUA + "create by ganma_").setScore(-2);
			obj.getScore(ChatColor.GOLD + "play the mc.zosukue.com").setScore(-3);

			p.setScoreboard(board);
		}
	}

	private static void clear() {
		if (board.getEntries() != null)
			for (String str : board.getEntries()) {
				board.resetScores(str);
			}
	}
}
