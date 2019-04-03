package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;

public class tosoCommand implements CommandExecutor {
    static Player p;
    static ItemStack menu1 = new ItemStack(Material.IRON_FENCE);
    static ItemMeta menu1data = menu1.getItemMeta();
    static ItemStack menu2 = new ItemStack(Material.STICK);
    static ItemMeta menu2data = menu2.getItemMeta();
    static ItemStack menu3 = new ItemStack(Material.IRON_BLOCK);
    static ItemMeta menu3data = menu3.getItemMeta();
    static ItemStack menu4 = new ItemStack(Material.REDSTONE_TORCH_ON);
    static ItemMeta menu4data = menu4.getItemMeta();
    static ItemStack close = new ItemStack(Material.REDSTONE_BLOCK);
    static ItemMeta closedata = close.getItemMeta();
    int kimari;
    String Stag = null;
    int tag = 0;
    Player hastag = null;
    String Skimari;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            p = (Player) sender;
            Player target = null;
            if (p.isOp()) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("start")) {
                        start();
                    } else if (args[0].equalsIgnoreCase("hunter")) {
                        if (args.length == 2) {
                            target = Bukkit.getPlayerExact(args[1]);
                        }
                        if (target != null) {
                            if(Minigames.Runner.getPlayers().contains(target)){
                                Minigames.Runner.removePlayer(target);
                                Minigames.Hunter.addPlayer(target);
                                Bukkit.getServer().broadcastMessage("ハンターは" + target.getName() + "さんに決まりました！");
                                Minigames.hunter = true;
                            }
                            else{
                                Minigames.Hunter.removePlayer(target);
                                Minigames.Runner.addPlayer(target);
                                Bukkit.getServer().broadcastMessage(target.getName() + "さんをハンターから削除しました。");
                                if(Minigames.Hunter.getPlayers() == null){
                                    Minigames.hunter = false;
                                    Bukkit.getServer().broadcastMessage("現在ハンターが0人になったため、ゲームを開始できなくなりました。");
                                }
                            }
                        } else {
                            hunter();
                        }
                    } else if (args[0].equalsIgnoreCase("setting")) {
                        setting();
                    } else if (args[0].equalsIgnoreCase("end")) {
                        Minigames.gametime = 10;
                    } else {
                        p.sendMessage(ChatColor.GRAY + "/toso start と打つと逃走中がスタートします。");
                        p.sendMessage(ChatColor.GRAY + "/toso hunter と打つと打った人がハンターに立候補した人が決定します。");
                        p.sendMessage(ChatColor.GRAY + "/toso setting と打つと設定用のGUIが表示されます。");
                    }
                } else {
                    p.sendMessage(ChatColor.GRAY + "/toso start と打つと逃走中がスタートします。");
                    p.sendMessage(ChatColor.GRAY + "/toso hunter と打つと打った人がハンターに立候補した人が決定します。");
                    p.sendMessage(ChatColor.GRAY + "/toso setting と打つと設定用のGUIが表示されます。");
                }

            } else {
                p.sendMessage("あなたはそのコマンドの権限を持っていません！");
            }
        } else {
            System.out.println("このコマンドはサーバーの中にいるプレイヤーが打ってね！");
        }
        return false;
    }

    void start() {
        if (Minigames.hunter) {
            Minigames.start = true;
            Bukkit.getServer().broadcastMessage(Minigames.GAME + "逃走中が開始しました！");
            Bukkit.getServer().broadcastMessage(Minigames.GAME + "制限時間は60分");
            Bukkit.getServer().broadcastMessage(Minigames.GAME + "ハンター放出まで残り1分です！残り時間が3600秒になるとハンターが放出します！");
            Bukkit.getServer().broadcastMessage(Minigames.GAME + "5秒後にテレポートとタイマーをスタートします。");
            Minigames.gametime = 3660;
            new gametimer().runTaskTimer(Minigames.plg, 100, 20);
            new Timer().runTaskTimer(Minigames.plg,0,30);
            new Timer2().runTaskTimer(Minigames.plg,0,50);
        } else p.sendMessage(Minigames.GAME + ChatColor.RED + "まだハンターを決めていません！");
    }

    public void hunter() {
        Minigames.hunter = true;
        Material block;
        Location l;
        int blockx;
        int blocky;
        int blockz;
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            l = pl.getLocation();
            blockx = l.getBlockX();
            blocky = l.getBlockY();
            blocky--;
            blockz = l.getBlockZ();
            l.setX(blockx);
            l.setY(blocky);
            l.setZ(blockz);
            block = l.getBlock().getType();
            Bukkit.getServer().broadcastMessage("乗っているブロックは" + block.name() + "です。");
            if (block == Material.DIAMOND_BLOCK) {
                pl.addScoreboardTag("hunter");
            }
        }
        tag();
    }

    public void setting() {
        Inventory inv;
        inv = Bukkit.createInventory(null, 9, "逃走中plugin 設定画面");
        menu1data.setDisplayName("牢獄の座標設定");
        menu2data.setDisplayName("ハンターボックス座標設定");
        menu3data.setDisplayName("ロビー座標設定");
        menu4data.setDisplayName("復活地点の座標設定");
        closedata.setDisplayName("閉じる");
        menu1.setItemMeta(menu1data);
        menu2.setItemMeta(menu2data);
        menu3.setItemMeta(menu3data);
        menu4.setItemMeta(menu4data);
        close.setItemMeta(this.closedata);
        inv.setItem(0, menu1);
        inv.setItem(1, menu2);
        inv.setItem(2, menu3);
        inv.setItem(3, menu4);
        inv.setItem(8, this.close);
        p.openInventory(inv);
    }

    public void tag() {
        for (Player hastag : Bukkit.getServer().getOnlinePlayers()) {
            if (hastag.getScoreboardTags().contains("hunter")) {
                Stag = String.valueOf(tag);
                hastag.addScoreboardTag(Stag);
                tag++;
            }
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i <= tag; i++) {
                list.add(i);
                Collections.shuffle(list);
            }
            kimari = list.get(0);
            Skimari = String.valueOf(kimari);
        }
        for (Player random : Bukkit.getServer().getOnlinePlayers()) {
            if (random.getScoreboardTags().contains(Skimari)) {
                Minigames.Hunter.addPlayer(random);
                Minigames.Runner.removePlayer(random);
                Bukkit.getServer().broadcastMessage("ハンターは" + random.getName() + "さんに決まりました！");
                Minigames.hunter = true;
            }
            random.removeScoreboardTag("hunter");
            for(;tag == 0;) {
                random.removeScoreboardTag(Stag);
                Stag = String.valueOf(tag);
                tag--;
            }
        }
    }
}

