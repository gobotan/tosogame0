package ga.ganma.minigames;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class GameSettingsManager {

	private static TosoNow plugin;
	private static YamlConfiguration conf;

	private static Location jailLoc = null;
	private static Location hunterBoxLoc = null;
	private static Location lobbyLoc = null;
	private static Location respawnLoc = null;
	private static Location mission1 = null;
	private static Location mission2 = null;
	private static Location mission3 = null;
	private static Location mission4 = null;

	public static void load(TosoNow plugin) {
		GameSettingsManager.plugin = plugin;

		conf = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
		jailLoc = getLocation(conf.getString(convertToConfigKey(KeyType.JAIL)));
		hunterBoxLoc = getLocation(conf.getString(convertToConfigKey(KeyType.HUNTER_BOX)));
		lobbyLoc = getLocation(conf.getString(convertToConfigKey(KeyType.LOBBY)));
		respawnLoc = getLocation(conf.getString(convertToConfigKey(KeyType.RESPAWN)));
		mission1 = getLocation(conf.getString(convertToConfigKey(KeyType.MISSION1)));
		mission2 = getLocation(conf.getString(convertToConfigKey(KeyType.MISSION2)));
		mission3 = getLocation(conf.getString(convertToConfigKey(KeyType.MISSION3)));
		mission4 = getLocation(conf.getString(convertToConfigKey(KeyType.MISSION4)));
	}

	public static void setLocation(KeyType type, Location loc) {

		if (GameManager.isRunningGame()) {
			throw new IllegalStateException(
					"The game is already running. Could not change location while game is running.");
		}

		if (type == KeyType.JAIL) {
			jailLoc = loc.clone();
		} else if (type == KeyType.HUNTER_BOX) {
			hunterBoxLoc = loc.clone();
		} else if (type == KeyType.LOBBY) {
			lobbyLoc = loc.clone();
		} else if (type == KeyType.RESPAWN) {
			respawnLoc = loc.clone();
		} else if (type == KeyType.MISSION1) {
			mission1 = loc.clone();
		} else if (type == KeyType.MISSION2) {
			mission2 = loc.clone();
		} else if (type == KeyType.MISSION3) {
			mission3 = loc.clone();
		} else if (type == KeyType.MISSION4) {
			mission4 = loc.clone();
		}

		conf.set(convertToConfigKey(type), toStringFromLocation(loc));
		try {
			conf.save(new File(plugin.getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Location getLocation(KeyType type) {
		if (type == KeyType.JAIL) {
			return jailLoc;
		} else if (type == KeyType.HUNTER_BOX) {
			return hunterBoxLoc;
		} else if (type == KeyType.LOBBY) {
			return lobbyLoc;
		} else if (type == KeyType.RESPAWN) {
			return respawnLoc;
		} else if (type == KeyType.MISSION1) {
			return mission1;
		} else if (type == KeyType.MISSION2) {
			return mission2;
		} else if (type == KeyType.MISSION3) {
			return mission3;
		} else if (type == KeyType.MISSION4) {
			return mission4;
		}

		return null;
	}

	public static boolean isAllComplete() {
		return jailLoc != null && hunterBoxLoc != null && lobbyLoc != null && respawnLoc != null && mission1 != null
				&& mission2 != null && mission3 != null && mission4 != null;
	}

	private static Location getLocation(String str) {
		if (str == null) {
			return null;
		}

		String[] split = str.split(",");
		Location loc = null;
		try {
			loc = new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]),
					Double.parseDouble(split[3]));
			loc.setYaw(Float.parseFloat(split[4]));
			loc.setPitch(Float.parseFloat(split[5]));
		} catch (Exception e) {
			// pass
		}

		return loc;
	}

	private static String toStringFromLocation(Location loc) {
		if (loc == null)
			return null;

		return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw()
				+ "," + loc.getPitch();
	}

	public enum KeyType {
		JAIL, HUNTER_BOX, LOBBY, RESPAWN, MISSION1, MISSION2, MISSION3, MISSION4;
	}

	private static String convertToConfigKey(KeyType type) {
		switch (type) {
		case JAIL:
			return "Jail";
		case HUNTER_BOX:
			return "HunterBox";
		case LOBBY:
			return "Lobby";
		case RESPAWN:
			return "ReSpawn";
		case MISSION1:
			return "Mission1";
		case MISSION2:
			return "Mission2";
		case MISSION3:
			return "Mission3";
		case MISSION4:
			return "Mission4";
		}

		return null;
	}
}
