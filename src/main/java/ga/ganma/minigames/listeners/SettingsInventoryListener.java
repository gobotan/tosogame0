package ga.ganma.minigames.listeners;

import static ga.ganma.minigames.TosoNow.*;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ga.ganma.minigames.TosoNow;
import ga.ganma.minigames.inventories.SettingsInventoryController;
import ga.ganma.minigames.inventories.SettingsInventoryController.ButtonType;

public class SettingsInventoryListener implements Listener {

	private FileConfiguration config;

	public SettingsInventoryListener(TosoNow plugin) {
		this.config = plugin.getConfig();
	}

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

		if (type == ButtonType.JAIL_LOCATION && !e.isShiftClick()) {
			Location l = pl.getLocation();
			this.config.set("jail.x", (int) l.getX());
			this.config.set("jail.y", (int) l.getY());
			this.config.set("jail.z", (int) l.getZ());
			this.config.set("jail.boolean", true);
			plugin.saveConfig();
			plugin.reloadConfig();
			pl.sendMessage(ChatColor.GRAY + "牢獄の座標を設定しました。");
			e.setCancelled(true);
		} else if (type == ButtonType.HUNTER_LOCATION && !e.isShiftClick()) {
			Location l = pl.getLocation();
			this.config.set("box.x", (int) l.getX());
			this.config.set("box.y", (int) l.getY());
			this.config.set("box.z", (int) l.getZ());
			this.config.set("box.boolean", true);
			plugin.saveConfig();
			plugin.reloadConfig();
			pl.sendMessage(ChatColor.GRAY + "ハンターのスポーン地点の座標を設定しました。");
			e.setCancelled(true);
		} else if (type == ButtonType.LOBBY_LOCATION && !e.isShiftClick()) {
			Location l = pl.getLocation();
			this.config.set("lobby.x", (int) l.getX());
			this.config.set("lobby.y", (int) l.getY());
			this.config.set("lobby.z", (int) l.getZ());
			this.config.set("lobby.boolean", true);
			plugin.saveConfig();
			plugin.reloadConfig();
			pl.sendMessage(ChatColor.GRAY + "ロビーの座標を設定しました。");
			e.setCancelled(true);
		} else if (type == ButtonType.RESPAWN_LOCATION && !e.isShiftClick()) {
			Location l = pl.getLocation();
			this.config.set("res.x", (int) l.getX());
			this.config.set("res.y", (int) l.getY());
			this.config.set("res.z", (int) l.getZ());
			this.config.set("res.boolean", true);
			plugin.saveConfig();
			plugin.reloadConfig();
			pl.sendMessage(ChatColor.GRAY + "復活地点の座標を設定しました。");
			e.setCancelled(true);
		} else if (type == ButtonType.MISSION_LOC_1 && !e.isShiftClick()) {
			Location l = pl.getLocation();
			this.config.set("mission1.x", (int) l.getX());
			this.config.set("mission1.y", (int) l.getY());
			this.config.set("mission1.z", (int) l.getZ());
			this.config.set("mission1.boolean", true);
			plugin.saveConfig();
			plugin.reloadConfig();
			pl.sendMessage(ChatColor.GRAY + "ミッションで使用するの座標1を設定しました。");
			e.setCancelled(true);
		} else if (type == ButtonType.MISSION_LOC_2 && !e.isShiftClick()) {
			Location l = pl.getLocation();
			this.config.set("mission2.x", (int) l.getX());
			this.config.set("mission2.y", (int) l.getY());
			this.config.set("mission2.z", (int) l.getZ());
			this.config.set("mission2.boolean", true);
			plugin.saveConfig();
			plugin.reloadConfig();
			pl.sendMessage(ChatColor.GRAY + "ミッションで使用するの座標2を設定しました。");
			e.setCancelled(true);
		} else if (type == ButtonType.MISSION_LOC_3 && !e.isShiftClick()) {
			Location l = pl.getLocation();
			this.config.set("mission3.x", (int) l.getX());
			this.config.set("mission3.y", (int) l.getY());
			this.config.set("mission3.z", (int) l.getZ());
			this.config.set("mission3.boolean", true);
			plugin.saveConfig();
			plugin.reloadConfig();
			pl.sendMessage(ChatColor.GRAY + "ミッションで使用するの座標3を設定しました。");
			e.setCancelled(true);
		} else if (type == ButtonType.MISSION_LOC_4 && !e.isShiftClick()) {
			Location l = pl.getLocation();
			this.config.set("mission4.x", (int) l.getX());
			this.config.set("mission4.y", (int) l.getY());
			this.config.set("mission4.z", (int) l.getZ());
			this.config.set("mission4.boolean", true);
			plugin.saveConfig();
			plugin.reloadConfig();
			pl.sendMessage(ChatColor.GRAY + "ミッションで使用するの座標4を設定しました。");
			e.setCancelled(true);
		} else if (type == ButtonType.CLOSE && !e.isShiftClick()) {
			e.setCancelled(true);
			pl.closeInventory();
		}
	}
}
