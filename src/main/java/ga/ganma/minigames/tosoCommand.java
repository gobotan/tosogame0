package ga.ganma.minigames;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

import static ga.ganma.minigames.Minigames.*;
import static org.bukkit.Bukkit.getServer;

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
    ArrayList<Player> randamhunter = new ArrayList<Player>();
    Player pla = null;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player target = null;
        if (sender instanceof Player) {
            p = (Player) sender;
            int tanka;
            if (p.isOp()) {
                if (args.length != 0) {
                    if (args[0].equalsIgnoreCase("start")) {
                        start();
                    } else if (args[0].equalsIgnoreCase("setting")) {
                        setting();
                    } else if (args[0].equalsIgnoreCase("end")) {
                        Minigames.gametime = 10;
                    } else if (args[0].equalsIgnoreCase("tanka")) {
                        if (args.length == 2) {
                            tanka = Integer.parseInt(args[1]);
                            Minigames.moneytanka = tanka;
                            getServer().broadcastMessage("賞金単価を1秒" + Minigames.moneytanka + "円に変更しました");
                        }
                    }else if(args[0].equalsIgnoreCase("prize")){
                        if(!start)
                        Minigames.sendmoney();
                    }
                } else {
                    p.sendMessage(ChatColor.GRAY + "/toso start と打つと逃走中がスタートします。");
                    p.sendMessage(ChatColor.GRAY + "/toso hunter と打つと打った人がハンターに立候補した人が決定します。");
                    p.sendMessage(ChatColor.GRAY + "/toso setting と打つと設定用のGUIが表示されます。");
                }
            } else {
                p.sendMessage("あなたはそのコマンドの権限を持っていません！");
            }

        }
        if (args[0].equalsIgnoreCase("hunter")) {
            if (args.length == 2) {
                target = Bukkit.getPlayerExact(args[1]);
                if (target != null) {
                    if (Minigames.Runner.getPlayers().contains(target)) {
                        Minigames.Runner.removePlayer(target);
                        Minigames.Hunter.addPlayer(target);
                        getServer().broadcastMessage("ハンターは" + target.getName() + "さんに決まりました！");
                        target.setPlayerListName(target.getName() + "[" + ChatColor.DARK_GRAY + "ハンター" + ChatColor.WHITE + "]");
                        hunter = true;
                    } else {
                        Minigames.Hunter.removePlayer(target);
                        Minigames.Runner.addPlayer(target);
                        getServer().broadcastMessage(target.getName() + "さんをハンターから削除しました。");
                        target.setPlayerListName(target.getName() + "[" + ChatColor.AQUA + "逃走者" + ChatColor.WHITE + "]");
                        if (Minigames.Hunter.getPlayers() == null) {
                            hunter = false;
                            getServer().broadcastMessage("現在ハンターが0人になったため、ゲームを開始できなくなりました。");
                        }
                    }
                }
            } else {
                hunter();
            }
        }

        return false;
    }


    void start() {
        if (Minigames.hunter) {
            Minigames.start = true;
            Minigames.plg.getConfig();
            Minigames.resL = new Location(
                    p.getWorld(),
                    plg.getConfig().getInt("res.x"),
                    plg.getConfig().getInt("res.y"),
                    plg.getConfig().getInt("res.z")
            );
            Minigames.hunterL = new Location(
                    p.getWorld(),
                    plg.getConfig().getInt("box.x"),
                    plg.getConfig().getInt("box.y"),
                    plg.getConfig().getInt("box.z")
            );
            getServer().broadcastMessage(Minigames.GAME + "逃走中が開始しました！");
            getServer().broadcastMessage(Minigames.GAME + "制限時間は60分");
            getServer().broadcastMessage(Minigames.GAME + "ハンター放出まで残り1分です！残り時間が3600秒になるとハンターが放出します！");
            getServer().broadcastMessage(Minigames.GAME + "5秒後にテレポートとタイマーをスタートします。");
            Minigames.gametime = 3660;
            new gametimer().runTaskTimer(Minigames.plg, 100, 20);
            new Timer().runTaskTimer(Minigames.plg, 0, 30);
            new Timer2().runTaskTimer(Minigames.plg, 0, 50);
        } else p.sendMessage(Minigames.GAME + ChatColor.RED + "まだハンターを決めていません！");
    }

    public void hunter() {
        Minigames.hunter = true;
        Material block;
        Location l;
        int blockx;
        int blocky;
        int blockz;
        for (Player pl : getServer().getOnlinePlayers()) {
            l = pl.getLocation();
            blockx = l.getBlockX();
            blocky = l.getBlockY();
            blocky--;
            blockz = l.getBlockZ();
            l.setX(blockx);
            l.setY(blocky);
            l.setZ(blockz);
            block = l.getBlock().getType();
            if (block == Material.DIAMOND_BLOCK) {
                randamhunter.add(pl);
            }
        }
        hunterkimekime();
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

    public void hunterkimekime() {
        if(!(randamhunter.isEmpty())){
            int size = randamhunter.size();
            Random random = new Random();
            int randomV = random.nextInt(size);
            pla = randamhunter.get(randomV);
            randamhunter.clear();
            if (Minigames.Runner.getPlayers().contains(pla)) {
                Minigames.Runner.removePlayer(pla);
                Minigames.Hunter.addPlayer(pla);
                getServer().broadcastMessage("ハンターは" + pla.getName() + "さんに決まりました！");
                pla.setPlayerListName(pla.getName() + "[" + ChatColor.DARK_GRAY + "ハンター" + ChatColor.WHITE + "]");
                Minigames.hunter = true;
            }
            else if (pla != null) {
                Minigames.Hunter.removePlayer(pla);
                Minigames.Runner.addPlayer(pla);
                getServer().broadcastMessage(pla.getName() + "さんをハンターから削除しました。");
                pla.setPlayerListName(pla.getName() + "[" + ChatColor.AQUA + "逃走者" + ChatColor.WHITE + "]");
                if (Minigames.Hunter.getPlayers() == null) {
                    Minigames.hunter = false;
                    getServer().broadcastMessage("現在ハンターが0人になったため、ゲームを開始できなくなりました。");
                }
            }
            else {
                System.out.println("エラー");
            }

        }
        else {
            Bukkit.broadcastMessage(ChatColor.RED + "ダイヤモンドブロックの上にまだ誰も乗っていません！");
        }
    }
}

