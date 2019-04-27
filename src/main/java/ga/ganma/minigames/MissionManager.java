package ga.ganma.minigames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import ga.ganma.minigames.missions.Mission;
import net.md_5.bungee.api.ChatColor;

public class MissionManager {

	private static List<Mission> randomMissions = new ArrayList<>();
	private static List<Location> randomLocations = new ArrayList<>();
	private static Mission currentMission = null;

	public static void registerRandomMissions(Mission... missions) {
		randomMissions.clear();
		randomMissions.addAll(Arrays.asList(missions));
		Collections.shuffle(randomMissions);
	}

	public static void registerRandomLocations(Location... locs) {
		randomLocations.clear();
		randomLocations.addAll(Arrays.asList(locs));
		Collections.shuffle(randomLocations);
	}

	public static Mission getMission(int num) {
		if (randomMissions.size() >= num || num < 0) {
			return null;
		}
		return randomMissions.get(num);
	}

	public static Location getLocation(int num) {
		if (randomLocations.size() >= num || num < 0) {
			return null;
		}
		return randomLocations.get(num);
	}

	public static void runMission(Mission mission) {
		if (isRunningMission()) {
			throw new IllegalStateException("A mission is already running.");
		}
		currentMission = mission;
	}

	public static Mission getCurrentMission() {
		return currentMission;
	}

	public static boolean isRunningMission() {
		return getCurrentMission() != null;
	}

	public static void completeMission(boolean byTimeLimit, Player p) {
		if (!byTimeLimit) {
			currentMission.missionComplete(p);
		}
		currentMission = null;
	}

	public static String getMission3KeyTitle() {
		return ChatColor.RED + "鍵";
	}
}
