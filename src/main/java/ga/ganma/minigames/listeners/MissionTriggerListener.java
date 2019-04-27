package ga.ganma.minigames.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ga.ganma.minigames.MissionManager;
import ga.ganma.minigames.event.GameTimeChangedEvent;
import ga.ganma.minigames.missions.Mission;
import ga.ganma.minigames.missions.Mission1;

public class MissionTriggerListener implements Listener {

	@EventHandler
	public void triggerMission1(GameTimeChangedEvent e) {
		if (e.getCurrentGameTime() == 1200) {
			Mission1 mission = new Mission1();
			mission.startMission(MissionManager.getLocation(2));
			MissionManager.runMission(mission);
		}
	}

	@EventHandler
	public void triggerMission2And3(GameTimeChangedEvent e) {
		if (e.getCurrentGameTime() == 3000 || e.getCurrentGameTime() == 2100) {
			int num = 0;
			if (e.getCurrentGameTime() == 2100) {
				num = 1;
			}

			Mission mission = MissionManager.getMission(num);
			mission.startMission(MissionManager.getLocation(num));
			MissionManager.runMission(mission);
		}
	}
}
