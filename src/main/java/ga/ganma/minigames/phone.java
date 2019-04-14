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

import static ga.ganma.minigames.tosoCommand.closedata;

public class phone implements CommandExecutor {
    public static final String kaigyo = System.getProperty("line.separator");
    static ItemStack missionlist = new ItemStack(Material.BOOK);
    static ItemMeta missionlistmeta = missionlist.getItemMeta();
    static ItemStack oldmissionlist = new ItemStack(Material.PAPER);
    static ItemMeta oldmissionlistmeta = oldmissionlist.getItemMeta();
    static ItemStack jaillist = new ItemStack(Material.IRON_FENCE);
    static ItemMeta jaillistmeta = jaillist.getItemMeta();
    static Inventory in;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;
        in = Bukkit.createInventory(null,54,"逃走中専用携帯電話");
        missionlistmeta.setDisplayName("現在のミッション");
        if(mission.ismission) {
            missionlistmeta.setLocalizedName(ChatColor.RED + "発動中");
            missionlistmeta.setLore(Arrays.asList(ChatColor.AQUA + "クリックで現在のミッションを見る"));
        }
        else missionlistmeta.setLore(Arrays.asList(ChatColor.RED + "現在ミッションは発動されていません"));
        if(mission.oldmission != 0) {
            oldmissionlistmeta.setDisplayName("過去のミッション");
            oldmissionlistmeta.setLore(Arrays.asList(ChatColor.AQUA + "クリックで過去のミッションを見る"));
        }
        else{
            oldmissionlistmeta.setDisplayName("過去のミッション");
            oldmissionlistmeta.setLore(Arrays.asList(ChatColor.AQUA + "まだ、過去にミッションは発令されていません！"));
        }
        jaillistmeta.setDisplayName("牢獄に入っている人");
        jaillistmeta.setLore(Arrays.asList(ChatColor.RESET + "現在の確保者：" + Minigames.Jailer.getSize() + "人",ChatColor.RESET + "右クリックで牢獄にいるプレイヤーを表示"));
        closedata.setDisplayName("閉じる");
        tosoCommand.close.setItemMeta(closedata);
        missionlist.setItemMeta(missionlistmeta);
        oldmissionlist.setItemMeta(oldmissionlistmeta);
        jaillist.setItemMeta(jaillistmeta);
        in.setItem(1,missionlist);
        in.setItem(3,oldmissionlist);
        in.setItem(5,jaillist);
        in.setItem(53,tosoCommand.close);

        p.openInventory(in);

        return false;
    }
}
