package ga.ganma.minigames.commands;

import static org.bukkit.Bukkit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import ga.ganma.minigames.GameManager;
import ga.ganma.minigames.GameManager.PlayerType;
import ga.ganma.minigames.GameSettingsManager;
import ga.ganma.minigames.GameSettingsManager.KeyType;
import ga.ganma.minigames.TosoNow;
import ga.ganma.minigames.inventories.SettingsInventoryController;

public class TosoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("このコマンドはプレイヤーからのみ有効です。");
			return true;
		}
		Player p = (Player) sender;

		// 引数なし
		if (args.length <= 0) {
			p.sendMessage(ChatColor.GRAY + "/toso start と打つと逃走中がスタートします。");
			p.sendMessage(ChatColor.GRAY + "/toso hunter と打つと打った人がハンターに立候補した人が決定します。");
			p.sendMessage(ChatColor.GRAY + "/toso setting と打つと設定用のGUIが表示されます。");
			p.sendMessage(ChatColor.GRAY + "/toso tanka  と打つと1秒あたりの賞金が設定できます。");
			p.sendMessage(ChatColor.GRAY + "/toso prize と打つと勝者に賞金を渡せます。");
			return true;
		}

		// toso start
		if (args[0].equalsIgnoreCase("start")) {
			start(p);
			return true;
		}

		// toso setting(s)
		if (args[0].equalsIgnoreCase("setting") || args[0].equalsIgnoreCase("settings")) {
			Inventory settingInv = SettingsInventoryController.generateInventory();
			p.openInventory(settingInv);
			return true;
		}

		// toso end
		if (args[0].equalsIgnoreCase("end") && GameManager.isRunningGame()) {
			GameManager.setGameTime(10, false);
			return true;
		}

		// toso tanka
		if (args[0].equalsIgnoreCase("tanka")) {
			if (args.length <= 1) {
				p.sendMessage(ChatColor.RED + "値段を入力して下さい！");
				return true;
			}

			int tanka = -1;
			try {
				tanka = Integer.parseInt(args[1]);
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "正しい整数を入力してください。");
				return true;
			}

			if (tanka <= 0) {
				p.sendMessage(ChatColor.RED + "正の数を入力してください。");
				return true;
			}

			GameManager.setPrizePerSecond(tanka);
			getServer().broadcastMessage("賞金単価を1秒" + GameManager.getPrizePerSecond() + "円に変更しました");
			return true;
		}

		if (args[0].equalsIgnoreCase("prize")) {
			if (GameManager.isRunningGame()) {
				p.sendMessage(ChatColor.RED + "現在ゲーム進行中のため賞金を与えることはできません！");
				return true;
			}

			GameManager.givePrize();
			return true;
		}

		if (args[0].equalsIgnoreCase("hunter") && !GameManager.isRunningGame()) {
			Player target = null;
			if (args.length == 2) {
				target = Bukkit.getPlayerExact(args[1]);

				if (target == null) {
					p.sendMessage(ChatColor.YELLOW + args[1] + ChatColor.RED + "という名前のプレイヤーが見つかりませんでした。");
					return true;
				}
			} else {
				target = getRandomHunter();
			}

			if (target == null) {
				p.sendMessage(ChatColor.RED + "条件に当てはまるプレイヤーが居ませんでした。");
				return true;
			}

			if (!GameManager.getHunters().contains(target)) {
				GameManager.setPlayerType(target, PlayerType.HUNTER);
				getServer().broadcastMessage("ハンターは" + target.getName() + "さんに決まりました！");
			} else {
				GameManager.setPlayerType(target, PlayerType.RUNNER);
				getServer().broadcastMessage(target.getName() + "さんをハンターから削除しました。");

				if (GameManager.getHunters().size() <= 0) {
					getServer().broadcastMessage("現在ハンターが0人になったため、ゲームを開始できなくなりました。");
				}
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("time")) {
			if (args.length == 2) {
				GameManager.setGameTime(Integer.parseInt(args[1]), false);
			}
			return true;
		}

		return true;
	}

	private void start(Player sender) {
		// Hunter Check
		if (GameManager.getHunters().size() <= 0) {
			sender.sendMessage(TosoNow.PREFIX + ChatColor.RED + "まだハンターを決めていません！");
			return;
		}

		// Locations Check
		if (!GameSettingsManager.isAllComplete()) {
			if (GameSettingsManager.getLocation(KeyType.RESPAWN) == null)
				sender.sendMessage(ChatColor.RED + "リスポーン地点が設定されていません！");
			if (GameSettingsManager.getLocation(KeyType.HUNTER_BOX) == null)
				sender.sendMessage(ChatColor.RED + "ハンターリスポーン地点が設定されていません！");
			if (GameSettingsManager.getLocation(KeyType.JAIL) == null)
				sender.sendMessage(ChatColor.RED + "牢屋が設定されていません！");
			if (GameSettingsManager.getLocation(KeyType.MISSION1) == null)
				sender.sendMessage(ChatColor.RED + "ミッション地点1が設定されていません！");
			if (GameSettingsManager.getLocation(KeyType.MISSION2) == null)
				sender.sendMessage(ChatColor.RED + "ミッション地点2が設定されていません！");
			if (GameSettingsManager.getLocation(KeyType.MISSION3) == null)
				sender.sendMessage(ChatColor.RED + "ミッション地点3が設定されていません！");
			if (GameSettingsManager.getLocation(KeyType.MISSION4) == null)
				sender.sendMessage(ChatColor.RED + "ミッション地点4が設定されていません！");
			return;
		}

		// Start Game
		GameManager.startGame();
	}

	private Player getRandomHunter() {
		Location loc;

		List<Player> targetList = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			loc = p.getLocation().clone();
			loc.subtract(0, 1, 0);

			if (loc.getBlock().getType() == Material.DIAMOND_BLOCK) {
				targetList.add(p);
			}
		}

		if (targetList.isEmpty()) {
			return null;
		}

		int randomNumber = new Random().nextInt(targetList.size());
		Player hunter = targetList.get(randomNumber);
		targetList.clear();

		return hunter;
	}
}
