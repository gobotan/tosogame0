package ga.ganma.minigames.listeners;

import static ga.ganma.minigames.TosoNow.*;
import static org.bukkit.Bukkit.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import ga.ganma.minigames.GameManager;
import ga.ganma.minigames.GameManager.PlayerType;
import ga.ganma.minigames.GameSettingsManager;
import ga.ganma.minigames.GameSettingsManager.KeyType;
import ga.ganma.minigames.TosoNow;
import ga.ganma.minigames.event.GameTimeChangedEvent;

public class GameListeners implements Listener {

	public static boolean chat = true;

	@EventHandler
	public void arrestedEvent(EntityDamageByEntityEvent e) {
		boolean isHunter;
		boolean isRunner;
		if (GameManager.isRunningGame()) {
			Entity ByEntity = e.getDamager();
			Entity fromEntity = e.getEntity();
			if (ByEntity instanceof Player) {
				if (fromEntity instanceof Player) {
					Player byPlayer = (Player) e.getDamager();
					Player fromPlayer = (Player) e.getEntity();

					isHunter = GameManager.getHunters().contains(byPlayer);
					isRunner = GameManager.getRunners().contains(fromPlayer);
					if (isHunter && isRunner) {
						fromPlayer.sendMessage("あなたは確保されました。3秒後に牢屋へテレポートします。");
						jailCount.put(fromPlayer, 3);
						GameManager.setPlayerType(fromPlayer, PlayerType.JAILER);
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

	private HashMap<Player, Integer> jailCount = new HashMap<>();

	@EventHandler
	public void teleportToJail(GameTimeChangedEvent e) {
		for (Player key : new ArrayList<Player>(jailCount.keySet())) {
			if (jailCount.get(key) == 0) {
				jailCount.remove(key);

			}
		}
	}

	@EventHandler
	public void changeSprintingEvent(PlayerToggleSprintEvent e) {
		if (!GameManager.isRunningGame()) {
			return;
		}

		List<Player> hunters = GameManager.getHunters();
		Player sprintPlayer = e.getPlayer();

		if (!hunters.contains(sprintPlayer)) {
			return;
		}

		if (e.isSprinting()) {
			sprintPlayer.setWalkSpeed(0.3f);
		} else {
			sprintPlayer.setWalkSpeed(0.15f);
		}
	}

	@EventHandler
	public void joinPlayerSetupEvent(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!GameManager.isRunningGame()) {
			p.sendMessage(PREFIX + "ゾス鯖逃走中へようこそ！");
			p.sendMessage(PREFIX + "ルールをしっかり読み、楽しい逃走中ライフをどうぞ！");

			GameManager.setPlayerType(p, PlayerType.RUNNER);

			p.sendMessage(PREFIX + ChatColor.GRAY + "あなたを逃走者に追加しました。");

			if (GameSettingsManager.getLocation(KeyType.LOBBY) != null)
				p.teleport(GameSettingsManager.getLocation(KeyType.LOBBY));

			p.getInventory().setContents(new ItemStack[36]);
			p.removePotionEffect(PotionEffectType.SPEED);
		} else if (!GameManager.getHunters().contains(p) || GameManager.getRunners().contains(p)) {
			if (GameManager.getCurrentGameTime() < 1800) {
				p.sendMessage("ゲームが開始してから30分以上経っているため、牢屋にスポーンしました。");
				GameManager.setPlayerType(p, PlayerType.JAILER);
				p.teleport(GameSettingsManager.getLocation(KeyType.JAIL));
			} else {
				if (!GameManager.getJailers().contains(p)) {
					p.sendMessage("ゲームが開始しているので、ゲーム開始場所にテレポートしました。");
					p.teleport(GameSettingsManager.getLocation(KeyType.RESPAWN));
					GameManager.setPlayerType(p, PlayerType.RUNNER);
					p.setSneaking(true);
				} else {
					p.sendMessage("あなたはすでに確保されているため、牢屋にテレポートしました。");
					p.teleport(GameSettingsManager.getLocation(KeyType.JAIL));
					GameManager.setPlayerType(p, PlayerType.JAILER);
				}
			}
		} else if (GameManager.getHunters().contains(p)) {
			p.sendMessage("途中でログアウトしたため、ハンターボックスにテレポートしました。");
			p.teleport(GameSettingsManager.getLocation(KeyType.HUNTER_BOX));
		}

		e.getPlayer().setFoodLevel(20);
		e.getPlayer().setHealth(20);
	}

	@EventHandler
	public void commandLogEvent(PlayerCommandPreprocessEvent e) {
		for (Player p : getServer().getOnlinePlayers()) {
			if (p.isOp() && p != e.getPlayer()) {
				p.sendMessage(ChatColor.GRAY + "[コマンドログ] " + e.getPlayer().getName() + "：" + e.getMessage());
			}
		}
	}

	@EventHandler
	public void publicChatCanceller(AsyncPlayerChatEvent e) {
		if (!chat) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(PREFIX + "現在、全体のチャットは制限されています。");
		} else {
			e.setCancelled(false);
		}
	}

	@EventHandler
	public void hunterArmorChangeCanceller(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();
		Inventory inv = e.getClickedInventory();

		if (inv == null || clickedItem == null || !clickedItem.hasItemMeta()) {
			return;
		}
		if (!inv.equals(p.getInventory())) {
			return;
		}

		if (TosoNow.hunterArmorTitle.equals(clickedItem.getItemMeta().getDisplayName())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void autoSneakListener(PlayerMoveEvent e) {
		e.getPlayer().setSneaking(true);
	}

	@EventHandler
	public void creativeInventoryAllowListener(InventoryCreativeEvent e) {
		e.setCancelled(false);
	}

	@EventHandler
	public void interactCanceller(EntityInteractEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void foodLevelChangeCanceller(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void blockDamageCanceller(EntityDamageByBlockEvent e) {
		if (e.getDamager() != null) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void itemDespawnCanceller(ItemDespawnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void sneakFixListener(PlayerToggleSneakEvent e) {
		if (!e.isSneaking()) {
			e.setCancelled(true);
		}
	}
}
