package ga.ganma.minigames.missions;

import ga.ganma.minigames.TosoNow;
import ga.ganma.minigames.commands.TosoCommand;
import ga.ganma.minigames.Mission;
import ga.ganma.minigames.listeners.GameListeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Mission2Time extends BukkitRunnable {

	@Override
	public void run() {
		if (TosoNow.gameTime <= Mission.mission2t) {
			Mission.isMission = false;
			this.cancel();
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.RED + "ミッション失敗", "発光が30秒間ついてしまった！", 20, 100, 20);
				p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1, 2);
				if (TosoNow.Runner.getEntries().contains(p.getName())) {
					PotionEffect potion = new PotionEffect(PotionEffectType.GLOWING, 600, 1);
					p.addPotionEffect(potion);
				}
			}
			Mission.missiontf.put("mission2", false);
			GameListeners.missionS = null;
			TosoCommand.world.getBlockAt(Mission.blockL).setType(Material.AIR);
		} else if (!Mission.isMission) {
			this.cancel();
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.sendTitle(ChatColor.BLUE + "ミッション成功！", Mission.CLEARp.getName() + "の活躍により発光は阻止された...", 20, 100, 20);
				p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 2);
			}
			Mission.missiontf.put("mission2", true);
			GameListeners.missionS = null;
			this.cancel();
		}
	}
}
