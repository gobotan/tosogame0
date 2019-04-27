package ga.ganma.minigames.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ga.ganma.minigames.MissionManager;
import ga.ganma.minigames.inventories.PhoneInventoryController;
import ga.ganma.minigames.inventories.PhoneInventoryController.ButtonType;
import ga.ganma.minigames.missions.Mission;

public class PhoneInventoryListener implements Listener {

	@EventHandler
	public void onClickPhoneInventory(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}

		Player pl = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();
		Inventory clickedInventory = e.getClickedInventory();

		if (clickedItem == null || clickedInventory == null) {
			return;
		}
		if (!clickedItem.hasItemMeta()) {
			return;
		}

		ButtonType type = PhoneInventoryController.getButtonType(clickedItem);

		if (type == ButtonType.MISSION_LIST && !e.isShiftClick()) {
			pl.closeInventory();
			if (MissionManager.isRunningMission()) {
				Mission mission = MissionManager.getCurrentMission();
				pl.sendMessage(mission.getDescription());
			} else {
				pl.sendMessage("現在発動されているミッションはありません。");
			}
		} else if (type == ButtonType.CLOSE && !e.isShiftClick()) {
			e.setCancelled(true);
			pl.closeInventory();
		} else if (type == ButtonType.JAIL_LIST && e.isShiftClick()) {
			e.setCancelled(true);
			pl.closeInventory();
			pl.sendMessage("未実装");
		}
	}
}
