package ga.ganma.minigames;

import org.bukkit.*;
import ga.ganma.minigames.missiontime.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class mission {
static public boolean ismission;
static byte oldmission;
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
static public HashMap<String,Boolean> missiontf = new HashMap<String, Boolean>();

    static public void mission1(int starttime, Location missionLocation){
        mission1t = starttime;
        mission1t = mission1t - 600;
        L1 = missionLocation;
        new mission1time().runTaskTimer(Minigames.plg,0,20);
    }

    static public void mission2(int starttime, Location missionLocation){
        mission2t = starttime;
        mission2t = mission2t - 600;
        L2 = missionLocation;
        new mission2time().runTaskTimer(Minigames.plg,0,20);
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendTitle(ChatColor.RED + "ミッションが発動された...","通報部隊の投入を阻止せよ",10,60,20);
            p.playSound(p.getLocation(), Sound.UI_TOAST_IN,1,2);
        }
        Eventget.missionS = "どこかにプレイヤーの発光を阻止する場所を置いた。" + "その場所を見つけ出し、その場所にあるアイテムを拾い、プレイヤーの発光を阻止せよ！"+"なお、そのアイテムを拾うと、使った者に対し逃走に有利な条件が付く！";
        item1meta.setDisplayName("プレイヤー発光阻止アイテム");
        item1.setItemMeta(item1meta);
        tosoCommand.world.dropItemNaturally(tosoCommand.mission2L,item1);
    }

    static public void mission3(int starttime, Location missionLocation){
        mission3t = starttime;
        mission3t = mission3t - 600;
        L3 = missionLocation;
        new mission3time().runTaskTimer(Minigames.plg,0,20);
    }

    static public void mission4(int starttime, Location missionLocation){
        mission4t = starttime;
        mission4t = mission4t - 600;
        L4 = missionLocation;
        new mission4time().runTaskTimer(Minigames.plg,0,20);
    }
}
