package ga.ganma.minigames;

import org.bukkit.*;
import ga.ganma.minigames.missiontime.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class Mission {
	static public boolean isMission;
	static public int mission1t;
	static public int mission2t;
	static public int mission3t;
	static public int mission4t;
	static Location L1;
	static Location L2;
	static Location L3;
	static Location L4;
	static ItemStack item1 = new ItemStack(Material.SEEDS);
	static public ItemMeta item1meta = item1.getItemMeta();
	static ItemStack item2 = new ItemStack(Material.TRIPWIRE_HOOK);
	static ItemMeta item2meta = item2.getItemMeta();
	static public HashMap<String, Boolean> missiontf = new HashMap<String, Boolean>();
	static public HashMap<Player, Boolean> mission2B = new HashMap<Player, Boolean>();
	static public Player CLEARp;
	static public Location blockL;

	static public void mission1(int starttime, Location missionLocation) {
		mission1t = starttime;
		mission1t = mission1t - 600;
		L1 = missionLocation;
		new Mission1Time().runTaskTimer(Minigames.plugin, 0, 20);
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED + "ミッションが発動された...", "ゲーム時間をループ状態から戻せ！", 10, 60, 20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1, 2);
		}
		EventGet.missionS = "ゲームエラーのため、ゲーム時間が1200秒から600秒の間でループするようになった！" + "これを直すために、ある地点にゲームエラーを修復するための岩盤を設置した。"
				+ "そのボタンを押し、ゲームエラーを直せ！";
		missionLocation.setX(missionLocation.getX() - 1);
		TosoCommand.world.getBlockAt(missionLocation).setType(Material.BEDROCK);
	}

	static public void mission2(int starttime, Location missionLocation) {
		mission2t = starttime;
		mission2t = mission2t - 600;
		L2 = missionLocation;
		new Mission2Time().runTaskTimer(Minigames.plugin, 0, 20);
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED + "ミッションが発動された...", "プレイヤーの発光を阻止せよ", 10, 60, 20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1, 2);
		}
		EventGet.missionS = "今から10分後にプレイヤーが30秒間発光してしまう！" + "しかし、どこかにプレイヤーの発光を阻止するブロックを置いた。"
				+ "その場所を見つけ出し、その場所にあるアイテムを拾い、プレイヤーの発光を阻止せよ！" + "なお、そのアイテムを拾うと、使った者に対し逃走に有利な条件が付く！";
		missionLocation.setX(missionLocation.getX() - 1);
		TosoCommand.world.getBlockAt(missionLocation).setType(Material.REDSTONE_BLOCK);
		blockL = missionLocation;
	}

	static public void mission3(int starttime, Location missionLocation) {
		mission3t = starttime;
		mission3t = mission3t - 600;
		L3 = missionLocation;
		new Mission3Time().runTaskTimer(Minigames.plugin, 0, 20);
		item2meta.setDisplayName("鍵");
		item2.setItemMeta(item2meta);
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED + "ミッションが発動された...", "時限装置を解除せよ！", 10, 60, 20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1, 2);
			p.getInventory().setItem(0, item2);
			mission2B.put(p, false);
		}
		EventGet.missionS = "プレイヤー全員に鍵を渡した。今から10分後に時限装置が鳴り響くようになり、ハンターが集まってくる！"
				+ "解除するには、他のプレイヤーが持っている鍵を右クリックして貰う必要がある！" + "なお、鍵は何回でも使用可能である。";
	}

	static public void mission4(int starttime, Location missionLocation) {
		mission4t = starttime;
		mission4t = mission4t - 600;
		L4 = missionLocation;
		new Mission4Time().runTaskTimer(Minigames.plugin, 0, 20);
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendTitle(ChatColor.RED + "ミッションが発動された...", "賞金増加のチャンス！", 10, 60, 20);
			p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1, 2);
		}
		blockL = missionLocation;
		EventGet.missionS = "ある地点にクリックすると賞金単価を1秒300円にするブロックを設置した。" + "しかし、そのブロックをクリックするとハンターが20分間強化されてしまう！"
				+ "押さなくても何もペナルティは無い。押すか押さないかはプレイヤーの自由だ！";
		EventGet.chat = false;
		missionLocation.setX(missionLocation.getX() - 1);
		TosoCommand.world.getBlockAt(missionLocation).setType(Material.DIAMOND_BLOCK);
	}
}
