package ga.ganma.minigames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ga.ganma.minigames.GameManager;
import ga.ganma.minigames.GameManager.FinishCause;
import ga.ganma.minigames.GameSettingsManager;
import ga.ganma.minigames.GameSettingsManager.KeyType;
import ga.ganma.minigames.TosoNow;
import ga.ganma.minigames.event.GameTimeChangedEvent;
import me.rayzr522.jsonmessage.JSONMessage;

public class GameTimeChangeListener implements Listener {

	@EventHandler
	public void teleportPlayers(GameTimeChangedEvent e) {
		if (e.getCurrentGameTime() == 3660) {

			for (Player p : Bukkit.getOnlinePlayers()) {
				if (GameManager.getRunners().contains(p)) {
					p.teleport(GameSettingsManager.getLocation(KeyType.RESPAWN));
				}
				p.setDisplayName(p.getPlayerListName().replace(p.getName(), ""));
				p.setCustomName("");
				p.setSneaking(true);
			}
		}
	}

	@EventHandler
	public void hunterReleaseCountDown(GameTimeChangedEvent e) {
		int time = e.getCurrentGameTime();

		if (time == 3630) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "30秒前", 10, 50, 30);
				p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
			return;
		}

		if (3601 <= time && time <= 3610) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.RED + "ハンター放出まで", ChatColor.WHITE + "" + (time - 3600), 0, 25, 0);
				p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
			}
		}
	}

	@EventHandler
	public void hunterRelease(GameTimeChangedEvent e) {
		if (e.getCurrentGameTime() != 3600) {
			return;
		}

		Bukkit.broadcastMessage(ChatColor.RED + "ハンターが放出されました！");
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 2);
			p.sendTitle(ChatColor.RED + "ハンター放出！", "時間が0秒になるまで逃げ切れ！", 20, 60, 40);

			if (GameManager.getHunters().contains(p)) {
				p.teleport(GameSettingsManager.getLocation(KeyType.HUNTER_BOX));
				p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 2);

				p.sendMessage(ChatColor.GRAY + "ハンターボックスにテレポートしました。");
				p.sendTitle(ChatColor.RED + "ハンター放出！", "逃走者を全員捕まえろ！", 20, 60, 40);

				p.setFoodLevel(20);
			}
		}
	}

	@EventHandler
	public void gameFinish(GameTimeChangedEvent e) {
		if (e.getCurrentGameTime() == 0) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.AQUA + "逃走中が終了しました！", null, 10, 50, 30);
				p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
				p.setSneaking(false);
			}

			Bukkit.getServer().broadcastMessage(TosoNow.PREFIX + "逃走中が終了しました！");
			GameManager.finishGame(FinishCause.TIME_LIMIT);
		}
	}

	@EventHandler
	public void actionBarDisplayer(GameTimeChangedEvent e) {
		if (GameManager.getCurrentGameTime() % 60 < 10) {
			JSONMessage msg = JSONMessage
					.create(GameManager.getCurrentGameTime() / 60 + "：0" + GameManager.getCurrentGameTime() % 60);
			for (Player p : Bukkit.getOnlinePlayers()) {
				msg.actionbar(p);
			}
		} else {
			JSONMessage msg = JSONMessage
					.create(GameManager.getCurrentGameTime() / 60 + "：" + GameManager.getCurrentGameTime() % 60);
			for (Player p : Bukkit.getOnlinePlayers()) {
				msg.actionbar(p);
			}
		}
	}
}
