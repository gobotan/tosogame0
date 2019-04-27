package ga.ganma.minigames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

import static ga.ganma.minigames.Minigames.*;
import static org.bukkit.Bukkit.getServer;

public class EventGet implements Listener {

	FileConfiguration config;
	static public String missionS;
	static public boolean chat;

	public EventGet(Plugin pl) {
		pl.getServer().getPluginManager().registerEvents(this, pl);
	}

	@EventHandler
	public void b(EntityDamageByEntityEvent e) {
		boolean isHunter;
		boolean isRunner;
		if (Minigames.start) {
			Set<String> tosoMember = Runner.getEntries();
			Set<String> huntMember = Hunter.getEntries();
			Entity ByEntity = e.getDamager();
			Entity fromEntity = e.getEntity();
			if (ByEntity instanceof Player) {
				if (fromEntity instanceof Player) {
					Player ByPlayer = (Player) e.getDamager();
					isHunter = huntMember.contains(ByPlayer.getName());
					Player fromplayer = (Player) e.getEntity();
					isRunner = tosoMember.contains(fromplayer.getName());
					if (isHunter && isRunner) {
						fromplayer.sendMessage("あなたは確保されました。3秒後に牢屋へテレポートします。");
						jailCount.put(fromplayer, 3);
						jailL = new Location(ByPlayer.getWorld(), plg.getConfig().getInt("jail.x"),
								plg.getConfig().getInt("jail.y"), plg.getConfig().getInt("jail.z"));
						Runner.removeEntry(fromplayer.getName());
						Jailer.addEntry(fromplayer.getName());
						e.setDamage(0d);
					} else {
						e.setCancelled(true);
					}
				}
			}
		}
		if (e.getEntity() instanceof Player) {
			((Player) e.getEntity()).damage(0d);
		}
	}

	@EventHandler
	public void c(PlayerToggleSprintEvent e) {
		if (start) {
			Set<String> huntMember = Hunter.getEntries();
			sprintpl = e.getPlayer();
			if (e.isSprinting()) {
				issprint.put(sprintpl, true);
				if (huntMember.contains(sprintpl.getName())) {
					sprintpl.setWalkSpeed(0.3f);
				}
			} else {
				issprint.put(sprintpl, false);
				if (huntMember.contains(sprintpl.getName())) {
					sprintpl.setWalkSpeed(0.15f);
				}
			}
		}
	}

