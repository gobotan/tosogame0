package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

import static ga.ganma.minigames.TosoCommand.closedata;

public class Phone implements CommandExecutor {
	public static final String kaigyo = System.getProperty("line.separator");
	static ItemStack missionlist = new ItemStack(Material.BOOK);
	static ItemMeta missionlistmeta = missionlist.getItemMeta();
	static ItemStack oldmissionlist = new ItemStack(Material.PAPER);
	static ItemMeta oldmissionlistmeta = oldmissionlist.getItemMeta();
	static ItemStack jaillist = new ItemStack(Material.IRON_FENCE);
	static ItemMeta jaillistmeta = jaillist.getItemMeta();
	static Inventory in;
	static HashMap<String, String> missionString = new HashMap<String, String>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		in = Bukkit.createInventory(null, 54, "逃走中専用携帯電話");
		missionlistmeta.setDisplayName("現在のミッション");
		if (Mission.isMission) {
			missionlistmeta.setLocalizedName(ChatColor.RED + "発動中");
			missionlistmeta.setLore(Arrays.asList(ChatColor.AQUA + "クリックで現在のミッションを見る"));
		} else
			missionlistmeta.setLore(Arrays.asList(ChatColor.RED + "現在ミッションは発動されていません"));
		jaillistmeta.setDisplayName("牢獄に入っている人");
		jaillistmeta.setLore(Arrays.asList(ChatColor.RESET + "現在の確保者：" + Minigames.Jailer.getSize() + "人",
				ChatColor.RESET + "右クリックで牢獄にいるプレイヤーを表示" + "※未実装（クリックしても何も起きません"));
		closedata.setDisplayName("閉じる");
		TosoCommand.close.setItemMeta(closedata);
		missionlist.setItemMeta(missionlistmeta);
		oldmissionlist.setItemMeta(oldmissionlistmeta);
		jaillist.setItemMeta(jaillistmeta);
		in.setItem(1, missionlist);
		in.setItem(5, jaillist);
		in.setItem(53, TosoCommand.close);

		p.openInventory(in);

		return false;
	}
}
