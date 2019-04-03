package ga.ganma.minigames;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.HashSet;

public final class Minigames extends JavaPlugin implements Listener {
    static boolean start;
    static boolean hunter;
    static Economy econ = null;
    static Permission perms = null;
    static Chat chat = null;
    static ScoreboardManager manager;
    static Scoreboard board;
    static Team Runner;
    static Team Hunter;
    static Team Jailer;
    final static String GAME = ("[" + ChatColor.RED + "ZOSU鯖逃走中" + ChatColor.WHITE + "]");
    static Player sprintpl;
    static FileConfiguration config;
    static public int prize;
    static int gametime = 3600;
    static Objective main;
    static Score Stime;
    static Score Smoney;
    static Score tanka;
    static Minigames plg;
    static Player fromplayer;
    static Location jailL;
    static int jailX;
    static int jailY;
    static int jailZ;
    static HashMap<Player, String> issprint = new HashMap<Player, String>();

    @Override
    public void onEnable() {
        plg = this;
        getLogger().info("逃走中プラグインが起動しました。");
        getLogger().info("create by ganma");
        getLogger().info("※このpluginはCC-BY-4.0ライセンスを適用しています。");
        getLogger().info("URL: https://choosealicense.com/licenses/cc-by-4.0/");
        getLogger().info("現在のバージョン0.0.3α");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("toso").setExecutor(new tosoCommand());
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - vaultが存在しません！" +
                    "このプラグインはvaultが前提プラグインとなっているため、必ず入れなければならないプラグインです！", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            econ = ((Economy) economyProvider.getProvider());
        }
        manager = Bukkit.getScoreboardManager();
        board = manager.getMainScoreboard();
        Runner = board.getTeam("Runner");
        if (Runner == null) {
            Runner = board.registerNewTeam("Runner");
            Runner.setAllowFriendlyFire(false);
            Runner.setCanSeeFriendlyInvisibles(true);
        }
        Hunter = board.getTeam("Hunter");
        if (Hunter == null) {
            Hunter = board.registerNewTeam("Hunter");
            Hunter.setAllowFriendlyFire(false);
            Hunter.setCanSeeFriendlyInvisibles(true);
        }
        Jailer = board.getTeam("Jailer");
        if (Jailer == null) {
            Jailer = board.registerNewTeam("Jailer");
            Hunter.setAllowFriendlyFire(false);
            Hunter.setCanSeeFriendlyInvisibles(true);
        }
        saveDefaultConfig();
        config = getConfig();
        main = board.getObjective("main");
        if (main == null) {
            main = board.registerNewObjective("main", "dummy");
            main.setDisplayName(GAME);
            main.setDisplaySlot(DisplaySlot.SIDEBAR);
            Stime = main.getScore("時間：" + 0);
            Smoney = main.getScore("賞金：" + 0 + "円");
            tanka = main.getScore("1秒：100円");
            Stime.setScore(0);
            Smoney.setScore(1);
            tanka.setScore(-1);
        }
        prize = 0;
    }

    @EventHandler
    public void b(EntityDamageByEntityEvent e) {
        if (start) {
            EntityType ByEntity = e.getDamager().getType();
            EntityType fromEntity = e.getEntity().getType();
            if (ByEntity == EntityType.PLAYER) {
                if (fromEntity == EntityType.PLAYER) {
                    Player ByPlayer = (Player) e.getDamager();
                    fromplayer = (Player) e.getEntity();
                    if (Hunter.getPlayers().contains(ByPlayer) && Runner.getPlayers().contains(fromplayer)) {
                        new jail().runTaskLater(this, 60);
                        fromplayer.sendMessage("あなたは確保されました。3秒後に牢屋へテレポートします。");
                        jailL = new Location(
                                ByPlayer.getWorld(),
                                getConfig().getInt("jail.x"),
                                getConfig().getInt("jail.y"),
                                getConfig().getInt("jail.z")
                        );
                        Runner.removePlayer(fromplayer);
                        Jailer.addPlayer(fromplayer);
                    }
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void c(PlayerToggleSprintEvent e) {
        if (start) {
            sprintpl = e.getPlayer();
            if (e.isSprinting()) {
                issprint.put(sprintpl,"sprint");
            }
            else{
                issprint.put(sprintpl,"walk");
            }
        }
    }
    @EventHandler
    public void a(InventoryClickEvent e) {
        if (e.getCurrentItem().getItemMeta() != null) {
            Player pl = (Player) e.getWhoClicked();
            String clickItem = e.getCurrentItem().getItemMeta().getDisplayName();
            config = getConfig();
            if (clickItem.equalsIgnoreCase(tosoCommand.menu1data.getDisplayName())) {
                Location l = pl.getLocation();
                config.set("jail.x", (int)l.getX());
                config.set("jail.y", (int)l.getY());
                config.set("jail.z", (int)l.getZ());
                config.set("jail.world",l.getWorld().getName());
                saveConfig();
                pl.sendMessage(ChatColor.GRAY + "牢獄の座標を設定しました。");
                e.setCancelled(true);
            } else if (clickItem.equalsIgnoreCase(tosoCommand.menu2data.getDisplayName())) {
                Location l = pl.getLocation();
                config.set("box.x", (int)l.getX());
                config.set("box.y", (int)l.getY());
                config.set("box.z", (int)l.getZ());
                config.set("box.world",l.getWorld());
                saveConfig();
                pl.sendMessage(ChatColor.GRAY + "ハンターのスポーン地点の座標を設定しました。");
                e.setCancelled(true);
            } else if (clickItem.equalsIgnoreCase(tosoCommand.menu3data.getDisplayName())) {
                Location l = pl.getLocation();
                config.set("lobby.x", (int)l.getX());
                config.set("lobby.y", (int)l.getY());
                config.set("lobby.z", (int)l.getZ());
                config.set("lobby.world",l.getWorld());
                saveConfig();
                pl.sendMessage(ChatColor.GRAY + "ロビーの座標を設定しました。");
                e.setCancelled(true);
            } else if(clickItem.equalsIgnoreCase(tosoCommand.menu4data.getDisplayName())){
                Location l = pl.getLocation();
                config.set("res.x", (int)l.getX());
                config.set("res.y", (int)l.getY());
                config.set("res.z", (int)l.getZ());
                config.set("res.world",l.getWorld());
                saveConfig();
                pl.sendMessage(ChatColor.GRAY + "復活地点の座標を設定しました。");
                e.setCancelled(true);
            } else if (clickItem.equalsIgnoreCase(tosoCommand.closedata.getDisplayName())) {
                e.setCancelled(true);
                pl.closeInventory();
            }
            reloadConfig();
        }
    }

    @EventHandler
    public void d(PlayerJoinEvent e){
        Player pl = e.getPlayer();
        pl.sendMessage(GAME + "ゾス鯖逃走中へようこそ！");
        pl.sendMessage(GAME + "ルールをしっかり読み、楽しい逃走中ライフをどうぞ！");
        pl.sendMessage(GAME + ChatColor.GRAY + "あなたを逃走者に追加しました。");
        Runner.addPlayer(pl);
        if(pl.getScoreboard().getTeams().equals("Hunter")){
            Hunter.removePlayer(pl);
        }
        pl.setPlayerListName(pl.getName() + "[" + ChatColor.AQUA + "逃走者" + ChatColor.WHITE + "]");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onDisable() {
        getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    public void sendmoney(){
        for(Player winner:getServer().getOnlinePlayers()){
            for(String tags:winner.getScoreboardTags()){
                if(tags.equalsIgnoreCase("winner")){
                    econ.depositPlayer(winner,prize);

                }
            }
            getServer().broadcastMessage(ChatColor.DARK_AQUA + "無事に逃げ切った人に賞金" + ChatColor.RED + prize + "円を渡しました！");
        }
    }
}