	@EventHandler
	public void a(InventoryClickEvent e) {
		if (e.getCurrentItem().getItemMeta() != null) {
			Player pl = (Player) e.getWhoClicked();
			String clickItem = e.getCurrentItem().getItemMeta().getDisplayName();
			config = plg.getConfig();
			if (clickItem.equals(TosoCommand.menu1data.getDisplayName()) && !e.isShiftClick()) {
				Location l = pl.getLocation();
				this.config.set("jail.x", (int) l.getX());
				this.config.set("jail.y", (int) l.getY());
				this.config.set("jail.z", (int) l.getZ());
				this.config.set("jail.boolean", true);
				plg.saveConfig();
				plg.reloadConfig();
				pl.sendMessage(ChatColor.GRAY + "牢獄の座標を設定しました。");
				e.setCancelled(true);
			} else if (clickItem.equalsIgnoreCase(TosoCommand.menu2data.getDisplayName()) && !e.isShiftClick()) {
				Location l = pl.getLocation();
				this.config.set("box.x", (int) l.getX());
				this.config.set("box.y", (int) l.getY());
				this.config.set("box.z", (int) l.getZ());
				this.config.set("box.boolean", true);
				plg.saveConfig();
				plg.reloadConfig();
				pl.sendMessage(ChatColor.GRAY + "ハンターのスポーン地点の座標を設定しました。");
				e.setCancelled(true);
			} else if (clickItem.equalsIgnoreCase(TosoCommand.menu3data.getDisplayName()) && !e.isShiftClick()) {
				Location l = pl.getLocation();
				this.config.set("lobby.x", (int) l.getX());
				this.config.set("lobby.y", (int) l.getY());
				this.config.set("lobby.z", (int) l.getZ());
				this.config.set("lobby.boolean", true);
				plg.saveConfig();
				plg.reloadConfig();
				pl.sendMessage(ChatColor.GRAY + "ロビーの座標を設定しました。");
				e.setCancelled(true);
			} else if (clickItem.equalsIgnoreCase(TosoCommand.menu4data.getDisplayName()) && !e.isShiftClick()) {
				Location l = pl.getLocation();
				this.config.set("res.x", (int) l.getX());
				this.config.set("res.y", (int) l.getY());
				this.config.set("res.z", (int) l.getZ());
				this.config.set("res.boolean", true);
				plg.saveConfig();
				plg.reloadConfig();
				pl.sendMessage(ChatColor.GRAY + "復活地点の座標を設定しました。");
				e.setCancelled(true);
			} else if (clickItem.equalsIgnoreCase(TosoCommand.menu5data.getDisplayName()) && !e.isShiftClick()) {
				Location l = pl.getLocation();
				this.config.set("mission1.x", (int) l.getX());
				this.config.set("mission1.y", (int) l.getY());
				this.config.set("mission1.z", (int) l.getZ());
				this.config.set("mission1.boolean", true);
				plg.saveConfig();
				plg.reloadConfig();
				pl.sendMessage(ChatColor.GRAY + "ミッションで使用するの座標1を設定しました。");
				e.setCancelled(true);
			} else if (clickItem.equalsIgnoreCase(TosoCommand.menu6data.getDisplayName()) && !e.isShiftClick()) {
				Location l = pl.getLocation();
				this.config.set("mission2.x", (int) l.getX());
				this.config.set("mission2.y", (int) l.getY());
				this.config.set("mission2.z", (int) l.getZ());
				this.config.set("mission2.boolean", true);
				plg.saveConfig();
				plg.reloadConfig();
				pl.sendMessage(ChatColor.GRAY + "ミッションで使用するの座標2を設定しました。");
				e.setCancelled(true);
			} else if (clickItem.equalsIgnoreCase(TosoCommand.menu7data.getDisplayName()) && !e.isShiftClick()) {
				Location l = pl.getLocation();
				this.config.set("mission3.x", (int) l.getX());
				this.config.set("mission3.y", (int) l.getY());
				this.config.set("mission3.z", (int) l.getZ());
				this.config.set("mission3.boolean", true);
				plg.saveConfig();
				plg.reloadConfig();
				pl.sendMessage(ChatColor.GRAY + "ミッションで使用するの座標3を設定しました。");
				e.setCancelled(true);
			} else if (clickItem.equalsIgnoreCase(TosoCommand.menu8data.getDisplayName()) && !e.isShiftClick()) {
				Location l = pl.getLocation();
				this.config.set("mission4.x", (int) l.getX());
				this.config.set("mission4.y", (int) l.getY());
				this.config.set("mission4.z", (int) l.getZ());
				this.config.set("mission4.boolean", true);
				plg.saveConfig();
				plg.reloadConfig();
				pl.sendMessage(ChatColor.GRAY + "ミッションで使用するの座標4を設定しました。");
				e.setCancelled(true);
			} else if (clickItem.equalsIgnoreCase(Phone.missionlistmeta.getDisplayName()) && !e.isShiftClick()) {
				pl.closeInventory();
				if (Mission.isMission && missionS != null) {
					pl.sendMessage(missionS);
				} else {
					pl.sendMessage("現在発動されているミッションはありません。");
				}
			} else if (clickItem.equalsIgnoreCase(TosoCommand.closedata.getDisplayName()) && !e.isShiftClick()) {
				e.setCancelled(true);
				pl.closeInventory();
			} else if (clickItem.equals(huntername)) {
				e.setCancelled(true);
			} else if (clickItem.contains("牢獄に入っている人") && e.isShiftClick()) {
				e.setCancelled(true);
				pl.closeInventory();
				pl.sendMessage("未実装");
			}
		}
	}

