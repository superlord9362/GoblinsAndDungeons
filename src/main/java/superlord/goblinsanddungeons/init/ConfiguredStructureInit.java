package superlord.goblinsanddungeons.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import superlord.goblinsanddungeons.GoblinsAndDungeons;

public class ConfiguredStructureInit {
	
	public static StructureFeature<?, ?> CONFIGURED_SMALL_GOBLIN_CAMP = StructureInit.SMALL_GOBLIN_CAMP.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_MEDIUM_GOBLIN_CAMP = StructureInit.MEDIUM_GOBLIN_CAMP.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_LARGE_GOBLIN_CAMP = StructureInit.LARGE_GOBLIN_CAMP.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	public static StructureFeature<?, ?> CONFIGURED_RUINED_KEEP = StructureInit.RUINED_KEEP.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
	
	public static void registerConfiguredStructures() {
		Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(GoblinsAndDungeons.MOD_ID, "configured_small_goblin_camp"), CONFIGURED_SMALL_GOBLIN_CAMP);
		FlatGenerationSettings.STRUCTURES.put(StructureInit.SMALL_GOBLIN_CAMP.get(), CONFIGURED_SMALL_GOBLIN_CAMP);
		Registry.register(registry, new ResourceLocation(GoblinsAndDungeons.MOD_ID, "configured_medium_goblin_camp"), CONFIGURED_MEDIUM_GOBLIN_CAMP);
		FlatGenerationSettings.STRUCTURES.put(StructureInit.MEDIUM_GOBLIN_CAMP.get(), CONFIGURED_MEDIUM_GOBLIN_CAMP);
		Registry.register(registry, new ResourceLocation(GoblinsAndDungeons.MOD_ID, "configured_large_goblin_camp"), CONFIGURED_LARGE_GOBLIN_CAMP);
		FlatGenerationSettings.STRUCTURES.put(StructureInit.LARGE_GOBLIN_CAMP.get(), CONFIGURED_LARGE_GOBLIN_CAMP);
		Registry.register(registry, new ResourceLocation(GoblinsAndDungeons.MOD_ID, "configured_ruined_keep"), CONFIGURED_RUINED_KEEP);
		FlatGenerationSettings.STRUCTURES.put(StructureInit.RUINED_KEEP.get(), CONFIGURED_RUINED_KEEP);
	}

}
