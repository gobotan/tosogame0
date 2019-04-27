package ga.ganma.minigames.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ga.ganma.minigames.GameSettingsManager;
import ga.ganma.minigames.GameSettingsManager.KeyType;
import ga.ganma.minigames.inventories.SettingsInventoryController;
import ga.ganma.minigames.inventories.SettingsInventoryController.ButtonType;

public class SettingsInventoryListener implements Listener {

	@EventHandler
	public void adminSettingsEvent(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}

		Player pl = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();
		Inventory clickedInventory = e.getClickedInventory();
		if (e.getCurrentItem().getItemMeta() == null) {
			return;
		}
		if (clickedItem == null || clickedInventory == null) {
			return;
		}

		ButtonType type = SettingsInventoryController.getButtonType(clickedItem);
		KeyType keyType = ButtonType.convertToKeyType(type);
		if (keyType != null) {
			e.setCancelled(true);
			GameSettingsManager.setLocation(keyType, pl.getLocation());
			pl.sendMessage(ChatColor.GRAY + ButtonType.convertToChatString(type) + "を設定しました。");
			return;
		}

		if (type == ButtonType.CLOSE && !e.isShiftClick()) {
			e.setCancelled(true);
			pl.closeInventory();
		}
	}
}
