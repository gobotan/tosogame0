package ga.ganma.minigames.listeners;

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

import ga.ganma.minigames.GameManager;
import ga.ganma.minigames.MissionManager;
import ga.ganma.minigames.event.GameTimeChangedEvent;
import ga.ganma.minigames.missions.Mission;
import ga.ganma.minigames.missions.Mission3;

public class MissionListener implements Listener {

	@EventHandler
	public void onGameTimeChanged(GameTimeChangedEvent e) {
		Mission mission = MissionManager.getCurrentMission();
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
		Mission mission = MissionManager.getCurrentMission();
		if (!hand.getItemMeta().getDisplayName().equals(MissionManager.getMission3KeyTitle())) {
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

		if (b == null || !MissionManager.isRunningMission()) {
			return;
		}

		// mission1 / mission3
		if (b.getType() == Material.BEDROCK || b.getType() == Material.REDSTONE_BLOCK) {
			MissionManager.completeMission(false, p);
			b.setType(Material.AIR);
			return;
		}
		if (b.getType() == Material.DIAMOND_BLOCK) {
			GameManager.setPrizePerSecond(300);
			for (Player player : GameManager.getHunters()) {
				PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 4000, 1);
				player.addPotionEffect(effect);
			}
			b.setType(Material.AIR);
			MissionManager.completeMission(false, p);
		}
	}
}
