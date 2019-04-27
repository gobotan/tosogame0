package ga.ganma.minigames.listeners;

import static ga.ganma.minigames.TosoNow.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import ga.ganma.minigames.MissionManagerFixed;
import ga.ganma.minigames.event.GameTimeChangedEvent;
import ga.ganma.minigames.missions.Mission;
import ga.ganma.minigames.missions.Mission3;

public class MissionListener implements Listener {

	@EventHandler
	public void onGameTimeChanged(GameTimeChangedEvent e) {
		Mission mission = MissionManagerFixed.getCurrentMission();
		if (mission != null) {
			mission.onGameTimeChanged(e.getCurrentGameTime());
		}
	}

	@EventHandler
	public void mission3Listener(PlayerInteractEntityEvent e) {
		if (!(e.getRightClicked() instanceof Player)) {
			return;
		}
		Player p = e.getPlayer();
		Player rightClicked = (Player) e.getRightClicked();
		ItemStack hand = p.getInventory().getItemInMainHand();
		Mission mission = MissionManagerFixed.getCurrentMission();
		if (!hand.getItemMeta().getDisplayName().equals(MissionManagerFixed.getMission3KeyTitle())) {
			return;
		}
		if (!(mission instanceof Mission3)) {
			return;
		}
		Mission3 mission3 = (Mission3) mission;
		boolean success = mission3.completePlayer(p);

		if (success) {
			p.sendMessage(rightClicked.getName() + "さんの時限爆弾を解除しました！");
			rightClicked.sendMessage("あなたは" + p.getName() + "さんに時限装置を解除されました！");
		} else if (!success) {
			p.sendMessage("あなたがクリックしたプレイヤーはすでに時限装置は解除されています。");
		}
	}

	@EventHandler
	public void missionClearListener(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();

		if (b == null || !MissionManagerFixed.isRunningMission()) {
			return;
		}

		// mission1 / mission3
		if (b.getType() == Material.BEDROCK || b.getType() == Material.REDSTONE_BLOCK) {
			MissionManagerFixed.completeMission(false, p);
			b.setType(Material.AIR);
			return;
		}
		if (b.getType() == Material.DIAMOND_BLOCK) {
			moneytanka = 300;
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (Hunter.getEntries().contains(player.getName())) {
					PotionEffect pe = new PotionEffect(PotionEffectType.SPEED, 4000, 1);
					player.addPotionEffect(pe);
				}
			}
			b.setType(Material.AIR);
			MissionManagerFixed.completeMission(false, p);
		}
	}
}
