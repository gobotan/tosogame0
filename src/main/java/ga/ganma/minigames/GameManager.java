package ga.ganma.minigames;

import static org.bukkit.Bukkit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import ga.ganma.minigames.GameSettingsManager.KeyType;
import ga.ganma.minigames.event.GameTimeChangedEvent;
import ga.ganma.minigames.missions.Mission2;
import ga.ganma.minigames.missions.Mission3;
import ga.ganma.minigames.missions.Mission4;
import jp.jyn.jecon.Jecon;

public class GameManager {

	/**
	 * System
	 */
	private static boolean isRunningGame = false;
	private static int prizePerSecond = 100;
	private static int currentTotalPrize = 0;

	/**
	 * GameTime
	 */
	@SuppressWarnings("unused")
	private static BukkitTask gameTimeTask = null;
	private static int currentGameTime = 0;

	/**
	 * Hunter
	 */
	private static ItemStack[] hunterArmors;

	/**
	 * ScoreBoards
	 */
	private static Team Runner;
	private static Team Hunter;
	private static Team Jailer;
	private static Objective main;

	/**
	 * Initialize
	 */
	static {
		ItemStack helmet = new ItemStack((Material.LEATHER_HELMET));
		ItemStack chestPlate = new ItemStack((Material.LEATHER_CHESTPLATE));
		ItemStack leggings = new ItemStack((Material.LEATHER_LEGGINGS));
		ItemStack boots = new ItemStack((Material.LEATHER_BOOTS));

		LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
		meta.setColor(Color.BLACK);
		meta.setDisplayName(TosoNow.hunterArmorTitle);
		meta.setUnbreakable(true);

		helmet.setItemMeta(meta);
		chestPlate.setItemMeta(meta);
		leggings.setItemMeta(meta);
		boots.setItemMeta(meta);

		ItemStack[] hunterArmorsLocal = { boots, leggings, chestPlate, helmet };
		hunterArmors = hunterArmorsLocal;
	}

	public static void startGame() {
		if (isRunningGame) {
			throw new IllegalStateException("The game is already started.");
		}
		if (!GameSettingsManager.isAllComplete()) {
			throw new IllegalStateException("Locations are not complete.");
		}

		MissionManager.registerRandomLocations(GameSettingsManager.getLocation(KeyType.MISSION1),
				GameSettingsManager.getLocation(KeyType.MISSION2),
				GameSettingsManager.getLocation(KeyType.MISSION3),
				GameSettingsManager.getLocation(KeyType.MISSION4));

		MissionManager.registerRandomMissions(new Mission2(), new Mission3(), new Mission4());

		getServer().broadcastMessage(TosoNow.PREFIX + "逃走中が開始しました！");
		getServer().broadcastMessage(TosoNow.PREFIX + "制限時間は60分");
		getServer().broadcastMessage(TosoNow.PREFIX + "ハンター放出まで残り1分です！残り時間が3600秒になるとハンターが放出します！");
		getServer().broadcastMessage(TosoNow.PREFIX + "5秒後にテレポートとタイマーをスタートします。");
		currentGameTime = 3660;
		runGameTimeTask();
		FoodLevelTimers.runBothTask();

		isRunningGame = true;
	}

	public static void finishGame(FinishCause cause) {

	}

	public static boolean isRunningGame() {
		return isRunningGame;
	}

	public static void addHunter(Player hunter) {
		Hunter.addEntry(hunter.getName());
	}

	public static void setPrizePerSecond(int value) {
		prizePerSecond = value;
	}

	public static int getPrizePerSecond() {
		return prizePerSecond;
	}

