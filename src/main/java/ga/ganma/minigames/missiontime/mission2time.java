package ga.ganma.minigames.missiontime;

import ga.ganma.minigames.Eventget;
import ga.ganma.minigames.Minigames;
import ga.ganma.minigames.mission;
import ga.ganma.minigames.tosoCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class mission2time extends BukkitRunnable {
    @Override
    public void run() {
        if (Minigames.gametime == mission.mission2t) {
            mission.ismission = false;
            this.cancel();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(ChatColor.RED + "ミッション失敗", "発光が30秒間ついてしまった！", 20, 100, 20);
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1, 2);
                PotionEffect potion = new PotionEffect(PotionEffectType.GLOWING, 600, 1);
                p.addPotionEffect(potion);
            }
            for (Entity e : tosoCommand.world.getEntities()) {
                if (e.getName().contains(mission.item1meta.getDisplayName())) {
                    e.remove();
                }
            }
            mission.missiontf.put("mission2", false);
            Eventget.missionS = null;
        }
        if (!mission.ismission) {
            this.cancel();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(ChatColor.BLUE + "ミッション成功！", null, 20, 100, 20);
                p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 2);
            }
            mission.missiontf.put("mission2", true);
            Eventget.missionS = null;
        }
    }
}
