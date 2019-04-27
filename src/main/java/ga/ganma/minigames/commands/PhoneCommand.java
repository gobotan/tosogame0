package ga.ganma.minigames.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import ga.ganma.minigames.inventories.PhoneInventoryController;
import net.md_5.bungee.api.ChatColor;

public class PhoneCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーのみ実行可能です。");
			return true;
		}
		Player p = (Player) sender;
		Inventory phoneInv = PhoneInventoryController.generateInventory();
		p.openInventory(phoneInv);
		return true;
	}
}
