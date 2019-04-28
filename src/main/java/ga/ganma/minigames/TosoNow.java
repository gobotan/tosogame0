package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ga.ganma.minigames.commands.PhoneCommand;
import ga.ganma.minigames.commands.TosoCommand;
import ga.ganma.minigames.inventories.PhoneInventoryController;
import ga.ganma.minigames.inventories.SettingsInventoryController;
import ga.ganma.minigames.listeners.GameListeners;
import ga.ganma.minigames.listeners.GameTimeChangeListener;
import ga.ganma.minigames.listeners.MissionTriggerListener;
import ga.ganma.minigames.listeners.PhoneInventoryListener;
import ga.ganma.minigames.listeners.SettingsInventoryListener;

public final class TosoNow extends JavaPlugin implements Listener {

	public FileConfiguration config;
	public static TosoNow plugin;
	public static final String hunterArmorTitle = "ハンターの装備";
	public final static String PREFIX = ("[" + ChatColor.RED + "ZOSU鯖逃走中" + ChatColor.WHITE + "] ");

	@Override
	public void onEnable() {
		plugin = this;

		saveDefaultConfig();

		// Log
		getLogger().info("逃走中プラグインのセットアップ中...");
		getLogger().info("製作者: " + String.join(", ", getDescription().getAuthors()));
		getLogger().info("現在のバージョン: " + getDescription().getVersion());

		// load settings from config
		GameSettingsManager.load(this);

		// initialize settings inventory
		SettingsInventoryController.init();
		PhoneInventoryController.init();

		// Register commands
		getCommand("toso").setExecutor(new TosoCommand());
		getCommand("toso").setPermissionMessage(PREFIX + ChatColor.RED + "あなたはそのコマンドの権限を持っていません！");
		getCommand("phone").setExecutor(new PhoneCommand());
		getCommand("phone").setPermission(PREFIX + ChatColor.RED + "コマンドを実行する権限がありません。 運営に問い合わせてください。");

		// Register Listeners
		Bukkit.getPluginManager().registerEvents(new GameListeners(), this);
		Bukkit.getPluginManager().registerEvents(new SettingsInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new PhoneInventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new GameTimeChangeListener(), this);
		Bukkit.getPluginManager().registerEvents(new MissionTriggerListener(), this);

		// Scoreboard Setup
		GameManager.setUpScoreboards();

		// Jcon check
		Plugin plugin = Bukkit.getPluginManager().getPlugin("Jecon");
		if (plugin == null || !plugin.isEnabled()) {
			getLogger().warning("前提プラグインであるJeconが導入されていません！");
			return;
		}
		getLogger().info("正常に起動しました。");
	}

	@Override
	public void onDisable() {
		getLogger().info(
				String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
	}
}
