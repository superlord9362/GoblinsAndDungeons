package superlord.goblinsanddungeons.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
	
	public final ForgeConfigSpec.IntValue ogreSpawnWeight;
	
	public final ForgeConfigSpec.IntValue largeGoblinCampMinDistance;
	public final ForgeConfigSpec.IntValue largeGoblinCampMaxDistance;
	public final ForgeConfigSpec.IntValue mediumGoblinCampMinDistance;
	public final ForgeConfigSpec.IntValue mediumGoblinCampMaxDistance;
	public final ForgeConfigSpec.IntValue smallGoblinCampMinDistance;
	public final ForgeConfigSpec.IntValue smallGoblinCampMaxDistance;
	public final ForgeConfigSpec.IntValue ruinedKeepMinDistance;
	public final ForgeConfigSpec.IntValue ruinedKeepMaxDistance;
	
	public final ForgeConfigSpec.BooleanValue superSecretSettings;
	
	public ServerConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		this.ogreSpawnWeight = buildInt(builder, "Ogre Spawn Weight", "all", 2, 1, 300, "The weight of Ogres in vanilla's spawn rate. Default is 2");
		this.largeGoblinCampMaxDistance = buildInt(builder, "Large Goblin Camp Max Distance", "all", 100, 1, 300, "The farthest two Large Goblin Camps can spawn from eachother. Keep this value higher than the min. Default is 100");
		this.largeGoblinCampMinDistance = buildInt(builder, "Large Goblin Camp Min Distance", "all", 50, 1, 300, "The closest two Large Goblin Camps can spawn from eachother. Keep this value lower than the max. Default is 50");
		this.mediumGoblinCampMaxDistance = buildInt(builder, "Medium Goblin Camp Max Distance", "all", 100, 1, 300, "The farthest two Medium Goblin Camps can spawn from eachother. Keep this value higher than the min. Default is 100");
		this.mediumGoblinCampMinDistance = buildInt(builder, "Medium Goblin Camp Min Distance", "all", 50, 1, 300, "The closest two Medium Goblin Camps can spawn from eachother. Keep this value lower than the max. Default is 50");
		this.smallGoblinCampMaxDistance = buildInt(builder, "Small Goblin Camp Max Distance", "all", 100, 1, 300, "The farthest two Small Goblin Camps can spawn from eachother. Keep this value higher than the min. Default is 100");
		this.smallGoblinCampMinDistance = buildInt(builder, "Small Goblin Camp Min Distance", "all", 50, 1, 300, "The closest two Small Goblin Camps can spawn from eachother. Keep this value lower than the max. Default is 50");
		this.ruinedKeepMaxDistance = buildInt(builder, "Ruined Keep Max Distance", "all", 100, 1, 300, "The farthest two Ruined Keeps can spawn from eachother. Keep this value higher than the min. Default is 100");
		this.ruinedKeepMinDistance = buildInt(builder, "Ruined Keep Min Distance", "all", 50, 1, 300, "The closest two Ruined Keeps can spawn from eachother. Keep this value lower than the max. Default is 50");
		this.superSecretSettings = buildBoolean(builder, "Super Secret Settings", "all", false, "Even I don't know what it does. Default is false");
	}
	
	private static ForgeConfigSpec.BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String name, String catagory, boolean defaultValue, String comment){
		return builder.comment(comment).translation(name).define(name, defaultValue);
	}

	private static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String name, String catagory, int defaultValue, int min, int max, String comment){
		return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
	}

}