	@EventHandler
	public void d(PlayerJoinEvent e) {
		Player pl = e.getPlayer();
		if (!start) {
			pl.sendMessage(GAME + "ゾス鯖逃走中へようこそ！");
			pl.sendMessage(GAME + "ルールをしっかり読み、楽しい逃走中ライフをどうぞ！");
			pl.sendMessage(GAME + ChatColor.GRAY + "あなたを逃走者に追加しました。");
			Runner.addEntry(pl.getName());
			pl.setWalkSpeed(0.2f);
			if (Hunter.getEntries().contains(pl.getName())) {
				Hunter.removeEntry(pl.getName());
			}
			pl.setPlayerListName(pl.getName() + "[" + ChatColor.AQUA + "逃走者" + ChatColor.WHITE + "]");
			if (plg.getConfig().getBoolean("lobby.boolean")) {
				Minigames.lobbyL = new Location(pl.getWorld(), plg.getConfig().getInt("lobby.x"),
						plg.getConfig().getInt("lobby.y"), plg.getConfig().getInt("lobby.z"));
				pl.teleport(lobbyL);
			}
			if (pl.getInventory().getChestplate() != null) {
				if (pl.getInventory().getChestplate().getItemMeta().getDisplayName().equals(huntername)) {
					pl.getInventory().setChestplate(null);
					pl.getInventory().setLeggings(null);
					pl.getInventory().setBoots(null);
					pl.getInventory().setHelmet(null);
				}
			}
			pl.getInventory().setItem(0, null);
			pl.getInventory().setItem(1, null);
			pl.getInventory().setItem(2, null);
			pl.getInventory().setItem(3, null);
			pl.getInventory().setItem(4, null);
			pl.getInventory().setItem(5, null);
			pl.getInventory().setItem(6, null);
			pl.getInventory().setItem(7, null);
			pl.getInventory().setItem(8, null);
			pl.getInventory().setItem(9, null);
			pl.getInventory().setItem(10, null);
			pl.getInventory().setItem(11, null);
			pl.getInventory().setItem(12, null);
			pl.getInventory().setItem(13, null);
			pl.getInventory().setItem(14, null);
			pl.getInventory().setItem(15, null);
			pl.getInventory().setItem(16, null);
			pl.getInventory().setItem(17, null);
			pl.getInventory().setItem(18, null);
			pl.getInventory().setItem(19, null);
			pl.getInventory().setItem(20, null);
			pl.getInventory().setItem(21, null);
			pl.getInventory().setItem(22, null);
			pl.getInventory().setItem(23, null);
			pl.getInventory().setItem(24, null);
			pl.getInventory().setItem(25, null);
			pl.getInventory().setItem(26, null);
			pl.getInventory().setItem(27, null);
			pl.removePotionEffect(PotionEffectType.SPEED);
		} else if (!Hunter.getEntries().contains(pl.getName()) || Runner.getEntries().contains(pl.getName())) {
			if (gameTime < 1800) {
				config = plg.getConfig();
				pl.sendMessage("ゲームが開始してから30分以上経っているため、牢屋にスポーンしました。");
				jailL = new Location(pl.getWorld(), config.getInt("jail.x"), config.getInt("jail.y"),
						config.getInt("jail.z"));
				pl.teleport(jailL);
			} else if (gameTime > 1800) {
				config = plg.getConfig();
				if (!Jailer.getEntries().contains(pl.getName())) {
					pl.sendMessage("ゲームが開始しているので、ゲーム開始場所にテレポートしました。");
					resL = new Location(pl.getWorld(), config.getInt("res.x"), config.getInt("res.y"),
							config.getInt("res.z"));
					pl.teleport(resL);
					Runner.addEntry(pl.getName());
					pl.setSneaking(true);
				} else {
					pl.sendMessage("あなたはすでに確保されているため、牢屋にテレポートしました。");
					jailL = new Location(pl.getWorld(), config.getInt("jail.x"), config.getInt("jail.y"),
							config.getInt("jail.z"));
					pl.teleport(jailL);
				}
			}
		} else if (Hunter.getEntries().contains(pl.getName())) {
			pl.sendMessage("途中でログアウトしたため、ハンターボックスにテレポートしました。");
			pl.teleport(hunterL);
		}
		e.getPlayer().setFoodLevel(20);
		e.getPlayer().setHealth(20);
	}

	@EventHandler
	public void e(PlayerCommandPreprocessEvent e) {
		for (Player p : getServer().getOnlinePlayers()) {
			if (p.isOp()) {
				p.sendMessage(ChatColor.GRAY + "[コマンドログ]" + e.getPlayer().getName() + "：" + e.getMessage());
			}
		}
	}

	@EventHandler
	public void g(EntityInteractEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void h(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void i(EntityDamageByBlockEvent e) {
		if (e.getDamager() != null) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void j(ItemDespawnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void l(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains("鍵")) {
				Player p = (Player) e.getRightClicked();
				if (!Mission.mission2B.get(p)) {
					Mission.mission2B.put((Player) e.getRightClicked(), true);
					(e.getRightClicked()).sendMessage("あなたは" + e.getPlayer().getName() + "さんに時限装置を解除されました！");
				} else if (Mission.mission2B.get(p)) {
					e.getPlayer().sendMessage("あなたがクリックしたプレイヤーはすでに時限装置は解除されています。");
				}
			}
		}
	}

	@EventHandler
	public void m(PlayerToggleSneakEvent e) {
		if (!e.isSneaking()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void n(AsyncPlayerChatEvent e) {
		if (!chat) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(GAME + "現在、全体のチャットは制限されています。");
		} else {
			e.setCancelled(false);
		}
	}

	@EventHandler
	public void o(PlayerInteractEvent e) {
		if (e.getClickedBlock() != null && Mission.isMission) {
			Block bl = e.getClickedBlock();
			if (bl.getType() == Material.DIAMOND_BLOCK) {
				moneytanka = 300;
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (Hunter.getEntries().contains(p.getName())) {
						PotionEffect pe = new PotionEffect(PotionEffectType.SPEED, 4000, 1);
						p.addPotionEffect(pe);
					}
				}
				bl.setType(Material.AIR);
				Mission.isMission = false;
				Mission.CLEARp = e.getPlayer();
			} else if (bl.getType() == Material.BEDROCK) {
				Mission.isMission = false;
				bl.setType(Material.AIR);
				Mission.CLEARp = e.getPlayer();
			} else if (bl.getType() == Material.REDSTONE_BLOCK) {
				Mission.isMission = false;
				bl.setType(Material.AIR);
				Mission.CLEARp = e.getPlayer();
			}
		}
	}

	@EventHandler
	public void p(PlayerMoveEvent e) {
		e.getPlayer().setSneaking(true);
	}

	@EventHandler
	public void q(InventoryCreativeEvent e) {
		e.setCancelled(false);
	}
}
