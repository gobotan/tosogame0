package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;

public class gametimer extends BukkitRunnable {
    @Override
    public void run() {
        if(Minigames.gametime == 3601) {
            Bukkit.broadcastMessage(ChatColor.RED + "ハンターが放出されました！");
            for (Player allp:Bukkit.getServer().getOnlinePlayers()){
                allp.playSound(allp.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,1,2);
                allp.sendTitle(ChatColor.RED + "ハンター放出！","時間が0秒になるまで逃げ切れ！" , 20,60,40);
            }
            for(Player p:Bukkit.getOnlinePlayers()){
                if(Minigames.Hunter.getEntries().contains(p.getName())) {
                    p.teleport(Minigames.hunterL);
                    p.sendMessage(ChatColor.GRAY + "ハンターボックスにテレポートしました。");
                    p.sendTitle(ChatColor.RED + "ハンター放出！", "逃走者を全員捕まえろ！", 20, 60, 40);
                }
            }
        }
        if(Minigames.gametime == 3602){
            for (Player allp:Bukkit.getServer().getOnlinePlayers()){
                allp.sendTitle(ChatColor.RED + "ハンター放出まで",ChatColor.WHITE + "1" , 0,25,0);
                allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK,1,2);
            }
        }
        if(Minigames.gametime == 3603){
            for (Player allp:Bukkit.getServer().getOnlinePlayers()){
                allp.sendTitle(ChatColor.RED + "ハンター放出まで",ChatColor.WHITE + "2" , 0,25,0);
                allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK,1,2);
            }
        }
        if(Minigames.gametime == 3604){
            for (Player allp:Bukkit.getServer().getOnlinePlayers()){
                allp.sendTitle(ChatColor.RED + "ハンター放出まで",ChatColor.WHITE + "3" , 0,25,0);
                allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK,1,2);
            }
        }
        if(Minigames.gametime == 3605){
            for (Player allp:Bukkit.getServer().getOnlinePlayers()){
                allp.sendTitle(ChatColor.RED + "ハンター放出まで",ChatColor.WHITE + "4" , 0,25,0);
                allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK,1,2);
            }
        }
        if(Minigames.gametime == 3606){
            for (Player allp:Bukkit.getServer().getOnlinePlayers()){
                allp.sendTitle(ChatColor.RED + "ハンター放出まで",ChatColor.WHITE + "5" , 0,25,0);
                allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK,1,2);
            }
        }
        if(Minigames.gametime == 3631){
            for (Player allp:Bukkit.getServer().getOnlinePlayers()){
                allp.sendTitle(ChatColor.RED + "ハンター放出まで",ChatColor.WHITE + "30秒前" , 10,50,30);
                allp.playSound(allp.getLocation(), Sound.UI_BUTTON_CLICK,1,2);
            }
        }
        if (Minigames.gametime == 1){
            Bukkit.getServer().broadcastMessage(Minigames.GAME + "逃走中が終了しました！");
            Minigames.start = false;
            this.cancel();
        }
        if(Minigames.gametime == 3660){
            for(Player p:Bukkit.getOnlinePlayers()){
                if(Minigames.Runner.getEntries().contains(p.getName())){
                    p.teleport(Minigames.resL);
                }
            }
        }
        Minigames.gametime --;
        if(Minigames.gametime < 3601){
            int pr = Minigames.prize;
            Minigames.prize = pr + Minigames.moneytanka;
        }
        Minigames.main.unregister();
        Minigames.main = Minigames.board.registerNewObjective("main", "dummy");
        Minigames.main.setDisplayName(Minigames.GAME);
        Minigames.main.setDisplaySlot(DisplaySlot.SIDEBAR);

        Minigames.Stime = Minigames.main.getScore(ChatColor.GREEN + "残り時間" + ChatColor.WHITE + "：" + Minigames.gametime + "秒");
        Minigames.Stime.setScore(0);
        Minigames.Smoney = Minigames.main.getScore(ChatColor.GOLD +  "賞金" + ChatColor.WHITE + "：" + Minigames.prize + "円");
        Minigames.Smoney.setScore(1);
        Minigames.tanka = Minigames.main.getScore("賞金単価：" + ChatColor.DARK_GREEN + "1秒" + ChatColor.WHITE + Minigames.moneytanka + ChatColor.DARK_GREEN + "円");
        Minigames.tanka.setScore(-1);

        for (Player p:Bukkit.getOnlinePlayers()){
            if (Minigames.jailcount.keySet().contains(p) && Minigames.jailcount.get(p) != null){
                if (Minigames.jailcount.get(p).equalsIgnoreCase("three")){
                    Minigames.jailcount.put(p,"two");
                }
                else if (Minigames.jailcount.get(p).equalsIgnoreCase("two")){
                    Minigames.jailcount.put(p,"one");
                }
                else if (Minigames.jailcount.get(p).equalsIgnoreCase("one")){
                    Minigames.jailcount.put(p,"zero");
                }
                else if(Minigames.jailcount.get(p).equalsIgnoreCase("zero")){
                    Minigames.jailcount.put(p,null);
                    p.teleport(Minigames.jailL);
                    p.sendMessage(ChatColor.GRAY + "牢屋にテレポートしました");
                }
            }
        }
    }
}
