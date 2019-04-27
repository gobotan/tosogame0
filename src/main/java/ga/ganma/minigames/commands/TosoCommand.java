package ga.ganma.minigames.commands;

import static org.bukkit.Bukkit.*;

import java.util.ArrayList;
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

	ArrayList<Player> randamhunter = new ArrayList<Player>();
	Player player = null;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("このコマンドはプレイヤーからのみ有効です。");
			return true;
		}
		Player p = (Player) sender;

		if (args.length != 0) {
			if (args[0].equalsIgnoreCase("start")) {
				start(p);
			} else if (args[0].equalsIgnoreCase("setting")) {
				Inventory settingInv = SettingsInventoryController.generateInventory();
				p.openInventory(settingInv);
			} else if (args[0].equalsIgnoreCase("end") && GameManager.isRunningGame()) {
				GameManager.setGameTime(10, false);
			} else if (args[0].equalsIgnoreCase("tanka")) {
				if (args.length == 2) {
					int tanka = Integer.parseInt(args[1]);
					GameManager.setPrizePerSecond(tanka);
					getServer().broadcastMessage("賞金単価を1秒" + GameManager.getPrizePerSecond() + "円に変更しました");
				}
			} else if (args[0].equalsIgnoreCase("prize")) {
				if (!GameManager.isRunningGame()) {
					GameManager.givePrize();
				}
			} else if (args[0].equalsIgnoreCase("hunter") && !GameManager.isRunningGame()) {
				if (args.length == 2) {
					Player target = Bukkit.getPlayerExact(args[1]);
					if (target != null) {
						if (GameManager.getHunters().contains(target)) {
							GameManager.setPlayerType(target, PlayerType.HUNTER);
							getServer().broadcastMessage("ハンターは" + target.getName() + "さんに決まりました！");
						} else {
							GameManager.setPlayerType(target, PlayerType.RUNNER);
							getServer().broadcastMessage(target.getName() + "さんをハンターから削除しました。");

							if (GameManager.getHunters().size() <= 0) {
								getServer().broadcastMessage("現在ハンターが0人になったため、ゲームを開始できなくなりました。");
							}
						}
					}
				} else {
					hunter();
				}
			} else if (args[0].equalsIgnoreCase("time")) {
				if (args.length == 2) {
					GameManager.setGameTime(Integer.parseInt(args[1]), false);
				}
			}
		} else {
			p.sendMessage(ChatColor.GRAY + "/toso start と打つと逃走中がスタートします。");
			p.sendMessage(ChatColor.GRAY + "/toso hunter と打つと打った人がハンターに立候補した人が決定します。");
			p.sendMessage(ChatColor.GRAY + "/toso setting と打つと設定用のGUIが表示されます。");
			p.sendMessage(ChatColor.GRAY + "/toso tanka  と打つと1秒あたりの賞金が設定できます。");
			p.sendMessage(ChatColor.GRAY + "/toso prize と打つと勝者に賞金を渡せます。");
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

	public void hunter() {
		Material block;
		Location loc;

		for (Player pl : getServer().getOnlinePlayers()) {
			loc = pl.getLocation().clone();
			loc.subtract(0, 1, 0);

			block = loc.getBlock().getType();
			if (block == Material.DIAMOND_BLOCK) {
				randamhunter.add(pl);
			}
		}

		selectHunter();
	}

	public void selectHunter() {
		if (!(randamhunter.isEmpty())) {
			int size = randamhunter.size();
			Random random = new Random();
			int randomV = random.nextInt(size);
			player = randamhunter.get(randomV);
			randamhunter.clear();

			GameManager.addHunter(player);

			if (GameManager.getRunners().contains(player)) {
				GameManager.setPlayerType(player, PlayerType.HUNTER);
				getServer().broadcastMessage("ハンターは" + player.getName() + "さんに決まりました！");
			} else if (player != null) {
				GameManager.setPlayerType(player, PlayerType.RUNNER);

				if (GameManager.getHunters().size() <= 0) {
					getServer().broadcastMessage("現在ハンターが0人になったため、ゲームを開始できなくなりました。");
				}
			} else {
				System.out.println("エラー");
				System.out.println("プラグイン開発者にエラーしたことをお伝え下さい。");
			}

		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "ダイヤモンドブロックの上にまだ誰も乗っていません！");
		}
	}
}
