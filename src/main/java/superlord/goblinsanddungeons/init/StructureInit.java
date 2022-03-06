package superlord.goblinsanddungeons.init;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import superlord.goblinsanddungeons.GoblinsAndDungeons;
import superlord.goblinsanddungeons.config.GoblinsDungeonsConfig;
import superlord.goblinsanddungeons.world.structures.LargeGoblinCampStructure;
import superlord.goblinsanddungeons.world.structures.LargeGoblinCampStructurePiece;
import superlord.goblinsanddungeons.world.structures.MediumGoblinCampStructure;
import superlord.goblinsanddungeons.world.structures.MediumGoblinCampStructurePiece;
import superlord.goblinsanddungeons.world.structures.RuinedKeepStructure;
import superlord.goblinsanddungeons.world.structures.RuinedKeepStructurePiece;
import superlord.goblinsanddungeons.world.structures.SmallGoblinCampStructure;
import superlord.goblinsanddungeons.world.structures.SmallGoblinCampStructurePiece;

@Mod.EventBusSubscriber(modid = GoblinsAndDungeons.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class StructureInit {

	public static final StructureFeature<NoneFeatureConfiguration> SMALL_GOBLIN_CAMP = new SmallGoblinCampStructure(NoneFeatureConfiguration.CODEC);
	public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> SMALL_GOBLIN_CAMP_FEATURE = configFeatureRegister(prefix("small_goblin_camp_feature"), SMALL_GOBLIN_CAMP.configured(NoneFeatureConfiguration.INSTANCE));
	public static final StructureFeature<NoneFeatureConfiguration> MEDIUM_GOBLIN_CAMP = new MediumGoblinCampStructure(NoneFeatureConfiguration.CODEC);
	public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> MEDIUM_GOBLIN_CAMP_FEATURE = configFeatureRegister(prefix("medium_goblin_camp_feature"), MEDIUM_GOBLIN_CAMP.configured(NoneFeatureConfiguration.INSTANCE));
	public static final StructureFeature<NoneFeatureConfiguration> LARGE_GOBLIN_CAMP = new LargeGoblinCampStructure(NoneFeatureConfiguration.CODEC);
	public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> LARGE_GOBLIN_CAMP_FEATURE = configFeatureRegister(prefix("large_goblin_camp_feature"), LARGE_GOBLIN_CAMP.configured(NoneFeatureConfiguration.INSTANCE));
	public static final StructureFeature<NoneFeatureConfiguration> RUINED_KEEP = new RuinedKeepStructure(NoneFeatureConfiguration.CODEC);
	public static final ConfiguredStructureFeature<NoneFeatureConfiguration, ? extends StructureFeature<NoneFeatureConfiguration>> RUINED_KEEP_FEATURE = configFeatureRegister(prefix("ruined_keep_feature"), RUINED_KEEP.configured(NoneFeatureConfiguration.INSTANCE));

	public static final StructurePieceType SMALL_GOBLIN_CAMP_STRUCTURE_PIECE = setPieceId(SmallGoblinCampStructurePiece.Piece::new, "SGCSP");
	public static final StructurePieceType MEDIUM_GOBLIN_CAMP_STRUCTURE_PIECE = setPieceId(MediumGoblinCampStructurePiece.Piece::new, "MGCSP");
	public static final StructurePieceType LARGE_GOBLIN_CAMP_STRUCTURE_PIECE = setPieceId(LargeGoblinCampStructurePiece.Piece::new, "LGCSP");
	public static final StructurePieceType RUINED_KEEP_STRUCTURE_PIECE = setPieceId(RuinedKeepStructurePiece.Piece::new, "RKSP");

	static StructurePieceType setPieceId(StructurePieceType.StructureTemplateType p_67164_, String p_67165_) {
		return Registry.register(Registry.STRUCTURE_PIECE, p_67165_.toLowerCase(Locale.ROOT), p_67164_);
	}
	
	@SubscribeEvent
	public static void registerfeature(RegistryEvent.Register<StructureFeature<?>> registry) {
		StructureFeature.STRUCTURES_REGISTRY.put("goblinsanddungeons:small_goblin_camp_feature", SMALL_GOBLIN_CAMP);
		StructureFeature.STRUCTURES_REGISTRY.put("goblinsanddungeons:medium_golbin_camp_feature", MEDIUM_GOBLIN_CAMP);
		StructureFeature.STRUCTURES_REGISTRY.put("goblinsanddungeons:large_golbin_camp_feature", LARGE_GOBLIN_CAMP);
		StructureFeature.STRUCTURES_REGISTRY.put("goblinsanddungeons:ruined_keep_feature", RUINED_KEEP);

		setupMapSpacingAndLand(SMALL_GOBLIN_CAMP, new StructureFeatureConfiguration(GoblinsDungeonsConfig.smallGoblinCampMaxDistance, GoblinsDungeonsConfig.smallGoblinCampMinDistance, 2538959), true);
		setupMapSpacingAndLand(MEDIUM_GOBLIN_CAMP, new StructureFeatureConfiguration(GoblinsDungeonsConfig.mediumGoblinCampMaxDistance, GoblinsDungeonsConfig.mediumGoblinCampMinDistance, 2895726), true);
		setupMapSpacingAndLand(LARGE_GOBLIN_CAMP, new StructureFeatureConfiguration(GoblinsDungeonsConfig.largeGoblinCampMaxDistance, GoblinsDungeonsConfig.largeGoblinCampMinDistance, 2716364), true);
		setupMapSpacingAndLand(RUINED_KEEP, new StructureFeatureConfiguration(GoblinsDungeonsConfig.ruinedKeepMaxDistance, GoblinsDungeonsConfig.ruinedKeepMinDistance, 2827153), true);

		registry.getRegistry().register(SMALL_GOBLIN_CAMP.setRegistryName("goblinsanddungeons:small_goblin_camp_feature"));
		registry.getRegistry().register(MEDIUM_GOBLIN_CAMP.setRegistryName("goblinsanddungeons:medium_goblin_camp_feature"));
		registry.getRegistry().register(LARGE_GOBLIN_CAMP.setRegistryName("goblinsanddungeons:large_goblin_camp_feature"));
		registry.getRegistry().register(RUINED_KEEP.setRegistryName("goblinsanddungeons:ruined_keep_feature"));
	}
	
	public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(
			F structure,
			StructureFeatureConfiguration structureFeatureConfiguration,
			boolean transformSurroundingLand) {
		if (transformSurroundingLand) {
			StructureFeature.NOISE_AFFECTING_FEATURES =
					ImmutableList.<StructureFeature<?>>builder()
							.addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
							.add(structure)
							.build();
		}
		StructureSettings.DEFAULTS =
				ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
						.putAll(StructureSettings.DEFAULTS)
						.put(structure, structureFeatureConfiguration)
						.build();

		BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
			Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

			/*
			 * Pre-caution in case a mod makes the structure map immutable like datapacks do.
			 * I take no chances myself. You never know what another mods does...
			 *
			 * structureConfig requires AccessTransformer (See resources/META-INF/accesstransformer.cfg)
			 */
			if (structureMap instanceof ImmutableMap) {
				Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
				tempMap.put(structure, structureFeatureConfiguration);
				settings.getValue().structureSettings().structureConfig = tempMap;
			} else {
				structureMap.put(structure, structureFeatureConfiguration);
			}
		});
	}

	private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> configFeatureRegister(String p_127268_, ConfiguredStructureFeature<FC, F> p_127269_) {
		return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, p_127268_, p_127269_);
	}

	private static String prefix(String path) {
		return "goblinsanddungeons:" + path;
	}

}
