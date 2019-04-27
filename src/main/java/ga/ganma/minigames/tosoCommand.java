package ga.ganma.minigames;

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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class TosoCommand implements CommandExecutor {
	public static Player p;
	public static ItemStack menu1 = new ItemStack(Material.IRON_FENCE);
	public static ItemMeta menu1data = menu1.getItemMeta();
	public static ItemStack menu2 = new ItemStack(Material.STICK);
	public static ItemMeta menu2data = menu2.getItemMeta();
	public static ItemStack menu3 = new ItemStack(Material.IRON_BLOCK);
	public static ItemMeta menu3data = menu3.getItemMeta();
	public static ItemStack menu4 = new ItemStack(Material.REDSTONE_TORCH_ON);
	public static ItemMeta menu4data = menu4.getItemMeta();
	public static ItemStack menu5 = new ItemStack(Material.PAPER);
	public static ItemMeta menu5data = menu5.getItemMeta();
	public static ItemStack menu6 = new ItemStack(Material.PAPER);
	public static ItemMeta menu6data = menu6.getItemMeta();
	public static ItemStack menu7 = new ItemStack(Material.PAPER);
	public static ItemMeta menu7data = menu7.getItemMeta();
	public static ItemStack menu8 = new ItemStack(Material.PAPER);
	public static ItemMeta menu8data = menu8.getItemMeta();
	public static ItemStack close = new ItemStack(Material.REDSTONE_BLOCK);
	public static ItemMeta closedata = close.getItemMeta();
	public static ItemStack hunter1 = new ItemStack((Material.LEATHER_CHESTPLATE));
	public static ItemStack hunter2 = new ItemStack((Material.LEATHER_HELMET));
	public static ItemStack hunter3 = new ItemStack((Material.LEATHER_LEGGINGS));
	public static ItemStack hunter4 = new ItemStack((Material.LEATHER_BOOTS));
	LeatherArmorMeta meta1 = (LeatherArmorMeta) hunter1.getItemMeta();
	LeatherArmorMeta meta2 = (LeatherArmorMeta) hunter2.getItemMeta();
	LeatherArmorMeta meta3 = (LeatherArmorMeta) hunter3.getItemMeta();
	LeatherArmorMeta meta4 = (LeatherArmorMeta) hunter4.getItemMeta();
	static public World world;
	static ArrayList<Location> missionL = new ArrayList<Location>();
	ArrayList<Player> randamhunter = new ArrayList<Player>();
	Player pla = null;
	static Location mission1L;
	static Location mission2L;
	static Location mission3L;
	static Location mission4L;
	static int mission2int;
	static int mission3Int;
	static int mission4int;

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
						setting();
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
									meta1.setDisplayName(huntername);
									meta1.setUnbreakable(true);
									meta2.setColor(Color.BLACK);
									meta2.setDisplayName(huntername);
									meta2.setUnbreakable(true);
									meta3.setColor(Color.BLACK);
									meta3.setDisplayName(huntername);
									meta3.setUnbreakable(true);
									meta4.setColor(Color.BLACK);
									meta4.setDisplayName(huntername);
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
									if (target.getInventory().getChestplate().getItemMeta().getDisplayName()
											.equals(huntername)) {
										target.getInventory().setChestplate(null);
										target.getInventory().setLeggings(null);
										target.getInventory().setBoots(null);
										target.getInventory().setHelmet(null);
									}
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
		FileConfiguration config = plugin.getConfig();
		if (TosoNow.hunter) {
			if (config.getBoolean("res.boolean") && config.getBoolean("box.boolean")
					&& config.getBoolean("jail.boolean") && config.getBoolean("mission1.boolean")
					&& config.getBoolean("mission2.boolean") && config.getBoolean("mission3.boolean")
					&& config.getBoolean("mission4.boolean")) {
				TosoNow.start = true;
				TosoNow.plugin.getConfig();
				world = p.getWorld();
				TosoNow.resL = new Location(p.getWorld(), plugin.getConfig().getInt("res.x"),
						plugin.getConfig().getInt("res.y"), plugin.getConfig().getInt("res.z"));
				TosoNow.hunterL = new Location(p.getWorld(), plugin.getConfig().getInt("box.x"),
						plugin.getConfig().getInt("box.y"), plugin.getConfig().getInt("box.z"));
				missionL.add(new Location(p.getWorld(), plugin.getConfig().getInt("mission1.x"),
						plugin.getConfig().getInt("mission1.y"), plugin.getConfig().getInt("mission1.z")));
				missionL.add(new Location(p.getWorld(), plugin.getConfig().getInt("mission2.x"),
						plugin.getConfig().getInt("mission2.y"), plugin.getConfig().getInt("mission2.z")));
				missionL.add(new Location(p.getWorld(), plugin.getConfig().getInt("mission3.x"),
						plugin.getConfig().getInt("mission3.y"), plugin.getConfig().getInt("mission3.z")));
				missionL.add(new Location(p.getWorld(), plugin.getConfig().getInt("mission4.x"),
						plugin.getConfig().getInt("mission4.y"), plugin.getConfig().getInt("mission4.z")));
				Collections.shuffle(missionL);
				mission1L = missionL.get(0);
				mission2L = missionL.get(1);
				mission3L = missionL.get(2);
				mission4L = missionL.get(3);
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(2);
				list.add(3);
				list.add(4);
				Collections.shuffle(list);
				mission2int = list.get(0);
				mission3Int = list.get(1);
				mission4int = list.get(2);
				getServer().broadcastMessage(TosoNow.GAME + "逃走中が開始しました！");
				getServer().broadcastMessage(TosoNow.GAME + "制限時間は60分");
				getServer().broadcastMessage(TosoNow.GAME + "ハンター放出まで残り1分です！残り時間が3600秒になるとハンターが放出します！");
				getServer().broadcastMessage(TosoNow.GAME + "5秒後にテレポートとタイマーをスタートします。");
				TosoNow.gameTime = 3660;
				new GameTimer().runTaskTimer(TosoNow.plugin, 100, 20);
				FoodLevelTimers.runBothTask();
			} else if (config.getBoolean("res.boolean") || config.getBoolean("box.boolean")
					|| config.getBoolean("jail.boolean") || config.getBoolean("mission1.boolean")
					|| config.getBoolean("mission2.boolean") || config.getBoolean("mission3.boolean")
					|| config.getBoolean("mission4.boolean")) {
				if (config.getBoolean("res.boolean")) {
					p.sendMessage(ChatColor.RED + "リスポーン地点が設定されていません！");
				}
				if (config.getBoolean("box.boolean")) {
					p.sendMessage(ChatColor.RED + "ハンターリスポーン地点が設定されていません！");
				}
				if (config.getBoolean("jail.boolean")) {
					p.sendMessage(ChatColor.RED + "牢屋が設定されていません！");
				}
				if (config.getBoolean("mission1.boolean")) {
					p.sendMessage(ChatColor.RED + "ミッション地点1が設定されていません！");
				}
				if (config.getBoolean("mission2.boolean")) {
					p.sendMessage(ChatColor.RED + "ミッション地点2が設定されていません！");
				}
				if (config.getBoolean("mission3.boolean")) {
					p.sendMessage(ChatColor.RED + "ミッション地点3が設定されていません！");
				}
				if (config.getBoolean("mission4.boolean")) {
					p.sendMessage(ChatColor.RED + "ミッション地点4が設定されていません！");
				}
			}
		} else
			p.sendMessage(TosoNow.GAME + ChatColor.RED + "まだハンターを決めていません！");
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

	public void setting() {
		Inventory inv;
		inv = Bukkit.createInventory(null, 18, "逃走中plugin 設定画面");
		menu1data.setDisplayName("牢獄の座標設定");
		menu2data.setDisplayName("ハンターボックス座標設定");
		menu3data.setDisplayName("ロビー座標設定");
		menu4data.setDisplayName("復活地点の座標設定");
		menu5data.setDisplayName("ミッションで使用する座標1");
		menu6data.setDisplayName("ミッションで使用する座標2");
		menu7data.setDisplayName("ミッションで使用する座標3");
		menu8data.setDisplayName("ミッションで使用する座標4");
		closedata.setDisplayName("閉じる");
		menu1.setItemMeta(menu1data);
		menu2.setItemMeta(menu2data);
		menu3.setItemMeta(menu3data);
		menu4.setItemMeta(menu4data);
		menu5.setItemMeta(menu5data);
		menu6.setItemMeta(menu6data);
		menu7.setItemMeta(menu7data);
		menu8.setItemMeta(menu8data);
		close.setItemMeta(TosoCommand.closedata);
		inv.setItem(0, menu1);
		inv.setItem(1, menu2);
		inv.setItem(2, menu3);
		inv.setItem(3, menu4);
		inv.setItem(4, menu5);
		inv.setItem(5, menu6);
		inv.setItem(6, menu7);
		inv.setItem(7, menu8);
		inv.setItem(17, TosoCommand.close);
		p.openInventory(inv);
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
				meta1.setDisplayName(huntername);
				meta2.setColor(Color.BLACK);
				meta2.setDisplayName(huntername);
				meta3.setColor(Color.BLACK);
				meta3.setDisplayName(huntername);
				meta4.setColor(Color.BLACK);
				meta4.setDisplayName(huntername);
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
				if (pla.getInventory().getChestplate().getItemMeta().getDisplayName().equals(huntername)) {
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
