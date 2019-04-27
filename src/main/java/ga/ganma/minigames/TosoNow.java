package ga.ganma.minigames;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import ga.ganma.minigames.listeners.GameListeners;
import jp.jyn.jecon.Jecon;

public final class TosoNow extends JavaPlugin implements Listener {

	public static boolean start;
	public static boolean hunter;
	public static ScoreboardManager manager;
	public static Scoreboard board;
	public static Team Runner;
	public static Team Hunter;
	public static Team Jailer;
	public final static String GAME = ("[" + ChatColor.RED + "ZOSU鯖逃走中" + ChatColor.WHITE + "]");
	public static Player sprintpl;
	public static int prize = 0;
	public static int gameTime = 3600;
	public static Objective main;
	public static Score Stime;
	public static Score Smoney;
	public static Score tanka;
	public static Score Srunner;
	public static Score Snull;
	public static Score Snull2;
	public static Score Snull3;
	public static Score Snull4;
	public static Score Snull5;
	public static Score serverInformation;
	public static Location jailL;
	public static Location resL;
	public static Location hunterL;
	public static Location lobbyL;
	public static HashMap<Player, Boolean> isSprint = new HashMap<Player, Boolean>();
	public static int moneytanka;
	public static HashMap<Player, Integer> jailCount = new HashMap<>();
	public static TosoNow plugin;
	public FileConfiguration config;
	public static final String huntername = "ハンターの装備";
	@SuppressWarnings("unused")
	private Jecon jecon;

	@Override
	public void onEnable() {
		plugin = this;

		saveDefaultConfig();

		// Log
		getLogger().info("逃走中プラグインのセットアップ中...");
		getLogger().info("製作者: " + String.join(", ", getDescription().getAuthors()));
		getLogger().info("現在のバージョン: " + getDescription().getVersion());

		// Register commands
		getCommand("toso").setExecutor(new TosoCommand());
		getCommand("phone").setExecutor(new Phone());

		// Register Listeners
		Bukkit.getPluginManager().registerEvents(new GameListeners(), this);

		// Scoreboard Setup
		setUpScoreboards();

		Plugin plugin = Bukkit.getPluginManager().getPlugin("Jecon");
		if (plugin == null || !plugin.isEnabled()) {
			getLogger().warning("前提プラグインであるJeconが導入されていません！");
			return;
		}

		this.jecon = (Jecon) plugin;
		getLogger().info("正常に起動しました。");
	}

	@Override
	public void onDisable() {
		getLogger().info(
				String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
	}

	static public void sendmoney() {
		int a = 0;
		for (Player winner : Bukkit.getServer().getOnlinePlayers()) {
			if (Runner.getEntries().contains(winner.getName())) {
				UUID id = winner.getUniqueId();
				Jecon.getInstance().getRepository().deposit(id, prize);
				Bukkit.getServer().broadcastMessage(ChatColor.DARK_AQUA + winner.getName() + "さんに賞金" + ChatColor.RED
						+ prize + ChatColor.DARK_AQUA + "円を渡しました！");
				a++;
			}
		}
		Bukkit.getServer().broadcastMessage("合計" + a + "人に賞金を渡しました！");
		prize = 0;
	}

	private void setUpScoreboards() {
		manager = Bukkit.getScoreboardManager();
		board = manager.getMainScoreboard();

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
		config = getConfig();
		main = board.getObjective("main");
		if (main != null)
			main.unregister();
		main = board.registerNewObjective("main", "dummy");
	}
}
