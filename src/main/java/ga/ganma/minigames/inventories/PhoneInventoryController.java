package ga.ganma.minigames.inventories;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ga.ganma.minigames.ItemHelper;
import ga.ganma.minigames.Mission;
import ga.ganma.minigames.TosoNow;
import net.md_5.bungee.api.ChatColor;

public class PhoneInventoryController {

	private static final int inventorySize = 9 * 6;
	private static final String inventoryTitle = ChatColor.GREEN + "逃走中専用携帯電話";
	private static ItemStack close;

	private static final Material jailListItemMaterial = Material.IRON_FENCE;
	private static final String jailListItemTitle = ChatColor.RED + "牢獄に入っている人";

	private static final Material missionItemMaterial = Material.BOOK;

	private static final String currentMission = "現在のミッション";
	private static final String missionIsAvailable = ChatColor.RED + "発動中";

	public static void init() {
		close = ItemHelper.create(Material.REDSTONE_BLOCK, "閉じる");
	}

	public static Inventory generateInventory() {
		Inventory inv;
		inv = Bukkit.createInventory(null, inventorySize, inventoryTitle);

		inv.setItem(1, getMissionItem());
		inv.setItem(5, getJailListItem());
		inv.setItem(53, close.clone());
		return inv;
	}

	public static ButtonType getButtonType(ItemStack item) {
		if (!item.hasItemMeta()) {
			return ButtonType.UNKNOWN;
		}

		ItemMeta meta = item.getItemMeta();

		if (jailListItemTitle.equals(meta.getDisplayName()) && item.getType() == jailListItemMaterial) {
			return ButtonType.JAIL_LIST;
		}
		if (Arrays.asList(currentMission, missionIsAvailable).contains(meta.getDisplayName())
				&& item.getType() == missionItemMaterial) {
			return ButtonType.MISSION_LIST;
		}
		if (item.equals(close)) {
			return ButtonType.CLOSE;
		}

		return ButtonType.UNKNOWN;
	}

	public static boolean isSettingsInventory(Inventory inv) {
		boolean sameTitle = inv.getTitle().equals(inventoryTitle);
		boolean sameSize = inv.getSize() == inventorySize;

		if (sameTitle && sameSize) {
			return true;
		}
		return false;
	}

	private static ItemStack getMissionItem() {
		ItemStack item;
		if (Mission.isMission) {
			item = ItemHelper.create(Material.BOOK, missionIsAvailable, ChatColor.AQUA + "クリックで現在のミッションを見る");
		} else {
			item = ItemHelper.create(Material.BOOK, currentMission, ChatColor.RED + "現在ミッションは発動されていません");
		}

		return item;
	}

	private static ItemStack getJailListItem() {
		return ItemHelper.create(jailListItemMaterial, jailListItemTitle,
				ChatColor.RESET + "現在の確保者：" + TosoNow.Jailer.getSize() + "人",
				ChatColor.RESET + "右クリックで牢獄にいるプレイヤーを表示" + "※未実装（クリックしても何も起きません");
	}

	public enum ButtonType {
		JAIL_LIST, MISSION_LIST, CLOSE, UNKNOWN;
	}
}