	public static void givePrize() {
		int count = 0;
		for (Player winner : Bukkit.getServer().getOnlinePlayers()) {
			if (Runner.getEntries().contains(winner.getName())) {
				UUID id = winner.getUniqueId();
				Jecon.getInstance().getRepository().deposit(id, getCurrentTotalPrize());
				Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA + winner.getName() + "さんに賞金" + ChatColor.RED
						+ getCurrentTotalPrize() + ChatColor.DARK_AQUA + "円を渡しました！");
				count++;
			}
		}
		Bukkit.getServer().broadcastMessage("合計" + count + "人に賞金を渡しました！");
		currentTotalPrize = 0;
	}

	public static void setGameTime(int num, boolean callEvent) {
		currentGameTime = num;
		if (callEvent) {
			Bukkit.getPluginManager().callEvent(new GameTimeChangedEvent(currentGameTime));
		}
	}

	public static int getCurrentGameTime() {
		return currentGameTime;
	}

	private static void setUpHunters(Player... players) {
		List<Player> plist = new ArrayList<>();

		if (players == null || players.length == 0) {
			for (String name : Hunter.getEntries()) {
				Player p = Bukkit.getPlayerExact(name);
				if (p == null)
					continue;

				plist.add(p);
			}
		} else {
			plist = Arrays.asList(players);
		}

		for (Player p : plist) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(hunterArmors);
			p.setPlayerListName(p.getName() + "[" + ChatColor.DARK_GRAY + "ハンター" + ChatColor.WHITE + "]");
			p.setWalkSpeed(0.15f);
		}
	}

	public static List<Player> getHunters() {
		List<Player> hunterPlayerList = new ArrayList<>();
		for (String hunterStr : Hunter.getEntries()) {
			Player p = Bukkit.getPlayerExact(hunterStr);
			if (p != null)
				hunterPlayerList.add(p);
		}
		return hunterPlayerList;
	}

	public static List<Player> getRunners() {
		List<Player> hunterPlayerList = new ArrayList<>();
		for (String hunterStr : Runner.getEntries()) {
			Player p = Bukkit.getPlayerExact(hunterStr);
			if (p != null)
				hunterPlayerList.add(p);
		}
		return hunterPlayerList;
	}

	public static List<Player> getJailers() {
		List<Player> hunterPlayerList = new ArrayList<>();
		for (String hunterStr : Jailer.getEntries()) {
			Player p = Bukkit.getPlayerExact(hunterStr);
			if (p != null)
				hunterPlayerList.add(p);
		}
		return hunterPlayerList;
	}

	public static void setPlayerType(Player p, PlayerType type) {
		if (type == PlayerType.HUNTER && !Hunter.getEntries().contains(p.getName())) {
			Runner.removeEntry(p.getName());
			Jailer.removeEntry(p.getName());

			GameManager.addHunter(p);
			GameManager.setUpHunters(p);
		} else if (type == PlayerType.RUNNER && !Runner.getEntries().contains(p.getName())) {
			Hunter.removeEntry(p.getName());
			Jailer.removeEntry(p.getName());

			Runner.addEntry(p.getName());
			p.setWalkSpeed(0.2f);
			p.setPlayerListName(p.getName() + "[" + ChatColor.AQUA + "逃走者" + ChatColor.WHITE + "]");
			p.getInventory().setArmorContents(new ItemStack[4]);
		} else if (type == PlayerType.JAILER && !Jailer.getEntries().contains(p.getName())) {
			Runner.removeEntry(p.getName());
			Hunter.removeEntry(p.getName());
		}
	}

	public static int getCurrentTotalPrize() {
		return currentTotalPrize;
	}

	private static void runGameTimeTask() {
		gameTimeTask = new BukkitRunnable() {
			@Override
			public void run() {
				currentGameTime--;
				GameTimeChangedEvent event = new GameTimeChangedEvent(currentGameTime);
				Bukkit.getPluginManager().callEvent(event);

				if (currentGameTime < 3600)
					currentTotalPrize += prizePerSecond;

				ScoreboardDisplayer.updateScoreboard();

				if (currentGameTime <= 0) {
					this.cancel();
				}
			}
		}.runTaskTimer(TosoNow.plugin, 100, 20);
	}

	protected static void setUpScoreboards() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();

		// Runner
		Runner = board.getTeam("Runner");
		if (Runner != null)
			Runner.unregister();
		Runner = board.registerNewTeam("Runner");
		Runner = board.getTeam("Runner");
		Runner.setAllowFriendlyFire(false);
		Runner.setCanSeeFriendlyInvisibles(true);
		Runner.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

		// Hunter
		Hunter = board.getTeam("Hunter");
		if (Hunter != null)
			Hunter.unregister();
		board.registerNewTeam("Hunter");
		Hunter = board.getTeam("Hunter");
		Hunter.setAllowFriendlyFire(false);
		Hunter.setCanSeeFriendlyInvisibles(true);
		Hunter.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);

		// Jailer
		Jailer = board.getTeam("Jailer");
		if (Jailer != null)
			Jailer.unregister();
		board.registerNewTeam("Jailer");
		Jailer = board.getTeam("Jailer");
		Jailer.setAllowFriendlyFire(false);
		Jailer.setCanSeeFriendlyInvisibles(true);

		// Main Scoreboard
		main = board.getObjective("main");
		if (main != null)
			main.unregister();
		main = board.registerNewObjective("main", "dummy");
	}

	public enum FinishCause {
		ELIMINATED_RUNNER, TIME_LIMIT, FORCE
	}

	public enum PlayerType {
		RUNNER, HUNTER, JAILER;
	}
}
