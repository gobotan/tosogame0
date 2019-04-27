package ga.ganma.minigames.commands;

import static ga.ganma.minigames.TosoNow.*;
import static org.bukkit.Bukkit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import ga.ganma.minigames.FoodLevelTimers;
import ga.ganma.minigames.GameSettingsManager;
import ga.ganma.minigames.GameSettingsManager.KeyType;
import ga.ganma.minigames.GameTimer;
import ga.ganma.minigames.TosoNow;
import ga.ganma.minigames.inventories.SettingsInventoryController;

public class TosoCommand implements CommandExecutor {
	public static Player p;
	public static ItemStack hunter1 = new ItemStack((Material.LEATHER_CHESTPLATE));
	public static ItemStack hunter2 = new ItemStack((Material.LEATHER_HELMET));
	public static ItemStack hunter3 = new ItemStack((Material.LEATHER_LEGGINGS));
	public static ItemStack hunter4 = new ItemStack((Material.LEATHER_BOOTS));
	LeatherArmorMeta meta1 = (LeatherArmorMeta) hunter1.getItemMeta();
	LeatherArmorMeta meta2 = (LeatherArmorMeta) hunter2.getItemMeta();
	LeatherArmorMeta meta3 = (LeatherArmorMeta) hunter3.getItemMeta();
	LeatherArmorMeta meta4 = (LeatherArmorMeta) hunter4.getItemMeta();
	static public World world;
	static ArrayList<Location> missionLocs = new ArrayList<Location>();
	ArrayList<Player> randamhunter = new ArrayList<Player>();
	Player pla = null;
	public static Location mission1L;
	public static Location mission2L;
	public static Location mission3L;
	public static Location mission4L;
	public static int mission2int;
	public static int mission3Int;
	public static int mission4int;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player target = null;
		if (sender instanceof Player) {
			p = (Player) sender;
			int tanka;
			if (p.isOp()) {
				if (args.length != 0) {
					if (args[0].equalsIgnoreCase("start")) {
						start();
					} else if (args[0].equalsIgnoreCase("setting")) {
						Inventory settingInv = SettingsInventoryController.generateInventory();
						p.openInventory(settingInv);
					} else if (args[0].equalsIgnoreCase("end") && start) {
						TosoNow.gameTime = 10;
					} else if (args[0].equalsIgnoreCase("tanka")) {
						if (args.length == 2) {
							tanka = Integer.parseInt(args[1]);
							TosoNow.moneytanka = tanka;
							getServer().broadcastMessage("賞金単価を1秒" + TosoNow.moneytanka + "円に変更しました");
						}
					} else if (args[0].equalsIgnoreCase("prize")) {
						if (!start)
							TosoNow.sendmoney();
					} else if (args[0].equalsIgnoreCase("hunter") && !start) {
						if (args.length == 2) {
							target = Bukkit.getPlayerExact(args[1]);
							if (target != null) {
								if (TosoNow.Runner.getEntries().contains(target.getName())) {
									meta1.setColor(Color.BLACK);
									meta1.setDisplayName(hunterArmorTitle);
									meta1.setUnbreakable(true);
									meta2.setColor(Color.BLACK);
									meta2.setDisplayName(hunterArmorTitle);
									meta2.setUnbreakable(true);
									meta3.setColor(Color.BLACK);
									meta3.setDisplayName(hunterArmorTitle);
									meta3.setUnbreakable(true);
									meta4.setColor(Color.BLACK);
									meta4.setDisplayName(hunterArmorTitle);
									meta4.setUnbreakable(true);
									hunter1.setItemMeta(meta1);
									hunter2.setItemMeta(meta2);
									hunter3.setItemMeta(meta3);
									hunter4.setItemMeta(meta4);
									TosoNow.Runner.removeEntry(target.getName());
									TosoNow.Hunter.addEntry(target.getName());
									getServer().broadcastMessage("ハンターは" + target.getName() + "さんに決まりました！");
									target.setWalkSpeed(0.15f);
									target.setPlayerListName(target.getName() + "[" + ChatColor.DARK_GRAY + "ハンター"
											+ ChatColor.WHITE + "]");
									target.getInventory().setChestplate(hunter1);
									target.getInventory().setHelmet(hunter2);
									target.getInventory().setLeggings(hunter3);
									target.getInventory().setBoots(hunter4);
									hunter = true;
								} else {
									TosoNow.Hunter.removeEntry(target.getName());
									TosoNow.Runner.addEntry(target.getName());
									getServer().broadcastMessage(target.getName() + "さんをハンターから削除しました。");
									target.setWalkSpeed(0.25f);
									target.setPlayerListName(
											target.getName() + "[" + ChatColor.AQUA + "逃走者" + ChatColor.WHITE + "]");

									target.getInventory().setChestplate(null);
									target.getInventory().setLeggings(null);
									target.getInventory().setBoots(null);
									target.getInventory().setHelmet(null);

									if (TosoNow.Hunter.getEntries().size() <= 0) {
										hunter = false;
										getServer().broadcastMessage("現在ハンターが0人になったため、ゲームを開始できなくなりました。");
									}
								}
							}
						} else {
							hunter();
						}
					} else if (args[0].equalsIgnoreCase("time")) {
						if (args.length == 2) {
							gameTime = Integer.parseInt(args[1]);
						}
					}
				} else {
					p.sendMessage(ChatColor.GRAY + "/toso start と打つと逃走中がスタートします。");
					p.sendMessage(ChatColor.GRAY + "/toso hunter と打つと打った人がハンターに立候補した人が決定します。");
					p.sendMessage(ChatColor.GRAY + "/toso setting と打つと設定用のGUIが表示されます。");
					p.sendMessage(ChatColor.GRAY + "/toso tanka  と打つと1秒あたりの賞金が設定できます。");
					p.sendMessage(ChatColor.GRAY + "/toso prize と打つと勝者に賞金を渡せます。");
				}
			} else {
				p.sendMessage("あなたはそのコマンドの権限を持っていません！");
			}

		}
		return false;

	}

	private void start() {
		if (TosoNow.hunter) {
			if (GameSettingsManager.isAllComplete()) {
				TosoNow.start = true;
				world = p.getWorld();
				missionLocs.add(GameSettingsManager.getLocation(KeyType.MISSION1));
				missionLocs.add(GameSettingsManager.getLocation(KeyType.MISSION2));
				missionLocs.add(GameSettingsManager.getLocation(KeyType.MISSION3));
				missionLocs.add(GameSettingsManager.getLocation(KeyType.MISSION4));
				Collections.shuffle(missionLocs);
				mission1L = missionLocs.get(0);
				mission2L = missionLocs.get(1);
				mission3L = missionLocs.get(2);
				mission4L = missionLocs.get(3);
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(2);
				list.add(3);
				list.add(4);
				Collections.shuffle(list);
				mission2int = list.get(0);
				mission3Int = list.get(1);
				mission4int = list.get(2);
				getServer().broadcastMessage(TosoNow.PREFIX + "逃走中が開始しました！");
				getServer().broadcastMessage(TosoNow.PREFIX + "制限時間は60分");
				getServer().broadcastMessage(TosoNow.PREFIX + "ハンター放出まで残り1分です！残り時間が3600秒になるとハンターが放出します！");
				getServer().broadcastMessage(TosoNow.PREFIX + "5秒後にテレポートとタイマーをスタートします。");
				TosoNow.gameTime = 3660;
				new GameTimer().runTaskTimer(TosoNow.plugin, 100, 20);
				FoodLevelTimers.runBothTask();
			} else {
				if (GameSettingsManager.getLocation(KeyType.RESPAWN) == null)
					p.sendMessage(ChatColor.RED + "リスポーン地点が設定されていません！");
				if (GameSettingsManager.getLocation(KeyType.HUNTER_BOX) == null)
					p.sendMessage(ChatColor.RED + "ハンターリスポーン地点が設定されていません！");
				if (GameSettingsManager.getLocation(KeyType.JAIL) == null)
					p.sendMessage(ChatColor.RED + "牢屋が設定されていません！");
				if (GameSettingsManager.getLocation(KeyType.MISSION1) == null)
					p.sendMessage(ChatColor.RED + "ミッション地点1が設定されていません！");
				if (GameSettingsManager.getLocation(KeyType.MISSION2) == null)
					p.sendMessage(ChatColor.RED + "ミッション地点2が設定されていません！");
				if (GameSettingsManager.getLocation(KeyType.MISSION3) == null)
					p.sendMessage(ChatColor.RED + "ミッション地点3が設定されていません！");
				if (GameSettingsManager.getLocation(KeyType.MISSION4) == null)
					p.sendMessage(ChatColor.RED + "ミッション地点4が設定されていません！");
			}
		} else {
			p.sendMessage(TosoNow.PREFIX + ChatColor.RED + "まだハンターを決めていません！");
		}
	}

	public void hunter() {
		TosoNow.hunter = true;
		Material block;
		Location l;
		int blockx;
		int blocky;
		int blockz;
		for (Player pl : getServer().getOnlinePlayers()) {
			l = pl.getLocation();
			blockx = l.getBlockX();
			blocky = l.getBlockY();
			blocky--;
			blockz = l.getBlockZ();
			l.setX(blockx);
			l.setY(blocky);
			l.setZ(blockz);
			block = l.getBlock().getType();
			if (block == Material.DIAMOND_BLOCK) {
				randamhunter.add(pl);
			}
		}
		hunterkimekime();
	}

	public void hunterkimekime() {
		if (!(randamhunter.isEmpty())) {
			int size = randamhunter.size();
			Random random = new Random();
			int randomV = random.nextInt(size);
			pla = randamhunter.get(randomV);
			randamhunter.clear();
			if (TosoNow.Runner.getEntries().contains(pla.getName())) {
				TosoNow.Runner.removeEntry(pla.getName());
				TosoNow.Hunter.addEntry(pla.getName());
				getServer().broadcastMessage("ハンターは" + pla.getName() + "さんに決まりました！");
				pla.setWalkSpeed(0.15f);
				pla.setPlayerListName(pla.getName() + "[" + ChatColor.DARK_GRAY + "ハンター" + ChatColor.WHITE + "]");
				meta1.setColor(Color.BLACK);
				meta1.setDisplayName(hunterArmorTitle);
				meta2.setColor(Color.BLACK);
				meta2.setDisplayName(hunterArmorTitle);
				meta3.setColor(Color.BLACK);
				meta3.setDisplayName(hunterArmorTitle);
				meta4.setColor(Color.BLACK);
				meta4.setDisplayName(hunterArmorTitle);
				hunter1.setItemMeta(meta1);
				hunter2.setItemMeta(meta2);
				hunter3.setItemMeta(meta3);
				hunter4.setItemMeta(meta4);
				pla.getInventory().setChestplate(hunter1);
				pla.getInventory().setHelmet(hunter2);
				pla.getInventory().setLeggings(hunter3);
				pla.getInventory().setBoots(hunter4);
				TosoNow.hunter = true;
			} else if (pla != null) {
				TosoNow.Hunter.removeEntry(pla.getName());
				TosoNow.Runner.addEntry(pla.getName());
				getServer().broadcastMessage(pla.getName() + "さんをハンターから削除しました。");
				pla.setWalkSpeed(0.2f);
				pla.setPlayerListName(pla.getName() + "[" + ChatColor.AQUA + "逃走者" + ChatColor.WHITE + "]");
				if (pla.getInventory().getChestplate().getItemMeta().getDisplayName().equals(hunterArmorTitle)) {
					pla.getInventory().setChestplate(null);
					pla.getInventory().setLeggings(null);
					pla.getInventory().setBoots(null);
					pla.getInventory().setHelmet(null);
				}
				if (TosoNow.Hunter.getEntries().size() <= 0) {
					TosoNow.hunter = false;
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
