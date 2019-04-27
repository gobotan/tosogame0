package ga.ganma.minigames.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ga.ganma.minigames.GameSettingsManager.KeyType;
import ga.ganma.minigames.ItemHelper;
import net.md_5.bungee.api.ChatColor;

public class SettingsInventoryController {

	private static final int inventorySize = 9 * 2;
	private static final String inventoryTitle = ChatColor.YELLOW + "逃走中plugin 設定画面";
	private static ItemStack menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8, close;

	public static void init() {
		menu1 = ItemHelper.create(Material.IRON_FENCE, "牢獄の座標設定");
		menu2 = ItemHelper.create(Material.STICK, "ハンターボックス座標設定");
		menu3 = ItemHelper.create(Material.IRON_BLOCK, "ロビー座標設定");
		menu4 = ItemHelper.create(Material.REDSTONE_TORCH_ON, "復活地点の座標設定");
		menu5 = ItemHelper.create(Material.PAPER, "ミッションで使用する座標1");
		menu6 = ItemHelper.create(Material.PAPER, "ミッションで使用する座標2");
		menu7 = ItemHelper.create(Material.PAPER, "ミッションで使用する座標3");
		menu8 = ItemHelper.create(Material.PAPER, "ミッションで使用する座標4");
		close = ItemHelper.create(Material.REDSTONE_BLOCK, "閉じる");
	}

	public static Inventory generateInventory() {
		Inventory inv;
		inv = Bukkit.createInventory(null, inventorySize, inventoryTitle);

		inv.setItem(0, menu1.clone());
		inv.setItem(1, menu2.clone());
		inv.setItem(2, menu3.clone());
		inv.setItem(3, menu4.clone());
		inv.setItem(4, menu5.clone());
		inv.setItem(5, menu6.clone());
		inv.setItem(6, menu7.clone());
		inv.setItem(7, menu8.clone());
		inv.setItem(17, close.clone());
		return inv;
	}

	public static ButtonType getButtonType(ItemStack item) {
		if (menu1.equals(item)) {
			return ButtonType.JAIL_LOCATION;
		} else if (menu2.equals(item)) {
			return ButtonType.HUNTER_LOCATION;
		} else if (menu3.equals(item)) {
			return ButtonType.LOBBY_LOCATION;
		} else if (menu4.equals(item)) {
			return ButtonType.RESPAWN_LOCATION;
		} else if (menu5.equals(item)) {
			return ButtonType.MISSION_LOC_1;
		} else if (menu6.equals(item)) {
			return ButtonType.MISSION_LOC_2;
		} else if (menu7.equals(item)) {
			return ButtonType.MISSION_LOC_3;
		} else if (menu8.equals(item)) {
			return ButtonType.MISSION_LOC_4;
		} else if (close.equals(item)) {
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

	public enum ButtonType {
		JAIL_LOCATION, HUNTER_LOCATION, LOBBY_LOCATION, RESPAWN_LOCATION, MISSION_LOC_1, MISSION_LOC_2, MISSION_LOC_3, MISSION_LOC_4, CLOSE, UNKNOWN;

		public static KeyType convertToKeyType(ButtonType type) {
			switch (type) {
			case JAIL_LOCATION:
				return KeyType.JAIL;
			case HUNTER_LOCATION:
				return KeyType.HUNTER_BOX;
			case LOBBY_LOCATION:
				return KeyType.LOBBY;
			case RESPAWN_LOCATION:
				return KeyType.RESPAWN;
			case MISSION_LOC_1:
				return KeyType.MISSION1;
			case MISSION_LOC_2:
				return KeyType.MISSION2;
			case MISSION_LOC_3:
				return KeyType.MISSION3;
			case MISSION_LOC_4:
				return KeyType.MISSION4;
			default:
				return null;
			}
		}

		public static String convertToChatString(ButtonType type) {
			switch (type) {
			case JAIL_LOCATION:
				return "牢獄";
			case HUNTER_LOCATION:
				return "ハンターのスポーン地点";
			case LOBBY_LOCATION:
				return "ロビー";
			case RESPAWN_LOCATION:
				return "復活地点";
			case MISSION_LOC_1:
				return "ミッションの座標1";
			case MISSION_LOC_2:
				return "ミッションの座標2";
			case MISSION_LOC_3:
				return "ミッションの座標3";
			case MISSION_LOC_4:
				return "ミッションの座標4";
			default:
				return null;
			}
		}
	}
}
