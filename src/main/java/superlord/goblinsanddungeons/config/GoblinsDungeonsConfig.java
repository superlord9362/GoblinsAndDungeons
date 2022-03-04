package superlord.goblinsanddungeons.config;

import net.minecraftforge.fml.config.ModConfig;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

public class GoblinsDungeonsConfig {
	
	public static int ogreSpawnWeight = 2;
	
	public static int largeGoblinCampMinDistance = 50;
	public static int largeGoblinCampMaxDistance = 100;
	public static int mediumGoblinCampMinDistance = 50;
	public static int mediumGoblinCampMaxDistance = 100;
	public static int smallGoblinCampMinDistance = 50;
	public static int smallGoblinCampMaxDistance = 100;
	public static int ruinedKeepMinDistance = 50;
	public static int ruinedKeepMaxDistance = 100;
	public static boolean magicalWorld = true;
	
	public static boolean superSecretSettings = false;
	
	public static void bakeClient(final ModConfig config) {
		
	}
	
	public static void bakeServer(final ModConfig config) {
		try {
			ogreSpawnWeight = GDConfigHolder.SERVER.ogreSpawnWeight.get();
			largeGoblinCampMinDistance = GDConfigHolder.SERVER.largeGoblinCampMinDistance.get();
			largeGoblinCampMaxDistance = GDConfigHolder.SERVER.largeGoblinCampMaxDistance.get();
			mediumGoblinCampMinDistance = GDConfigHolder.SERVER.mediumGoblinCampMinDistance.get();
			mediumGoblinCampMaxDistance = GDConfigHolder.SERVER.mediumGoblinCampMaxDistance.get();
			smallGoblinCampMinDistance = GDConfigHolder.SERVER.smallGoblinCampMinDistance.get();
			smallGoblinCampMaxDistance = GDConfigHolder.SERVER.smallGoblinCampMaxDistance.get();
			ruinedKeepMinDistance = GDConfigHolder.SERVER.ruinedKeepMinDistance.get();
			ruinedKeepMaxDistance = GDConfigHolder.SERVER.ruinedKeepMaxDistance.get();
			magicalWorld = GDConfigHolder.SERVER.magicalWorld.get();
			superSecretSettings = GDConfigHolder.SERVER.superSecretSettings.get();
		} catch (Exception e) {
			GoblinsAndDungeons.LOGGER.warn("An exception was caused trying to load the config for Goblins & Dungeons");
			e.printStackTrace();
		}
	}

}
